-- 第十五份 TeaYu 增量迁移。兼容 fresh-install 建表脚本已包含该列的场景。
SET @teayu_claim_proof_images_ddl = IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
   WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'eb_teayu_stall_claim' AND COLUMN_NAME = 'proof_image_urls') = 0,
  'ALTER TABLE `eb_teayu_stall_claim` ADD COLUMN `proof_image_urls` TEXT DEFAULT NULL COMMENT ''可选摊位/经营现场照片URL，逗号分隔'' AFTER `proof`',
  'SELECT 1'
);
PREPARE teayu_claim_proof_images_stmt FROM @teayu_claim_proof_images_ddl;
EXECUTE teayu_claim_proof_images_stmt;
DEALLOCATE PREPARE teayu_claim_proof_images_stmt;
