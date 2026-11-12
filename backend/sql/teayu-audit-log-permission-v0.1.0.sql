-- Register the existing CRMEB sensitive-log permission so an operator role can grant it.
INSERT INTO `eb_system_menu` (`pid`,`name`,`icon`,`perms`,`component`,`menu_type`,`sort`,`is_show`,`is_delte`,`create_time`,`update_time`)
SELECT 424,'敏感操作日志查看',NULL,'admin:log:sensitive:list','', 'A',2,0,0,NOW(),NOW()
WHERE EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `id`=424 AND `is_delte`=0)
  AND NOT EXISTS (SELECT 1 FROM `eb_system_menu` WHERE `perms`='admin:log:sensitive:list' AND `is_delte`=0);
