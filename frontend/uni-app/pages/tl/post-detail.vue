<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>逛摊记录</text><!-- #ifdef MP-WEIXIN --><button class="share-button" open-type="share">转发</button><!-- #endif --><!-- #ifndef MP-WEIXIN --><text @tap="share">分享</text><!-- #endif --></view>
    <view v-if="loading" class="state">正在加载内容…</view>
    <view v-else-if="failed" class="state" @tap="load">加载失败，点击重试</view>
    <template v-else-if="post">
      <view class="post-card">
        <view class="author"><image v-if="post.authorAvatar" :src="post.authorAvatar" mode="aspectFill" /><text v-else class="avatar">茶</text><text>{{ post.authorName || '茶余用户' }}</text><text class="time">{{ post.createTime }}</text></view>
        <text class="content">{{ post.content }}</text>
        <view v-if="images.length" class="images"><image v-for="(image,index) in images" :key="index" :src="image" mode="aspectFill" @tap="preview(index)" /></view>
        <view v-if="post.stallId" class="stall-link" @tap="openStall"><text>{{ post.stallName || '关联摊位' }}</text><text>{{ post.stallAddress || '查看摊位详情' }}</text><text>›</text></view>
        <view class="actions"><text @tap="toggleLike">{{ post.liked ? '♥' : '♡' }} {{ post.likeCount || 0 }}</text><text>{{ comments.length }} 条评论</text><text v-if="post.mine" class="delete-action" @tap="deletePost">删除动态</text></view>
      </view>
      <view class="comment-head"><text>评论</text><text class="report-action" @tap="reportContent('post', post.id)">举报动态</text></view>
      <view v-if="!comments.length" class="empty">还没有评论，来分享你的发现吧</view>
      <view v-for="comment in comments" :key="comment.id" class="comment" :class="{ reply: comment.parentId }">
        <view class="comment-author"><image v-if="comment.authorAvatar" :src="comment.authorAvatar" mode="aspectFill" /><text v-else class="comment-avatar">茶</text><text>{{ comment.authorName || '茶余用户' }}</text></view>
        <text v-if="comment.parentId" class="reply-to">回复 {{ parentAuthor(comment.parentId) }}</text>
        <text>{{ comment.content }}</text>
        <view class="comment-foot"><text class="time">{{ comment.createTime }}</text><text class="report-action" @tap="startReply(comment)">回复</text><text class="report-action" @tap="reportContent('comment', comment.id)">举报</text><text v-if="comment.mine" class="delete-action" @tap="deleteComment(comment)">删除</text></view>
      </view>
      <view class="composer"><text v-if="replyTarget" class="replying" @tap="replyTarget = null">回复 {{ replyTarget.authorName || '茶余用户' }} ×</text><input v-model="draft" maxlength="1000" :placeholder="replyTarget ? `回复 ${replyTarget.authorName || '茶余用户'}` : '友善交流，分享你的发现'" confirm-type="send" @confirm="sendComment" /><button @tap="sendComment">发送</button></view>
    </template>
  </view>
</template>

<script setup>
import { computed, ref } from 'vue'
import { onLoad, onShareAppMessage } from '@dcloudio/uni-app'
import { useAppStore } from '@/store/app.js'
import request from '@/utils/request.js'

const store = useAppStore()
const postId = ref('')
const post = ref(null)
const comments = ref([])
const draft = ref('')
const replyTarget = ref(null)
const loading = ref(false)
const failed = ref(false)
const images = computed(() => (post.value?.imageUrls || '').split(',').filter(Boolean))

onLoad(options => { postId.value = options?.id || ''; load() })
onShareAppMessage(() => ({ title: post.value?.content || 'TeasLeisure 逛摊记录', path: `/pages/tl/post-detail?id=${postId.value}` }))

async function load() {
  if (!postId.value) { failed.value = true; return }
  loading.value = true; failed.value = false
  try {
    const [postResult, commentResult] = await Promise.all([
      request.get(`tl/community/posts/${postId.value}`, {}, { noAuth: true }),
      request.get(`tl/community/posts/${postId.value}/comments`, { page: 1, limit: 50 }, { noAuth: true })
    ])
    post.value = postResult.data
    comments.value = commentResult.data?.list || []
  } catch (error) { failed.value = true; uni.showToast({ title: error.msg || '内容加载失败', icon: 'none' }) }
  finally { loading.value = false }
}

async function toggleLike() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以点赞', icon: 'none' }); return }
  const active = !post.value.liked
  try {
    await request.post(`tl/community/posts/${postId.value}/like?active=${active}`, {})
    post.value.liked = active
    post.value.likeCount = Math.max(0, Number(post.value.likeCount || 0) + (active ? 1 : -1))
  } catch (error) { uni.showToast({ title: error.msg || '操作失败', icon: 'none' }) }
}

async function sendComment() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以评论', icon: 'none' }); return }
  const content = draft.value.trim()
  if (!content) return
  try {
    await request.post(`tl/community/posts/${postId.value}/comments`, { content, parentId: replyTarget.value?.id || null })
    draft.value = ''
    replyTarget.value = null
    uni.showModal({
      title: '评论已提交审核',
      content: '可在“我的评论”查看审核进度和处理说明。',
      confirmText: '查看状态',
      cancelText: '继续浏览',
      success: result => { if (result.confirm) uni.navigateTo({ url: '/pages/tl/my-comments' }) }
    })
  } catch (error) { uni.showToast({ title: error.msg || '评论失败', icon: 'none' }) }
}

function deletePost() {
  if (!store.isLogin || !post.value?.mine) return
  uni.showModal({ title: '删除动态', content: '删除后，这条动态会从公开页面移除。', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/posts/${postId.value}`)
      uni.showToast({ title: '动态已删除', icon: 'none' })
      setTimeout(() => uni.navigateBack({ delta: 1 }), 300)
    } catch (error) { uni.showToast({ title: error.msg || '删除失败', icon: 'none' }) }
  } })
}

function deleteComment(comment) {
  if (!store.isLogin || !comment.mine) return
  uni.showModal({ title: '删除评论', content: '确认删除这条评论？', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/comments/${comment.id}`)
      comments.value = comments.value.filter(item => item.id !== comment.id)
      if (replyTarget.value?.id === comment.id) replyTarget.value = null
      uni.showToast({ title: '评论已删除', icon: 'none' })
    } catch (error) { uni.showToast({ title: error.msg || '删除失败', icon: 'none' }) }
  } })
}

function startReply(comment) { replyTarget.value = comment }
function parentAuthor(parentId) { return comments.value.find(item => item.id === parentId)?.authorName || '评论作者' }

async function reportContent(targetType, targetId) {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以举报', icon: 'none' }); return }
  uni.showModal({ title: '举报内容', editable: true, placeholderText: '请简述原因', success: async result => {
    const reason = result.confirm ? result.content?.trim() : ''
    if (!reason) return
    try { await request.post('tl/content-reports', { targetType, targetId, reason }); uni.showToast({ title: '举报已提交', icon: 'none' }) }
    catch (error) { uni.showToast({ title: error.msg || '举报失败', icon: 'none' }) }
  } })
}

function openStall() { uni.navigateTo({ url: `/pages/tl/detail?id=${post.value.stallId}` }) }
function preview(index) { uni.previewImage({ current: images.value[index], urls: images.value }) }
function share() { uni.setClipboardData({ data: `${window.location.origin}${window.location.pathname}#/pages/tl/post-detail?id=${postId.value}`, success: () => uni.showToast({ title: '分享链接已复制', icon: 'none' }) }) }
function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; padding-bottom: 120rpx; background: #f3f3f3; color: #222; }.nav { height: 96rpx; padding: 0 28rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 29rpx; font-weight: 600; }.nav .share-button { margin: 0; padding: 0; height: auto; line-height: normal; background: transparent; font-size: 24rpx; }.nav .share-button::after { border: 0; }.post-card { margin: 20rpx 24rpx; padding: 26rpx; display: flex; flex-direction: column; gap: 20rpx; background: #fff; border-radius: 16rpx; }.author { display: flex; align-items: center; gap: 12rpx; color: #555; font-size: 22rpx; }.author image,.avatar { flex: none; width: 50rpx; height: 50rpx; border-radius: 50%; background: #eee; }.avatar { display: flex; align-items: center; justify-content: center; }.time { margin-left: auto; color: #999; font-size: 20rpx; }.content { font-size: 29rpx; line-height: 1.6; white-space: pre-wrap; }.images { display: flex; flex-wrap: wrap; gap: 10rpx; }.images image { width: 210rpx; height: 210rpx; border-radius: 10rpx; }.stall-link { display: flex; flex-direction: column; gap: 8rpx; padding: 18rpx; background: #f5f5f5; border-radius: 10rpx; font-size: 23rpx; }.stall-link text:last-child { display: none; }.actions { display: flex; gap: 35rpx; color: #555; font-size: 24rpx; }.comment-head { padding: 12rpx 30rpx; font-size: 28rpx; font-weight: 600; display: flex; justify-content: space-between; }.report-action { font-size: 20rpx; color: #999; }.comment-foot { display: flex; justify-content: space-between; align-items: center; }.empty,.state { padding: 55rpx 30rpx; color: #888; text-align: center; }.comment { margin: 12rpx 24rpx; padding: 20rpx 24rpx; display: flex; flex-direction: column; gap: 10rpx; background: #fff; border-radius: 12rpx; font-size: 24rpx; }.comment .time { margin-left: 0; }.composer { position: fixed; bottom: 0; left: 0; right: 0; padding: 14rpx 20rpx calc(14rpx + env(safe-area-inset-bottom)); display: flex; align-items: center; gap: 14rpx; background: #fff; }.composer input { flex: 1; padding: 16rpx 22rpx; border-radius: 32rpx; background: #f2f2f2; font-size: 24rpx; }.composer button { margin: 0; font-size: 23rpx; }
.comment.reply { margin-left: 64rpx; background: #f9f9f9; }.comment-author { display: flex; align-items: center; gap: 10rpx; color: #666; font-size: 21rpx; }.comment-author image,.comment-avatar { flex: none; width: 42rpx; height: 42rpx; border-radius: 50%; background: #eee; }.comment-avatar { display: flex; align-items: center; justify-content: center; }.reply-to { color: #888; font-size: 20rpx; }.replying { color: #888; font-size: 20rpx; }.delete-action { color: #b44; font-size: 20rpx; }
</style>
