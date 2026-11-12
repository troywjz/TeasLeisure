CREATE TABLE IF NOT EXISTS `eb_teayu_conversation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `stall_id` BIGINT NOT NULL,
  `visitor_user_id` INT NOT NULL,
  `owner_user_id` INT NOT NULL,
  `last_message_id` BIGINT DEFAULT NULL,
  `last_message_time` DATETIME DEFAULT NULL,
  `visitor_read_message_id` BIGINT DEFAULT NULL,
  `owner_read_message_id` BIGINT DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_closed` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teayu_conversation_pair` (`stall_id`,`visitor_user_id`),
  KEY `idx_teayu_conversation_visitor` (`visitor_user_id`,`last_message_time`),
  KEY `idx_teayu_conversation_owner` (`owner_user_id`,`last_message_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `eb_teayu_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `conversation_id` BIGINT NOT NULL,
  `sender_user_id` INT NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_teayu_message_page` (`conversation_id`,`id`),
  KEY `idx_teayu_message_rate` (`conversation_id`,`sender_user_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `eb_teayu_user_block` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `blocked_user_id` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teayu_user_block` (`user_id`,`blocked_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `eb_teayu_message_report` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `conversation_id` BIGINT NOT NULL,
  `message_id` BIGINT DEFAULT NULL,
  `reporter_user_id` INT NOT NULL,
  `reason` VARCHAR(500) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0 pending 1 resolved 2 dismissed',
  `handled_by` INT DEFAULT NULL,
  `handle_note` VARCHAR(500) DEFAULT NULL,
  `handle_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_teayu_message_report` (`status`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 5,'茶余私信举报处理权限',NULL,'teayu:message:report','', 'A',0,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:message:report' AND `is_delte`=0);
