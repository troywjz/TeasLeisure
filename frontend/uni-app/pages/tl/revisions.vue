<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>我的资料修订</text><text></text></view>
    <view v-if="loading && !rows.length" class="state">正在读取修订记录…</view>
    <view v-else-if="failed && !rows.length" class="state" @tap="load(true)">加载失败，点击重试</view>
    <view v-else-if="!rows.length" class="state empty">还没有资料修订申请。提交摊位资料变更后，可以在这里查看审核进度。</view>
    <view v-for="revision in rows" :key="revision.id" class="row" @tap="open(revision)">
      <image v-if="revision.coverImage" class="cover" :src="revision.coverImage" mode="aspectFill" />
      <view v-else class="cover placeholder">摊</view>
      <view class="body">
        <text class="name">{{ revision.stallName || '摊位资料暂不可见' }}</text>
        <text class="address">{{ stallLocationLabel(revision) }}</text>
        <text class="status" :class="statusClass(revision.status)">{{ statusText(revision.status) }}</text>
        <text v-if="revision.auditNote" class="note">审核说明：{{ revision.auditNote }}</text>
        <text class="time">提交时间：{{ formatDate(revision.createTime) }}</text>
        <text v-if="revision.auditTime" class="time">处理时间：{{ formatDate(revision.auditTime) }}</text>
      </view>
    </view>
    <view v-if="loading && rows.length" class="list-state">正在加载更多…</view>
    <view v-else-if="failed && rows.length" class="list-state retry" @tap="load(retryReset)">加载失败，点击重试</view>
    <view v-else-if="rows.length && !hasMore" class="list-state">没有更多修订记录了</view>
    <TlTabBar active="profile" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onReachBottom, onShow } from '@dcloudio/uni-app'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import request from '@/utils/request.js'
import TlTabBar from './components/TabBar.vue'
import { stallLocationLabel } from './utils/stall-location.js'

const store = useAppStore()
const rows = ref([])
const loading = ref(false)
const failed = ref(false)
const retryReset = ref(false)
const page = ref(0)
const pageSize = 20
const hasMore = ref(true)

onShow(() => { if (!store.isLogin) toLogin(); else load(true) })
onReachBottom(() => {
  if (!loading.value && hasMore.value) load(false)
})

async function load(reset = false) {
  if (loading.value || (!reset && !hasMore.value)) return
  const requestedPage = reset ? 1 : page.value + 1
  const previousPage = page.value
  const previousHasMore = hasMore.value
  failed.value = false
  loading.value = true
  if (reset) { page.value = 0; hasMore.value = true }
  try {
    const response = await request.get('tl/me/stalls/revisions', { page: requestedPage, limit: pageSize })
    const result = response.data || {}
    const nextRows = result.list || []
    rows.value = reset ? nextRows : rows.value.concat(nextRows)
    page.value = requestedPage
    hasMore.value = requestedPage < Number(result.totalPage || 0)
  } catch (error) {
    failed.value = true
    retryReset.value = reset
    if (reset) { page.value = previousPage; hasMore.value = previousHasMore }
    uni.showToast({ title: error.msg || '修订记录加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function statusText(status) { return ({ 0: '审核中', 1: '已通过', 2: '未通过' })[status] || '状态待确认' }
function statusClass(status) { return status === 1 ? 'approved' : status === 0 ? 'pending' : 'muted' }
function formatDate(value) {
  if (!value) return '—'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return value
  const pad = number => String(number).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
}
function open(revision) {
  if (revision.stallId && revision.stallName) uni.navigateTo({ url: `/pages/tl/detail?id=${revision.stallId}` })
}
function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }
.state { padding: 120rpx 45rpx; text-align: center; color: #777; font-size: 25rpx; line-height: 1.6; }.empty { margin: 30rpx; background: #fff; border-radius: 16rpx; }
.row { margin: 20rpx 24rpx; padding: 20rpx; display: flex; align-items: flex-start; gap: 20rpx; border-radius: 16rpx; background: #fff; }
.cover { width: 130rpx; height: 116rpx; flex: none; border-radius: 12rpx; background: #ddd; }.placeholder { display: flex; align-items: center; justify-content: center; color: #777; }
.body { min-width: 0; flex: 1; display: flex; flex-direction: column; gap: 8rpx; }.name { font-size: 27rpx; color: #222; font-weight: 600; }.address,.note,.time { font-size: 21rpx; color: #888; line-height: 1.5; }.status { font-size: 22rpx; }.approved { color: #39834a; }.pending { color: #b37a1e; }.muted { color: #999; }
.list-state { padding: 24rpx; text-align: center; color: #888; font-size: 22rpx; }.retry { color: #a45c27; }
</style>
