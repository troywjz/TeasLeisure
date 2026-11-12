<template>
  <view class="page">
    <view class="nav"><text v-if="conversationId" @tap="back">‹</text><text>{{ conversationId ? '私信' : '消息' }}</text><text></text></view>
    <template v-if="!conversationId">
      <view v-if="!isLogin" class="state" @tap="login">登录后查看会话</view>
      <view v-else-if="loading" class="state">正在加载会话…</view>
      <view v-else-if="!conversations.length" class="state">还没有私信，打开摊位详情联系摊主</view>
      <view v-for="item in conversations" :key="item.id" class="conversation" @tap="open(item)">
        <view class="avatar">摊</view>
        <view class="summary"><text class="title">{{ item.stallName || `摊位 ${item.stallId}` }} · {{ item.peerName || '摊主' }}</text><text class="last">{{ item.canSend ? (item.lastMessageContent || '会话已创建，开始打个招呼吧') : '当前会话已暂停发送' }}</text></view>
        <text v-if="item.unreadCount" class="unread">{{ item.unreadCount > 99 ? '99+' : item.unreadCount }}</text>
        <text class="time">{{ formatDate(item.lastMessageTime || item.createTime) }}</text>
      </view>
      <TlTabBar active="messages" />
    </template>
    <template v-else>
      <scroll-view class="thread" scroll-y :scroll-into-view="scrollTarget">
        <view v-if="loading" class="state">正在读取消息…</view>
        <view v-for="message in messages" :id="`message-${message.id}`" :key="message.id" class="message-row" :class="{ mine: message.mine }">
          <view class="bubble">{{ message.content }}</view>
        </view>
        <view id="message-end" class="end-anchor"></view>
      </scroll-view>
      <view v-if="conversation && !conversation.canSend" class="send-paused">当前会话已暂停发送</view>
      <view v-else-if="conversation" class="composer"><input v-model="draft" maxlength="2000" placeholder="输入消息" confirm-type="send" @confirm="send" /><button :disabled="sending" @tap="send">发送</button></view>
      <view v-else class="send-paused">正在确认会话状态…</view>
      <view class="thread-actions"><text @tap="report">举报会话</text><text @tap="block">{{ blocked ? '解除屏蔽' : '屏蔽对方' }}</text></view>
    </template>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onHide, onLoad, onShow, onUnload } from '@dcloudio/uni-app'
import { useAppStore } from '@/store/app.js'
import { toLogin } from '@/libs/login.js'
import request from '@/utils/request.js'
import TlTabBar from './components/TabBar.vue'

const store = useAppStore()
const isLogin = ref(false)
const conversationId = ref('')
const conversations = ref([])
const messages = ref([])
const loading = ref(false)
const sending = ref(false)
const draft = ref('')
const scrollTarget = ref('message-end')
const conversation = ref(null)
const blocked = ref(false)
let pollTimer = null

onLoad(options => { conversationId.value = options?.conversationId || '' })
onShow(() => {
  isLogin.value = store.isLogin
  if (!isLogin.value) return
  if (conversationId.value) loadMessages()
  else loadConversations()
  clearPolling()
  pollTimer = setInterval(() => {
    if (!store.isLogin || loading.value || sending.value) return
    if (conversationId.value) loadMessages(true)
    else loadConversations(true)
  }, 12000)
})
onHide(clearPolling)
onUnload(clearPolling)

async function loadConversations(silent = false) {
  if (loading.value) return
  loading.value = true
  try { const res = await request.get('tl/conversations'); conversations.value = res.data || [] }
  catch (e) { if (!silent) uni.showToast({ title: e.msg || '会话加载失败', icon: 'none' }) }
  finally { loading.value = false }
}

async function loadMessages(silent = false) {
  if (loading.value) return
  loading.value = true
  try {
    const list = await request.get('tl/conversations')
    conversations.value = list.data || []
    conversation.value = conversations.value.find(item => String(item.id) === String(conversationId.value)) || null
    const res = await request.get(`tl/conversations/${conversationId.value}/messages`, { page: 1, limit: 100 })
    const page = res.data || {}
    messages.value = (page.list || []).slice().reverse()
    await loadBlockStatus()
    if (messages.value.length) {
      scrollTarget.value = `message-${messages.value[messages.value.length - 1].id}`
      await request.post(`tl/conversations/${conversationId.value}/read`, { lastMessageId: messages.value[messages.value.length - 1].id }, {}, true)
    }
  } catch (e) { if (!silent) uni.showToast({ title: e.msg || '消息加载失败', icon: 'none' }) }
  finally { loading.value = false }
}

async function send() {
  const content = draft.value.trim()
  if (!content || sending.value) return
  if (!conversation.value?.canSend) { uni.showToast({ title: '当前会话已暂停发送', icon: 'none' }); return }
  sending.value = true
  try {
    await request.post(`tl/conversations/${conversationId.value}/messages`, { content })
    draft.value = ''
    await loadMessages()
  } catch (e) { uni.showToast({ title: e.msg || '发送失败', icon: 'none' }) }
  finally { sending.value = false }
}

async function open(item) {
  conversationId.value = String(item.id)
  conversation.value = item
  await loadMessages()
}

async function block() {
  if (!conversationId.value) { uni.showToast({ title: '会话信息缺失', icon: 'none' }); return }
  uni.showModal({ title: blocked.value ? '解除屏蔽' : '屏蔽对方', content: blocked.value ? '解除后可继续发送消息。' : '屏蔽后，双方都无法继续向对方发送消息。', success: async result => {
    if (!result.confirm) return
    try {
      await request.post(`tl/conversations/${conversationId.value}/block`, { blocked: !blocked.value }, {}, true)
      blocked.value = !blocked.value
      await loadMessages(true)
      uni.showToast({ title: blocked.value ? '已屏蔽' : '已解除屏蔽', icon: 'none' })
    }
    catch (e) { uni.showToast({ title: e.msg || '操作失败', icon: 'none' }) }
  } })
}

async function loadBlockStatus() {
  if (!conversationId.value) { blocked.value = false; return }
  try { const result = await request.get(`tl/conversations/${conversationId.value}/block`); blocked.value = Boolean(result.data) }
  catch (e) { blocked.value = false }
}

async function report() {
  const last = messages.value[messages.value.length - 1]
  uni.showModal({ title: '举报会话', editable: true, placeholderText: '请简述原因', success: async result => {
    const reason = result.confirm ? result.content?.trim() : ''
    if (!reason) return
    try { await request.post('tl/conversations/reports', { conversationId: Number(conversationId.value), messageId: last?.id || null, reason }); uni.showToast({ title: '举报已提交', icon: 'none' }) }
    catch (e) { uni.showToast({ title: e.msg || '举报失败', icon: 'none' }) }
  } })
}

function formatDate(value) { return value ? String(value).replace('T', ' ').slice(0, 16) : '' }
function login() { toLogin() }
function back() { conversationId.value = ''; loadConversations() }
function clearPolling() { if (pollTimer) { clearInterval(pollTimer); pollTimer = null } }
</script>

<style lang="scss" scoped>
.page { min-height: 100vh; background: #f3f3f3; padding-bottom: 110rpx; box-sizing: border-box; }
.nav { height: 96rpx; background: #e4e4e4; display: flex; align-items: center; justify-content: space-between; padding: 0 30rpx; font-size: 30rpx; font-weight: 600; }
.state { padding: 120rpx 20rpx; text-align: center; color: #777; }
.conversation { background: #fff; padding: 22rpx 28rpx; display: flex; align-items: center; gap: 20rpx; border-bottom: 1rpx solid #eee; }
.avatar { width: 80rpx; height: 80rpx; border-radius: 50%; background: #e8e8e8; display: flex; align-items: center; justify-content: center; color: #555; }
.summary { flex: 1; min-width: 0; display: flex; flex-direction: column; gap: 10rpx; }.title { font-size: 27rpx; color: #222; }.last,.time { font-size: 21rpx; color: #999; }.time { flex: none; }
.unread { min-width: 34rpx; height: 34rpx; line-height: 34rpx; padding: 0 8rpx; border-radius: 20rpx; background: #d74c3d; color: #fff; text-align: center; font-size: 18rpx; }
.thread { height: calc(100vh - 260rpx); padding: 20rpx 24rpx; box-sizing: border-box; }.message-row { display: flex; margin: 16rpx 0; }.message-row.mine { justify-content: flex-end; }.bubble { max-width: 75%; padding: 18rpx 22rpx; border-radius: 16rpx; background: #fff; color: #222; font-size: 26rpx; line-height: 1.5; word-break: break-all; }.mine .bubble { background: #dcebd9; }.end-anchor { height: 1rpx; }.send-paused { padding: 22rpx 30rpx; text-align: center; color: #999; background: #fff; font-size: 23rpx; }
.composer { position: fixed; bottom: 74rpx; left: 0; right: 0; padding: 16rpx 20rpx; display: flex; gap: 14rpx; background: #fff; }.composer input { flex: 1; background: #f3f3f3; padding: 14rpx 20rpx; border-radius: 34rpx; font-size: 25rpx; }.composer button { margin: 0; font-size: 24rpx; }
.thread-actions { position: fixed; bottom: 0; left: 0; right: 0; height: 72rpx; background: #fff; display: flex; align-items: center; justify-content: center; gap: 50rpx; color: #888; font-size: 21rpx; }
</style>
