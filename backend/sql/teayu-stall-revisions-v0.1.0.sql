CREATE TABLE IF NOT EXISTS `eb_teayu_stall_revision` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `stall_id` BIGINT NOT NULL,
  `pending_key` BIGINT DEFAULT NULL COMMENT '待审核唯一键，处理后清空',
  `editor_user_id` INT NOT NULL,
  `name` VARCHAR(120) NOT NULL,
  `category_id` INT DEFAULT NULL,
  `description` VARCHAR(2000) DEFAULT NULL,
  `address` VARCHAR(500) DEFAULT NULL,
  `city_code` VARCHAR(32) DEFAULT NULL,
  `district_code` VARCHAR(32) DEFAULT NULL,
  `longitude` DECIMAL(10,7) DEFAULT NULL,
  `latitude` DECIMAL(10,7) DEFAULT NULL,
  `cover_image` VARCHAR(500) DEFAULT NULL,
  `image_urls` TEXT DEFAULT NULL COMMENT '多图URL，逗号分隔；首图与摊位封面兼容',
  `business_hours` VARCHAR(500) DEFAULT NULL,
  `contact_phone` VARCHAR(64) DEFAULT NULL,
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0 pending 1 approved 2 rejected',
  `audited_by` INT DEFAULT NULL,
  `audit_note` VARCHAR(500) DEFAULT NULL,
  `audit_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_teayu_revision_review` (`status`,`create_time`),
  KEY `idx_teayu_revision_stall` (`stall_id`,`status`),
  UNIQUE KEY `uk_teayu_revision_pending` (`pending_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余摊位资料变更快照';

INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 5,'茶余摊位资料修改审核权限',NULL,'teayu:stall:revision:review','', 'A',0,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:stall:revision:review' AND `is_delte`=0);
