-------------------dts.init start--------------------------
--dts_action
CREATE TABLE `dts_action` (
   `action_id` varchar(128) NOT NULL,
   `activity_id` varchar(128) NOT NULL,
   `service` varchar(32) NOT NULL COMMENT '业务系统beanId',
   `clazz_name` varchar(256) NOT NULL COMMENT '类名称',
   `action` varchar(32) NOT NULL COMMENT '二阶操作',
   `version` varchar(8) NOT NULL,
   `protocol` varchar(8) NOT NULL,
   `status` varchar(2) NOT NULL,
   `context` varchar(1024) NOT NULL,
   `is_deleted` char(1) NOT NULL,
   `gmt_created` datetime NOT NULL,
   `gmt_modified` datetime NOT NULL,
   `creator` varchar(32) NOT NULL,
   `modifier` varchar(32) NOT NULL,
   PRIMARY KEY (`action_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 --dts_activity
 CREATE TABLE `dts_activity` (
   `activity_id` varchar(128) NOT NULL,
   `app` varchar(32) NOT NULL,
   `biz_type` varchar(32) DEFAULT NULL,
   `context` varchar(1024) NOT NULL,
   `status` varchar(2) NOT NULL,
   `is_deleted` char(1) NOT NULL,
   `gmt_created` datetime NOT NULL,
   `gmt_modified` datetime NOT NULL,
   `creator` varchar(32) NOT NULL,
   `modifier` varchar(32) NOT NULL,
   PRIMARY KEY (`activity_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 --dts_activity_action_rule
 CREATE TABLE `dts_activity_action_rule` (
   `biz_action` varchar(128) NOT NULL,
   `biz_action_name` varchar(32) NOT NULL,
   `biz_type` varchar(128) NOT NULL,
   `service` varchar(32) NOT NULL COMMENT '业务系统beanId',
   `clazz_name` varchar(256) NOT NULL COMMENT '类名称',
   `trans_recovery_id` varchar(32) NOT NULL COMMENT 'dts二阶恢复beanId',
   `is_deleted` char(1) NOT NULL DEFAULT 'N',
   `gmt_created` datetime NOT NULL,
   `gmt_modified` datetime NOT NULL,
   `creator` varchar(32) NOT NULL DEFAULT 'system',
   `modifier` varchar(32) DEFAULT 'system',
   PRIMARY KEY (`biz_action`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 --dts_activity_rule
 CREATE TABLE `dts_activity_rule` (
   `biz_type` varchar(128) NOT NULL,
   `app` varchar(32) NOT NULL,
   `app_cname` varchar(128) NOT NULL,
   `biz_type_name` varchar(128) NOT NULL,
   `is_deleted` char(1) NOT NULL DEFAULT 'N',
   `gmt_created` datetime NOT NULL,
   `gmt_modified` datetime NOT NULL,
   `creator` varchar(32) NOT NULL,
   `modifier` varchar(32) NOT NULL,
   PRIMARY KEY (`biz_type`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 
 -----------------dts.init end---------------------------
 
 -----------------dts.test.init start--------------------
 --dts_test
 CREATE TABLE `dts_test` (
   `name` varchar(128) DEFAULT NULL,
   `value` varchar(1024) DEFAULT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 -----------------dts.test.init end--------------------