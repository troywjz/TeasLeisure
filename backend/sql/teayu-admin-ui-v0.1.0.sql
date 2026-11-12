-- Unified TeaYu moderation page and the permission points already enforced by the API.
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 5,'茶余审核','el-icon-checked','admin:teayu:review:list','/content/teayuReview','C',80,1,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0);

INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT (SELECT `id` FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0 LIMIT 1),'摊位投稿审核',NULL,'teayu:stall:review','', 'A',1,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:stall:review' AND `is_delte`=0);
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT (SELECT `id` FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0 LIMIT 1),'摊主认领审核',NULL,'teayu:stall:claim:review','', 'A',2,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:stall:claim:review' AND `is_delte`=0);
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT (SELECT `id` FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0 LIMIT 1),'摊位资料审核',NULL,'teayu:stall:revision:review','', 'A',3,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:stall:revision:review' AND `is_delte`=0);
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT (SELECT `id` FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0 LIMIT 1),'社区内容审核',NULL,'teayu:community:review','', 'A',4,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:community:review' AND `is_delte`=0);
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT (SELECT `id` FROM `eb_system_menu` WHERE `perms`='admin:teayu:review:list' AND `is_delte`=0 LIMIT 1),'私信举报处理',NULL,'teayu:message:report','', 'A',5,0,0,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='teayu:message:report' AND `is_delte`=0);
