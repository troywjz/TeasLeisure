CREATE TABLE IF NOT EXISTS `eb_teayu_moderation_event` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `target_type` VARCHAR(32) NOT NULL COMMENT 'stall_submission/stall_claim/stall_revision/post/comment/message_report/content_report/stall',
 `target_id` BIGINT NOT NULL,
 `related_id` BIGINT DEFAULT NULL COMMENT 'Related stall or parent record ID',
 `from_status` TINYINT DEFAULT NULL,
 `to_status` TINYINT DEFAULT NULL,
 `action` VARCHAR(32) NOT NULL COMMENT 'approve/reject/resolve/dismiss/hide',
 `actor_id` INT NOT NULL,
 `actor_account` VARCHAR(64) NOT NULL,
 `reason` VARCHAR(500) DEFAULT NULL,
 `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 KEY `idx_teayu_moderation_target` (`target_type`,`target_id`,`create_time`,`id`),
 KEY `idx_teayu_moderation_actor` (`actor_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TeaYu moderation status transition history';
