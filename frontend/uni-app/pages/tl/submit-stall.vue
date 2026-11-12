<template>
  <view class="page">
    <view class="nav"><text @tap="back">‹</text><text>提交地摊信息</text><text class="submit" @tap="submit">提交审核</text></view>
    <view v-if="loadingCategories" class="loading">正在加载分类…</view>
    <view v-else class="form">
      <input v-model="form.name" maxlength="120" placeholder="摊位名称（必填）" />
      <picker mode="selector" :range="categoryNames" @change="selectCategory">
        <view class="picker">{{ selectedCategoryName || '选择摊位分类（必填）' }}<text>›</text></view>
      </picker>
      <textarea v-model="form.description" maxlength="2000" placeholder="介绍摊位特色、招牌和出摊情况" />
      <input v-model="form.address" maxlength="500" placeholder="详细地址" />
      <RegionPicker :city-code="form.cityCode" :district-code="form.districtCode" @change="setRegion" />
      <input v-model="form.businessHours" maxlength="500" placeholder="营业时间，例如 17:00-22:00" />
      <view class="location" @tap="chooseLocation"><text>{{ locationLabel }}</text><text>选择位置 ›</text></view>
      <view class="images">
        <view v-for="(url, index) in images" :key="index" class="image"><image :src="url" mode="aspectFill" /><text @tap.stop="removeImage(index)">×</text></view>
        <view v-if="images.length < 9" class="add" @tap="uploadImages"><text>＋</text><text>{{ images.length ? '添加图片' : '上传图片' }}</text></view>
      </view>
      <text class="hint">最多上传 9 张，第一张将作为列表封面。</text>
      <text class="hint">投稿通过审核后会展示给附近用户；如你是摊主，可在摊位详情申请认领。</text>
    </view>
  </view>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import request from '@/utils/request.js'
import util from '@/utils/util.js'
import { useAppStore } from '@/store/app.js'
import RegionPicker from './components/RegionPicker.vue'

const store = useAppStore()
const categories = ref([])
const loadingCategories = ref(false)
const submitting = ref(false)
const images = ref([])
const form = reactive({ name: '', categoryId: null, description: '', address: '', cityCode: '', districtCode: '', longitude: null, latitude: null, businessHours: '', coverImage: '' })
const categoryNames = computed(() => categories.value.map(item => item.name))
const selectedCategoryName = computed(() => categories.value.find(item => item.id === form.categoryId)?.name || '')
const locationLabel = computed(() => form.latitude != null && form.longitude != null ? `${form.address || '已选择位置'}（${Number(form.latitude).toFixed(5)}, ${Number(form.longitude).toFixed(5)}）` : '地图选点（选填）')

onLoad(loadCategories)

async function loadCategories() {
  loadingCategories.value = true
  try { const result = await request.get('tl/categories', {}, { noAuth: true }); categories.value = result.data || [] }
  catch (error) { uni.showToast({ title: error.msg || '分类加载失败', icon: 'none' }) }
  finally { loadingCategories.value = false }
}

function selectCategory(event) { const category = categories.value[Number(event.detail.value)]; if (category) form.categoryId = category.id }
function setRegion(region) { form.cityCode = region.cityCode; form.districtCode = region.districtCode }
function chooseLocation() { uni.chooseLocation({ success: location => { form.address = location.address || location.name || form.address; form.longitude = location.longitude; form.latitude = location.latitude }, fail: () => uni.showToast({ title: '位置选择未完成，可手动填写地址', icon: 'none' }) }) }

function uploadImages() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以上传封面', icon: 'none' }); return }
  uni.chooseImage({ count: 9 - images.value.length, sizeType: ['compressed'], sourceType: ['album', 'camera'], success: async result => {
    for (const path of result.tempFilePaths) {
      try {
        const uploaded = await new Promise((resolve, reject) => util.uploadImgs(path, { name: 'multipart', model: 'user', pid: 7 }, resolve, reject))
        if (uploaded?.data?.url) images.value.push(uploaded.data.url)
      } catch (error) { uni.showToast({ title: error.message || '图片上传失败', icon: 'none' }) }
    }
    form.coverImage = images.value[0] || ''
  } })
}

function removeImage(index) { images.value.splice(index, 1); form.coverImage = images.value[0] || '' }

async function submit() {
  if (!store.isLogin) { uni.showToast({ title: '登录后可以投稿', icon: 'none' }); return }
  if (!form.name.trim() || !form.categoryId) { uni.showToast({ title: '请填写名称并选择分类', icon: 'none' }); return }
  if (submitting.value) return
  submitting.value = true
  try {
    await request.post('tl/stall-submissions', { ...form, name: form.name.trim(), coverImage: images.value[0] || '', imageUrls: images.value.join(',') })
    uni.showModal({ title: '投稿已提交', content: '审核进度可在“我的投稿”查看。', showCancel: false, success: () => uni.redirectTo({ url: '/pages/tl/submissions' }) })
  } catch (error) { uni.showToast({ title: error.msg || '提交失败', icon: 'none' }) }
  finally { submitting.value = false }
}

function back() { uni.navigateBack({ delta: 1 }) }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; color: #222; }.nav { height: 96rpx; padding: 0 28rpx; display: flex; align-items: center; justify-content: space-between; background: #e4e4e4; font-size: 29rpx; }.submit { font-size: 24rpx; }.form { margin: 22rpx; padding: 26rpx; display: flex; flex-direction: column; gap: 22rpx; background: #fff; border-radius: 16rpx; }.form input,.form textarea,.picker { width: 100%; box-sizing: border-box; padding: 18rpx; background: #f6f6f6; border-radius: 10rpx; font-size: 25rpx; }.form textarea { min-height: 200rpx; }.picker,.location { display: flex; align-items: center; justify-content: space-between; color: #666; }.location { padding: 10rpx 4rpx; font-size: 23rpx; }.images { display: flex; flex-wrap: wrap; gap: 14rpx; }.image,.add { position: relative; width: 190rpx; height: 190rpx; overflow: hidden; background: #eee; border-radius: 10rpx; }.image image { width: 100%; height: 100%; }.image text { position: absolute; right: 6rpx; top: 6rpx; color: #fff; background: #444a; border-radius: 50%; width: 38rpx; height: 38rpx; text-align: center; line-height: 36rpx; }.add { display: flex; flex-direction: column; align-items: center; justify-content: center; color: #888; font-size: 64rpx; }.add text:last-child { font-size: 21rpx; }.hint,.loading { color: #888; font-size: 22rpx; line-height: 1.5; }.loading { padding: 80rpx; text-align: center; }
</style>
