<template>
  <div class="divBox">
    <el-card shadow="never">
      <template #header>
        <div class="review-header">
          <strong>TeasLeisure 内容审核</strong>
          <el-button type="primary" @click="openOperatorCreate" v-hasPermi="['tl:stall:review']">平台录入摊位</el-button>
        </div>
      </template>
      <el-dialog v-model="createDialog" title="平台录入摊位（提交审核）" width="760px" :close-on-click-modal="false">
        <el-form ref="createFormRef" :model="createForm" :rules="createRules" label-width="110px">
          <el-form-item label="摊位名称" prop="name"><el-input v-model="createForm.name" maxlength="120" show-word-limit /></el-form-item>
          <el-form-item label="摊位分类" prop="categoryId">
            <el-select v-model="createForm.categoryId" placeholder="选择摊位分类" style="width: 100%">
              <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="摊位介绍"><el-input v-model="createForm.description" type="textarea" :rows="3" maxlength="2000" show-word-limit /></el-form-item>
          <el-form-item label="详细地址"><el-input v-model="createForm.address" maxlength="500" /></el-form-item>
          <el-form-item label="省市区">
            <el-cascader v-model="cityPath" :options="cityTree" :props="cityProps" clearable filterable style="width: 100%" placeholder="选择省 / 市 / 区县" @change="handleCreateRegionChange" />
          </el-form-item>
          <el-form-item label="经纬度">
            <div class="coordinate-fields">
              <el-input-number v-model="createForm.longitude" :precision="7" :step="0.0001" controls-position="right" placeholder="经度" />
              <el-input-number v-model="createForm.latitude" :precision="7" :step="0.0001" controls-position="right" placeholder="纬度" />
            </div>
          </el-form-item>
          <el-form-item label="营业时间"><el-input v-model="createForm.businessHours" maxlength="500" placeholder="例如 17:00-22:00" /></el-form-item>
          <el-form-item label="公开电话"><el-input v-model="createForm.contactPhone" maxlength="64" /></el-form-item>
          <el-form-item label="摊位图片">
            <el-upload
              v-model:file-list="uploadFiles"
              :http-request="uploadOperatorImage"
              :limit="9"
              accept="image/jpeg,image/png,image/webp"
              list-type="picture-card"
              :on-success="operatorImageUploaded"
              :on-remove="operatorImageRemoved"
              :on-exceed="imageLimitExceeded"
            >
              <span>+</span>
            </el-upload>
            <div class="upload-hint">最多 9 张，第一张作为列表封面。图片通过 CRMEB 图片上传接口保存。</div>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="createDialog = false">取消</el-button>
          <el-button :loading="duplicateLoading" @click="checkOperatorDuplicates">查重</el-button>
          <el-button type="primary" :loading="creating" @click="submitOperatorStall">提交到待审核队列</el-button>
        </template>
      </el-dialog>
      <el-dialog v-model="duplicateDialog" title="潜在重复摊位候选" width="860px">
        <el-alert type="info" :closable="false" show-icon title="候选结果只供人工核对；系统不会自动合并、驳回或公开摊位。" />
        <el-table v-loading="duplicateLoading" :data="duplicateCandidates" border stripe empty-text="未找到潜在重复摊位">
          <el-table-column prop="id" label="ID" width="90" />
          <el-table-column prop="name" label="名称" min-width="160" show-overflow-tooltip />
          <el-table-column label="区域 / 地址" min-width="200" show-overflow-tooltip>
            <template #default="scope">{{ stallRegionLabel(scope.row) }}<span v-if="scope.row.address"> · {{ scope.row.address }}</span></template>
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template #default="scope"><el-tag :type="stallStatusTag(scope.row.auditStatus)">{{ stallStatusLabel(scope.row.auditStatus) }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="sourceType" label="来源" width="110"><template #default="scope">{{ stallSourceLabel(scope.row.sourceType) }}</template></el-table-column>
          <el-table-column label="封面" width="90">
            <template #default="scope"><el-image v-if="scope.row.coverImage" :src="scope.row.coverImage" fit="cover" :preview-src-list="[scope.row.coverImage]" preview-teleported style="width: 48px; height: 48px" /></template>
          </el-table-column>
        </el-table>
        <template #footer>
          <el-button @click="duplicateDialog = false">关闭</el-button>
          <el-button v-if="duplicateContext === 'operator'" type="warning" @click="continueOperatorAfterDuplicateCheck">仍继续录入</el-button>
        </template>
      </el-dialog>
      <el-tabs v-model="active" @tab-change="handleTabChange">
        <el-tab-pane label="摊位投稿" name="stalls"><ReviewTable :rows="stalls" :loading="loading" kind="stalls" @approve="approve" @reject="reject" @check-duplicates="checkPendingStallDuplicates" /></el-tab-pane>
        <el-tab-pane label="摊位维护" name="stallManage">
          <div class="stall-filters">
            <el-input v-model="manageKeyword" clearable placeholder="摊位名称 / 地址" style="width: 220px" @keyup.enter="queryManagedStalls" />
            <el-cascader v-model="manageRegionPath" :options="cityTree" :props="cityProps" clearable filterable placeholder="省 / 市 / 区县" style="width: 260px" @change="handleManageRegionChange" />
            <el-select v-model="manageCategoryId" clearable placeholder="全部分类" style="width: 180px">
              <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
            <el-select v-model="manageAuditStatus" clearable placeholder="已发布 / 已隐藏" style="width: 160px">
              <el-option label="已发布" :value="1" />
              <el-option label="已隐藏" :value="3" />
            </el-select>
            <el-button type="primary" @click="queryManagedStalls">筛选</el-button>
            <el-button @click="resetManageFilters">清除</el-button>
          </div>
          <el-table v-loading="loading" :data="managedStalls" border stripe empty-text="暂无已发布或已隐藏摊位">
            <el-table-column prop="name" label="摊位名称" min-width="170" show-overflow-tooltip />
            <el-table-column label="区域" min-width="180" show-overflow-tooltip>
              <template #default="scope">{{ stallRegionLabel(scope.row) }}</template>
            </el-table-column>
            <el-table-column label="分类" width="140">
              <template #default="scope">{{ categoryLabel(scope.row.categoryId) }}</template>
            </el-table-column>
            <el-table-column prop="sourceType" label="来源" width="120">
              <template #default="scope">{{ stallSourceLabel(scope.row.sourceType) }}</template>
            </el-table-column>
            <el-table-column label="状态" width="100">
              <template #default="scope"><el-tag :type="scope.row.auditStatus === 1 ? 'success' : 'info'">{{ scope.row.auditStatus === 1 ? '已发布' : '已隐藏' }}</el-tag></template>
            </el-table-column>
            <el-table-column label="图片" min-width="210">
              <template #default="scope">
                <div class="review-images">
                  <el-image v-for="(url, index) in stallImages(scope.row)" :key="`${scope.row.id}-${index}`" :src="url" fit="cover" :preview-src-list="stallImages(scope.row)" :initial-index="index" preview-teleported style="width: 48px; height: 48px" />
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="130" fixed="right">
              <template #default="scope">
                <el-button v-if="scope.row.auditStatus === 1" link type="danger" @click="changeStallVisibility(scope.row, true)">隐藏</el-button>
                <el-button v-else link type="success" @click="changeStallVisibility(scope.row, false)">恢复展示</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination v-if="manageTotal > manageLimit" class="audit-pagination" :current-page="managePage" :page-size="manageLimit" :total="manageTotal" layout="prev, pager, next, total" @current-change="changeManagePage" />
        </el-tab-pane>
        <el-tab-pane label="摊主认领" name="claims"><ReviewTable :rows="claims" :loading="loading" kind="claims" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="资料修订" name="revisions"><ReviewTable :rows="revisions" :loading="loading" kind="revisions" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="动态" name="posts"><ReviewTable :rows="posts" :loading="loading" kind="posts" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="评论" name="comments"><ReviewTable :rows="comments" :loading="loading" kind="comments" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="私信举报" name="reports"><ReviewTable :rows="reports" :loading="loading" kind="reports" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="内容举报" name="contentReports"><ReviewTable :rows="contentReports" :loading="loading" kind="contentReports" @approve="approve" @reject="reject" /></el-tab-pane>
        <el-tab-pane label="操作记录" name="audit">
          <el-alert v-if="auditError" :title="auditError" type="warning" :closable="false" show-icon />
          <div class="audit-hint">按时间倒序显示 TeasLeisure 后台敏感操作记录；需要 `admin:log:sensitive:list` 权限。</div>
          <el-table v-loading="loading" :data="auditLogs" border stripe empty-text="暂无 TeasLeisure 操作记录">
            <el-table-column prop="createTime" label="时间" width="180" />
            <el-table-column prop="adminAccount" label="管理员" width="140" show-overflow-tooltip />
            <el-table-column prop="description" label="操作" min-width="180" show-overflow-tooltip />
            <el-table-column label="接口" min-width="260" show-overflow-tooltip>
              <template #default="scope">{{ scope.row.requestMethod }} {{ scope.row.url }}</template>
            </el-table-column>
            <el-table-column prop="requestParam" label="请求内容" min-width="240" show-overflow-tooltip />
            <el-table-column label="结果" width="100">
              <template #default="scope"><el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">{{ scope.row.status === 0 ? '成功' : '异常' }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="errorMsg" label="异常说明" min-width="200" show-overflow-tooltip />
            <el-table-column prop="result" label="返回摘要" min-width="220" show-overflow-tooltip />
          </el-table>
          <el-pagination
            v-if="auditTotal > auditLimit"
            class="audit-pagination"
            :current-page="auditPage"
            :page-size="auditLimit"
            :total="auditTotal"
            layout="prev, pager, next, total"
            @current-change="changeAuditPage"
          />
        </el-tab-pane>
        <el-tab-pane label="状态历史" name="stateEvents">
          <el-alert v-if="stateEventError" :title="stateEventError" type="warning" :closable="false" show-icon />
          <div class="event-filters">
            <el-select v-model="eventTargetType" clearable placeholder="全部事件类型" style="width: 190px">
              <el-option label="摊位投稿" value="stall_submission" />
              <el-option label="摊主认领" value="stall_claim" />
              <el-option label="资料修订" value="stall_revision" />
              <el-option label="动态" value="post" />
              <el-option label="评论" value="comment" />
              <el-option label="私信举报" value="message_report" />
              <el-option label="内容举报" value="content_report" />
              <el-option label="账号状态" value="user_status" />
              <el-option label="隐藏内容" value="stall" />
            </el-select>
            <el-input v-model="eventTargetId" clearable type="number" placeholder="目标记录 ID" style="width: 180px" />
            <el-button type="primary" @click="queryStateEvents">筛选</el-button>
            <el-button @click="resetStateEventFilters">清除</el-button>
          </div>
          <el-table v-loading="loading" :data="stateEvents" border stripe empty-text="暂无状态变更记录">
            <el-table-column prop="createTime" label="时间" width="180" />
            <el-table-column prop="targetType" label="对象类型" width="130">
              <template #default="scope">{{ eventTargetTypeLabel(scope.row.targetType) }}</template>
            </el-table-column>
            <el-table-column prop="targetId" label="对象 ID" width="100" />
            <el-table-column prop="relatedId" label="关联 ID" width="100" />
            <el-table-column prop="action" label="动作" width="110">
              <template #default="scope">{{ eventActionLabel(scope.row.action) }}</template>
            </el-table-column>
            <el-table-column label="状态变化" width="190">
              <template #default="scope">{{ eventStatusLabel(scope.row.targetType, scope.row.fromStatus) }} → {{ eventStatusLabel(scope.row.targetType, scope.row.toStatus) }}</template>
            </el-table-column>
            <el-table-column label="操作者" width="180" show-overflow-tooltip>
              <template #default="scope">{{ eventActorTypeLabel(scope.row.actorType) }} · {{ scope.row.actorAccount }}</template>
            </el-table-column>
            <el-table-column prop="reason" label="处理说明" min-width="220" show-overflow-tooltip />
          </el-table>
          <el-pagination
            v-if="stateEventTotal > stateEventLimit"
            class="audit-pagination"
            :current-page="stateEventPage"
            :page-size="stateEventLimit"
            :total="stateEventTotal"
            layout="prev, pager, next, total"
            @current-change="changeStateEventPage"
          />
        </el-tab-pane>
        <el-tab-pane label="摊位分类" name="categories">
          <div class="category-toolbar"><el-button type="primary" @click="addCategory">新增分类</el-button></div>
          <el-table v-loading="loading" :data="categories" border stripe empty-text="暂无分类">
            <el-table-column prop="id" label="ID" width="90" />
            <el-table-column prop="name" label="分类名称" min-width="180" />
            <el-table-column prop="sort" label="排序" width="100" />
            <el-table-column label="状态" width="120"><template #default="scope">{{ scope.row.enabled ? '启用' : '停用' }}</template></el-table-column>
            <el-table-column label="操作" width="130"><template #default="scope"><el-button link type="primary" @click="toggleCategory(scope.row)">{{ scope.row.enabled ? '停用' : '启用' }}</el-button></template></el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { defineComponent, h, onMounted, reactive, ref, withDirectives } from 'vue'
import { ElButton, ElImage, ElMessage, ElMessageBox, ElTable, ElTableColumn, vLoading } from 'element-plus'
import request from '@/utils/request'
import { fileImageApi } from '@/api/systemSetting'

const active = ref('stalls')
const loading = ref(false)
const stalls = ref([]), claims = ref([]), revisions = ref([]), posts = ref([]), comments = ref([]), reports = ref([])
const managedStalls = ref([])
const contentReports = ref([])
const categories = ref([])
const auditLogs = ref([])
const auditError = ref('')
const auditPage = ref(1)
const auditLimit = 100
const auditTotal = ref(0)
const stateEvents = ref([])
const stateEventError = ref('')
const stateEventPage = ref(1)
const stateEventLimit = 100
const stateEventTotal = ref(0)
const eventTargetType = ref('')
const eventTargetId = ref('')
const managePage = ref(1)
const manageLimit = 20
const manageTotal = ref(0)
const manageKeyword = ref('')
const manageRegionPath = ref([])
const manageCityCode = ref('')
const manageDistrictCode = ref('')
const manageCategoryId = ref(null)
const manageAuditStatus = ref(null)
const createDialog = ref(false)
const creating = ref(false)
const createFormRef = ref(null)
const categoryOptions = ref([])
const cityTree = ref([])
const cityPath = ref([])
const uploadFiles = ref([])
const imageUrls = ref([])
let operatorOptionsPromise = null
const duplicateDialog = ref(false)
const duplicateLoading = ref(false)
const duplicateCandidates = ref([])
const duplicateContext = ref('')
const cityProps = { children: 'child', label: 'name', value: 'cityId', checkStrictly: true }
const createForm = reactive({ name: '', categoryId: null, description: '', address: '', cityCode: '', districtCode: '', longitude: null, latitude: null, businessHours: '', contactPhone: '' })
const createRules = {
  name: [{ required: true, message: '请填写摊位名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择摊位分类', trigger: 'change' }]
}
const endpoints = {
  stalls: '/admin/tl/stalls/review', stallManage: '/admin/tl/stalls/review/manage', claims: '/admin/tl/stall-claims/pending',
  duplicates: '/admin/tl/stalls/review/duplicates',
  revisions: '/admin/tl/stall-revisions', posts: '/admin/tl/community/review/posts',
  comments: '/admin/tl/community/review/comments', reports: '/admin/tl/message-reports',
  categories: '/admin/tl/categories', contentReports: '/admin/tl/content-reports',
  audit: '/admin/tl/audit-logs', stateEvents: '/admin/tl/moderation-events'
}
const storage = { stalls, claims, revisions, posts, comments, reports, categories, contentReports, audit: auditLogs, stateEvents }

async function openOperatorCreate() {
  resetOperatorForm()
  createDialog.value = true
  try {
    await loadOperatorOptions()
    if (!categoryOptions.value.length) ElMessage.warning('请先创建并启用摊位分类')
  } catch (error) {
    ElMessage.error(error.message || '地区或分类数据加载失败')
  }
}

async function loadOperatorOptions() {
  if (categoryOptions.value.length && cityTree.value.length) return
  if (!operatorOptionsPromise) {
    operatorOptionsPromise = request({ url: `${endpoints.stalls}/operator/options`, method: 'GET' })
      .then(options => {
        categoryOptions.value = options?.categories || []
        cityTree.value = options?.cityTree || []
      })
      .finally(() => { operatorOptionsPromise = null })
  }
  return operatorOptionsPromise
}

function resetOperatorForm() {
  Object.assign(createForm, { name: '', categoryId: null, description: '', address: '', cityCode: '', districtCode: '', longitude: null, latitude: null, businessHours: '', contactPhone: '' })
  cityPath.value = []
  uploadFiles.value = []
  imageUrls.value = []
  createFormRef.value?.clearValidate()
}

function handleCreateRegionChange(path) {
  createForm.cityCode = path?.length >= 2 ? String(path[1]) : ''
  createForm.districtCode = path?.length >= 3 ? String(path[2]) : ''
}

async function uploadOperatorImage(options) {
  try {
    const formData = new FormData()
    formData.append('multipart', options.file)
    const result = await fileImageApi(formData, { model: 'user', pid: 7 })
    const url = result?.url || ''
    if (!url) throw new Error('图片上传响应缺少访问地址')
    options.onSuccess({ url })
  } catch (error) {
    options.onError(error)
    ElMessage.error(error.message || '图片上传失败')
  }
}

function operatorImageUploaded(response) {
  if (response?.url && imageUrls.value.length < 9) imageUrls.value.push(response.url)
}

function operatorImageRemoved(file) {
  const url = file.response?.url || file.url
  const index = imageUrls.value.indexOf(url)
  if (index >= 0) imageUrls.value.splice(index, 1)
}

function imageLimitExceeded() { ElMessage.warning('摊位最多上传 9 张图片') }

async function submitOperatorStall() {
  if (creating.value) return
  const valid = await createFormRef.value?.validate().catch(() => false)
  if (!valid) return
  creating.value = true
  try {
    await request({
      url: `${endpoints.stalls}/operator`, method: 'POST',
      data: { ...createForm, coverImage: imageUrls.value[0] || '', imageUrls: imageUrls.value.join(',') }
    })
    ElMessage.success('摊位已创建并进入待审核队列')
    createDialog.value = false
    active.value = 'stalls'
    await loadActive()
  } catch (error) {
    ElMessage.error(error.message || '摊位录入失败')
  } finally { creating.value = false }
}

async function checkOperatorDuplicates() {
  await checkDuplicates({
    name: createForm.name,
    address: createForm.address,
    cityCode: createForm.cityCode,
    districtCode: createForm.districtCode,
    lat: createForm.latitude,
    lng: createForm.longitude
  }, 'operator')
}

async function checkPendingStallDuplicates(kind, row) {
  if (kind !== 'stalls') return
  await checkDuplicates({
    name: row.name,
    address: row.address,
    cityCode: row.cityCode,
    districtCode: row.districtCode,
    lat: row.latitude,
    lng: row.longitude,
    excludeId: row.id
  }, 'pending')
}

async function checkDuplicates(params, context) {
  if (!String(params.name || '').trim() && !String(params.address || '').trim() && (params.lat == null || params.lng == null)) {
    ElMessage.warning('请先填写名称、地址或完整经纬度')
    return
  }
  duplicateContext.value = context
  duplicateDialog.value = true
  duplicateLoading.value = true
  duplicateCandidates.value = []
  try {
    duplicateCandidates.value = await request({ url: endpoints.duplicates, method: 'GET', params }) || []
  } catch (error) {
    duplicateDialog.value = false
    ElMessage.error(error.message || '查重查询失败')
  } finally { duplicateLoading.value = false }
}

function continueOperatorAfterDuplicateCheck() { duplicateDialog.value = false }

const ReviewTable = defineComponent({
  props: { rows: { type: Array, default: () => [] }, loading: Boolean, kind: String },
  emits: ['approve', 'reject', 'checkDuplicates'],
  setup(props, { emit }) {
    const cols = {
      stalls: [['id','ID'],['name','摊位'],['address','地址'],['sourceType','来源'],['createTime','提交时间']],
      claims: [['id','ID'],['stallId','摊位ID'],['userId','申请用户'],['proof','认领说明'],['createTime','提交时间']],
      revisions: [['id','ID'],['stallId','摊位ID'],['editorUserId','修改人'],['name','摊位名称'],['address','地址'],['createTime','提交时间']],
      posts: [['id','ID'],['stallId','摊位ID'],['authorId','作者'],['content','动态内容'],['createTime','提交时间']],
      comments: [['id','ID'],['postId','动态ID'],['authorId','作者'],['content','评论内容'],['createTime','提交时间']],
      reports: [['id','ID'],['conversationId','会话ID'],['messageId','消息ID'],['reporterUserId','举报人'],['reason','举报原因'],['createTime','提交时间']],
      contentReports: [['id','ID'],['targetType','目标类型'],['targetId','目标ID'],['targetSummary','目标内容'],['targetAddress','摊位地址'],['relatedContent','关联动态'],['relatedStallName','关联摊位'],['relatedStallAddress','摊位地址'],['reporterUserId','举报人'],['reason','举报原因'],['createTime','提交时间']]
    }
    const rowImages = row => [...new Set([row.coverImage, ...(row.imageUrls || '').split(','), ...(row.proofImageUrls || '').split(',')].map(url => (url || '').trim()).filter(Boolean))]
    return () => withDirectives(h(ElTable, { data: props.rows, border: true, stripe: true, emptyText: '暂无待处理记录' }, {
      default: () => [
        ...cols[props.kind].map(([prop,label]) => h(ElTableColumn, { prop, label, minWidth: ['content','proof','reason','targetSummary','relatedContent'].includes(prop) ? 220 : 120, showOverflowTooltip: true })),
        ...(['stalls', 'revisions', 'claims'].includes(props.kind) ? [h(ElTableColumn, { label: props.kind === 'claims' ? '现场照片' : '图片', minWidth: 220 }, { default: ({ row }) => {
          const images = rowImages(row)
          return h('div', { class: 'review-images' }, images.map((url, index) => h(ElImage, {
            src: url, fit: 'cover', previewSrcList: images, initialIndex: index, previewTeleported: true,
            style: { width: '54px', height: '54px', marginRight: '6px' }
          })))
        } })] : []),
        h(ElTableColumn, { label: '操作', width: props.kind === 'stalls' ? 230 : 170, fixed: 'right' }, { default: ({ row }) => h('div', { class: 'actions' }, [
          ...(props.kind === 'stalls' ? [h(ElButton, { type: 'info', link: true, onClick: () => emit('checkDuplicates', props.kind, row) }, () => '查重')] : []),
          h(ElButton, { type: 'success', link: true, onClick: () => emit('approve', props.kind, row) }, () => ['reports','contentReports'].includes(props.kind) ? '隐藏并处理' : '通过'),
          h(ElButton, { type: 'danger', link: true, onClick: () => emit('reject', props.kind, row) }, () => ['reports','contentReports'].includes(props.kind) ? '驳回举报' : '驳回')
        ]) })
      ]
    }), [[vLoading, props.loading]])
  }
})

onMounted(loadActive)
async function handleTabChange(name) {
  active.value = name
  if (name === 'audit') auditPage.value = 1
  if (name === 'stateEvents') stateEventPage.value = 1
  if (name === 'stallManage') {
    managePage.value = 1
    try { await loadOperatorOptions() }
    catch (error) { ElMessage.error(error.message || '摊位选项加载失败') }
  }
  loadActive()
}

function handleManageRegionChange(path) {
  if (path?.length === 1) {
    manageRegionPath.value = []
    manageCityCode.value = ''
    manageDistrictCode.value = ''
    ElMessage.info('请至少选择一个城市')
    return
  }
  manageCityCode.value = path?.length >= 2 ? String(path[1]) : ''
  manageDistrictCode.value = path?.length >= 3 ? String(path[2]) : ''
}

function queryManagedStalls() { managePage.value = 1; loadActive() }
function resetManageFilters() {
  manageKeyword.value = ''
  manageRegionPath.value = []
  manageCityCode.value = ''
  manageDistrictCode.value = ''
  manageCategoryId.value = null
  manageAuditStatus.value = null
  managePage.value = 1
  loadActive()
}
function changeManagePage(page) { managePage.value = page; loadActive() }

function findCityName(code, nodes = cityTree.value) {
  const wanted = String(code || '')
  for (const node of nodes || []) {
    if (String(node.cityId) === wanted) return node.name
    const childName = findCityName(code, node.child || [])
    if (childName) return childName
  }
  return ''
}
function stallRegionLabel(stall) {
  return [findCityName(stall.cityCode), findCityName(stall.districtCode)].filter(Boolean).join(' / ') || stall.address || '地区未设置'
}
function categoryLabel(categoryId) { return categoryOptions.value.find(item => Number(item.id) === Number(categoryId))?.name || '—' }
function stallSourceLabel(sourceType) { return ({ operator: '平台录入', user_submission: '用户投稿', merchant_submission: '商家入驻' })[sourceType] || sourceType || '—' }
function stallStatusLabel(status) { return ({ 0: '待审核', 1: '已发布', 2: '已驳回', 3: '已隐藏' })[status] || '状态未知' }
function stallStatusTag(status) { return status === 1 ? 'success' : status === 0 ? 'warning' : 'info' }
function stallImages(stall) {
  return [...new Set([stall.coverImage, ...(stall.imageUrls || '').split(',')].map(url => (url || '').trim()).filter(Boolean))]
}

async function changeStallVisibility(stall, hide) {
  const actionText = hide ? '隐藏' : '恢复展示'
  let reason
  try {
    const result = await ElMessageBox.prompt(`请输入${actionText}理由，最多500字`, `${actionText}摊位`, {
      inputType: 'textarea', inputMaxlength: 500, inputValidator: value => !!value?.trim() && value.trim().length <= 500 || '请填写1到500字的理由',
      confirmButtonText: actionText, cancelButtonText: '取消'
    })
    reason = result.value.trim()
  } catch { return }
  try {
    await request({ url: `${endpoints.stallManage.replace('/manage', '')}/${stall.id}/${hide ? 'hide' : 'restore'}`, method: 'POST', data: { reason } })
    ElMessage.success(hide ? '摊位已隐藏' : '摊位已恢复展示')
    await loadActive()
  } catch (error) { ElMessage.error(error.message || `${actionText}失败`) }
}

function changeAuditPage(page) {
  auditPage.value = page
  loadActive()
}

function queryStateEvents() {
  stateEventPage.value = 1
  loadActive()
}

function resetStateEventFilters() {
  eventTargetType.value = ''
  eventTargetId.value = ''
  stateEventPage.value = 1
  loadActive()
}

function changeStateEventPage(page) {
  stateEventPage.value = page
  loadActive()
}

async function loadActive() {
  loading.value = true
  try {
    const params = active.value === 'claims' ? {} : active.value === 'audit'
      ? { page: auditPage.value, limit: auditLimit }
      : active.value === 'stateEvents'
        ? { page: stateEventPage.value, limit: stateEventLimit }
        : active.value === 'stallManage'
          ? { page: managePage.value, limit: manageLimit }
        : { page: 1, limit: 100 }
    if (active.value === 'stallManage') {
      if (manageKeyword.value.trim()) params.keyword = manageKeyword.value.trim()
      if (manageCityCode.value) params.cityCode = manageCityCode.value
      if (manageDistrictCode.value) params.districtCode = manageDistrictCode.value
      if (manageCategoryId.value != null) params.categoryId = manageCategoryId.value
      if (manageAuditStatus.value != null) params.auditStatus = manageAuditStatus.value
    }
    if (active.value === 'stateEvents') {
      if (eventTargetType.value) params.targetType = eventTargetType.value
      const targetId = String(eventTargetId.value || '').trim()
      if (targetId) {
        if (!/^\d+$/.test(targetId) || Number(targetId) <= 0) throw new Error('目标记录 ID 必须为正整数')
        params.targetId = targetId
      }
    }
    const result = await request({ url: endpoints[active.value], method: 'GET', params })
    const rows = Array.isArray(result) ? result : (result?.list || [])
    if (active.value === 'audit') {
      auditError.value = ''
      auditLogs.value = rows
      auditTotal.value = Number(result?.total || 0)
    } else if (active.value === 'stateEvents') {
      stateEventError.value = ''
      stateEvents.value = rows
      stateEventTotal.value = Number(result?.total || 0)
    } else if (active.value === 'stallManage') {
      managedStalls.value = rows
      manageTotal.value = Number(result?.total || 0)
    } else storage[active.value].value = rows
  } catch (error) {
    if (active.value === 'audit') {
      auditLogs.value = []
      auditTotal.value = 0
      auditError.value = `操作记录加载失败（需 CRMEB 权限 admin:log:sensitive:list）：${error.message || '请求失败'}`
    } else if (active.value === 'stateEvents') {
      stateEvents.value = []
      stateEventTotal.value = 0
      stateEventError.value = error.message || '状态历史加载失败'
    } else ElMessage.error(error.message || '审核列表加载失败')
  }
  finally { loading.value = false }
}
function eventTargetTypeLabel(type) {
  return ({ stall_submission: '摊位投稿', stall_claim: '摊主认领', stall_revision: '资料修订', post: '动态', comment: '评论', message_report: '私信举报', content_report: '内容举报', user_status: '账号状态', stall: '摊位内容' })[type] || type || '—'
}
function eventActionLabel(action) {
  return ({ approve: '审核通过', reject: '审核驳回', resolve: '处理举报', dismiss: '驳回举报', hide: '隐藏内容', ban: '禁用账号', restore: '恢复账号', operator_create: '平台录入待审核' })[action] || action || '—'
}
function eventActorTypeLabel(actorType) {
  return ({ admin: '后台管理员', front_staff: '移动运营' })[actorType] || actorType || '—'
}
function eventStatusLabel(targetType, status) {
  if (status == null) return '—'
  if (targetType === 'user_status') return status === 1 ? '正常' : status === 0 ? '禁用' : String(status)
  if (status === 0) return '待审核'
  if (status === 1) return ['message_report', 'content_report'].includes(targetType) ? '已处理' : '已发布/通过'
  if (status === 2) return ['message_report', 'content_report'].includes(targetType) ? '已驳回' : '审核驳回'
  if (status === 3) return '已隐藏'
  return String(status)
}
async function approve(kind, row) { await decide(kind, row, true) }
async function reject(kind, row) { await decide(kind, row, false) }
async function decide(kind, row, accepted) {
  let note = ''
  if (!accepted || kind==='stalls' || kind==='claims' || kind==='revisions' || kind==='reports' || kind==='contentReports') {
    try { const result = await ElMessageBox.prompt(accepted ? '审核说明（选填）' : '请填写驳回/处理说明', accepted ? '审核通过' : '审核处理', { inputValue: '', inputValidator: value => accepted || !!value?.trim() || '请填写处理说明', confirmButtonText: accepted ? '通过' : '确认处理', cancelButtonText: '取消' }); note = result.value }
    catch { return }
  }
  const routes = {
    stalls: `/admin/tl/stalls/review/${row.id}/${accepted?'approve':'reject'}`,
    claims: `/admin/tl/stall-claims/${row.id}/${accepted?'approve':'reject'}`,
    revisions: `/admin/tl/stall-revisions/${row.id}/${accepted?'approve':'reject'}`,
    posts: `/admin/tl/community/review/posts/${row.id}/${accepted?'approve':'reject'}`,
    comments: `/admin/tl/community/review/comments/${row.id}/${accepted?'approve':'reject'}`,
    reports: `/admin/tl/message-reports/${row.id}/${accepted?'resolve':'dismiss'}`,
    contentReports: `/admin/tl/content-reports/${row.id}/${accepted?'resolve':'dismiss'}`
  }
  const params = note ? { note } : {}
  if (kind === 'contentReports' && accepted) params.hideTarget = true
  try { await request({ url: routes[kind], method: 'POST', params }); ElMessage.success('处理完成'); await loadActive() }
  catch (error) { ElMessage.error(error.message || '处理失败') }
}

async function addCategory() {
  try {
    const result = await ElMessageBox.prompt('输入分类名称', '新增摊位分类', { inputValidator: value => !!value?.trim() || '分类名称不能为空', confirmButtonText: '创建' })
    const nextSort = (categories.value.reduce((max, item) => Math.max(max, Number(item.sort) || 0), 0) || 0) + 10
    await request({ url: endpoints.categories, method: 'POST', data: { name: result.value.trim(), sort: nextSort, enabled: true } })
    ElMessage.success('分类已创建')
    await loadActive()
  } catch (error) { if (error !== 'cancel' && error !== 'close') ElMessage.error(error.message || '创建失败') }
}

async function toggleCategory(row) {
  try {
    await request({ url: endpoints.categories, method: 'POST', data: { ...row, enabled: !row.enabled } })
    ElMessage.success(row.enabled ? '分类已停用' : '分类已启用')
    await loadActive()
  } catch (error) { ElMessage.error(error.message || '更新失败') }
}
</script>

<style scoped>
.actions { display: flex; align-items: center; gap: 12px; }
.category-toolbar { display: flex; justify-content: flex-end; margin-bottom: 14px; }
.event-filters { display: flex; align-items: center; gap: 10px; margin-bottom: 14px; }
.audit-pagination { justify-content: flex-end; margin-top: 16px; }
.audit-hint { margin: 14px 0; color: #777; font-size: 13px; }
.audit-pagination { justify-content: flex-end; margin-top: 16px; }
.review-images { display: flex; flex-wrap: wrap; gap: 6px; }
</style>
