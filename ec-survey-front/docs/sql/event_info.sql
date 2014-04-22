/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50155
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50155
File Encoding         : 65001

Date: 2013-09-18 19:06:17
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `event_info`
-- ----------------------------
DROP TABLE IF EXISTS `event_info`;
CREATE TABLE `event_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `no` char(15) NOT NULL COMMENT '报案号',
  `name` char(20) NOT NULL,
  `car_no` char(10) DEFAULT NULL COMMENT '车牌号',
  `phone_no` char(13) DEFAULT NULL COMMENT '手机号',
  `longitude` double(9,6) NOT NULL COMMENT '经度',
  `latitude` double(8,6) NOT NULL COMMENT '纬度',
  `geohash` point DEFAULT NULL COMMENT 'GEOHASH',
  `area_id` int(11) DEFAULT NULL COMMENT '区域ID',
  `op_uid` int(11) DEFAULT NULL COMMENT '录单员',
  `survey_uid` char(50) DEFAULT NULL COMMENT '查勘员，逗号分隔',
  `org_id` int(11) NOT NULL COMMENT '所属机构ID',
  `survey_car_id` int(11) DEFAULT NULL COMMENT '查勘车id',
  `address` char(255) NOT NULL COMMENT '详细地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '案件状态（0待调度；1已调度；2已完成；3手动终结；4超时终结。）',
  `event_desc` varchar(400) DEFAULT NULL COMMENT '案件描述',
  `process_time` datetime DEFAULT NULL COMMENT '案件调度时间',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cancelpeople` varchar(20) DEFAULT NULL COMMENT '取消人',
  `cancelreason` varchar(400) DEFAULT NULL COMMENT '取消原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='报案事件信息表';

-- ----------------------------
-- Records of event_info
-- ----------------------------
