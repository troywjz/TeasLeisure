<template>
  <view class="tabbar">
    <view class="tab" :class="{ active: active === 'home' }" @tap="open('index')">首页</view>
    <view class="tab" :class="{ active: active === 'map' }" @tap="open('map')">地图</view>
    <view class="tab publish" @tap="open('publish')"><text>＋</text></view>
    <view class="tab" :class="{ active: active === 'messages' }" @tap="open('messages')"><text>消息</text><text v-if="unreadCount" class="badge">{{ unreadCount > 99 ? '99+' : unreadCount }}</text></view>
    <view class="tab" :class="{ active: active === 'profile' }" @tap="open('profile')">我的</view>
  </view>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import request from '@/utils/request.js'
import { useAppStore } from '@/store/app.js'

defineProps({ active: { type: String, default: '' } })
const appStore = useAppStore()
const unreadCount = ref(0)

onMounted(async () => {
  if (!appStore.isLogin) return
  try { const result = await request.get('tl/conversations/unread-count'); unreadCount.value = Number(result.data || 0) }
  catch (e) { unreadCount.value = 0 }
})

function open(page) {
  const target = `/pages/tl/${page}`
  if (page === 'index') {
    uni.reLaunch({ url: target })
    return
  }
  uni.redirectTo({ url: target })
}
</script>

<style lang="scss" scoped>
.tabbar { position: fixed; z-index: 10; left: 0; right: 0; bottom: 0; height: 100rpx; padding-bottom: env(safe-area-inset-bottom); display: flex; align-items: center; justify-content: space-around; background: #fff; box-shadow: 0 -5rpx 18rpx rgba(0,0,0,.06); }
.tab { flex: 1; display: flex; justify-content: center; align-items: center; height: 100%; color: #222; font-size: 28rpx; }
.active { font-weight: 700; }
.tab:nth-child(4) { position: relative; gap: 8rpx; }.badge { min-width: 28rpx; height: 28rpx; line-height: 28rpx; padding: 0 6rpx; border-radius: 18rpx; background: #d74c3d; color: #fff; font-size: 17rpx; text-align: center; }
.publish text { width: 80rpx; height: 80rpx; line-height: 76rpx; text-align: center; border-radius: 50%; background: #dedede; color: #222; font-size: 58rpx; font-weight: 300; }
</style>
