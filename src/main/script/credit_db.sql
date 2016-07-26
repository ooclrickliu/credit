
CREATE DATABASE IF NOT EXISTS `credit` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE `credit`;

GRANT ALL PRIVILEGES ON credit.* to 'credit'@'%' IDENTIFIED BY 'credit#123';
GRANT ALL PRIVILEGES ON credit.* to 'credit'@'localhost' IDENTIFIED BY 'credit#123';


--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `real_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_face_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_back_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `person_id_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `marital_status` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `degree` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_name1` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_relation1` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_phone1` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_name2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_relation2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relative_phone2` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_password` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wx_pay_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nick_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `head_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `approve_time` timestamp NULL DEFAULT NULL,
  `approve_note` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `credit_line` float DEFAULT NULL,
  `user_state` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;


--
-- 表的结构 `credit_apply`
--

CREATE TABLE IF NOT EXISTS `credit_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` float DEFAULT NULL,
  `returned_base` float DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `interest` float DEFAULT NULL,
  `commission` float DEFAULT NULL,
  `commission_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `apply_state` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `apply_time` timestamp NULL DEFAULT NULL,
  `approve_time` timestamp NULL DEFAULT NULL,
  `approve_note` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `effective_time` timestamp NULL DEFAULT NULL,
  `due_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;


--
-- 表的结构 `credit_pay_record`
--

CREATE TABLE IF NOT EXISTS `credit_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` bigint(20) NOT NULL,
  `credit_base` float DEFAULT NULL,
  `credit_interest` float DEFAULT NULL,
  `returned_amount` float DEFAULT NULL,
  `remain_base` float DEFAULT NULL,
  `return_state` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pay_img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `return_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;


alter table user add level int DEFAULT 0 after approve_note;
