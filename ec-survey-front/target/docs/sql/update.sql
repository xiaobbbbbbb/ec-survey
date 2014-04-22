-- 2013-09-03 行车报告 car_report 添加起点和终点位置

ALTER TABLE  `car_report` ADD  `start_address` CHAR( 200 ) NOT NULL COMMENT  '起点位置' AFTER  `last_data_id` ,
ADD  `end_address` CHAR( 200 ) NOT NULL COMMENT  '结束位置' AFTER  `start_address`;

-- 2013-09-04
ALTER TABLE  `area_info` CHANGE  `longitude`  `longitude` DOUBLE( 9, 6 ) NULL COMMENT  '中心点经度';
ALTER TABLE  `area_info` CHANGE  `latitude`  `latitude` DOUBLE( 8, 6 ) NULL COMMENT  '中心点纬度';

-- 2013-09-09 去掉区域经纬度  以及半径 yinql
ALTER TABLE  `area_info` DROP  `longitude`;
ALTER TABLE  `area_info` DROP  `latitude`;
ALTER TABLE  `area_info` DROP  `radius`;

-- 2013-09-10
ALTER TABLE `car_report` DROP COLUMN `begin_time`, DROP COLUMN `end_time`, MODIFY COLUMN `create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `update_time`;
ALTER TABLE `car_report` ADD COLUMN `last_client_time`  datetime NULL COMMENT '最后上传数据时间' AFTER `create_time`;
ALTER TABLE `car_report` ADD COLUMN `status`  int NULL DEFAULT 0 COMMENT '记录状态（0:未进行数据修正，1：已经修正）' AFTER `last_client_time`;
ALTER TABLE `ral_org` ADD COLUMN `city_id`  int NULL COMMENT '所在城市ID' AFTER `update_time`;
ALTER TABLE `device_data` ADD COLUMN `status`  int NULL DEFAULT 0 COMMENT '状态（0：未修正经纬度；1：已经修正经纬度）' AFTER `update_time`;

-- 2013-09-11
ALTER TABLE `car_report` ADD COLUMN `first_client_time`  datetime NULL COMMENT '第一条gps记录时间' AFTER `create_time`;

-- 2013-09-11 将电子围栏报警时间设置为char类型 yinql
ALTER TABLE  `fence_info` CHANGE  `alarm_start_time`  `alarm_start_time` CHAR( 50 ) NULL DEFAULT NULL COMMENT  '报警开始时间';
ALTER TABLE  `fence_info` CHANGE  `alarm_end_time`  `alarm_end_time` CHAR( 50 ) NULL DEFAULT NULL COMMENT  '报警结束时间';
ALTER TABLE `event_info` MODIFY COLUMN `address`  char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址' AFTER `survey_car_id`;
ALTER TABLE `fence_alarm_info` ADD COLUMN `fence_id`  int NULL COMMENT '围栏ID' AFTER `org_id`;


-- 2013-09-12
ALTER TABLE `car_report` MODIFY COLUMN `status`  int(11) NULL DEFAULT 0 COMMENT '记录状态（0:未结束此行车，1：已经结束行车，2：已经修正数据）' AFTER `last_client_time`;

-- 2013-09-13 yinql
ALTER TABLE  `fence_info` ADD  `grade` INT NULL COMMENT  '当前地图的级别' AFTER  `description`;
ALTER TABLE  `fence_info` ADD  `center_points` CHAR( 50 ) NULL COMMENT  '当前地图的中心点' AFTER  `grade`;


-- 2013-09-16

DROP TABLE IF EXISTS `car_report_day`;
CREATE TABLE `car_report_day` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`car_id`  int(11) NOT NULL COMMENT '车辆id' ,
`survey_num`  int(11) NULL DEFAULT NULL COMMENT '每日勘察次数' ,
`traffic_num`  int(11) NULL DEFAULT NULL COMMENT '违章次数' ,
`total_day_mile`  float(9,2) NOT NULL COMMENT '日总里程' ,
`avg_speed`  int(11) NULL DEFAULT NULL COMMENT '日平均速度' ,
`total_fence_counts`  int(11) NULL DEFAULT NULL COMMENT '围栏告警纪录' ,
`total_hypervelocity`  int(11) NOT NULL DEFAULT 0 COMMENT '超速纪录' ,
`client_day`  date NOT NULL COMMENT '客户端时间' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='车辆运行日报告'
AUTO_INCREMENT=8;


DROP TABLE IF EXISTS `car_report_month`;
CREATE TABLE `car_report_month` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`car_id`  int(11) NOT NULL COMMENT '车辆id' ,
`survey_num`  int(11) NULL DEFAULT NULL COMMENT '查勘次数' ,
`traffic_num`  int(11) NULL DEFAULT NULL COMMENT '违章次数' ,
`total_month_mile`  float(9,2) NOT NULL COMMENT '月里程' ,
`total_fence_counts`  int(11) NOT NULL COMMENT '月围栏告警纪录' ,
`total_hypervelocity`  int(11) NOT NULL COMMENT '月超速纪录' ,
`month`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '月份' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='车辆运行月报告'
AUTO_INCREMENT=3;

-- 2013-09-23 zengb
ALTER TABLE `event_info` ADD COLUMN `cancelpeople`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '取消人' AFTER `update_time`;
ALTER TABLE `event_info` ADD COLUMN `cancelreason`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '取消原因' AFTER `cancelpeople`;
ALTER TABLE `event_info` ADD COLUMN `car_latitude`  double(8,6) NULL COMMENT ' 调度时车辆纬度' AFTER `cancelreason`;
ALTER TABLE `event_info` ADD COLUMN `car_longitude`  double(9,6) NULL COMMENT '调度时车辆经度' AFTER `car_latitude`;

