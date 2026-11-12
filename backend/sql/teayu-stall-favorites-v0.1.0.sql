CREATE TABLE IF NOT EXISTS `eb_teayu_stall_favorite` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `stall_id` BIGINT NOT NULL,
  `user_id` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teayu_stall_favorite` (`stall_id`,`user_id`),
  KEY `idx_teayu_favorite_user` (`user_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余摊位收藏';
