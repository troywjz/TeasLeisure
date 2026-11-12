-- 茶余增量迁移。保留 CRMEB 上游原始 SQL，单独执行并按项目版本纳入后续完整库脚本。
CREATE TABLE IF NOT EXISTS `eb_teayu_stall` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '摊位ID',
  `name` VARCHAR(120) NOT NULL COMMENT '摊位名称',
  `category_id` INT DEFAULT NULL COMMENT '摊位分类ID',
  `description` VARCHAR(2000) DEFAULT NULL COMMENT '摊位介绍',
  `address` VARCHAR(500) DEFAULT NULL COMMENT '地址描述',
  `city_code` VARCHAR(32) DEFAULT NULL COMMENT '城市编码',
  `district_code` VARCHAR(32) DEFAULT NULL COMMENT '区县编码',
  `longitude` DECIMAL(10,7) DEFAULT NULL COMMENT '经度',
  `latitude` DECIMAL(10,7) DEFAULT NULL COMMENT '纬度',
  `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
  `image_urls` TEXT DEFAULT NULL COMMENT '多图URL，逗号分隔；首图与cover_image一致',
  `business_hours` VARCHAR(500) DEFAULT NULL COMMENT '营业时间JSON',
  `contact_phone` VARCHAR(64) DEFAULT NULL COMMENT '摊主主动公开的联系电话',
  `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '0待审 1已发布 2驳回 3隐藏',
  `source_type` VARCHAR(32) NOT NULL DEFAULT 'user_submission' COMMENT 'user_submission merchant operator',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建用户ID',
  `owner_user_id` INT DEFAULT NULL COMMENT '审核认领后的摊主用户ID',
  `audited_by` INT DEFAULT NULL COMMENT '审核管理员ID',
  `audit_note` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_stall_public_area` (`audit_status`, `is_deleted`, `city_code`, `district_code`, `category_id`),
  KEY `idx_stall_creator` (`created_by`),
  KEY `idx_stall_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余地摊主档';

-- 管理员需在角色管理中分配该审核权限，审核 API 由 Spring Security 校验。
INSERT INTO `eb_system_menu`
  (`pid`, `name`, `icon`, `perms`, `component`, `menu_type`, `sort`, `is_show`, `is_delte`, `create_time`, `update_time`)
SELECT 5, '茶余摊位审核权限', NULL, 'teayu:stall:review', '', 'A', 0, 0, 0, NOW(), NOW()
WHERE NOT EXISTS (
  SELECT 1 FROM `eb_system_menu` WHERE `perms` = 'teayu:stall:review' AND `is_delte` = 0
);
