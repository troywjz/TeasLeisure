# TeasLeisure（TL）

[English](README.md)

TeasLeisure 是面向本地地摊商家和消费者的信息发现与社区互动平台。仓库基于 CRMEB Java，复用其账号认证、管理端权限和文件上传能力，并扩展摊位、社区、私信和内容审核模块。

## 功能概览

- 按城市、区域、分类、距离和营业状态发现摊位。
- 个人用户提交摊位线索；经营者申请认领，通过审核后管理摊位。
- 摊主发布摊位动态；用户评论、回复、点赞、收藏和举报。
- 用户之间私信，支持屏蔽、举报、频控、未读数和已读游标。
- 运营后台审核摊位投稿、认领、修订、动态、评论和举报。
- 动态默认不自动过期，也不提供续期；作者删除或平台审核隐藏后下线。
- 首版不包含在线交易、支付、配送、拼团和分销。

业务范围见[产品概览](docs/PRODUCT_OVERVIEW.md)。

## 在线演示

两个演示都使用本地模拟数据，不需要数据库、Redis 或微信授权。

**H5 用户端**（Node.js 20+）：

```powershell
cd frontend/uni-app
npm ci
npm run dev:h5:demo
```

打开终端显示的 Vite 地址，并访问 `/#/pages/tl/index`。演示包含摊位分类、示例动态、摊位详情和评论。

**Web 运营端**（Python 3）：

```powershell
cd frontend/admin/public
python -m http.server 5174 --bind 127.0.0.1
```

打开 <http://127.0.0.1:5174/teasleisure-demo.html>。审核列表支持本地筛选、搜索、通过和退回操作。操作只保存在当前页面中。

演示限制和截图入口见[Demo 指南](docs/DEMO.md)。登录、商家认领、发布、上传和私信等真实操作需要后端及账号服务。

## 技术栈

- Java 17、Spring Boot 4.1.2、MyBatis-Plus、PageHelper
- MySQL 8.4、Redis/Valkey
- uni-app、Vue 3、Vite：H5 和微信小程序
- Vue 管理端：平台运营和审核

代码以 [CRMEB Java](https://github.com/crmeb/crmeb_java) 为基础。CRMEB 商城订单、支付、库存和营销模块与 TL 地摊业务分开维护。

## 本地完整开发环境

准备 Git for Windows、JDK 17、Maven 3.9+、Node.js 20+、MySQL 8.4 和 Redis/Valkey。复制根目录 `.env.example` 为本机 `.env`，填写数据库账号密码、随机 JWT 密钥和可写的图片目录；不要提交 `.env`。

Java 不会自动读取仓库根目录的 `.env`。每个启动后端的 PowerShell 窗口都要先从仓库根目录执行：

```powershell
. .\scripts\load-env.ps1
```

### 初始化数据库

1. 创建本地开发数据库和有建表权限的数据库账号。
2. 导入 `backend/sql/Crmeb_v3.1.sql`。
3. 按[迁移工具说明](backend/tools/tl-migrate/README.md)检查并应用 TL 数据库迁移。

MySQL 默认端口 `3306`，Redis/Valkey 默认端口 `6379`。详细环境变量和换电脑步骤见[本地开发说明](docs/LOCAL_DEVELOPMENT.md)。

### 构建并启动后端

在仓库根目录构建：

```powershell
mvn -f backend/pom.xml -pl crmeb-admin,crmeb-front -am -DskipTests clean package
```

在两个独立的 PowerShell 窗口中分别加载 `.env` 并启动：

```powershell
. .\scripts\load-env.ps1
java -jar backend/crmeb-front/target/Crmeb-front.jar --spring.profiles.active=tl-local
```

```powershell
. .\scripts\load-env.ps1
java -jar backend/crmeb-admin/target/Crmeb-admin.jar --spring.profiles.active=tl-local
```

前台 API 默认监听 `8081`，管理端 API 默认监听 `8080`。图片目录需要存在并允许后端写入。

### 运行前端

H5 真实 API 开发模式：

```powershell
cd frontend/uni-app
npm ci
npm run dev:h5
```

微信小程序构建：

```powershell
npm run build:mp-weixin
```

管理端 Web：

```powershell
cd frontend/admin
npm ci
npm run dev
```

H5 开发 API 地址可在 `frontend/uni-app/.env.development` 设置 `VITE_TL_API_URL`。生产构建需要 HTTPS API 根域名；`VITE_TL_H5_URL` 用于分享链接。

## 需要自行配置的外部服务

- 微信 AppID/Secret、小程序域名白名单和直播权限
- 地图服务密钥、短信服务、对象存储凭据
- 生产环境 HTTPS API 域名、CORS 和文件上传目录

本地模拟演示不依赖这些凭据。正式开放对应功能时，再按实际使用情况配置。

## CI 与安全状态

GitHub Actions 会检查后端测试、H5/小程序构建、管理端构建和 CodeQL。更新依赖时请先确认兼容性，再合并 Dependabot PR。

## 许可证

仓库采用 Apache License 2.0，见根目录 [LICENSE](LICENSE)。保留 CRMEB 版权声明；第三方依赖与资源保留其各自的许可证声明。
