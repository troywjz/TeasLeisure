CREATE TABLE IF NOT EXISTS `eb_teayu_content_report` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `target_type` VARCHAR(16) NOT NULL COMMENT 'stall/post/comment',
 `target_id` BIGINT NOT NULL,
 `reporter_user_id` INT NOT NULL,
 `reason` VARCHAR(500) NOT NULL,
 `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0 pending 1 resolved 2 dismissed',
 `hide_target` TINYINT(1) NOT NULL DEFAULT 0,
 `handled_by` INT DEFAULT NULL,
 `handle_note` VARCHAR(500) DEFAULT NULL,
 `handle_time` DATETIME DEFAULT NULL,
 `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 KEY `idx_teayu_content_report_queue` (`status`,`create_time`),
 KEY `idx_teayu_content_report_target` (`target_type`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余用户内容举报';

INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 5,'茶余内容举报处理',NULL,'teayu:content:report','', 'A',30,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:content:report' AND `is_delte`=0);
