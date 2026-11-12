<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>管理摊位</text><text></text></view>
    <view v-if="loading" class="state">正在加载摊位…</view>
    <view v-else-if="failed" class="state" @tap="load">加载失败，点击重试</view>
    <view v-else-if="!stalls.length" class="state empty">还没有已认领摊位。先在摊位详情页申请认领，审核通过后可在这里管理。</view>
    <view v-if="!loading && !failed" class="history-link" @tap="openRevisions">查看我的资料修订记录 ›</view>
    <view v-for="stall in stalls" :key="stall.id" class="stall" @tap="open(stall)">
      <image v-if="stall.coverImage" class="cover" :src="stall.coverImage" mode="aspectFill" />
      <view v-else class="cover placeholder">摊</view>
      <view class="body">
        <text class="name">{{ stall.name }}</text>
        <text class="address">{{ stallLocationLabel(stall) }}</text>
        <text class="status" :class="statusClass(stall.auditStatus)">{{ statusText(stall.auditStatus) }}</text>
        <text class="today-status" :class="{ open: stall.openToday }">{{ todayStatusText(stall) }}</text>
        <button v-if="stall.auditStatus === 1" class="today-button" @tap.stop="chooseTodayStatus(stall)">更新今天状态</button>
        <button class="edit" @tap.stop="edit(stall)">编辑资料</button>
      </view>
      <text class="arrow">›</text>
    </view>
    <TlTabBar active="profile" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onShow } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import TlTabBar from './components/TabBar.vue'
import { stallLocationLabel } from './utils/stall-location.js'

const store = useAppStore()
const stalls = ref([])
const loading = ref(false)
const failed = ref(false)

onShow(() => { if (!store.isLogin) toLogin(); else load() })

async function load() {
  if (!store.isLogin) { toLogin(); return }
  loading.value = true; failed.value = false
  try {
    const response = await request.get('tl/me/stalls/managed', { page: 1, limit: 100 })
    stalls.value = response.data?.list || []
  } catch (error) {
    failed.value = true
    uni.showToast({ title: error.msg || '摊位加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function statusText(status) {
  return ({ 0: '待审核', 1: '已展示', 2: '审核未通过', 3: '已隐藏' })[status] || '状态待确认'
}
function statusClass(status) { return status === 1 ? 'published' : status === 0 ? 'pending' : 'muted' }
function todayStatusText(stall) {
  if (!stall.todayStatusKnown) return '今天状态：待更新'
  return stall.openToday ? `今天出摊${stall.todayHours ? ' · ' + stall.todayHours : ''}` : '今天休息'
}
function chooseTodayStatus(stall) {
  uni.showActionSheet({ itemList: ['今天出摊', '今天休息'], success: result => {
    if (result.tapIndex === 0) {
      uni.showModal({ title: '今天出摊', editable: true, placeholderText: '例如 17:00-22:00', success: answer => {
        if (!answer.confirm) return
        const hours = (answer.content || '').trim()
        if (!isValidHours(hours)) { uni.showToast({ title: '时段格式须为 HH:mm-HH:mm', icon: 'none' }); return }
        saveTodayStatus(stall, true, hours)
      } })
    } else saveTodayStatus(stall, false, '')
  } })
}
function isValidHours(value) {
  const match = /^([01]\d|2[0-3]):([0-5]\d)-([01]\d|2[0-3]):([0-5]\d)$/.exec(value)
  return !!match && `${match[1]}:${match[2]}` !== `${match[3]}:${match[4]}`
}
async function saveTodayStatus(stall, isOpen, hours) {
  try {
    await request.put(`tl/stalls/${stall.id}/today-status`, { isOpen, hours })
    stall.todayStatusKnown = true
    stall.openToday = isOpen
    stall.todayHours = hours || null
    uni.showToast({ title: '今天状态已更新', icon: 'none' })
  } catch (error) { uni.showToast({ title: error.msg || '状态更新失败', icon: 'none' }) }
}
function open(stall) { uni.navigateTo({ url: `/pages/tl/detail?id=${stall.id}` }) }
function edit(stall) { uni.navigateTo({ url: `/pages/tl/edit-stall?id=${stall.id}` }) }
function openRevisions() { uni.navigateTo({ url: '/pages/tl/revisions' }) }
function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }
.state { padding: 120rpx 45rpx; text-align: center; color: #777; font-size: 25rpx; line-height: 1.6; }.empty { margin: 30rpx; background: #fff; border-radius: 16rpx; }
.stall { margin: 20rpx 24rpx; padding: 20rpx; display: flex; align-items: center; gap: 20rpx; border-radius: 16rpx; background: #fff; }
.cover { width: 130rpx; height: 116rpx; flex: none; border-radius: 12rpx; background: #ddd; }.placeholder { display: flex; align-items: center; justify-content: center; color: #777; }
.body { min-width: 0; flex: 1; display: flex; flex-direction: column; gap: 10rpx; }.name { font-size: 27rpx; color: #222; font-weight: 600; }.address { font-size: 22rpx; color: #777; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }.status { font-size: 21rpx; }.published { color: #39834a; }.pending { color: #b37a1e; }.muted { color: #999; }.arrow { color: #aaa; font-size: 40rpx; }
.today-status { font-size: 21rpx; color: #888; }.today-status.open { color: #32834a; }
.today-button { align-self: flex-start; margin: 0; padding: 0 18rpx; min-height: 48rpx; line-height: 48rpx; border-radius: 24rpx; background: #ebf4ed; color: #32834a; font-size: 20rpx; }
.edit { align-self: flex-start; margin: 0; padding: 0 20rpx; min-height: 52rpx; line-height: 52rpx; border-radius: 26rpx; background: #f1f1f1; color: #444; font-size: 21rpx; }
.history-link { margin: 20rpx 24rpx; padding: 24rpx 28rpx; border-radius: 14rpx; background: #fff; color: #39834a; font-size: 24rpx; }
</style>
