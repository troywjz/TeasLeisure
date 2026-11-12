<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>我的收藏</text><text></text></view>
    <view v-if="loading" class="state">正在加载收藏…</view>
    <view v-else-if="failed" class="state" @tap="load">加载失败，点击重试</view>
    <view v-else-if="!stalls.length" class="state empty">还没有收藏的摊位</view>
    <view v-for="stall in stalls" :key="stall.id" class="stall" @tap="open(stall)">
      <image v-if="stall.coverImage" class="cover" :src="stall.coverImage" mode="aspectFill" />
      <view v-else class="cover placeholder">摊</view>
      <view class="body"><text class="name">{{ stall.name }}</text><text class="address">{{ stallLocationLabel(stall) }}</text></view>
      <text class="arrow">›</text>
    </view>
    <TlTabBar active="profile" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onShow } from '@dcloudio/uni-app'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import request from '@/utils/request.js'
import TlTabBar from './components/TabBar.vue'
import { stallLocationLabel } from './utils/stall-location.js'

const store = useAppStore()
const stalls = ref([])
const loading = ref(false)
const failed = ref(false)

onShow(() => { if (!store.isLogin) toLogin(); else load() })
async function load() {
  loading.value = true; failed.value = false
  try { const result = await request.get('tl/favorites/stalls', { page: 1, limit: 100 }); stalls.value = result.data?.list || [] }
  catch (error) { failed.value = true; uni.showToast({ title: error.msg || '收藏加载失败', icon: 'none' }) }
  finally { loading.value = false }
}
function open(stall) { uni.navigateTo({ url: `/pages/tl/detail?id=${stall.id}` }) }
function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); }.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }.state { padding: 120rpx 45rpx; text-align: center; color: #777; font-size: 25rpx; }.empty { margin: 30rpx; background: #fff; border-radius: 16rpx; }.stall { margin: 20rpx 24rpx; padding: 20rpx; display: flex; align-items: center; gap: 20rpx; border-radius: 16rpx; background: #fff; }.cover { width: 130rpx; height: 116rpx; flex: none; border-radius: 12rpx; background: #ddd; }.placeholder { display: flex; align-items: center; justify-content: center; color: #777; }.body { min-width: 0; flex: 1; display: flex; flex-direction: column; gap: 10rpx; }.name { font-size: 27rpx; color: #222; font-weight: 600; }.address { font-size: 22rpx; color: #777; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }.arrow { color: #aaa; font-size: 40rpx; }
</style>
