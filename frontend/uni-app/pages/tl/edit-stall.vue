<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>编辑摊位资料</text><text class="submit" @tap="submit">提交审核</text></view>
    <view v-if="loading" class="state">正在加载摊位资料…</view>
    <view v-else-if="failed" class="state" @tap="load">加载失败，点击重试</view>
    <view v-else class="form">
      <input v-model="form.name" maxlength="120" placeholder="摊位名称" />
      <textarea v-model="form.description" maxlength="2000" placeholder="介绍一下摊位特色" />
      <input v-model="form.address" maxlength="500" placeholder="详细地址" />
      <RegionPicker :city-code="form.cityCode" :district-code="form.districtCode" @change="setRegion" />
      <input v-model="form.businessHours" maxlength="500" placeholder="营业时间，例如 17:00-22:00" />
      <input v-model="form.contactPhone" maxlength="64" placeholder="公开联系电话（选填）" />
      <view class="images">
        <view v-for="(url, i) in images" :key="i" class="image"><image :src="url" mode="aspectFill" /><text @tap="remove(i)">×</text></view>
        <view v-if="images.length < 9" class="add" @tap="upload">＋<text>上传图片</text></view>
      </view>
      <text class="hint">资料经平台复核后更新，当前已发布信息会继续展示。</text>
    </view>
  </view>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import util from '@/utils/util.js'
import { useAppStore } from '@/store/app.js'
import RegionPicker from './components/RegionPicker.vue'

const store = useAppStore()
const id = ref('')
const loading = ref(false)
const failed = ref(false)
const submitting = ref(false)
const images = ref([])
const form = reactive({ name: '', description: '', address: '', businessHours: '', contactPhone: '', categoryId: null, cityCode: '', districtCode: '', longitude: null, latitude: null })

onLoad(options => { id.value = options?.id || ''; load() })

async function load() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可维护摊位', icon: 'none' }); return }
  loading.value = true; failed.value = false
  try {
    const result = await request.get('tl/me/stalls/managed', { page: 1, limit: 100 })
    const managed = (result.data?.list || []).find(item => String(item.id) === String(id.value))
    if (!managed) throw new Error('当前账号没有管理此摊位的权限')
    Object.assign(form, managed)
    images.value = (managed.imageUrls || '').split(',').filter(Boolean)
    if (!images.value.length && managed.coverImage) images.value = [managed.coverImage]
  } catch (error) { failed.value = true; uni.showToast({ title: error.message || error.msg || '资料加载失败', icon: 'none' }) }
  finally { loading.value = false }
}

function upload() {
  if (images.value.length >= 9) return
  uni.chooseImage({ count: 9 - images.value.length, sizeType: ['compressed'], sourceType: ['album', 'camera'], success: async result => {
    for (const path of result.tempFilePaths) {
      try {
        const uploaded = await new Promise((resolve, reject) => util.uploadImgs(path, { name: 'multipart', model: 'user', pid: 7 }, resolve, reject))
        if (uploaded?.data?.url) images.value.push(uploaded.data.url)
      } catch (error) { uni.showToast({ title: error.message || '图片上传失败', icon: 'none' }) }
    }
  } })
}

function remove(index) { images.value.splice(index, 1) }
function setRegion(region) { form.cityCode = region.cityCode; form.districtCode = region.districtCode }

async function submit() {
  if (submitting.value || !form.name.trim()) { uni.showToast({ title: '请填写摊位名称', icon: 'none' }); return }
  submitting.value = true
  try {
    await request.post(`tl/stalls/${id.value}/revisions`, { ...form, name: form.name.trim(), coverImage: images.value[0] || '', imageUrls: images.value.join(',') })
    uni.showToast({ title: '已提交审核', icon: 'none' })
    setTimeout(() => uni.redirectTo({ url: '/pages/tl/revisions' }), 350)
  } catch (error) { uni.showToast({ title: error.msg || '提交失败', icon: 'none' }) }
  finally { submitting.value = false }
}

function back() { uni.navigateBack() }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; color: #222; }.nav { height: 96rpx; padding: 0 28rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 29rpx; }.submit { font-size: 24rpx; }.form { margin: 24rpx; padding: 26rpx; background: #fff; border-radius: 16rpx; display: flex; flex-direction: column; gap: 24rpx; }.form input,.form textarea { box-sizing: border-box; width: 100%; padding: 18rpx; background: #f7f7f7; border-radius: 10rpx; font-size: 25rpx; }.form textarea { min-height: 220rpx; }.images { display: flex; flex-wrap: wrap; gap: 14rpx; }.image,.add { width: 190rpx; height: 190rpx; position: relative; background: #eee; border-radius: 10rpx; overflow: hidden; }.image image { width: 100%; height: 100%; }.image text { position: absolute; top: 4rpx; right: 6rpx; background: #444a; color: white; border-radius: 50%; width: 38rpx; height: 38rpx; text-align: center; line-height: 36rpx; }.add { display: flex; flex-direction: column; align-items: center; justify-content: center; font-size: 64rpx; color: #888; }.add text { font-size: 21rpx; }.hint,.state { color: #888; font-size: 22rpx; line-height: 1.5; }.state { padding: 120rpx 30rpx; text-align: center; }
</style>
