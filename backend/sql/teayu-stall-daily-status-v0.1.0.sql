CREATE TABLE IF NOT EXISTS `eb_teayu_stall_daily_status` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `stall_id` BIGINT NOT NULL,
 `status_date` DATE NOT NULL,
 `is_open` TINYINT(1) NOT NULL,
 `hours` VARCHAR(80) DEFAULT NULL,
 `updated_by` INT NOT NULL,
 `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 UNIQUE KEY `uk_teayu_stall_daily_status` (`stall_id`,`status_date`),
 KEY `idx_teayu_stall_open_today` (`status_date`,`is_open`),
 CONSTRAINT `fk_teayu_daily_status_stall` FOREIGN KEY (`stall_id`) REFERENCES `eb_teayu_stall` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余摊位每日出摊状态';
