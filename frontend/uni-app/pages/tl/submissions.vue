<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>我的投稿</text><text></text></view>
    <view v-if="loading" class="state">正在读取投稿…</view>
    <view v-else-if="failed" class="state" @tap="load">加载失败，点击重试</view>
    <view v-else-if="!rows.length" class="state empty">还没有投稿记录。发现新摊位时，可以提交线索帮更多人找到它。</view>
    <view v-for="stall in rows" :key="stall.id" class="row" @tap="open(stall)">
      <image v-if="stall.coverImage" class="cover" :src="stall.coverImage" mode="aspectFill" />
      <view v-else class="cover placeholder">摊</view>
      <view class="body"><text class="name">{{ stall.name }}</text><text class="address">{{ stallLocationLabel(stall) }}</text><text class="status" :class="statusClass(stall.auditStatus)">{{ statusText(stall.auditStatus) }}</text><text v-if="stall.auditNote" class="note">审核说明：{{ stall.auditNote }}</text>
        <view v-if="stall.auditStatus === 1" class="next-step" @tap.stop>
          <button v-if="stall.claimedByMe" class="action manage" @tap="manageClaimed">管理摊位</button>
          <button v-else-if="stall.claimable" class="action claim" @tap="applyClaim(stall)">申请认领</button>
          <text v-else-if="!stall.claimedByMe" class="claimed-note">已由摊主认领</text>
        </view>
      </view>
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
const rows = ref([])
const loading = ref(false)
const failed = ref(false)

onShow(() => { if (!store.isLogin) toLogin(); else load() })
async function load() {
  loading.value = true; failed.value = false
  try { const response = await request.get('tl/me/stalls/submissions', { page: 1, limit: 100 }); rows.value = response.data?.list || [] }
  catch (error) { failed.value = true; uni.showToast({ title: error.msg || '投稿加载失败', icon: 'none' }) }
  finally { loading.value = false }
}
function statusText(status) { return ({ 0: '待审核', 1: '已发布', 2: '审核未通过', 3: '已隐藏' })[status] || '状态待确认' }
function statusClass(status) { return status === 1 ? 'published' : status === 0 ? 'pending' : 'muted' }
function open(stall) { if (stall.auditStatus === 1) uni.navigateTo({ url: `/pages/tl/detail?id=${stall.id}` }) }
function applyClaim(stall) { uni.navigateTo({ url: `/pages/tl/claim-apply?id=${stall.id}` }) }
function manageClaimed() { uni.navigateTo({ url: '/pages/tl/managed' }) }
function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); }.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }.state { padding: 120rpx 45rpx; text-align: center; color: #777; font-size: 25rpx; line-height: 1.6; }.empty { margin: 30rpx; background: #fff; border-radius: 16rpx; }.row { margin: 20rpx 24rpx; padding: 20rpx; display: flex; align-items: center; gap: 20rpx; border-radius: 16rpx; background: #fff; }.cover { width: 130rpx; height: 116rpx; flex: none; border-radius: 12rpx; background: #ddd; }.placeholder { display: flex; align-items: center; justify-content: center; color: #777; }.body { min-width: 0; flex: 1; display: flex; flex-direction: column; gap: 8rpx; }.name { font-size: 27rpx; color: #222; font-weight: 600; }.address,.note { font-size: 21rpx; color: #888; }.status { font-size: 22rpx; }.published { color: #39834a; }.pending { color: #b37a1e; }.muted { color: #999; }.next-step { display: flex; align-items: center; gap: 12rpx; margin-top: 4rpx; }.action { margin: 0; padding: 0 18rpx; min-height: 48rpx; line-height: 48rpx; border-radius: 24rpx; font-size: 21rpx; }.claim { color: #fff; background: #39834a; }.manage { color: #333; background: #eee; }.claimed-note { color: #999; font-size: 20rpx; }
</style>
