# TeasLeisure (TL)

TeasLeisure is a local street-stall discovery and community platform. It reuses CRMEB Java capabilities for authentication, admin RBAC, file uploads, and operations while adding TL domains for stalls, community, conversations, and moderation.

## MVP

- Browse stalls by city, district, category, distance, daily availability, and current business hours.
- Individuals submit stall leads; merchants claim stalls and manage them after platform review.
- Merchants publish posts tied to claimed stalls. Users comment, reply, like, save, and report content.
- Private messages support blocking, reporting, rate limits, unread counts, and read cursors. Operators review submissions and retain moderation history.
- Posts have no automatic expiry or renewal. Author deletion and moderation hide them from public views.
- Online checkout, payment, delivery, group buying, and distribution are outside the first release.

See [Product overview](docs/PRODUCT_OVERVIEW.md) and [Local development](docs/LOCAL_DEVELOPMENT.md).

## Stack

Java 17, Spring Boot 4.1.2, MyBatis-Plus, PageHelper, MySQL, Redis/Valkey, uni-app Vue 3/Vite for H5 and WeChat Mini Program, and a Vue admin console.

The codebase is based on [CRMEB Java](https://github.com/crmeb/crmeb_java). Upstream license and source notices are retained. Commerce order, payment, inventory, and marketing modules remain separate from TL domains.

## Development

Install JDK 17, Maven 3.9+, Node.js 20+, MySQL 8.4, and Redis/Valkey. Copy `.env.example` to a private `.env` file and fill in credentials. Never commit `.env`.

1. Import the CRMEB baseline SQL from `backend/sql/Crmeb_v3.1.sql`.
2. Inspect and apply TL database migrations using [the migration tool](backend/tools/tl-migrate/README.md).
3. Build the backend:

   ```powershell
   mvn -f backend/pom.xml -pl crmeb-admin,crmeb-front -am -DskipTests clean package
   ```

4. Run front/admin with the `tl-local` profile. Default API ports are front `8081` and admin `8080`.
5. In `frontend/uni-app`, run `npm ci` and `npm run dev:h5`; use `npm run build:mp-weixin` for a WeChat Mini Program build.
6. In `frontend/admin`, run `npm ci` and `npm run dev`.

Production builds require `VITE_TL_API_URL` with an HTTPS API origin. `VITE_TL_H5_URL` sets the H5 share origin.

## License

The repository retains the CRMEB upstream license and source notices. Third-party Maven/npm dependencies and bundled imagery or themes follow their respective licenses.