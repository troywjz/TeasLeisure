# Local Development

## Requirements

- Git for Windows
- JDK 17 and Maven 3.9+
- Node.js 20+
- MySQL 8.4 for the standard local setup
- Redis or Valkey
- Python 3 for the standalone platform web demo

## Environment

Copy `.env.example` to `.env` and set local DB credentials, a random JWT secret of at least 32 bytes, and a writable image path. Keep `.env` out of version control. Java and Vite do not automatically read the repository-root `.env`; load it into each PowerShell process before starting Java with:

```powershell
. .\scripts\load-env.ps1
```

Dot-sourcing keeps settings in the current PowerShell session. Run this in each terminal that starts a backend process. Vite H5 development settings come from `frontend/uni-app/.env.development`; set `VITE_TL_API_URL` there when the API does not use the local default `http://127.0.0.1:8081`. The `tl-local` Spring profile prefers `TL_*` variables and accepts legacy `TEAYU_*` aliases for existing installs.

## Database

1. Install MySQL and Redis/Valkey, then create a database and a DB user with schema migration privileges.
2. Import `backend/sql/Crmeb_v3.1.sql` into the empty database.
3. From the repository root, dot-source `.\scripts\load-env.ps1`, then use `backend/tools/tl-migrate/README.md` to inspect the plan, check the baseline, and apply ordered migrations.

The first 16 TeaYu migration files are immutable because existing catalogs record their SHA-256 hashes. Migration 17 renames persisted business tables and menu permissions to the TL namespace. The migration ledger retains its legacy name so an existing catalog can resume safely.

## Build and run

From the repository root:

```powershell
mvn -f backend/pom.xml -pl crmeb-admin,crmeb-front -am -DskipTests clean package
```

Run each command from a separate PowerShell window at the repository root. Dot-source the env loader in each window first. The front and admin jars listen on `8081` and `8080`; MySQL and Redis/Valkey commonly use `3306` and `6379`. Create the image directory configured by `TL_IMAGE_PATH` and grant the app user write access. Use an administrator account created in your own CRMEB development database.

```powershell
. .\scripts\load-env.ps1
java -jar backend/crmeb-front/target/Crmeb-front.jar --spring.profiles.active=tl-local
```

```powershell
. .\scripts\load-env.ps1
java -jar backend/crmeb-admin/target/Crmeb-admin.jar --spring.profiles.active=tl-local
```

The local admin profile uses in-memory Quartz, pauses scheduling, and disables install-statistics reporting.

For H5 and WeChat Mini Program:

```powershell
cd frontend/uni-app
npm ci
npm run dev:h5
npm run build:mp-weixin
```

The current DCloud Vite plugin declares Vite `5.2.8` as an exact peer, while the repository pins the security-fixed Vite `6.4.5`; `frontend/uni-app/.npmrc` enables legacy peer resolution for this verified pairing. H5 and Mini Program builds both pass. Recheck this constraint when updating the DCloud suite.

Set `VITE_TL_API_URL` for production builds. Its value must be an HTTPS API root URL; `VITE_TL_H5_URL` sets the share-link origin.

For the Vue admin UI:

```powershell
cd frontend/admin
npm install
npm run dev
```

`frontend/admin` currently has no lockfile, so use `npm install` there. The H5 project has a lockfile and uses `npm ci`.

## Moving to another computer

1. Clone the GitHub repository and install the requirements above.
2. Copy `.env.example` to an ignored local `.env`; set `TL_DB_URL`, `TL_DB_USER`, `TL_DB_PASSWORD`, `TL_REDIS_*`, `TL_JWT_SECRET`, and `TL_IMAGE_PATH` for that computer. Keep `TL_IMAGE_PATH` writable.
3. Start MySQL and Redis/Valkey, create/import the database, and apply the migration manifest as described above.
4. Build the jars, start front/admin in separate PowerShell windows, then start H5 with `npm ci` and `npm run dev:h5`.
5. For a no-backend preview, run `npm run dev:h5:demo` in `frontend/uni-app`; serve the Web moderation demo from `frontend/admin/public` with Python 3. It uses fake local data and requires no credentials.

WeChat AppID/secret, a map provider key, SMS, object storage, and production HTTPS domains are external integration settings. The H5 and local web demos work without them. WeChat builds, store submission, production upload, and public H5 deployment require the corresponding account credentials, domain allowlists, HTTPS API URL, and CORS configuration.
