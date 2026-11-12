<template>
  <view class="page">
    <view class="nav"><text @tap="goBack">‹</text><text>摊位详情</text><text></text></view>
    <view v-if="loading" class="state">正在加载摊位…</view>
    <view v-else-if="error" class="state" @tap="load">{{ error }}，点此重试</view>
    <template v-else-if="stall">
      <swiper v-if="stallImages.length" class="cover-gallery" :indicator-dots="stallImages.length > 1" circular>
        <swiper-item v-for="(url, index) in stallImages" :key="index"><image class="cover" :src="url" mode="aspectFill" /></swiper-item>
      </swiper>
      <view v-else class="cover placeholder"></view>
      <view class="stall-info">
        <text class="name">{{ stall.name }}</text>
        <text class="address" @tap="openMap">⌖ {{ stallLocationLabel(stall) }} · 查看地图</text>
        <text class="hours">营业时间：{{ stall.businessHours || '以摊主现场安排为准' }}</text>
        <text class="today-status">{{ stall.todayStatusKnown ? (stall.openToday ? '今日出摊' : '今日休息') : '今日状态待更新' }}<text v-if="stall.todayHours"> · {{ stall.todayHours }}</text></text>
        <text v-if="stall.currentOpen === true" class="current-status open-now">现在营业</text>
        <text v-else-if="stall.currentOpen === false" class="current-status closed-now">现在休息</text>
        <text class="desc">{{ stall.description || '摊主还没有填写介绍' }}</text>
        <button class="claim" @tap="claim">申请认领摊位</button>
        <button class="contact" @tap="contactOwner">私信摊主</button>
        <button class="favorite" @tap="toggleFavorite">{{ isFavorite ? '已收藏' : '收藏摊位' }}</button>
        <text class="report-link" @tap="reportContent('stall', stall.id)">举报摊位</text>
        <!-- #ifdef MP-WEIXIN -->
        <button class="share" open-type="share">转发摊位</button>
        <!-- #endif -->
        <!-- #ifndef MP-WEIXIN -->
        <button class="share" @tap="shareStall">复制分享链接</button>
        <!-- #endif -->
      </view>
      <view class="section-title">摊主动态与逛摊记录</view>
      <view v-if="!posts.length" class="empty">还没有动态，来分享第一条逛摊记录吧</view>
      <view v-for="post in posts" :key="post.id" class="post">
        <view class="post-author"><image v-if="post.authorAvatar" :src="post.authorAvatar" mode="aspectFill" /><text v-else class="author-placeholder">摊</text><text>{{ post.authorName || '茶余用户' }}</text></view>
        <text class="post-text">{{ post.content }}</text>
        <view v-if="postImages(post).length" class="post-images">
          <image v-for="(url, index) in postImages(post)" :key="index" :src="url" mode="aspectFill" />
        </view>
        <text class="post-time">{{ post.createTime }}</text>
        <view class="actions">
          <text @tap="like(post)">{{ post.liked ? '♥' : '♡' }} {{ post.likeCount || 0 }}</text>
          <text @tap="showComments(post)">评论</text>
          <text v-if="post.mine" class="delete-action" @tap="deletePost(post)">删除动态</text>
        </view>
        <view v-if="activePost === post.id" class="comments">
          <view v-for="comment in comments" :key="comment.id" class="comment" :class="{ reply: comment.parentId }">
            <view class="comment-author"><text>{{ comment.authorName || '茶余用户' }}</text><text v-if="comment.parentId" class="reply-to">回复 {{ parentAuthor(comment.parentId) }}</text></view>
            <text>{{ comment.content }}</text>
            <view class="comment-actions"><text class="reply-action" @tap="startReply(comment)">回复</text><text class="report-action" @tap="reportContent('comment', comment.id)">举报</text><text v-if="comment.mine" class="delete-action" @tap="deleteComment(comment)">删除</text></view>
          </view>
          <view class="comment-entry"><text v-if="replyTarget" class="replying" @tap="replyTarget = null">回复 {{ replyTarget.authorName || '茶余用户' }} ×</text><input v-model="commentText" :placeholder="replyTarget ? `回复 ${replyTarget.authorName || '茶余用户'}` : '友善交流，分享你的发现'" /><button @tap="sendComment(post)">发送</button></view>
        </view>
      </view>
      <button class="publish" @tap="writePost">发布逛摊记录</button>
    </template>
  </view>
</template>

<script setup>
import { computed, ref } from 'vue'
import { onLoad, onReachBottom, onShareAppMessage } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import { useAppStore } from '@/store/app.js'
import { stallLocationLabel } from './utils/stall-location.js'

const store = useAppStore()
const stallId = ref('')
const stall = ref(null)
const stallImages = computed(() => {
  const urls = (stall.value?.imageUrls || '').split(',').map(url => url.trim()).filter(Boolean)
  return urls.length ? urls : (stall.value?.coverImage ? [stall.value.coverImage] : [])
})
const posts = ref([])
const comments = ref([])
const loading = ref(false)
const error = ref('')
const activePost = ref(null)
const commentText = ref('')
const replyTarget = ref(null)
const isFavorite = ref(false)
const page = ref(1)
const hasMore = ref(true)
const postImages = (post) => (post.imageUrls || '').split(',').filter(Boolean)

onLoad((options) => { stallId.value = options?.stallId || options?.id || ''; load() })
onReachBottom(() => loadPosts())
onShareAppMessage(() => ({ title: stall.value?.name || 'TeasLeisure 地摊', path: `/pages/tl/detail?id=${stallId.value}` }))

async function load() {
  if (!stallId.value) { error.value = '缺少摊位编号'; return }
  loading.value = true; error.value = ''
  try {
    const res = await request.get(`tl/stalls/${stallId.value}`, {}, { noAuth: true })
    stall.value = res.data
    await loadFavoriteState()
    await loadPosts(true)
  } catch (e) { error.value = e.msg || '加载失败' } finally { loading.value = false }
}

async function loadPosts(reset = false) {
  if (!hasMore.value && !reset) return
  if (reset) { page.value = 1; hasMore.value = true }
  try {
    const res = await request.get('tl/community/posts', { stallId: stallId.value, page: page.value, limit: 10 }, { noAuth: true })
    const data = res.data || {}
    posts.value = reset ? (data.list || []) : posts.value.concat(data.list || [])
    hasMore.value = page.value < (data.totalPage || 0); page.value += 1
  } catch (e) { uni.showToast({ title: e.msg || '动态加载失败', icon: 'none' }) }
}

async function showComments(post) {
  activePost.value = post.id
  replyTarget.value = null
  try { const res = await request.get(`tl/community/posts/${post.id}/comments`, { page: 1, limit: 30 }, { noAuth: true }); comments.value = res.data?.list || [] }
  catch (e) { uni.showToast({ title: e.msg || '评论加载失败', icon: 'none' }) }
}

async function sendComment(post) {
  if (!store.token) { uni.showToast({ title: '登录后可以评论', icon: 'none' }); return }
  const content = commentText.value.trim(); if (!content) return
  try {
    await request.post(`tl/community/posts/${post.id}/comments`, { content, parentId: replyTarget.value?.id || null })
    commentText.value = ''; replyTarget.value = null
    uni.showModal({
      title: '评论已提交审核',
      content: '可在“我的评论”查看审核进度和处理说明。',
      confirmText: '查看状态',
      cancelText: '继续浏览',
      success: result => { if (result.confirm) uni.navigateTo({ url: '/pages/tl/my-comments' }) }
    })
  } catch (e) { uni.showToast({ title: e.msg || '发送失败', icon: 'none' }) }
}

function deletePost(post) {
  if (!store.token || !post?.mine) return
  uni.showModal({ title: '删除动态', content: '删除后，这条动态会从公开页面移除。', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/posts/${post.id}`)
      posts.value = posts.value.filter(item => item.id !== post.id)
      if (activePost.value === post.id) { activePost.value = null; comments.value = [] }
      uni.showToast({ title: '动态已删除', icon: 'none' })
    } catch (e) { uni.showToast({ title: e.msg || '删除失败', icon: 'none' }) }
  } })
}

function deleteComment(comment) {
  if (!store.token || !comment?.mine) return
  uni.showModal({ title: '删除评论', content: '确认删除这条评论？', confirmText: '删除', confirmColor: '#b44', success: async result => {
    if (!result.confirm) return
    try {
      await request.delete(`tl/community/comments/${comment.id}`)
      comments.value = comments.value.filter(item => item.id !== comment.id)
      if (replyTarget.value?.id === comment.id) replyTarget.value = null
      uni.showToast({ title: '评论已删除', icon: 'none' })
    } catch (e) { uni.showToast({ title: e.msg || '删除失败', icon: 'none' }) }
  } })
}

function startReply(comment) { replyTarget.value = comment }
function parentAuthor(parentId) { return comments.value.find(item => item.id === parentId)?.authorName || '评论作者' }

function openMap() {
  const latitude = Number(stall.value?.latitude)
  const longitude = Number(stall.value?.longitude)
  if (!Number.isFinite(latitude) || !Number.isFinite(longitude) || latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
    uni.showToast({ title: '摊主还没有更新准确位置', icon: 'none' })
    return
  }
  uni.openLocation({ latitude, longitude, name: stall.value.name, address: stallLocationLabel(stall.value), scale: 16 })
}

async function like(post) {
  if (!store.token) { uni.showToast({ title: '登录后可以点赞', icon: 'none' }); return }
  const active = !post.liked
  try {
    await request.post(`tl/community/posts/${post.id}/like?active=${active}`, {})
    post.liked = active
    post.likeCount = Math.max(0, Number(post.likeCount || 0) + (active ? 1 : -1))
  }
  catch (e) { uni.showToast({ title: e.msg || '操作失败', icon: 'none' }) }
}

function claim() {
  if (!store.token) { uni.showToast({ title: '登录后可申请认领', icon: 'none' }); return }
  uni.navigateTo({ url: `/pages/tl/claim-apply?id=${stallId.value}` })
}

async function contactOwner() {
  if (!store.token) { uni.showToast({ title: '登录后可以联系摊主', icon: 'none' }); return }
  try {
    const result = await request.post('tl/conversations', { stallId: Number(stallId.value) }, {}, true)
    uni.navigateTo({ url: `/pages/tl/messages?conversationId=${result.data}` })
  } catch (e) { uni.showToast({ title: e.msg || '暂时无法联系摊主', icon: 'none' }) }
}

async function toggleFavorite() {
  if (!store.token) { uni.showToast({ title: '登录后可以收藏', icon: 'none' }); return }
  try {
    if (isFavorite.value) {
      await request.delete(`tl/favorites/stalls/${stallId.value}`)
      isFavorite.value = false
    } else {
      await request.post('tl/favorites/stalls', { stallId: Number(stallId.value) })
      isFavorite.value = true
    }
    uni.showToast({ title: isFavorite.value ? '已收藏' : '已取消收藏', icon: 'none' })
  } catch (e) { uni.showToast({ title: e.msg || '操作失败', icon: 'none' }) }
}

function shareStall() {
  const route = `/pages/tl/detail?id=${stallId.value}`
  const url = typeof window !== 'undefined' ? `${window.location.origin}${window.location.pathname}#${route}` : route
  uni.setClipboardData({ data: url, success: () => uni.showToast({ title: '摊位链接已复制', icon: 'none' }) })
}

function reportContent(targetType, targetId) {
  if (!store.token) { uni.showToast({ title: '登录后可以举报', icon: 'none' }); return }
  uni.showModal({ title: '举报内容', editable: true, placeholderText: '请简述原因', success: async result => {
    const reason = result.confirm ? result.content?.trim() : ''
    if (!reason) return
    try { await request.post('tl/content-reports', { targetType, targetId, reason }); uni.showToast({ title: '举报已提交', icon: 'none' }) }
    catch (error) { uni.showToast({ title: error.msg || '举报失败', icon: 'none' }) }
  } })
}

async function loadFavoriteState() {
  if (!store.token) return
  try {
    const result = await request.get('tl/favorites/stalls', { page: 1, limit: 100 })
    isFavorite.value = (result.data?.list || []).some(item => String(item.id) === String(stallId.value))
  } catch (e) { /* Favorite state remains available from the explicit action. */ }
}

function writePost() { uni.navigateTo({ url: `/pages/tl/publish?stallId=${stallId.value}` }) }
function goBack() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f4f4f4; padding-bottom: 120rpx; color: #222; }
.nav { height: 96rpx; display: flex; align-items: center; justify-content: space-between; padding: 0 30rpx; background: #e4e4e4; font-size: 30rpx; }
.cover-gallery { width: 100%; height: 750rpx; background: #d9d9d9; }.cover { width: 100%; height: 750rpx; background: #d9d9d9; }
.placeholder { background: linear-gradient(145deg,#e5e2dd,#cbc6bf); }
.stall-info { padding: 30rpx; background: #fff; display: flex; flex-direction: column; gap: 18rpx; }
.name { font-size: 38rpx; font-weight: 650; }
.address,.hours { font-size: 24rpx; color: #666; }
.address { text-decoration: underline; text-underline-offset: 4rpx; }.today-status { font-size: 23rpx; color: #836b42; }
.current-status { font-size: 23rpx; }.open-now { color: #287b42; }.closed-now { color: #888; }
.desc { font-size: 28rpx; line-height: 1.6; white-space: pre-wrap; }
.claim { margin: 8rpx 0 0; border: 1rpx solid #aaa; background: #fff; border-radius: 12rpx; font-size: 26rpx; }
.contact { margin: 0; background: #e4e4e4; color: #222; border-radius: 12rpx; font-size: 26rpx; }
.favorite { margin: 0; background: #fff; color: #444; border: 1rpx solid #ddd; border-radius: 12rpx; font-size: 26rpx; }
.share { margin: 0; background: #fff; color: #444; border: 1rpx solid #ddd; border-radius: 12rpx; font-size: 26rpx; }
.report-link { align-self: flex-end; color: #999; font-size: 21rpx; padding: 4rpx 8rpx; }
.section-title { padding: 28rpx 30rpx 10rpx; font-size: 30rpx; font-weight: 600; }
.empty,.state { padding: 50rpx 30rpx; text-align: center; color: #777; }
.post { margin: 18rpx 24rpx; padding: 26rpx; border-radius: 16rpx; background: #fff; display: flex; flex-direction: column; gap: 16rpx; }
.post-author { display: flex; align-items: center; gap: 12rpx; color: #555; font-size: 22rpx; }.post-author image,.author-placeholder { width: 48rpx; height: 48rpx; border-radius: 50%; background: #eee; }.author-placeholder { text-align: center; line-height: 48rpx; }
.post-text { font-size: 28rpx; line-height: 1.55; }
.post-images { display: flex; flex-wrap: wrap; gap: 10rpx; }.post-images image { width: 200rpx; height: 200rpx; border-radius: 10rpx; }
.post-time { color: #999; font-size: 22rpx; }
.actions { display: flex; gap: 40rpx; color: #666; font-size: 24rpx; }
.comments { padding-top: 16rpx; border-top: 1rpx solid #eee; }
.comment { padding: 12rpx 0; font-size: 24rpx; }
.comment.reply { margin-left: 30rpx; padding-left: 16rpx; border-left: 3rpx solid #ddd; }.comment-author { display: flex; gap: 12rpx; color: #555; font-size: 21rpx; }.reply-to,.reply-action,.replying { color: #999; font-size: 20rpx; }.reply-action { align-self: flex-end; }.replying { white-space: nowrap; }
.comment-entry { display: flex; align-items: center; gap: 12rpx; }
.comment-entry input { flex: 1; background: #f5f5f5; padding: 12rpx 18rpx; border-radius: 28rpx; font-size: 22rpx; }
.comment-entry button { margin: 0; font-size: 22rpx; }
.delete-action { color: #b44; font-size: 20rpx; }
.comment-actions { display: flex; justify-content: flex-end; gap: 24rpx; }.report-action { color: #999; font-size: 20rpx; }
.publish { position: fixed; bottom: 26rpx; left: 30rpx; right: 30rpx; background: #e4e4e4; color: #222; border-radius: 48rpx; }
</style>
