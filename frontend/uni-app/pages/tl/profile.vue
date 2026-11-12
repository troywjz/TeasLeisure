<template>
  <view class="page">
    <view class="nav-title">我的</view>
    <view class="profile-card">
      <view class="avatar">{{ isLogin ? '我' : '茶' }}</view>
      <view><text class="name">{{ isLogin ? `用户${uid || ''}` : '欢迎来到 TeasLeisure' }}</text><text class="hint">发现地摊中的生活方式</text></view>
      <button v-if="!isLogin" class="login" @tap="login">登录</button>
    </view>
    <view class="switch-row"><text>当前身份</text><view class="switches"><text :class="{ chosen: role === 'user' }" @tap="setRole('user')">个人</text><text :class="{ chosen: role === 'merchant' }" @tap="setRole('merchant')">摊主</text></view></view>
    <view class="menu">
      <view v-if="role === 'user'" class="item" @tap="navigate('submit-stall')"><text>提交地摊信息</text><text>›</text></view>
      <view v-if="role === 'user'" class="item" @tap="navigate('submissions')"><text>我的投稿</text><text>›</text></view>
      <view v-if="role === 'user'" class="item" @tap="navigate('claims')"><text>我的认领</text><text>›</text></view>
      <view v-if="role === 'user'" class="item" @tap="navigate('my-posts')"><text>我的动态</text><text>›</text></view>
      <view v-if="role === 'user'" class="item" @tap="navigate('my-comments')"><text>我的评论</text><text>›</text></view>
      <view v-if="role === 'user'" class="item" @tap="navigate('favorites')"><text>我的收藏</text><text>›</text></view>
      <view v-if="role === 'merchant'" class="item" @tap="navigate('claims')"><text>认领申请记录</text><text>›</text></view>
      <view v-if="role === 'merchant'" class="item" @tap="navigate('my-posts')"><text>我的动态</text><text>›</text></view>
      <view v-if="role === 'merchant'" class="item" @tap="navigate('my-comments')"><text>我的评论</text><text>›</text></view>
      <view v-if="role === 'merchant'" class="item" @tap="navigate('revisions')"><text>我的资料修订</text><text>›</text></view>
      <view v-if="role === 'merchant'" class="item" @tap="navigate('managed')"><text>管理已认领摊位</text><text>›</text></view>
      <view class="item" @tap="uni.showToast({ title: '反馈页面接入中', icon: 'none' })"><text>意见反馈</text><text>›</text></view>
      <view class="item" @tap="uni.showToast({ title: '关于 TeasLeisure', icon: 'none' })"><text>关于 TeasLeisure</text><text>›</text></view>
    </view>
    <TlTabBar active="profile" />
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onShow } from '@dcloudio/uni-app'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import TlTabBar from './components/TabBar.vue'

const appStore = useAppStore()
const isLogin = ref(false)
const uid = ref('')
const role = ref(uni.getStorageSync('tl_profile_role') === 'merchant' ? 'merchant' : 'user')

onShow(() => { isLogin.value = appStore.isLogin; uid.value = appStore.uid || '' })
function login() { toLogin() }
function setRole(value) {
  role.value = value
  uni.setStorageSync('tl_profile_role', value)
}
function navigate(page) {
  if (!isLogin.value) { login(); return }
  uni.redirectTo({ url: `/pages/tl/${page}` })
}
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: calc(100rpx + env(safe-area-inset-bottom)); }
.nav-title { height: 100rpx; background: #e4e4e4; display: flex; align-items: center; justify-content: center; font-size: 30rpx; font-weight: 600; }
.profile-card { display: flex; align-items: center; gap: 22rpx; padding: 42rpx 30rpx; background: #fff; }
.avatar { width: 104rpx; height: 104rpx; border-radius: 50%; background: #dedede; display: flex; align-items: center; justify-content: center; color: #333; font-size: 34rpx; }
.profile-card > view:nth-child(2) { flex: 1; display: flex; flex-direction: column; gap: 12rpx; }
.name { font-size: 30rpx; color: #222; font-weight: 600; }.hint { font-size: 22rpx; color: #888; }
.login { margin: 0; font-size: 24rpx; color: #333; background: #eee; }
.switch-row { margin-top: 22rpx; padding: 26rpx 30rpx; display: flex; align-items: center; justify-content: space-between; background: #fff; font-size: 26rpx; }
.switches { display: flex; gap: 24rpx; color: #888; }.switches text { padding: 10rpx 18rpx; border-radius: 24rpx; }.switches .chosen { color: #222; background: #eee; font-weight: 600; }
.menu { margin-top: 22rpx; background: #fff; }.item { min-height: 92rpx; display: flex; align-items: center; justify-content: space-between; padding: 0 30rpx; border-bottom: 1rpx solid #eee; font-size: 25rpx; color: #333; }.item text:last-child { color: #aaa; font-size: 38rpx; }
</style>
