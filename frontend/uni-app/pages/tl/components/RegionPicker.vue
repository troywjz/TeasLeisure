<template>
  <view class="region-picker">
    <picker mode="multiSelector" :disabled="!tree.length" :range="columns" :value="indices" @columnchange="changeColumn" @change="confirmRegion">
      <view class="picker-value"><text>{{ label || pickerHint }}</text><text class="arrow">›</text></view>
    </picker>
    <text v-if="loadFailed" class="retry" @tap="loadTree">重试</text>
    <text v-if="cityCode" class="clear" @tap="$emit('change', { cityCode: '', districtCode: '', label: '' })">清除</text>
  </view>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { getCity } from '@/api/api.js'

const props = defineProps({ cityCode: { type: [String, Number], default: '' }, districtCode: { type: [String, Number], default: '' } })
const emit = defineEmits(['change'])
const tree = ref([])
const loading = ref(false)
const loadFailed = ref(false)
const indices = ref([0, 0, 0])

const provinces = computed(() => tree.value || [])
const cities = computed(() => provinces.value[indices.value[0]]?.child || [])
const districts = computed(() => cities.value[indices.value[1]]?.child || [])
const columns = computed(() => [
  provinces.value.map(item => item.name),
  cities.value.length ? cities.value.map(item => item.name) : [''],
  ['不限区县', ...districts.value.map(item => item.name)]
])
const label = computed(() => {
  const province = provinces.value[indices.value[0]]
  const city = cities.value[indices.value[1]]
  const district = districts.value[indices.value[2] - 1]
  const cityCode = String(props.cityCode || '')
  if (!cityCode || !tree.value.length) return ''
  if (!city || String(city.cityId) !== cityCode) return '地区编码待核对'
  return [province?.name, city.name, district?.name].filter(Boolean).join(' ')
})
const pickerHint = computed(() => loading.value ? '正在读取地区…' : loadFailed.value ? '地区列表加载失败' : '选择省 / 市 / 区县')

onMounted(loadTree)

async function loadTree() {
  if (loading.value) return
  loading.value = true
  loadFailed.value = false
  try {
    const response = await getCity()
    const list = response.data
    tree.value = Array.isArray(list) ? list : []
    if (!tree.value.length) loadFailed.value = true
    syncSelection()
  } catch (error) {
    tree.value = []
    loadFailed.value = true
    uni.showToast({ title: error.msg || '地区列表加载失败', icon: 'none' })
  } finally {
    loading.value = false
  }
}

watch(() => [props.cityCode, props.districtCode], syncSelection)

function syncSelection() {
  if (!tree.value.length) return
  const cityCode = String(props.cityCode || '')
  const districtCode = String(props.districtCode || '')
  let provinceIndex = 0
  let cityIndex = 0
  let districtIndex = 0
  if (cityCode) {
    for (let p = 0; p < tree.value.length; p++) {
      const foundCity = (tree.value[p].child || []).findIndex(city => String(city.cityId) === cityCode)
      if (foundCity >= 0) { provinceIndex = p; cityIndex = foundCity; break }
    }
    if (districtCode) {
      const foundDistrict = (tree.value[provinceIndex]?.child?.[cityIndex]?.child || []).findIndex(district => String(district.cityId) === districtCode)
      if (foundDistrict >= 0) districtIndex = foundDistrict + 1
    }
  }
  indices.value = [provinceIndex, cityIndex, districtIndex]
}

function changeColumn(event) {
  const [provinceIndex, cityIndex, districtIndex] = indices.value
  if (event.detail.column === 0) indices.value = [event.detail.value, 0, 0]
  else if (event.detail.column === 1) indices.value = [provinceIndex, event.detail.value, 0]
  else indices.value = [provinceIndex, cityIndex, event.detail.value]
}

function confirmRegion(event) {
  indices.value = event.detail.value || indices.value
  const province = provinces.value[indices.value[0]]
  const city = cities.value[indices.value[1]]
  const district = districts.value[indices.value[2] - 1]
  if (!city) { uni.showToast({ title: '请选择城市', icon: 'none' }); return }
  emit('change', {
    cityCode: String(city.cityId),
    districtCode: district ? String(district.cityId) : '',
    label: [province?.name, city.name, district?.name].filter(Boolean).join(' ')
  })
}
</script>

<style lang="scss" scoped>
.region-picker { display: flex; align-items: center; gap: 18rpx; }.picker-value { min-height: 72rpx; flex: 1; padding: 0 20rpx; display: flex; align-items: center; justify-content: space-between; box-sizing: border-box; background: #f6f6f6; border-radius: 10rpx; color: #666; font-size: 24rpx; }.arrow { color: #aaa; font-size: 34rpx; }.clear,.retry { flex: none; color: #8b6252; font-size: 22rpx; }
</style>
