# Local Development

## Requirements

- JDK 17 and Maven 3.9+
- Node.js 20+
- MySQL 8.4 for the standard local setup
- Redis or Valkey

## Environment

Copy `.env.example` to `.env` and provide local DB credentials, a high-entropy JWT secret, and asset path. Keep `.env` out of version control. The `tl-local` Spring profile prefers `TL_*` variables and temporarily accepts `TEAYU_*` aliases for existing local setups.

## Database

1. Create an empty TL development database.
2. Import `backend/sql/Crmeb_v3.1.sql`.
3. Use `backend/tools/tl-migrate/README.md` to inspect the plan, check the baseline, and apply ordered migrations.

The first 16 TeaYu migration files are immutable because existing catalogs record their SHA-256 hashes. Migration 17 renames persisted business tables and menu permissions to the TL namespace. The migration ledger retains its legacy name so an existing catalog can resume safely.

## Build and run

From the repository root:

```powershell
mvn -f backend/pom.xml -pl crmeb-admin,crmeb-front -am -DskipTests clean package
```

Run the generated front and admin jars with `--spring.profiles.active=tl-local`. Default API ports are front `8081` and admin `8080`. The local admin profile uses in-memory Quartz, pauses scheduling, and disables install-statistics reporting.

For H5 and WeChat Mini Program:

```powershell
cd frontend/uni-app
npm ci
npm run dev:h5
npm run build:mp-weixin
```

Set `VITE_TL_API_URL` for production builds. Its value must be an HTTPS API root URL; `VITE_TL_H5_URL` sets the share-link origin.

For the Vue admin UI:

```powershell
cd frontend/admin
npm ci
npm run dev
```