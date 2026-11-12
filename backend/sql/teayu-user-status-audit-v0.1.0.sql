ALTER TABLE `eb_teayu_moderation_event`
    ADD COLUMN `actor_type` VARCHAR(16) NOT NULL DEFAULT 'admin' AFTER `action`;
