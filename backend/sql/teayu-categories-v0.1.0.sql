CREATE TABLE IF NOT EXISTS `eb_teayu_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `icon` VARCHAR(500) DEFAULT NULL,
  `sort` INT NOT NULL DEFAULT 0,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teayu_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='茶余摊位分类';

INSERT INTO `eb_teayu_category` (`name`,`sort`,`enabled`) VALUES
('美食小吃',10,1),('饮品甜品',20,1),('手作饰品',30,1),('生活百货',40,1),('夜市集市',50,1),('便民服务',60,1)
ON DUPLICATE KEY UPDATE `sort`=VALUES(`sort`), `enabled`=1;

INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 5,'摊位分类管理',NULL,'teayu:category:manage','', 'A',20,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:category:manage' AND `is_delte`=0);
