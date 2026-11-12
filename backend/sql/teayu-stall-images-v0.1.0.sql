-- 第十四份 TeaYu 增量迁移。兼容 fresh-install 基线脚本已包含 image_urls 的场景。
SET @teayu_stall_image_urls_ddl = IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
   WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'eb_teayu_stall' AND COLUMN_NAME = 'image_urls') = 0,
  'ALTER TABLE `eb_teayu_stall` ADD COLUMN `image_urls` TEXT DEFAULT NULL COMMENT ''多图URL，逗号分隔；首图与cover_image一致'' AFTER `cover_image`',
  'SELECT 1'
);
PREPARE teayu_stall_image_urls_stmt FROM @teayu_stall_image_urls_ddl;
EXECUTE teayu_stall_image_urls_stmt;
DEALLOCATE PREPARE teayu_stall_image_urls_stmt;

SET @teayu_revision_image_urls_ddl = IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
   WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'eb_teayu_stall_revision' AND COLUMN_NAME = 'image_urls') = 0,
  'ALTER TABLE `eb_teayu_stall_revision` ADD COLUMN `image_urls` TEXT DEFAULT NULL COMMENT ''多图URL，逗号分隔；首图与封面兼容'' AFTER `cover_image`',
  'SELECT 1'
);
PREPARE teayu_revision_image_urls_stmt FROM @teayu_revision_image_urls_ddl;
EXECUTE teayu_revision_image_urls_stmt;
DEALLOCATE PREPARE teayu_revision_image_urls_stmt;
