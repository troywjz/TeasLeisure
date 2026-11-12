<template>
  <view class="page">
    <view class="nav-title">附近地摊</view>
    <view class="map-stage">
      <map v-if="hasCoordinates" class="map" :latitude="center.latitude" :longitude="center.longitude" :markers="markers" @markertap="selectMarker" />
      <view v-else class="map-empty">摊位位置正在完善，可先浏览附近列表</view>
      <view v-if="selectedStall" class="selected-card">
        <view class="selected-summary">
          <image v-if="selectedStall.coverImage" :src="selectedStall.coverImage" mode="aspectFill" class="selected-cover" />
          <view v-else class="selected-cover placeholder">摊</view>
          <view class="selected-copy"><text class="selected-name">{{ selectedStall.name }}</text><text class="selected-address">{{ stallLocationLabel(selectedStall) }}</text></view>
          <text class="selected-close" @tap="selectedStall = null">×</text>
        </view>
        <view class="selected-actions">
          <button class="action-button" @tap.stop="contactOwner(selectedStall)">发私信</button>
          <button class="action-button" @tap.stop="toggleFavorite(selectedStall)">{{ isFavorite(selectedStall) ? '已收藏' : '收藏' }}</button>
          <!-- #ifdef MP-WEIXIN -->
          <button class="action-button" open-type="share">转发</button>
          <!-- #endif -->
          <!-- #ifdef H5 -->
          <button class="action-button" @tap.stop="shareStall(selectedStall)">转发</button>
          <!-- #endif -->
          <button class="action-button primary" @tap.stop="openStall(selectedStall)">查看详情</button>
        </view>
      </view>
    </view>
    <scroll-view class="stall-list" scroll-y>
      <view v-if="loading && !stalls.length" class="state">正在加载地摊…</view>
      <view v-else-if="failed && !stalls.length" class="state" @tap="loadStalls">加载失败，点此重试</view>
      <view v-else-if="!stalls.length" class="state">附近还没有地摊信息</view>
      <view v-for="stall in stalls" :key="stall.id" class="stall-wrap">
        <view class="stall" :class="{ active: String(selectedStall?.id) === String(stall.id) }" @tap="selectStall(stall)">
          <image v-if="stall.coverImage" :src="stall.coverImage" mode="aspectFill" class="cover" />
          <view v-else class="cover placeholder">摊</view>
          <view class="copy"><text class="name">{{ stall.name }}</text><text class="address">{{ stallLocationLabel(stall) }}</text></view>
          <text class="arrow">›</text>
        </view>
        <view v-if="String(selectedStall?.id) === String(stall.id)" class="list-actions">
          <button class="action-button" @tap.stop="contactOwner(stall)">发私信</button>
          <button class="action-button" @tap.stop="toggleFavorite(stall)">{{ isFavorite(stall) ? '已收藏' : '收藏' }}</button>
          <!-- #ifdef MP-WEIXIN -->
          <button class="action-button" open-type="share">转发</button>
          <!-- #endif -->
          <!-- #ifdef H5 -->
          <button class="action-button" @tap.stop="shareStall(stall)">转发</button>
          <!-- #endif -->
          <button class="action-button primary" @tap.stop="openStall(stall)">查看详情</button>
        </view>
      </view>
    </scroll-view>
    <TlTabBar active="map" />
  </view>
</template>

<script setup>
import { computed, ref } from 'vue'
import { onLoad, onShareAppMessage } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import TlTabBar from './components/TabBar.vue'
import { stallLocationLabel } from './utils/stall-location.js'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'

const store = useAppStore()
const stalls = ref([])
const selectedStall = ref(null)
const favoriteIds = ref(new Set())
const loading = ref(false)
const failed = ref(false)
const center = ref({ latitude: 34.27, longitude: 108.95 })
const hasCoordinates = computed(() => stalls.value.some(item => item.latitude && item.longitude))
const markers = computed(() => stalls.value.filter(item => item.latitude && item.longitude).map(item => ({
  id: Number(item.id), latitude: Number(item.latitude), longitude: Number(item.longitude), title: item.name, width: 26, height: 32
})))

onLoad(() => {
  uni.getLocation({ type: 'gcj02', success: location => {
    center.value = { latitude: location.latitude, longitude: location.longitude }
    loadStalls(location.latitude, location.longitude)
  }, fail: () => loadStalls() })
})
onShareAppMessage(() => ({
  title: selectedStall.value?.name || '附近地摊 · TeasLeisure',
  path: selectedStall.value ? `/pages/tl/detail?id=${selectedStall.value.id}` : '/pages/tl/map'
}))

async function loadStalls(latitude, longitude) {
  loading.value = true
  failed.value = false
  try {
    const params = { page: 1, limit: 100 }
    if (latitude != null && longitude != null) Object.assign(params, { lat: latitude, lng: longitude, radiusMeters: 10000, sort: 'distance' })
    const result = await request.get('tl/stalls', params, { noAuth: true })
    stalls.value = result.data?.list || []
    if (selectedStall.value) selectedStall.value = stalls.value.find(item => String(item.id) === String(selectedStall.value.id)) || null
    await loadFavoriteStates()
    if ((latitude == null || longitude == null) && stalls.value.length) {
      const firstWithCoordinates = stalls.value.find(item => item.latitude && item.longitude)
      if (firstWithCoordinates) center.value = { latitude: Number(firstWithCoordinates.latitude), longitude: Number(firstWithCoordinates.longitude) }
    }
  } catch (error) {
    failed.value = true
    uni.showToast({ title: error.msg || '地摊列表加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function selectMarker(event) {
  const item = stalls.value.find(stall => Number(stall.id) === Number(event.detail.markerId))
  if (item) selectStall(item)
}

function selectStall(stall) { selectedStall.value = stall }
function openStall(stall) { uni.navigateTo({ url: `/pages/tl/detail?id=${stall.id}` }) }

async function loadFavoriteStates() {
  if (!store.token) { favoriteIds.value = new Set(); return }
  try {
    const result = await request.get('tl/favorites/stalls', { page: 1, limit: 1000 })
    favoriteIds.value = new Set((result.data?.list || []).map(item => String(item.id)))
  } catch (error) { favoriteIds.value = new Set() }
}

function isFavorite(stall) { return favoriteIds.value.has(String(stall.id)) }

async function contactOwner(stall) {
  if (!store.token) { toLogin(); return }
  try {
    const result = await request.post('tl/conversations', { stallId: Number(stall.id) }, {}, true)
    uni.navigateTo({ url: `/pages/tl/messages?conversationId=${result.data}` })
  } catch (error) { uni.showToast({ title: error.msg || '暂时无法联系摊主', icon: 'none' }) }
}

async function toggleFavorite(stall) {
  if (!store.token) { toLogin(); return }
  const stallId = String(stall.id)
  try {
    if (favoriteIds.value.has(stallId)) {
      await request.delete(`tl/favorites/stalls/${stall.id}`)
      const next = new Set(favoriteIds.value); next.delete(stallId); favoriteIds.value = next
      uni.showToast({ title: '已取消收藏', icon: 'none' })
    } else {
      await request.post('tl/favorites/stalls', { stallId: Number(stall.id) })
      favoriteIds.value = new Set([...favoriteIds.value, stallId])
      uni.showToast({ title: '已收藏', icon: 'none' })
    }
  } catch (error) { uni.showToast({ title: error.msg || '操作失败', icon: 'none' }) }
}

function shareStall(stall) {
  const route = `/pages/tl/detail?id=${stall.id}`
  const url = typeof window !== 'undefined' ? `${window.location.origin}${window.location.pathname}#${route}` : route
  uni.setClipboardData({ data: url, success: () => uni.showToast({ title: '摊位链接已复制', icon: 'none' }) })
}
</script>

<style lang="scss" scoped>
.page { height: 100vh; display: flex; flex-direction: column; background: #f0f0f0; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); box-sizing: border-box; }
.nav-title { height: 100rpx; flex: none; background: #e4e4e4; display: flex; align-items: center; justify-content: center; font-size: 30rpx; font-weight: 600; }
.map-stage { position: relative; height: 42vh; width: 100%; flex: none; }.map { height: 100%; width: 100%; }
.map-empty { height: 42vh; display: flex; align-items: center; justify-content: center; color: #777; background: #e3e3e3; font-size: 25rpx; }
.selected-card { position: absolute; left: 20rpx; right: 20rpx; bottom: 16rpx; padding: 18rpx; border-radius: 16rpx; background: #fff; box-shadow: 0 6rpx 24rpx #0002; }.selected-summary { display: flex; align-items: center; gap: 14rpx; }.selected-cover { width: 88rpx; height: 76rpx; flex: none; border-radius: 10rpx; background: #eee; }.selected-copy { display: flex; flex-direction: column; gap: 8rpx; min-width: 0; flex: 1; }.selected-name { font-size: 25rpx; font-weight: 600; color: #222; }.selected-address { font-size: 20rpx; color: #777; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }.selected-close { color: #888; padding: 8rpx; font-size: 34rpx; }.selected-actions { display: flex; align-items: center; gap: 8rpx; margin-top: 14rpx; }.action-button { flex: 1; min-width: 0; height: 56rpx; line-height: 56rpx; margin: 0; padding: 0 8rpx; border-radius: 28rpx; border: 1rpx solid #ddd; background: #fff; color: #444; font-size: 19rpx; }.action-button.primary { color: #fff; background: #333; border-color: #333; }
.stall-list { flex: 1; min-height: 0; background: #fff; }
.stall-wrap { border-bottom: 1rpx solid #eee; }.stall { display: flex; align-items: center; gap: 20rpx; padding: 20rpx 28rpx; }.stall.active { background: #f5f8f5; }.list-actions { display: flex; align-items: center; gap: 8rpx; padding: 0 18rpx 16rpx; }
.action-button { flex: 1; min-width: 0; height: 54rpx; line-height: 54rpx; margin: 0; padding: 0 8rpx; border-radius: 27rpx; border: 1rpx solid #ddd; background: #fff; color: #444; font-size: 19rpx; }.action-button.primary { color: #fff; background: #333; border-color: #333; }
.cover { width: 112rpx; height: 92rpx; border-radius: 12rpx; background: #ddd; flex: none; }
.placeholder { display: flex; align-items: center; justify-content: center; color: #777; }
.copy { min-width: 0; flex: 1; display: flex; flex-direction: column; gap: 10rpx; }
.name { font-size: 27rpx; font-weight: 600; color: #222; }
.address { font-size: 22rpx; color: #777; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }
.arrow { font-size: 40rpx; color: #aaa; }
.state { padding: 60rpx 20rpx; text-align: center; color: #777; }
</style>
