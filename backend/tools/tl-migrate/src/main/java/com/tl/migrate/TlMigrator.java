package com.tl.migrate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Standalone, fail-fast runner for TeasLeisure's explicitly ordered MySQL migrations. */
public final class TlMigrator {
    private static final String[] MIGRATIONS = {
        "teayu-v0.1.0.sql",
        "teayu-categories-v0.1.0.sql",
        "teayu-stall-favorites-v0.1.0.sql",
        "teayu-stall-claims-v0.1.0.sql",
        "teayu-community-v0.1.0.sql",
        "teayu-conversations-v0.1.0.sql",
        "teayu-stall-revisions-v0.1.0.sql",
        "teayu-admin-ui-v0.1.0.sql",
        "teayu-content-reports-v0.1.0.sql",
        "teayu-stall-daily-status-v0.1.0.sql",
        "teayu-moderation-events-v0.1.0.sql",
        "teayu-user-status-audit-v0.1.0.sql",
        "teayu-audit-log-permission-v0.1.0.sql",
        "teayu-stall-images-v0.1.0.sql",
        "teayu-stall-claim-images-v0.1.0.sql",
        "teayu-category-encoding-fix-v0.1.0.sql",
        "teasleisure-tl-schema-v0.1.0.sql"
    };
    // Keep the existing ledger and advisory lock names so already-migrated catalogs continue from their checksums.
    private static final String LOCK_NAME = "teayu_schema_migration_v1";
    private static final String LEDGER_TABLE = "teayu_schema_migration"; // Legacy checksum ledger retained for upgrades.
    private static final String TL_RENAME_MIGRATION = "teasleisure-tl-schema-v0.1.0.sql";
    private static final String[][] TABLE_RENAMES = {
        {"eb_teayu_stall", "eb_tl_stall"}, {"eb_teayu_category", "eb_tl_category"},
        {"eb_teayu_stall_favorite", "eb_tl_stall_favorite"}, {"eb_teayu_stall_claim", "eb_tl_stall_claim"},
        {"eb_teayu_post", "eb_tl_post"}, {"eb_teayu_comment", "eb_tl_comment"},
        {"eb_teayu_reaction", "eb_tl_reaction"}, {"eb_teayu_conversation", "eb_tl_conversation"},
        {"eb_teayu_message", "eb_tl_message"}, {"eb_teayu_user_block", "eb_tl_user_block"},
        {"eb_teayu_message_report", "eb_tl_message_report"}, {"eb_teayu_stall_revision", "eb_tl_stall_revision"},
        {"eb_teayu_content_report", "eb_tl_content_report"}, {"eb_teayu_stall_daily_status", "eb_tl_stall_daily_status"},
        {"eb_teayu_moderation_event", "eb_tl_moderation_event"}
    };
    private static final String[][] REQUIRED_COLUMNS = {
        {"eb_tl_stall", "id,name,category_id,description,address,city_code,district_code,longitude,latitude,cover_image,image_urls,business_hours,contact_phone,audit_status,source_type,created_by,owner_user_id,audited_by,audit_note,audit_time,create_time,update_time,is_deleted"},
        {"eb_tl_category", "id,name,icon,sort,enabled"},
        {"eb_tl_stall_favorite", "id,stall_id,user_id,create_time"},
        {"eb_tl_stall_claim", "id,stall_id,user_id,proof,proof_image_urls,status,audited_by,audit_note,audit_time,create_time"},
        {"eb_tl_post", "id,stall_id,author_id,content,image_urls,audit_status,create_time,update_time,is_deleted"},
        {"eb_tl_comment", "id,post_id,author_id,parent_id,content,audit_status,create_time,is_deleted"},
        {"eb_tl_reaction", "id,post_id,user_id,reaction_type,create_time"},
        {"eb_tl_conversation", "id,stall_id,visitor_user_id,owner_user_id,last_message_id,last_message_time,visitor_read_message_id,owner_read_message_id,create_time,update_time,is_closed"},
        {"eb_tl_message", "id,conversation_id,sender_user_id,content,create_time,is_deleted"},
        {"eb_tl_user_block", "id,user_id,blocked_user_id,create_time"},
        {"eb_tl_message_report", "id,conversation_id,message_id,reporter_user_id,reason,status,handled_by,handle_note,handle_time,create_time"},
        {"eb_tl_stall_revision", "id,stall_id,pending_key,editor_user_id,name,category_id,description,address,city_code,district_code,longitude,latitude,cover_image,image_urls,business_hours,contact_phone,status,audited_by,audit_note,audit_time,create_time"},
        {"eb_tl_content_report", "id,target_type,target_id,reporter_user_id,reason,status,hide_target,handled_by,handle_note,handle_time,create_time"},
        {"eb_tl_stall_daily_status", "id,stall_id,status_date,is_open,hours,updated_by,create_time,update_time"},
        {"eb_tl_moderation_event", "id,target_type,target_id,related_id,from_status,to_status,action,actor_type,actor_id,actor_account,reason,create_time"}
    };
    private static final String[][] REQUIRED_INDEXES = {
        {"eb_tl_stall", "idx_stall_public_area"}, {"eb_tl_stall", "idx_stall_creator"}, {"eb_tl_stall", "idx_stall_name"},
        {"eb_tl_category", "uk_teayu_category_name"}, {"eb_tl_stall_favorite", "uk_teayu_stall_favorite"},
        {"eb_tl_stall_favorite", "idx_teayu_favorite_user"},
        {"eb_tl_post", "idx_teayu_post_public"}, {"eb_tl_post", "idx_teayu_post_author"},
        {"eb_tl_comment", "idx_teayu_comment_public"}, {"eb_tl_comment", "idx_teayu_comment_author"},
        {"eb_tl_reaction", "uk_teayu_reaction"}, {"eb_tl_conversation", "uk_teayu_conversation_pair"},
        {"eb_tl_conversation", "idx_teayu_conversation_visitor"}, {"eb_tl_conversation", "idx_teayu_conversation_owner"},
        {"eb_tl_message", "idx_teayu_message_page"}, {"eb_tl_message", "idx_teayu_message_rate"},
        {"eb_tl_user_block", "uk_teayu_user_block"}, {"eb_tl_message_report", "idx_teayu_message_report"},
        {"eb_tl_stall_claim", "idx_claim_review"}, {"eb_tl_stall_claim", "idx_claim_stall"}, {"eb_tl_stall_claim", "idx_claim_user"},
        {"eb_tl_stall_revision", "idx_teayu_revision_review"}, {"eb_tl_stall_revision", "idx_teayu_revision_stall"},
        {"eb_tl_stall_revision", "uk_teayu_revision_pending"},
        {"eb_tl_content_report", "idx_teayu_content_report_queue"}, {"eb_tl_content_report", "idx_teayu_content_report_target"},
        {"eb_tl_stall_daily_status", "uk_teayu_stall_daily_status"}, {"eb_tl_stall_daily_status", "idx_teayu_stall_open_today"},
        {"eb_tl_moderation_event", "idx_teayu_moderation_target"}, {"eb_tl_moderation_event", "idx_teayu_moderation_actor"}
    };
    private static final String[] REQUIRED_PERMISSIONS = {
        "tl:stall:review", "tl:category:manage", "tl:stall:claim:review",
        "tl:stall:revision:review", "tl:community:review", "tl:message:report", "tl:content:report",
        "admin:tl:review:list", "admin:log:sensitive:list"
    };
    private static final String[] REQUIRED_CATEGORIES = {"美食小吃", "饮品甜品", "手作饰品", "生活百货", "夜市集市", "便民服务"};

    private TlMigrator() { }

    public static void main(String[] args) throws Exception {
        if (!validArguments(args)) {
            printUsage();
            System.exit(2);
            return;
        }
        String mode = args[0];
        Path directory = Paths.get(modeDirectory(args));
        if (!Files.isDirectory(directory)) throw new IllegalArgumentException("Migration directory does not exist: " + directory);
        if ("--plan".equals(mode)) {
            printPlan(directory);
            return;
        }

        String url = requiredEnv("TL_DB_URL", "TEAYU_DB_URL");
        String user = requiredEnv("TL_DB_USER", "TEAYU_DB_USER");
        String password = requiredEnv("TL_DB_PASSWORD", "TEAYU_DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(true); // MySQL DDL commits implicitly; ledger is written only after a whole file succeeds.
            if ("--check".equals(mode)) {
                checkDatabase(connection, directory);
                return;
            }
            acquireLock(connection);
            try {
                if ("--baseline".equals(mode)) {
                    baseline(connection, args[2], directory);
                    return;
                }
                int schemaVersion = schemaVersion(connection);
                boolean hasLedger = tableExists(connection, LEDGER_TABLE);
                int existingRows = hasLedger ? ledgerCount(connection) : 0;
                if ((schemaVersion == 16 && existingRows != 16) || (schemaVersion == 17 && existingRows != 16 && existingRows != 17)) {
                    throw new IllegalStateException("Schema/ledger state requires review: run --check and baseline the existing schema before applying pending migrations");
                }
                if (schemaVersion == 0 && existingRows != 0) throw new IllegalStateException("Migration ledger exists without a recognized TeasLeisure schema");
                ensureLedger(connection);
                for (int i = 0; i < MIGRATIONS.length; i++) {
                    applyOne(connection, directory.resolve(MIGRATIONS[i]), i + 1);
                }
            } finally {
                releaseLock(connection);
            }
        } catch (SQLException e) {
            // SQLException messages may include endpoint details; credentials are intentionally never emitted.
            System.err.println("Migration stopped (" + e.getClass().getSimpleName() + "). Check database connectivity and server logs.");
            throw e;
        }
    }

    private static boolean validArguments(String[] args) {
        if (args.length < 1) return false;
        if ("--plan".equals(args[0]) || "--apply".equals(args[0]) || "--check".equals(args[0])) return args.length <= 2;
        if ("--baseline".equals(args[0])) return (args.length == 3 || args.length == 4) && "--confirm-baseline".equals(args[1]);
        return false;
    }

    private static String modeDirectory(String[] args) {
        if ("--baseline".equals(args[0])) return args.length == 4 ? args[3] : "backend/sql";
        return args.length == 2 ? args[1] : "backend/sql";
    }

    private static void printUsage() {
        System.err.println("Usage: TlMigrator --plan [migration-directory]");
        System.err.println("       TlMigrator --check [migration-directory]");
        System.err.println("       TlMigrator --baseline --confirm-baseline <catalog> [migration-directory]");
        System.err.println("       TlMigrator --apply [migration-directory]");
        System.err.println("Default migration-directory: backend/sql (relative to the current directory)");
    }

    private static void checkDatabase(Connection c, Path directory) throws Exception {
        int schemaVersion = schemaVersion(c);
        if (schemaVersion == 0) throw new IllegalStateException("No complete legacy or tl TeasLeisure schema is present");
        List<String> problems = inspectSchema(c, schemaVersion == 16);
        String catalog = currentCatalog(c);
        boolean ledgerTable = tableExists(c, LEDGER_TABLE);
        int ledgerRows = ledgerTable ? ledgerCount(c) : 0;
        if (!ledgerTable) {
            System.out.println("INFO migration ledger: absent");
        } else {
            System.out.println("INFO migration ledger rows: " + ledgerRows);
            inspectLedger(c, problems, directory, schemaVersion);
        }
        for (String problem : problems) System.out.println("MISSING/CONFLICT: " + problem);
        if (!problems.isEmpty()) throw new IllegalStateException("TeasLeisure database check failed with " + problems.size() + " issue(s)");
        if (schemaVersion == 16) System.out.println("OK catalog=" + catalog + " legacy TeasLeisure schema is complete; migration 17 is pending");
        else System.out.println("OK catalog=" + catalog + " tl tables, columns, indexes, and permission seeds are present");
    }

    private static int schemaVersion(Connection c) throws SQLException {
        int legacy = 0, renamed = 0, missing = 0;
        for (String[] pair : TABLE_RENAMES) {
            boolean oldExists = tableExists(c, pair[0]);
            boolean newExists = tableExists(c, pair[1]);
            if (oldExists && newExists) throw new IllegalStateException("Mixed tl table namespace at " + pair[0]);
            if (!oldExists && !newExists) { missing++; continue; }
            if (oldExists) legacy++; else renamed++;
        }
        if (legacy == 0 && renamed == 0) return 0;
        if (missing != 0) throw new IllegalStateException("Incomplete TeasLeisure business table set");
        if (legacy == TABLE_RENAMES.length) return 16;
        if (renamed == TABLE_RENAMES.length) return 17;
        throw new IllegalStateException("Mixed legacy/tl table namespace");
    }

    private static List<String> inspectSchema(Connection c, boolean legacy) throws SQLException {
        List<String> missing = new ArrayList<String>();
        String catalog = currentCatalog(c);
        for (String[] requirement : REQUIRED_COLUMNS) {
            String table = schemaTable(requirement[0], legacy);
            if (!tableExists(c, table)) { missing.add("table " + table); continue; }
            for (String column : requirement[1].split(",")) if (!columnExists(c, catalog, table, column)) missing.add("column " + table + "." + column);
        }
        for (String[] requirement : REQUIRED_INDEXES) {
            String table = schemaTable(requirement[0], legacy);
            if (!indexExists(c, catalog, table, requirement[1])) missing.add("index " + table + "." + requirement[1]);
        }
        String dailyTable = schemaTable("eb_tl_stall_daily_status", legacy);
        if (!foreignKeyExists(c, catalog, dailyTable, "fk_teayu_daily_status_stall", schemaTable("eb_tl_stall", legacy))) missing.add("foreign key " + dailyTable + ".fk_teayu_daily_status_stall");
        for (String permission : REQUIRED_PERMISSIONS) {
            String expected = legacy ? permission.replace("tl:", "teayu:") : permission;
            if (!permissionExists(c, expected)) missing.add("menu permission " + expected);
        }
        String categoryTable = schemaTable("eb_tl_category", legacy);
        if (tableExists(c, categoryTable)) {
            for (String category : REQUIRED_CATEGORIES) if (!categoryExists(c, categoryTable, category)) missing.add("category seed " + category);
        }
        return missing;
    }

    private static String schemaTable(String tlTable, boolean legacy) { return legacy ? tlTable.replace("eb_tl_", "eb_teayu_") : tlTable; }

    private static void baseline(Connection c, String confirmedCatalog, Path directory) throws Exception {
            String catalog = currentCatalog(c);
            if (!catalog.equals(confirmedCatalog)) throw new IllegalStateException("Confirmed catalog does not match connected catalog");
            if (tableExists(c, LEDGER_TABLE) && ledgerCount(c) != 0) throw new IllegalStateException("Baseline refused: migration ledger is not empty");
            int schemaVersion = schemaVersion(c);
            if (schemaVersion == 0) throw new IllegalStateException("Baseline refused: no complete legacy or tl schema is present");
            List<String> problems = inspectSchema(c, schemaVersion == 16);
            if (!problems.isEmpty()) {
                for (String problem : problems) System.err.println("MISSING: " + problem);
                throw new IllegalStateException("Baseline refused: required schema inspection failed");
            }
            List<String> checksums = new ArrayList<String>();
            for (int i = 0; i < schemaVersion; i++) {
                String migration = MIGRATIONS[i];
                Path file = directory.resolve(migration);
                if (!Files.isRegularFile(file)) throw new IOException("Missing migration file: " + migration);
                checksums.add(sha256(Files.readAllBytes(file)));
            }
            if (!tableExists(c, LEDGER_TABLE)) try (Statement s = c.createStatement()) { s.execute(ledgerDdl()); }
            c.setAutoCommit(false);
            try {
                for (int i = 0; i < schemaVersion; i++) {
                    try (PreparedStatement p = c.prepareStatement("INSERT INTO " + LEDGER_TABLE + "(version, script_name, checksum, installed_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)")) {
                        p.setString(1, MIGRATIONS[i]); p.setString(2, MIGRATIONS[i]); p.setString(3, checksums.get(i)); p.executeUpdate();
                    }
                }
                c.commit();
            } catch (Exception e) { c.rollback(); throw e; }
            finally { c.setAutoCommit(true); }
            System.out.println("Baseline recorded " + schemaVersion + " migrations for catalog " + catalog + "; no migration SQL was executed");
            if (schemaVersion == 16) System.out.println("Legacy schema recorded; run --apply to execute migration 17");
    }

    private static void inspectLedger(Connection c, List<String> problems, Path directory, int expectedCount) throws SQLException, IOException, NoSuchAlgorithmException {
        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT version, checksum FROM " + LEDGER_TABLE)) {
            int found = 0;
            while (rs.next()) {
                found++;
                String version = rs.getString(1), checksum = rs.getString(2);
                Path file = directory.resolve(version);
                if (!isMigration(version, expectedCount)) { problems.add("unexpected ledger version " + version); continue; }
                if (!Files.isRegularFile(file) || !sha256(Files.readAllBytes(file)).equalsIgnoreCase(checksum)) problems.add("ledger checksum conflict " + version);
            }
            if (found > 0 && found != expectedCount) problems.add("partial migration ledger: found " + found + " of " + expectedCount + " expected entries for this schema");
        }
    }

    private static boolean isMigration(String name, int count) { for (int i = 0; i < count; i++) if (MIGRATIONS[i].equals(name)) return true; return false; }
    private static String currentCatalog(Connection c) throws SQLException { try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT DATABASE()")) { if (!rs.next() || rs.getString(1) == null) throw new SQLException("No current database selected"); return rs.getString(1); } }
    private static boolean tableExists(Connection c, String table) throws SQLException { try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?")) { p.setString(1, table); try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; } } }
    private static boolean columnExists(Connection c, String catalog, String table, String column) throws SQLException { try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND COLUMN_NAME = ?")) { p.setString(1, catalog); p.setString(2, table); p.setString(3, column); try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; } } }
    private static boolean indexExists(Connection c, String catalog, String table, String index) throws SQLException { try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND INDEX_NAME = ?")) { p.setString(1, catalog); p.setString(2, table); p.setString(3, index); try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; } } }
    private static boolean foreignKeyExists(Connection c, String catalog, String table, String key, String referencedTable) throws SQLException {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? " +
                "AND CONSTRAINT_NAME = ? AND COLUMN_NAME = 'stall_id' AND REFERENCED_TABLE_SCHEMA = ? " +
                "AND REFERENCED_TABLE_NAME = ? AND REFERENCED_COLUMN_NAME = 'id'";
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, catalog); p.setString(2, table); p.setString(3, key); p.setString(4, catalog); p.setString(5, referencedTable);
            try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; }
        }
    }
    private static boolean permissionExists(Connection c, String permission) throws SQLException { if (!tableExists(c, "eb_system_menu")) return false; try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM eb_system_menu WHERE perms = ? AND is_delte = 0")) { p.setString(1, permission); try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; } } }
    private static boolean categoryExists(Connection c, String table, String name) throws SQLException { try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM `" + table + "` WHERE name = ?")) { p.setString(1, name); try (ResultSet r = p.executeQuery()) { r.next(); return r.getInt(1) > 0; } } }
    private static int ledgerCount(Connection c) throws SQLException { try (Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) FROM " + LEDGER_TABLE)) { r.next(); return r.getInt(1); } }
    private static String ledgerDdl() { return "CREATE TABLE " + LEDGER_TABLE + " (version VARCHAR(190) NOT NULL PRIMARY KEY, script_name VARCHAR(255) NOT NULL, checksum CHAR(64) NOT NULL, installed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"; }

    private static void printPlan(Path directory) throws IOException, NoSuchAlgorithmException {
        System.out.println("TeasLeisure migration plan (no database access)");
        for (int i = 0; i < MIGRATIONS.length; i++) {
            Path file = directory.resolve(MIGRATIONS[i]);
            if (!Files.isRegularFile(file)) throw new IOException("Missing migration file: " + file);
            System.out.println(String.format("%02d %s SHA-256 %s", i + 1, MIGRATIONS[i], sha256(Files.readAllBytes(file))));
        }
    }

    private static void applyOne(Connection c, Path file, int order) throws Exception {
        if (!Files.isRegularFile(file)) throw new IOException("Missing migration file: " + file);
        byte[] bytes = Files.readAllBytes(file);
        String checksum = sha256(bytes);
        String name = file.getFileName().toString();
        try (PreparedStatement q = c.prepareStatement("SELECT checksum FROM " + LEDGER_TABLE + " WHERE version = ?")) {
            q.setString(1, name);
            try (ResultSet rs = q.executeQuery()) {
                if (rs.next()) {
                    String old = rs.getString(1);
                    if (!checksum.equalsIgnoreCase(old)) throw new IllegalStateException("Checksum mismatch for registered migration " + name);
                    System.out.println("SKIP [" + order + "/" + MIGRATIONS.length + "] " + name);
                    return;
                }
            }
        }
        System.out.println("APPLY [" + order + "/" + MIGRATIONS.length + "] " + name);
        List<String> statements = splitStatements(new String(bytes, StandardCharsets.UTF_8));
        int firstStatement = 0;
        if (TL_RENAME_MIGRATION.equals(name)) {
            int oldTables = 0, newTables = 0;
            for (String[] rename : TABLE_RENAMES) {
                boolean oldExists = tableExists(c, rename[0]);
                boolean newExists = tableExists(c, rename[1]);
                if (oldExists && newExists || !oldExists && !newExists) throw new IllegalStateException("Ambiguous TL schema rename state for " + rename[0] + " and " + rename[1]);
                if (oldExists) oldTables++; else newTables++;
            }
            if (newTables == TABLE_RENAMES.length) firstStatement = 1; // A previous attempt completed the atomic RENAME TABLE statement.
            else if (oldTables != TABLE_RENAMES.length) throw new IllegalStateException("Partial TL table rename state; inspect and repair the catalog before retrying");
            if (statements.isEmpty() || !statements.get(0).trim().regionMatches(true, 0, "RENAME TABLE", 0, 12)) throw new IllegalStateException("TL schema migration must start with a single RENAME TABLE statement");
        }
        for (int i = firstStatement; i < statements.size(); i++) {
            String sql = statements.get(i);
            try (Statement statement = c.createStatement()) { statement.execute(sql); }
        }
        try (PreparedStatement insert = c.prepareStatement(
                "INSERT INTO " + LEDGER_TABLE + "(version, script_name, checksum, installed_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)")) {
            insert.setString(1, name);
            insert.setString(2, name);
            insert.setString(3, checksum);
            insert.executeUpdate();
        }
    }

    private static void ensureLedger(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS " + LEDGER_TABLE + " (" +
                    "version VARCHAR(190) NOT NULL PRIMARY KEY, script_name VARCHAR(255) NOT NULL, " +
                    "checksum CHAR(64) NOT NULL, installed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
        }
    }

    private static void acquireLock(Connection c) throws SQLException {
        try (PreparedStatement p = c.prepareStatement("SELECT GET_LOCK(?, 30)")) {
            p.setString(1, LOCK_NAME);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next() || rs.getInt(1) != 1) throw new SQLException("Could not acquire migration lock");
            }
        }
    }

    private static void releaseLock(Connection c) {
        try (PreparedStatement p = c.prepareStatement("SELECT RELEASE_LOCK(?)")) {
            p.setString(1, LOCK_NAME);
            p.executeQuery().close();
        } catch (SQLException ignored) { System.err.println("Could not release migration lock; connection close will release it."); }
    }

    private static String requiredEnv(String key, String legacyKey) {
        String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) value = System.getenv(legacyKey);
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("Required environment variable is missing: " + key);
        return value;
    }

    private static String sha256(byte[] bytes) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
        StringBuilder out = new StringBuilder(64);
        for (byte b : digest) out.append(String.format("%02x", b & 0xff));
        return out.toString();
    }

    /** Splits ordinary MySQL scripts while respecting quoted strings and SQL comments. */
    static List<String> splitStatements(String source) {
        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        char quote = 0;
        boolean lineComment = false, blockComment = false;
        for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i), next = i + 1 < source.length() ? source.charAt(i + 1) : 0;
            if (lineComment) { if (ch == '\n') { lineComment = false; current.append(ch); } continue; }
            if (blockComment) { if (ch == '*' && next == '/') { blockComment = false; i++; } continue; }
            if (quote == 0 && ch == '-' && next == '-' && (i + 2 == source.length() || Character.isWhitespace(source.charAt(i + 2)))) { lineComment = true; i++; continue; }
            if (quote == 0 && ch == '#') { lineComment = true; continue; }
            if (quote == 0 && ch == '/' && next == '*') { blockComment = true; i++; current.append(' '); continue; }
            if (quote != 0) {
                current.append(ch);
                if (ch == '\\' && i + 1 < source.length()) current.append(source.charAt(++i));
                else if (ch == quote && next == quote) current.append(source.charAt(++i));
                else if (ch == quote) quote = 0;
            } else if (ch == '\'' || ch == '"' || ch == '`') { quote = ch; current.append(ch); }
            else if (ch == ';') { addStatement(result, current); }
            else current.append(ch);
        }
        if (quote != 0 || blockComment) throw new IllegalArgumentException("Unterminated quote or block comment in migration SQL");
        addStatement(result, current);
        return result;
    }

    private static void addStatement(List<String> out, StringBuilder current) {
        String sql = current.toString().trim();
        if (!sql.isEmpty()) out.add(sql);
        current.setLength(0);
    }
}
