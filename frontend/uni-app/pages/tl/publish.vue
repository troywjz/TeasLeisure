<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>发布摊位动态</text><text @tap="submit" class="send">发布</text></view>
    <view v-if="loadingStalls" class="state">正在加载本人已认领摊位…</view>
    <view v-else-if="loadFailed" class="state" @tap="loadManagedStalls">摊位列表加载失败，点击重试</view>
    <view v-else-if="!stalls.length" class="state empty">
      <text class="empty-title">当前账号还没有可发布动态的摊位</text>
      <text class="empty-copy">先提交摊位信息并等待发布审核，通过后再申请认领；认领审核通过后即可发布摊位动态。</text>
      <button class="secondary" @tap="goSubmitStall">提交摊位信息</button>
      <button class="secondary" @tap="goClaims">查看认领申请</button>
    </view>
    <view v-else class="form">
      <view class="stall-select" @tap="chooseStall">
        <view><text class="field-label">发布到摊位</text><text v-if="selectedStall" class="stall-name">{{ selectedStall.name }}</text><text v-else class="select-placeholder">请选择名下摊位</text></view>
        <text v-if="stalls.length > 1" class="change">切换 ›</text>
      </view>
      <view v-if="requestedStallMissing" class="warning">链接中的摊位不是当前账号已认领的摊位，请从名下摊位中选择。</view>
      <template v-if="selectedStall">
        <textarea v-model="content" maxlength="2000" placeholder="记录摊位今天出摊、美食和有趣的人…" class="content" />
        <view class="image-row">
          <view v-for="(url, index) in imageUrls" :key="index" class="image-item"><image :src="url" mode="aspectFill" /><text @tap="removeImage(index)">×</text></view>
          <view v-if="imageUrls.length < 9" class="add-image" @tap="chooseImages"><text>＋</text><text>添加图片</text></view>
        </view>
        <view class="hint">仅可为本人已认领且已发布的摊位发布动态。内容提交后经平台审核，再展示给其他用户。</view>
      </template>
    </view>
    <view v-if="submitting" class="mask">正在提交…</view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import util from '@/utils/util.js'
import { useAppStore } from '@/store/app.js'

const store = useAppStore()
const stallId = ref(null)
const stalls = ref([])
const selectedStall = ref(null)
const loadingStalls = ref(true)
const loadFailed = ref(false)
const requestedStallMissing = ref(false)
const content = ref('')
const imageUrls = ref([])
const submitting = ref(false)

onLoad(async options => {
  stallId.value = options?.stallId ? Number(options.stallId) : null
  if (!store.isLogin) { uni.showToast({ title: '登录后可发布摊位动态', icon: 'none' }); setTimeout(() => uni.navigateBack(), 500); return }
  await loadManagedStalls()
})

async function loadManagedStalls() {
  loadingStalls.value = true
  loadFailed.value = false
  try {
    const response = await request.get('tl/me/stalls/managed', { page: 1, limit: 100 })
    stalls.value = response.data?.list || []
    if (stallId.value) {
      selectedStall.value = stalls.value.find(item => Number(item.id) === Number(stallId.value)) || null
      requestedStallMissing.value = !selectedStall.value
    } else if (stalls.value.length === 1) selectedStall.value = stalls.value[0]
  } catch (error) {
    loadFailed.value = true
    uni.showToast({ title: error.msg || '已认领摊位加载失败', icon: 'none' })
  } finally { loadingStalls.value = false }
}

function chooseStall() {
  if (stalls.value.length < 2) return
  uni.showActionSheet({ itemList: stalls.value.map(item => item.name), success: result => {
    selectedStall.value = stalls.value[result.tapIndex]
    stallId.value = Number(selectedStall.value.id)
    requestedStallMissing.value = false
  } })
}

function chooseImages() {
  if (!store.token) { uni.showToast({ title: '登录后可以上传图片', icon: 'none' }); return }
  const remaining = 9 - imageUrls.value.length
  if (remaining <= 0) return
  uni.chooseImage({ count: remaining, sizeType: ['compressed'], sourceType: ['album', 'camera'], success: async result => {
    for (const path of result.tempFilePaths) {
      try {
        const response = await new Promise((resolve, reject) => util.uploadImgs(path, { name: 'multipart', model: 'user', pid: 7 }, resolve, reject))
        const url = response?.data?.url
        if (url) imageUrls.value.push(url)
      } catch (error) { uni.showToast({ title: error.message || '图片上传失败', icon: 'none' }) }
    }
  } })
}

function removeImage(index) { imageUrls.value.splice(index, 1) }

async function submit() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以发布动态', icon: 'none' }); return }
  if (!selectedStall.value || !stalls.value.some(item => Number(item.id) === Number(selectedStall.value.id))) {
    uni.showToast({ title: '请选择本人已认领的摊位', icon: 'none' }); return
  }
  const text = content.value.trim()
  if (!text) { uni.showToast({ title: '先写下摊位动态', icon: 'none' }); return }
  submitting.value = true
  try {
    await request.post('tl/community/posts', { stallId: Number(selectedStall.value.id), content: text, imageUrls: imageUrls.value.join(',') })
    uni.showModal({
      title: '动态已提交审核', content: '可在“我的动态”查看审核进度和处理说明。',
      confirmText: '查看状态', cancelText: '稍后查看',
      success: result => { if (result.confirm) uni.redirectTo({ url: '/pages/tl/my-posts' }); else uni.navigateBack() }
    })
  } catch (error) { uni.showToast({ title: error.msg || '发布失败，请稍后重试', icon: 'none' }) }
  finally { submitting.value = false }
}

function goSubmitStall() { uni.navigateTo({ url: '/pages/tl/submit-stall' }) }
function goClaims() { uni.navigateTo({ url: '/pages/tl/claims' }) }
function back() { uni.navigateBack() }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f5f5f5; color: #222; }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; }.send { font-size: 26rpx; }
.form,.state { margin: 24rpx; padding: 28rpx; background: #fff; border-radius: 16rpx; }.state { text-align: center; color: #777; font-size: 25rpx; line-height: 1.6; }.empty { display: flex; flex-direction: column; gap: 18rpx; }.empty-title { color: #222; font-weight: 600; }.empty-copy { font-size: 22rpx; }.secondary { margin: 0; background: #eee; color: #222; font-size: 24rpx; }
.stall-select { display: flex; align-items: center; justify-content: space-between; padding: 18rpx 20rpx; margin-bottom: 20rpx; background: #f6f6f6; border-radius: 12rpx; }.stall-select > view { display: flex; flex-direction: column; gap: 10rpx; }.field-label { color: #888; font-size: 21rpx; }.stall-name { color: #222; font-size: 26rpx; font-weight: 600; }.select-placeholder,.change { color: #777; font-size: 23rpx; }.warning { margin-bottom: 18rpx; color: #a46c16; font-size: 22rpx; }
.content { width: 100%; min-height: 320rpx; font-size: 29rpx; line-height: 1.6; }.image-row { display: flex; flex-wrap: wrap; gap: 16rpx; margin-top: 24rpx; }.image-item,.add-image { width: 190rpx; height: 190rpx; background: #eee; border-radius: 12rpx; overflow: hidden; position: relative; }.image-item image { width: 100%; height: 100%; }.image-item text { position: absolute; right: 6rpx; top: 4rpx; width: 38rpx; height: 38rpx; text-align: center; line-height: 34rpx; color: #fff; background: rgba(0,0,0,.55); border-radius: 50%; }.add-image { display: flex; flex-direction: column; justify-content: center; align-items: center; color: #777; font-size: 22rpx; gap: 8rpx; }.add-image text:first-child { font-size: 70rpx; font-weight: 200; }.hint { margin-top: 30rpx; color: #999; font-size: 22rpx; }.mask { position: fixed; inset: 0; display: flex; align-items: center; justify-content: center; background: rgba(0,0,0,.25); color: #fff; font-size: 28rpx; }
</style>
