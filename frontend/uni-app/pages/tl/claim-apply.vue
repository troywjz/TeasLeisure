<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>申请认领摊位</text><text class="submit" @tap="submit">提交申请</text></view>
    <view v-if="loading" class="state">正在加载摊位…</view>
    <view v-else-if="failed" class="state" @tap="loadStall">加载失败，点此重试</view>
    <view v-else class="form">
      <view class="stall-card">
        <image v-if="stall?.coverImage" class="cover" :src="stall.coverImage" mode="aspectFill" />
        <view class="stall-copy">
          <text class="stall-name">{{ stall?.name || '摊位' }}</text>
          <text class="stall-address">{{ stallLocationLabel(stall) }}</text>
        </view>
      </view>
      <textarea v-model="proof" class="proof" maxlength="1000" placeholder="请说明你与摊位的关系或经营情况（必填）" />
      <view class="image-grid">
        <view v-for="(url, index) in proofImages" :key="`${index}-${url}`" class="image-item">
          <image :src="url" mode="aspectFill" />
          <text class="remove" @tap="removeImage(index)">×</text>
        </view>
        <view v-if="proofImages.length < 5" class="add-image" @tap="chooseImages"><text>＋</text><text>添加现场照片</text></view>
      </view>
      <text class="hint">可选上传最多 5 张摊位或经营现场照片。无需提供身份证、营业执照或其他证件。</text>
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
import { toLogin } from '@/libs/login.js'
import { stallLocationLabel } from './utils/stall-location.js'

const store = useAppStore()
const stallId = ref('')
const stall = ref(null)
const loading = ref(false)
const failed = ref(false)
const submitting = ref(false)
const proof = ref('')
const proofImages = ref([])

onLoad(options => {
  stallId.value = options?.id || ''
  if (!store.isLogin) { toLogin(); return }
  loadStall()
})

async function loadStall() {
  if (!stallId.value) { failed.value = true; uni.showToast({ title: '缺少摊位编号', icon: 'none' }); return }
  loading.value = true; failed.value = false
  try {
    const response = await request.get(`tl/stalls/${stallId.value}`, {}, { noAuth: true })
    stall.value = response.data
  } catch (error) {
    failed.value = true
    uni.showToast({ title: error.msg || '摊位加载失败', icon: 'none' })
  } finally { loading.value = false }
}

function chooseImages() {
  if (!store.isLogin) { toLogin(); return }
  const remaining = 5 - proofImages.value.length
  if (remaining <= 0) return
  uni.chooseImage({ count: remaining, sizeType: ['compressed'], sourceType: ['album', 'camera'], success: async result => {
    for (const path of result.tempFilePaths) {
      try {
        const uploaded = await new Promise((resolve, reject) => util.uploadImgs(path, { name: 'multipart', model: 'user', pid: 7 }, resolve, reject))
        const url = uploaded?.data?.url
        if (url) proofImages.value.push(url)
      } catch (error) { uni.showToast({ title: error.message || '现场照片上传失败', icon: 'none' }) }
    }
  } })
}

function removeImage(index) { proofImages.value.splice(index, 1) }

async function submit() {
  if (!store.isLogin) { toLogin(); return }
  if (!proof.value.trim()) { uni.showToast({ title: '请填写认领说明', icon: 'none' }); return }
  if (submitting.value) return
  submitting.value = true
  try {
    await request.post('tl/stall-claims', {
      stallId: Number(stallId.value), proof: proof.value.trim(), proofImageUrls: proofImages.value.join(',')
    })
    uni.showModal({ title: '申请已提交', content: '平台审核完成后可在“我的认领”查看结果。', showCancel: false, success: () => uni.redirectTo({ url: '/pages/tl/claims' }) })
  } catch (error) {
    if ((error.msg || '').includes('已有待处理的认领申请')) {
      uni.showModal({ title: '认领申请已提交', content: '这家摊位已有一条待审核申请，可在“我的认领”查看进度。', showCancel: false, confirmText: '查看进度', success: () => uni.redirectTo({ url: '/pages/tl/claims' }) })
    } else uni.showToast({ title: error.msg || '认领申请提交失败', icon: 'none' })
  } finally { submitting.value = false }
}

function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); background: #f3f3f3; color: #222; }
.nav { height: 96rpx; padding: 0 30rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 30rpx; font-weight: 600; }.submit { font-size: 24rpx; color: #333; }
.form { margin: 24rpx; padding: 24rpx; border-radius: 16rpx; background: #fff; display: flex; flex-direction: column; gap: 22rpx; }
.stall-card { display: flex; align-items: center; gap: 18rpx; padding-bottom: 18rpx; border-bottom: 1rpx solid #eee; }.cover { width: 130rpx; height: 112rpx; border-radius: 12rpx; background: #eee; }.stall-copy { display: flex; flex-direction: column; gap: 12rpx; min-width: 0; }.stall-name { font-size: 27rpx; font-weight: 600; }.stall-address { font-size: 22rpx; color: #888; }
.proof { min-height: 180rpx; box-sizing: border-box; padding: 18rpx; border-radius: 10rpx; background: #f6f6f6; font-size: 25rpx; line-height: 1.5; }
.image-grid { display: flex; flex-wrap: wrap; gap: 14rpx; }.image-item,.add-image { position: relative; width: 190rpx; height: 190rpx; border-radius: 10rpx; overflow: hidden; background: #eee; }.image-item image { width: 100%; height: 100%; }.remove { position: absolute; top: 6rpx; right: 6rpx; width: 38rpx; height: 38rpx; border-radius: 50%; color: #fff; text-align: center; line-height: 36rpx; background: #444a; }.add-image { display: flex; flex-direction: column; align-items: center; justify-content: center; color: #888; font-size: 58rpx; }.add-image text:last-child { font-size: 20rpx; }
.hint,.state { color: #888; font-size: 22rpx; line-height: 1.55; }.state { padding: 120rpx 34rpx; text-align: center; }.mask { position: fixed; inset: 0; display: flex; align-items: center; justify-content: center; background: #fff9; color: #555; font-size: 25rpx; }
</style>
