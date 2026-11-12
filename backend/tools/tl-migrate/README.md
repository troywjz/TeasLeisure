# TeasLeisure SQL migration tool

This standalone Java 8 command-line tool adds an ordered, checksummed ledger for the TeasLeisure SQL files. It preserves the historical first 16 migration filenames and checksums. Migration 17 renames the business tables and menu permission namespace to `tl`; the legacy ledger table and advisory-lock name remain stable so existing catalogs resume from their recorded checksums. The MySQL Connector/J dependency already declared in `backend/pom.xml` is the only runtime dependency.

## Order

The manifest is deliberately explicit in `TlMigrator.java`; a missing file stops the run. Add future migrations by appending a uniquely named file to that list after reviewing dependencies. Existing applied file bytes are identified by SHA-256. A matching ledger row is skipped; a changed script with the same filename stops the run.

## Build and run

Build from this directory. The isolated POM writes compiled classes and the executable jar under `target/`; the dependency copy step writes Connector/J there too. `--plan` prints order and SHA-256 without database access. `--check` uses SELECT and INFORMATION_SCHEMA reads to check required tables, every migrated TeaYu column, all 30 explicitly named indexes, the daily-status foreign key, nine menu permission seeds, six canonical category names and any existing ledger. `--baseline` requires a successful structural check, an empty or absent ledger, and exact catalog confirmation; it records current file names/checksums without executing migration SQL. `--apply` reads DB environment variables and executes the migration manifest. Missing or unknown mode prints usage and exits nonzero.

```powershell
Set-Location backend/tools/tl-migrate
mvn clean package dependency:copy-dependencies -DincludeScope=runtime
$classpath = 'target/tl-migrate-0.1.0.jar;target/dependency/*'
java -cp $classpath com.tl.migrate.TlMigrator --plan ../../../backend/sql

$env:TL_DB_URL = 'jdbc:mysql://db-host:3306/teasleisure?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai'
$env:TL_DB_USER = 'migration-user'
$env:TL_DB_PASSWORD = '<supply securely>'
# Read-only structural and ledger review:
java -cp $classpath com.tl.migrate.TlMigrator --check ../../../backend/sql
# Existing manually migrated catalog only, after reviewing --check and comparing to the approved schema:
java -cp $classpath com.tl.migrate.TlMigrator --baseline --confirm-baseline teasleisure ../../../backend/sql
# Fresh schema or a catalog with 16 historical TeaYu migrations:
java -cp $classpath com.tl.migrate.TlMigrator --apply ../../../backend/sql
```

Do not put credentials in command-line arguments, shell history, or logs. Apply mode prefers `TL_DB_URL`, `TL_DB_USER`, and `TL_DB_PASSWORD`, and falls back to the historical `TEAYU_DB_*` variables for existing local environments; it never prints their values. Use a dedicated deployment identity with permission to create the ledger and apply the reviewed TeasLeisure DDL. The DB name is selected by the JDBC URL. The default SQL folder is `backend/sql`, relative to the current working directory.

## Operational behavior and limits

- A MySQL advisory lock serializes this runner; lock acquisition waits up to 30 seconds.
- The historical ledger is `teayu_schema_migration(version, script_name, checksum, installed_at)` and the advisory lock is `teayu_schema_migration_v1`; both names are retained as migration metadata for compatibility.
- Scripts run in manifest order. Any SQL or checksum error stops later scripts.
- MySQL DDL may commit implicitly. If a script fails partway through, inspect the database and reconcile it before rerunning; the runner intentionally does not mark a partially completed file as installed.
- The parser supports ordinary SQL statements, single/double/backtick quotes, `--`, `#`, and block comments. It does not implement MySQL client directives such as `DELIMITER`, stored-program bodies, or custom delimiters. Keep migration files within the supported format.
- Existing databases where the first 16 files have a valid ledger receive migration 17 as an upgrade. It renames `eb_teayu_*` business tables to `eb_tl_*`, updates `teayu:*` permission values and the admin menu component path, then records its checksum. The first 16 SQL files remain unchanged so their existing hashes continue to validate. Review the target catalog and take a backup before applying migration 17.
- Baseline procedure: take a backup; run `--check`; independently compare the required objects with the approved target catalog and confirm its exact catalog name; ensure the ledger is absent or empty; then invoke `--baseline --confirm-baseline <catalog>`. Baseline only writes ledger rows after every check passes; it never executes migration SQL. Partial/non-empty ledger, unexpected version, checksum conflict, missing structure, category seed, or catalog mismatch stops baseline. `--check` creates no tables and changes no data.
- This is a review prototype. No database connection or migration execution is part of its preparation.
