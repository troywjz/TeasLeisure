CREATE TABLE IF NOT EXISTS `eb_teayu_stall_claim` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `stall_id` BIGINT NOT NULL,
  `user_id` INT NOT NULL,
  `proof` VARCHAR(1000) NOT NULL COMMENT '认领说明或证明材料地址',
  `proof_image_urls` TEXT DEFAULT NULL COMMENT '可选摊位/经营现场照片URL，逗号分隔',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0待审 1通过 2驳回',
  `audited_by` INT DEFAULT NULL,
  `audit_note` VARCHAR(500) DEFAULT NULL,
  `audit_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_claim_review` (`status`, `create_time`),
  KEY `idx_claim_stall` (`stall_id`, `status`),
  KEY `idx_claim_user` (`user_id`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余摊主认领申请';

INSERT INTO `eb_system_menu`
  (`pid`, `name`, `icon`, `perms`, `component`, `menu_type`, `sort`, `is_show`, `is_delte`, `create_time`, `update_time`)
SELECT 5, '茶余认领审核权限', NULL, 'teayu:stall:claim:review', '', 'A', 0, 0, 0, NOW(), NOW()
WHERE NOT EXISTS (
  SELECT 1 FROM `eb_system_menu` WHERE `perms` = 'teayu:stall:claim:review' AND `is_delte` = 0
);
