<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>我的评论</text><text></text></view>
    <view v-if="loading && !rows.length" class="state">正在读取评论…</view>
    <view v-else-if="failed && !rows.length" class="state" @tap="load(true)">加载失败，点击重试</view>
    <view v-else-if="!rows.length" class="state empty">还没有评论。发表评论后，可以在这里查看审核状态。</view>
    <view v-for="comment in rows" :key="comment.id" class="comment-card">
      <view class="comment-content">{{ comment.content }}</view>
      <view v-if="comment.parentId" class="parent-reference">回复评论 #{{ comment.parentId }}</view>
      <view v-if="comment.postPublished" class="post-summary" :class="{ unavailable: !comment.postPubliclyVisible }" @tap="openPost(comment)">
        <view class="post-copy"><text class="post-label">关联动态</text><text class="post-content">{{ comment.postContent || '查看关联动态' }}</text></view>
        <image v-if="postImages(comment).length" :src="postImages(comment)[0]" mode="aspectFill" />
        <text v-if="comment.stallName" class="stall-name">{{ comment.stallName }}</text>
      </view>
      <view v-else class="post-unavailable">关联动态暂不可见</view>
      <view v-if="comment.postPublished && !comment.postPubliclyVisible" class="post-unavailable">动态已审核，但关联摊位当前不公开</view>
      <view class="meta">
        <text class="status" :class="statusClass(comment.auditStatus)">{{ statusText(comment.auditStatus) }}</text>
        <text class="time">{{ formatDate(comment.createTime) }}</text>
      </view>
      <text v-if="comment.auditNote" class="audit-note">审核说明：{{ comment.auditNote }}</text>
      <view class="actions"><text v-if="comment.postPubliclyVisible" class="view-action" @tap="openPost(comment)">查看动态</text><text class="delete-action" @tap="deleteComment(comment)">删除</text></view>
    </view>
    <view v-if="loading && rows.length" class="list-state">正在加载更多…</view>
    <view v-else-if="loadMoreFailed || (failed && rows.length)" class="list-state retry" @tap="load(retryReset)">加载失败，点击重试</view>
    <view v-else-if="rows.length && !hasMore" class="list-state">没有更多评论了</view>
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
  const previousHasMore = hasMore.value
  loading.value = true
  failed.value = false
  loadMoreFailed.value = false
  if (reset) { page.value = 0; hasMore.value = true }
  try {
    const response = await request.get('tl/community/me/comments', { page: requestedPage, limit: pageSize })
    const data = response.data || {}
    const nextRows = data.list || []
    rows.value = reset ? nextRows : rows.value.concat(nextRows)
    page.value = requestedPage
    hasMore.value = requestedPage < Number(data.totalPage || 0)
  } catch (error) {
    failed.value = true
    retryReset.value = reset
    if (reset) { page.value = previousPage; hasMore.value = previousHasMore }
    else loadMoreFailed.value = true
    uni.showToast({ title: error.msg || '评论加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function statusText(status) { return ({ 0: '审核中', 1: '已通过', 2: '未通过', 3: '已隐藏' })[status] || '状态待确认' }
function statusClass(status) { return status === 1 ? 'approved' : status === 0 ? 'pending' : 'muted' }
function postImages(comment) { return (comment.postImageUrls || '').split(',').map(url => url.trim()).filter(Boolean) }
function formatDate(value) {
  if (!value) return '—'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return value
  const pad = number => String(number).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
}
function openPost(comment) { if (comment.postPubliclyVisible) uni.navigateTo({ url: `/pages/tl/post-detail?id=${comment.postId}` }) }
function back() { uni.navigateBack({ delta: 1 }) }

function deleteComment(comment) {
  if (!store.isLogin) { toLogin(); return }
  uni.showModal({ title: '删除评论', content: '确认删除这条评论？', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/comments/${comment.id}`)
      rows.value = rows.value.filter(item => item.id !== comment.id)
      uni.showToast({ title: '已删除', icon: 'none' })
    } catch (error) { uni.showToast({ title: error.msg || '删除失败', icon: 'none' }) }
  } })
}
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); color: #222; }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }
.state { padding: 120rpx 40rpx; text-align: center; color: #777; font-size: 25rpx; line-height: 1.5; }.empty { margin: 24rpx; border-radius: 16rpx; background: #fff; }
.comment-card { margin: 20rpx 24rpx; padding: 24rpx; border-radius: 16rpx; background: #fff; display: flex; flex-direction: column; gap: 14rpx; }.comment-content { font-size: 26rpx; line-height: 1.55; white-space: pre-wrap; }.parent-reference { color: #888; font-size: 20rpx; }
.post-summary { display: flex; align-items: center; gap: 12rpx; padding: 14rpx; border-radius: 10rpx; background: #f5f5f5; }.post-summary.unavailable { opacity: .7; }.post-copy { flex: 1; min-width: 0; display: flex; flex-direction: column; gap: 6rpx; }.post-label { color: #777; font-size: 19rpx; }.post-content { font-size: 21rpx; color: #444; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }.post-summary image { width: 66rpx; height: 66rpx; border-radius: 8rpx; background: #eee; }.stall-name { color: #888; font-size: 19rpx; }.post-unavailable { padding: 14rpx; color: #999; background: #f7f7f7; border-radius: 10rpx; font-size: 20rpx; }
.meta { display: flex; align-items: center; gap: 14rpx; }.status { font-size: 22rpx; }.approved { color: #39834a; }.pending { color: #b37a1e; }.muted { color: #999; }.time { margin-left: auto; color: #999; font-size: 20rpx; }.audit-note { color: #9b5c32; font-size: 22rpx; line-height: 1.5; }.actions { display: flex; justify-content: flex-end; gap: 28rpx; color: #666; font-size: 22rpx; }.view-action { color: #39834a; }.delete-action { color: #b44; }
.list-state { padding: 24rpx; text-align: center; color: #888; font-size: 22rpx; }.retry { color: #a45c27; }
</style>
