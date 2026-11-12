# TeasLeisure（TL）

TeasLeisure 是本地地摊发现与互动平台。项目复用 CRMEB Java 的认证、后台 RBAC、上传和运营管理能力，扩展地摊、社区、私信与内容审核领域。

## 产品范围

- 按城市、区域、分类、距离、今日出摊和当前营业状态浏览摊位。
- 个人用户可提交摊位线索；经营者可申请认领，审核通过后管理摊位。
- 摊主为已认领摊位发布动态。个人用户可浏览动态、评论/回复、点赞、收藏和举报。
- 私信支持屏蔽、举报、频控、未读提示和已读游标；运营端统一审核投稿、认领、修订、动态、评论和举报。
- 动态不自动过期，也不提供续期；作者删除或平台审核隐藏后下线。
- 首版暂缓在线交易、支付、配送、拼团采购和分销。

详细业务边界见 [产品概览](docs/PRODUCT_OVERVIEW.md)，本地配置见 [开发说明](docs/LOCAL_DEVELOPMENT.md)。

## 技术架构

- Java 17、Spring Boot 4.1.2、MyBatis-Plus、PageHelper。
- MySQL 8.4、Redis/Valkey、对象存储。
- uni-app Vue 3/Vite 支持 H5 和微信小程序；Vue 管理端支持平台审核。
- 按 account、stall、community、conversation、moderation 组织业务边界。

CRMEB Java 上游：<https://github.com/crmeb/crmeb_java>。根目录保留上游许可证和源码版权声明；电商订单、支付、库存和营销模块与 TL 业务分离。

## 本地开发

准备 JDK 17、Maven 3.9+、Node.js 20+、MySQL 8.4 和 Redis/Valkey。复制 `.env.example` 为本地 `.env`，填写数据库密码、JWT 密钥和资源地址；`.env` 不要提交。

1. 导入 CRMEB 基线 SQL：`backend/sql/Crmeb_v3.1.sql`。
2. 按 [迁移工具说明](backend/tools/tl-migrate/README.md)检查并应用数据库迁移。
3. 在仓库根目录构建后端：

   ```powershell
   mvn -f backend/pom.xml -pl crmeb-admin,crmeb-front -am -DskipTests clean package
   ```

4. 使用 `tl-local` profile 启动 front/admin；默认 API 端口分别为 `8081` 和 `8080`。
5. 在 `frontend/uni-app` 执行 `npm ci`，使用 `npm run dev:h5` 启动 H5，使用 `npm run build:mp-weixin` 构建微信小程序。
6. 在 `frontend/admin` 执行 `npm ci` 和 `npm run dev` 启动管理端。

生产前端构建需设置 `VITE_TL_API_URL` 为 HTTPS API 根域名；`VITE_TL_H5_URL` 用于生成 H5 分享链接。

## 许可证

仓库保留 CRMEB 上游许可证和源码版权声明。Maven/npm 依赖及随包图片、主题资源遵循各自许可证。