/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : ec-survey

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2013-07-30 19:42:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `alarm_info`
-- ----------------------------
DROP TABLE IF EXISTS `alarm_info`;
CREATE TABLE `alarm_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_id`  int(11) NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`type`  int(11) NOT NULL ,
`description`  char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`client_time`  datetime NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='报警'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of alarm_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `area_grid_info`
-- ----------------------------
DROP TABLE IF EXISTS `area_grid_info`;
CREATE TABLE `area_grid_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='基站信息'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of area_grid_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `area_info`
-- ----------------------------
DROP TABLE IF EXISTS `area_info`;
CREATE TABLE `area_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`city_id`  int(11) NOT NULL ,
`parent_id`  int(11) NOT NULL COMMENT '父类ID' ,
`longitude`  double(9,6) NOT NULL COMMENT '中心点经度' ,
`latitude`  double(8,6) NOT NULL COMMENT '中心点纬度' ,
`radius`  int(11) NOT NULL COMMENT '半径' ,
`order_no`  int(11) NOT NULL DEFAULT 0 ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='区域信息'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of area_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `car_info`
-- ----------------------------
DROP TABLE IF EXISTS `car_info`;
CREATE TABLE `car_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_id`  int(11) NOT NULL ,
`car_no`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`car_model_id`  int(11) NOT NULL COMMENT '车型' ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='查勘车信息'
/*!50003 AUTO_INCREMENT=4 */

;

-- ----------------------------
-- Records of car_info
-- ----------------------------
BEGIN;
INSERT INTO car_info VALUES ('1', '1', '粤B88888', '1', '2013-07-30 18:19:23', '2013-07-30 18:19:26'), ('2', '2', '粤B88887', '2', '2013-07-30 18:19:42', '2013-07-30 18:19:45'), ('3', '3', '粤B88886', '3', '2013-07-30 18:19:51', '2013-07-30 18:19:57');
COMMIT;

-- ----------------------------
-- Table structure for `car_status`
-- ----------------------------
DROP TABLE IF EXISTS `car_status`;
CREATE TABLE `car_status` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`car_id`  int(11) NOT NULL ,
`is_acc_on`  tinyint(1) NOT NULL DEFAULT 0 ,
`client_time`  datetime NOT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='车辆状态'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of car_status
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `city_info`
-- ----------------------------
DROP TABLE IF EXISTS `city_info`;
CREATE TABLE `city_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`longitude`  double(9,6) NOT NULL COMMENT '经度' ,
`latitude`  double(8,6) NOT NULL COMMENT '纬度' ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='城市信息'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of city_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `device_data`
-- ----------------------------
DROP TABLE IF EXISTS `device_data`;
CREATE TABLE `device_data` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_id`  int(11) NOT NULL ,
`gps_time`  datetime NOT NULL COMMENT 'gps 时间' ,
`gps_longitude`  double(9,4) NOT NULL COMMENT '经度' ,
`gps_latitude`  double(8,4) NOT NULL COMMENT '纬度' ,
`baidu_longitude`  double(9,6) NOT NULL ,
`baidu_latitude`  double(8,6) NOT NULL ,
`geohash`  point NULL DEFAULT NULL COMMENT 'geohash' ,
`area_no`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基站区域代码' ,
`grid_no`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基站栅格号' ,
`mile_meter`  int(11) NOT NULL COMMENT '两点之间里程' ,
`speed`  double(4,1) NOT NULL ,
`temperature`  double(3,1) NOT NULL COMMENT '温度' ,
`is_charge_voltage`  bigint(1) NOT NULL DEFAULT 0 COMMENT '是否使用外部电源' ,
`voltage_inner`  double(3,2) NOT NULL COMMENT '锂电电压' ,
`voltage_outer`  double(4,2) NOT NULL COMMENT '车截电池电压' ,
`angle_value`  double(4,1) NOT NULL COMMENT '磁偏角' ,
`angle_direction`  enum('E','W') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '磁偏角方向' ,
`client_time`  datetime NOT NULL COMMENT '数据存储时间' ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='设备上报数据'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of device_data
-- ----------------------------
BEGIN;
INSERT INTO device_data VALUES ('1', '1', '1970-01-01 08:17:00', '11355.8900', '2232.9000', '113.931565', '22.548370', '\0\0\0\0\0\0\0\0\0\0\0\0\04@\0\0\0\0\0\0 @', '26230', 'ED9', '0', '0.0', '0.0', '0', '3.74', '0.00', '294.9', 'E', '2013-07-25 08:17:37', '2013-07-30 14:38:35'), ('2', '2', '1970-01-01 08:17:00', '11355.8900', '2232.9000', '113.931565', '22.547370', '\0\0\0\0\0\0\0���a�6@JF�{\\@', '26230', 'ED9', '0', '0.0', '0.0', '0', '3.74', '0.00', '294.9', 'E', '2013-07-25 08:17:37', '2013-07-30 15:42:44'), ('3', '3', '1970-01-01 08:17:00', '11355.8900', '2232.9000', '113.932565', '22.547370', '\0\0\0\0\0\0\0���a�6@JF�{\\@', '26230', 'ED9', '0', '0.0', '0.0', '0', '3.74', '0.00', '294.9', 'E', '2013-07-25 08:17:37', '2013-07-30 16:44:57');
COMMIT;

-- ----------------------------
-- Table structure for `device_info`
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`serial_no`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='设备信息表'
/*!50003 AUTO_INCREMENT=4 */

;

-- ----------------------------
-- Records of device_info
-- ----------------------------
BEGIN;
INSERT INTO device_info VALUES ('1', '20130726', '860041022279247', '2013-07-26 17:29:48', '2013-07-26 17:29:53'), ('2', '20130727', '860041022279248', '2013-07-30 18:34:21', '2013-07-30 18:34:22'), ('3', '20130728', '860041022279249', '2013-07-30 18:34:42', '2013-07-30 18:34:43');
COMMIT;

-- ----------------------------
-- Table structure for `event_info`
-- ----------------------------
DROP TABLE IF EXISTS `event_info`;
CREATE TABLE `event_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`no`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '201307260000001' ,
`name`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`car_no`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`phone_no`  int(13) NOT NULL ,
`longitude`  double(9,6) NOT NULL COMMENT '经度' ,
`latitude`  double(8,6) NOT NULL COMMENT '纬度' ,
`geohash`  point NOT NULL COMMENT 'GEOHASH' ,
`area_info_id`  int(11) NOT NULL ,
`op_uid`  int(11) NOT NULL COMMENT '录单员' ,
`survey_uid`  int(11) NOT NULL COMMENT '查勘员' ,
`address`  char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址' ,
`status`  int(11) NOT NULL DEFAULT 0 COMMENT '案件状态' ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='报案事件信息表'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of event_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `fence_info`
-- ----------------------------
DROP TABLE IF EXISTS `fence_info`;
CREATE TABLE `fence_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='电子围栏'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of fence_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `fence_point_info`
-- ----------------------------
DROP TABLE IF EXISTS `fence_point_info`;
CREATE TABLE `fence_point_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`fence_info_id`  int(11) NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='电子围栏-坐标点信息'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of fence_point_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `log_info`
-- ----------------------------
DROP TABLE IF EXISTS `log_info`;
CREATE TABLE `log_info` (
`id`  int(11) NOT NULL DEFAULT 0 ,
`mark`  int(11) NOT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='操作日志'

;

-- ----------------------------
-- Records of log_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `op_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `op_user_info`;
CREATE TABLE `op_user_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`login_name`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '10' ,
`passwd`  int(32) NOT NULL ,
`display_name`  int(20) NOT NULL ,
`email`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`login_time`  datetime NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='后台管理员'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of op_user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `survey_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `survey_user_info`;
CREATE TABLE `survey_user_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`area_info_id`  int(11) NOT NULL COMMENT '所属片区ID' ,
`car_id`  int(11) NOT NULL ,
`name`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`phone_no`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  int(11) NOT NULL DEFAULT 0 COMMENT '工作状态' ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='查勘员信息'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of survey_user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `tag_info`
-- ----------------------------
DROP TABLE IF EXISTS `tag_info`;
CREATE TABLE `tag_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`serial_no`  char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`create_time`  datetime NOT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='RFID电子标签信息表'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Records of tag_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Auto increment value for `alarm_info`
-- ----------------------------
ALTER TABLE `alarm_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `area_grid_info`
-- ----------------------------
ALTER TABLE `area_grid_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `area_info`
-- ----------------------------
ALTER TABLE `area_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `car_info`
-- ----------------------------
ALTER TABLE `car_info` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `car_status`
-- ----------------------------
ALTER TABLE `car_status` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `city_info`
-- ----------------------------
ALTER TABLE `city_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `device_data`
-- ----------------------------
ALTER TABLE `device_data` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `device_info`
-- ----------------------------
ALTER TABLE `device_info` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `event_info`
-- ----------------------------
ALTER TABLE `event_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `fence_info`
-- ----------------------------
ALTER TABLE `fence_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `fence_point_info`
-- ----------------------------
ALTER TABLE `fence_point_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `op_user_info`
-- ----------------------------
ALTER TABLE `op_user_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `survey_user_info`
-- ----------------------------
ALTER TABLE `survey_user_info` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `tag_info`
-- ----------------------------
ALTER TABLE `tag_info` AUTO_INCREMENT=1;
