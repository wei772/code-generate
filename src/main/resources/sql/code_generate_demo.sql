create  database  code_generate;

use code_generate;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `time` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) COMMENT='用户';

INSERT INTO `user` (`id`, `code`, `time`) VALUES (1, 'wei', '2024-12-13 17:24:41');


CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  PRIMARY KEY (`id`)
)  COMMENT='用户组';