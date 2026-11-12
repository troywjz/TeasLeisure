<template>
  <view class="page">
    <view class="nav-title">首页</view>

    <view class="search-bar">
      <input v-model="searchDraft" class="search-input" maxlength="120" placeholder="搜索摊位名称或介绍" confirm-type="search" @confirm="submitSearch" />
      <text v-if="searchDraft || selectedKeyword" class="clear-search" @tap="clearSearch">清除</text>
      <button class="search-button" @tap="submitSearch">搜索</button>
    </view>

    <scroll-view v-if="categories.length" class="category-bar" scroll-x show-scrollbar="false">
      <view class="category-list">
        <text class="category-chip" :class="{ selected: selectedCategoryId === null }" @tap="selectCategory(null)">全部</text>
        <text v-for="category in categories" :key="category.id" class="category-chip" :class="{ selected: selectedCategoryId === category.id }" @tap="selectCategory(category.id)">{{ category.name }}</text>
      </view>
    </scroll-view>
    <view class="region-filter">
      <RegionPicker :city-code="selectedCityCode" :district-code="selectedDistrictCode" @change="selectRegion" />
    </view>
    <view class="status-filters">
      <view class="today-filter" :class="{ selected: todayOpenFilter }" @tap="toggleTodayOpen">
        <text>{{ todayOpenFilter ? '✓ 今天出摊' : '筛选今天出摊' }}</text>
      </view>
      <view class="today-filter now-filter" :class="{ selected: openNowFilter }" @tap="toggleOpenNow">
        <text>{{ openNowFilter ? '✓ 现在营业' : '现在营业' }}</text>
      </view>
    </view>

    <view class="feed">
      <view v-if="loading && !posts.length && !stalls.length" class="state">正在发现地摊动态…</view>
      <view v-else-if="failed && !posts.length && !stalls.length" class="state" @tap="loadFeed(true)">加载失败，点此重试</view>
      <view v-else-if="!posts.length && !stalls.length" class="state">{{ selectedKeyword ? '没有找到匹配的摊位' : '暂时还没有内容，来提交第一条逛摊记录吧' }}</view>

      <view v-if="feedMode === 'posts' && posts.length" class="grid">
        <view v-for="post in posts" :key="`post-${post.id}`" class="card" @tap="openPost(post)">
          <image v-if="coverFor(post)" class="cover" :src="coverFor(post)" mode="aspectFill" />
          <view v-else class="cover placeholder"></view>
          <view class="card-body">
            <text class="description">{{ post.content }}</text>
            <view class="meta">
              <image v-if="post.authorAvatar" class="avatar-image" :src="post.authorAvatar" mode="aspectFill" />
              <text v-else class="avatar">摊</text>
              <text class="author">{{ post.authorName || '茶余用户' }}</text>
            </view>
            <text class="location">{{ post.stallAddress || post.stallName || '逛摊记录' }}</text>
          </view>
        </view>
      </view>

      <view v-else-if="feedMode === 'stalls' && stalls.length" class="grid">
        <view v-for="stall in stalls" :key="stall.id" class="card" @tap="openStall(stall)">
          <image v-if="stall.coverImage" class="cover" :src="stall.coverImage" mode="aspectFill" />
          <view v-else class="cover placeholder"></view>
          <view class="card-body">
            <text class="name">{{ stall.name }}</text>
            <text class="description">{{ stall.description || '看看摊主今天带来了什么' }}</text>
            <text v-if="stall.todayStatusKnown" class="today-status">{{ stall.openToday ? `今天出摊${stall.todayHours ? ' · ' + stall.todayHours : ''}` : '今天休息' }}</text>
            <text v-if="stall.currentOpen === true" class="current-status open-now">现在营业</text>
            <text v-else-if="stall.currentOpen === false" class="current-status closed-now">现在休息</text>
            <view class="meta">
              <text class="avatar">摊</text>
              <text class="location">{{ stallLocationLabel(stall) }}</text>
            </view>
          </view>
        </view>
      </view>

      <view v-if="(posts.length || stalls.length) && loading" class="more">正在加载…</view>
      <view v-else-if="(posts.length || stalls.length) && !hasMore" class="more">已经到底啦</view>
    </view>

    <TlTabBar active="home" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad, onReachBottom, onPullDownRefresh } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import TlTabBar from './components/TabBar.vue'
import RegionPicker from './components/RegionPicker.vue'
import { stallLocationLabel } from './utils/stall-location.js'

const posts = ref([])
const stalls = ref([])
const feedMode = ref('posts')
const categories = ref([])
const selectedCategoryId = ref(null)
const todayOpenFilter = ref(false)
const openNowFilter = ref(false)
const selectedCityCode = ref('')
const selectedDistrictCode = ref('')
const searchDraft = ref('')
const selectedKeyword = ref('')
const loading = ref(false)
const failed = ref(false)
const reloadRequested = ref(false)
const page = ref(1)
const hasMore = ref(true)
const pageSize = 20

onLoad(() => { loadCategories(); loadFeed(true) })
onReachBottom(() => loadFeed())
onPullDownRefresh(async () => {
  await loadFeed(true)
  uni.stopPullDownRefresh()
})

async function loadFeed(reset = false) {
  if (loading.value) {
    if (reset) reloadRequested.value = true
    return
  }
  if (!reset && !hasMore.value) return
  if (reset) {
    page.value = 1
    hasMore.value = true
    failed.value = false
    posts.value = []
    stalls.value = []
    feedMode.value = todayOpenFilter.value || openNowFilter.value || selectedCityCode.value || selectedKeyword.value ? 'stalls' : 'posts'
  }
  loading.value = true
  try {
    if (feedMode.value === 'posts') {
      const params = {
      page: page.value,
      limit: pageSize
      }
      if (selectedCategoryId.value != null) params.categoryId = selectedCategoryId.value
      const result = await request.get('tl/community/posts', params, { noAuth: true })
      const data = result.data || {}
      const nextItems = data.list || []
      if (reset && !nextItems.length) {
        feedMode.value = 'stalls'
        page.value = 1
        const fallbackParams = { page: 1, limit: pageSize }
        if (selectedCategoryId.value != null) fallbackParams.categoryId = selectedCategoryId.value
        if (selectedKeyword.value) fallbackParams.keyword = selectedKeyword.value
        if (selectedCityCode.value) fallbackParams.cityCode = selectedCityCode.value
        if (selectedDistrictCode.value) fallbackParams.districtCode = selectedDistrictCode.value
        if (todayOpenFilter.value) fallbackParams.todayOpen = true
        if (openNowFilter.value) fallbackParams.openNow = true
        const fallback = await request.get('tl/stalls', fallbackParams, { noAuth: true })
        const stallData = fallback.data || {}
        stalls.value = stallData.list || []
        hasMore.value = page.value < (stallData.totalPage || 0)
      } else {
        posts.value = reset ? nextItems : posts.value.concat(nextItems)
        hasMore.value = page.value < (data.totalPage || 0)
      }
    } else {
      const params = { page: page.value, limit: pageSize }
      if (selectedCategoryId.value != null) params.categoryId = selectedCategoryId.value
      if (selectedKeyword.value) params.keyword = selectedKeyword.value
      if (selectedCityCode.value) params.cityCode = selectedCityCode.value
      if (selectedDistrictCode.value) params.districtCode = selectedDistrictCode.value
      if (todayOpenFilter.value) params.todayOpen = true
      if (openNowFilter.value) params.openNow = true
      const result = await request.get('tl/stalls', params, { noAuth: true })
      const data = result.data || {}
      stalls.value = reset ? (data.list || []) : stalls.value.concat(data.list || [])
      hasMore.value = page.value < (data.totalPage || 0)
    }
    page.value += 1
  } catch (error) {
    failed.value = true
    uni.showToast({ title: error.msg || '加载失败，请稍后重试', icon: 'none' })
  } finally {
    loading.value = false
    if (reloadRequested.value) {
      reloadRequested.value = false
      await loadFeed(true)
    }
  }
}

async function loadCategories() {
  try { const result = await request.get('tl/categories', {}, { noAuth: true }); categories.value = result.data || [] }
  catch (error) { categories.value = [] }
}

function selectCategory(categoryId) {
  if (selectedCategoryId.value === categoryId) return
  selectedCategoryId.value = categoryId
  feedMode.value = todayOpenFilter.value || openNowFilter.value || selectedCityCode.value || selectedKeyword.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function toggleTodayOpen() {
  todayOpenFilter.value = !todayOpenFilter.value
  feedMode.value = todayOpenFilter.value || openNowFilter.value || selectedCityCode.value || selectedKeyword.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function toggleOpenNow() {
  openNowFilter.value = !openNowFilter.value
  feedMode.value = todayOpenFilter.value || openNowFilter.value || selectedCityCode.value || selectedKeyword.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function selectRegion(region) {
  selectedCityCode.value = region.cityCode
  selectedDistrictCode.value = region.districtCode
  feedMode.value = region.cityCode || todayOpenFilter.value || openNowFilter.value || selectedKeyword.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function submitSearch() {
  selectedKeyword.value = searchDraft.value.trim().slice(0, 120)
  feedMode.value = selectedKeyword.value || selectedCityCode.value || todayOpenFilter.value || openNowFilter.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function clearSearch() {
  searchDraft.value = ''
  selectedKeyword.value = ''
  feedMode.value = selectedCityCode.value || todayOpenFilter.value || openNowFilter.value ? 'stalls' : 'posts'
  loadFeed(true)
}

function openStall(stall) {
  uni.navigateTo({ url: `/pages/tl/detail?id=${stall.id}` })
}

function coverFor(post) { return (post.imageUrls || '').split(',').filter(Boolean)[0] || post.stallCoverImage || '' }
function openPost(post) { uni.navigateTo({ url: `/pages/tl/post-detail?id=${post.id}` }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f0f0f0; padding-bottom: 112rpx; box-sizing: border-box; }
.nav-title { height: 100rpx; background: #e4e4e4; display: flex; align-items: center; justify-content: center; color: #111; font-size: 30rpx; font-weight: 600; }
.search-bar { display: flex; align-items: center; gap: 12rpx; margin: 18rpx 30rpx 0; padding: 10rpx 14rpx 10rpx 22rpx; border-radius: 36rpx; background: #fff; }.search-input { flex: 1; min-width: 0; height: 58rpx; font-size: 24rpx; }.clear-search { color: #888; font-size: 22rpx; }.search-button { margin: 0; padding: 0 24rpx; height: 58rpx; line-height: 58rpx; border-radius: 30rpx; background: #222; color: #fff; font-size: 22rpx; }
.feed { padding: 40rpx 30rpx 24rpx; }
.category-bar { width: 100%; white-space: nowrap; background: #f0f0f0; }.category-list { display: inline-flex; align-items: center; gap: 14rpx; padding: 18rpx 30rpx 0; }.category-chip { flex: none; padding: 12rpx 22rpx; border-radius: 28rpx; background: #fff; color: #555; font-size: 22rpx; }.category-chip.selected { background: #222; color: #fff; }
.region-filter { margin: 16rpx 30rpx 0; }
.status-filters { display: flex; flex-wrap: wrap; gap: 12rpx; margin: 16rpx 30rpx 0; }.today-filter { display: inline-flex; padding: 12rpx 20rpx; border: 1rpx solid #ccc; border-radius: 28rpx; color: #555; font-size: 22rpx; }.today-filter.selected { color: #fff; border-color: #32834a; background: #32834a; }.now-filter.selected { border-color: #9b6430; background: #9b6430; }.today-status { color: #32834a; font-size: 19rpx; }.current-status { font-size: 19rpx; }.open-now { color: #257b3e; }.closed-now { color: #888; }
.grid { display: flex; flex-wrap: wrap; justify-content: space-between; row-gap: 20rpx; }
.card { width: 330rpx; min-height: 310rpx; background: #fff; border-radius: 20rpx; overflow: hidden; }
.cover { display: block; width: 330rpx; height: 200rpx; background: #d9d9d9; }
.placeholder { background: linear-gradient(140deg, #e2ddd4, #c9c4bb); }
.card-body { padding: 8rpx 14rpx 12rpx; display: flex; flex-direction: column; gap: 8rpx; }
.name { color: #171717; font-size: 23rpx; font-weight: 600; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }
.description { color: #333; font-size: 20rpx; line-height: 1.35; height: 52rpx; overflow: hidden; }
.meta { display: flex; align-items: center; gap: 8rpx; min-width: 0; }
.avatar { flex: none; width: 28rpx; height: 28rpx; line-height: 28rpx; text-align: center; border: 1rpx solid #888; border-radius: 50%; color: #555; font-size: 17rpx; }
.avatar-image { flex: none; width: 30rpx; height: 30rpx; border-radius: 50%; }
.author { color: #555; font-size: 18rpx; }
.location { color: #666; font-size: 17rpx; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }
.state { min-height: 55vh; display: flex; align-items: center; justify-content: center; color: #666; font-size: 26rpx; }
.more { text-align: center; color: #777; font-size: 22rpx; padding: 32rpx 0 20rpx; }
</style>
