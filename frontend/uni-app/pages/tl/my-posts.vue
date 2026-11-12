<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>我的动态</text><text></text></view>
    <view v-if="loading && !rows.length" class="state">正在读取动态…</view>
    <view v-else-if="failed && !rows.length" class="state" @tap="load(true)">加载失败，点击重试</view>
    <view v-else-if="!rows.length" class="state empty">还没有动态。认领摊位并通过审核后，可发布摊位动态。</view>
    <view v-for="post in rows" :key="post.id" class="post-card">
      <view class="post-content" @tap="open(post)">{{ post.content }}</view>
      <view v-if="images(post).length" class="image-row">
        <image v-for="(url, index) in images(post)" :key="index" :src="url" mode="aspectFill" @tap="preview(post, index)" />
      </view>
      <view v-if="post.stallName" class="stall-summary" @tap="openStall(post)">
        <image v-if="post.stallCoverImage" :src="post.stallCoverImage" mode="aspectFill" />
        <text class="stall-name">{{ post.stallName }}</text>
        <text class="stall-address">{{ post.stallAddress || '查看关联摊位' }}</text>
      </view>
      <view class="meta">
        <text class="status" :class="postStatusClass(post)">{{ postStatusText(post) }}</text>
        <text class="time">{{ formatDate(post.createTime) }}</text>
      </view>
      <text v-if="post.auditNote" class="audit-note">审核说明：{{ post.auditNote }}</text>
      <view class="actions">
        <text v-if="post.publiclyVisible" class="view-action" @tap="open(post)">查看动态</text>
        <text v-else-if="post.auditStatus === 1" class="visibility-note">已审核，关联摊位当前不公开</text>
        <text class="delete-action" @tap="deletePost(post)">删除</text>
      </view>
    </view>
    <view v-if="loading && rows.length" class="list-state">正在加载更多…</view>
    <view v-else-if="loadMoreFailed || (failed && rows.length)" class="list-state retry" @tap="load(retryReset)">加载失败，点击重试</view>
    <view v-else-if="rows.length && !hasMore" class="list-state">没有更多动态了</view>
    <TlTabBar active="profile" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onReachBottom, onShow } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import TlTabBar from './components/TabBar.vue'

const store = useAppStore()
const rows = ref([])
const page = ref(0)
const pageSize = 20
const totalPage = ref(0)
const hasMore = ref(true)
const loading = ref(false)
const failed = ref(false)
const loadMoreFailed = ref(false)
const retryReset = ref(false)

onShow(() => { if (!store.isLogin) toLogin(); else load(true) })
onReachBottom(() => { if (!loading.value && hasMore.value) load(false) })

async function load(reset = false) {
  if (loading.value || (!reset && !hasMore.value)) return
  const requestedPage = reset ? 1 : page.value + 1
  const previousPage = page.value
  const previousTotalPage = totalPage.value
  const previousHasMore = hasMore.value
  loading.value = true
  failed.value = false
  loadMoreFailed.value = false
  if (reset) { page.value = 0; totalPage.value = 0; hasMore.value = true }
  try {
    const response = await request.get('tl/community/me/posts', { page: requestedPage, limit: pageSize })
    const data = response.data || {}
    const nextRows = data.list || []
    rows.value = reset ? nextRows : rows.value.concat(nextRows)
    page.value = requestedPage
    totalPage.value = Number(data.totalPage || 0)
    hasMore.value = requestedPage < totalPage.value
  } catch (error) {
    failed.value = true
    retryReset.value = reset
    if (reset) {
      page.value = previousPage
      totalPage.value = previousTotalPage
      hasMore.value = previousHasMore
    } else loadMoreFailed.value = true
    uni.showToast({ title: error.msg || '动态加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function postStatusText(post) { return post.auditStatus === 1 && !post.publiclyVisible ? '已通过 · 当前不公开' : ({ 0: '审核中', 1: '已通过', 2: '未通过', 3: '已隐藏' })[post.auditStatus] || '状态待确认' }
function postStatusClass(post) { return post.auditStatus === 1 && !post.publiclyVisible ? 'muted' : post.auditStatus === 1 ? 'approved' : post.auditStatus === 0 ? 'pending' : 'muted' }
function images(post) { return (post.imageUrls || '').split(',').map(url => url.trim()).filter(Boolean) }
function formatDate(value) {
  if (!value) return '—'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return value
  const pad = number => String(number).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
}
function open(post) { if (post.publiclyVisible) uni.navigateTo({ url: `/pages/tl/post-detail?id=${post.id}` }) }
function openStall(post) { if (post.stallId) uni.navigateTo({ url: `/pages/tl/detail?id=${post.stallId}` }) }
function preview(post, index) { uni.previewImage({ current: images(post)[index], urls: images(post) }) }
function back() { uni.navigateBack({ delta: 1 }) }

function deletePost(post) {
  if (!store.isLogin) { toLogin(); return }
  uni.showModal({ title: '删除动态', content: '确认删除这条动态？', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/posts/${post.id}`)
      rows.value = rows.value.filter(item => item.id !== post.id)
      uni.showToast({ title: '已删除', icon: 'none' })
    } catch (error) { uni.showToast({ title: error.msg || '删除失败', icon: 'none' }) }
  } })
}
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); color: #222; }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }
.state { padding: 120rpx 40rpx; text-align: center; color: #777; font-size: 25rpx; line-height: 1.5; }.empty { margin: 24rpx; border-radius: 16rpx; background: #fff; }
.post-card { margin: 20rpx 24rpx; padding: 24rpx; border-radius: 16rpx; background: #fff; display: flex; flex-direction: column; gap: 16rpx; }.post-content { font-size: 27rpx; line-height: 1.55; white-space: pre-wrap; }.image-row { display: flex; flex-wrap: wrap; gap: 10rpx; }.image-row image { width: 190rpx; height: 190rpx; border-radius: 10rpx; }
.stall-summary { display: flex; align-items: center; gap: 12rpx; padding: 12rpx; border-radius: 10rpx; background: #f5f5f5; }.stall-summary image { width: 58rpx; height: 58rpx; border-radius: 8rpx; background: #eee; }.stall-name { font-size: 21rpx; font-weight: 600; }.stall-address { flex: 1; min-width: 0; color: #888; font-size: 19rpx; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }
.meta { display: flex; align-items: center; gap: 14rpx; }.status { font-size: 22rpx; }.approved { color: #39834a; }.pending { color: #b37a1e; }.muted { color: #999; }.time { margin-left: auto; color: #999; font-size: 20rpx; }.audit-note { color: #9b5c32; font-size: 22rpx; line-height: 1.5; }.actions { display: flex; justify-content: flex-end; gap: 28rpx; color: #666; font-size: 22rpx; }.view-action { color: #39834a; }.visibility-note { color: #999; font-size: 20rpx; }.delete-action { color: #b44; }
.list-state { padding: 24rpx; text-align: center; color: #888; font-size: 22rpx; }.retry { color: #a45c27; }
</style>
