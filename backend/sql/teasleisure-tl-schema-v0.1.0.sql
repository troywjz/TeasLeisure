-- TeasLeisure namespace migration. The 16 preceding TeaYu scripts remain immutable for ledger checksum compatibility.
RENAME TABLE
  `eb_teayu_stall` TO `eb_tl_stall`,
  `eb_teayu_category` TO `eb_tl_category`,
  `eb_teayu_stall_favorite` TO `eb_tl_stall_favorite`,
  `eb_teayu_stall_claim` TO `eb_tl_stall_claim`,
  `eb_teayu_post` TO `eb_tl_post`,
  `eb_teayu_comment` TO `eb_tl_comment`,
  `eb_teayu_reaction` TO `eb_tl_reaction`,
  `eb_teayu_conversation` TO `eb_tl_conversation`,
  `eb_teayu_message` TO `eb_tl_message`,
  `eb_teayu_user_block` TO `eb_tl_user_block`,
  `eb_teayu_message_report` TO `eb_tl_message_report`,
  `eb_teayu_stall_revision` TO `eb_tl_stall_revision`,
  `eb_teayu_content_report` TO `eb_tl_content_report`,
  `eb_teayu_stall_daily_status` TO `eb_tl_stall_daily_status`,
  `eb_teayu_moderation_event` TO `eb_tl_moderation_event`;

UPDATE `eb_system_menu`
SET `perms` = REPLACE(`perms`, 'teayu:', 'tl:')
WHERE `perms` LIKE '%teayu:%';

UPDATE `eb_system_menu`
SET `component` = REPLACE(`component`, 'teayuReview', 'tlReview')
WHERE `component` LIKE '%teayuReview%';
