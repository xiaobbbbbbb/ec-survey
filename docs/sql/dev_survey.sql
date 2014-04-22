-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 07 月 29 日 19:59
-- 服务器版本: 5.1.65
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `dev_survey`
--

-- --------------------------------------------------------

--
-- 表的结构 `alarm_info`
--

DROP TABLE IF EXISTS `alarm_info`;
CREATE TABLE IF NOT EXISTS `alarm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` int(11) NOT NULL,
  `description` char(50) NOT NULL,
  `client_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `alarm_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `area_grid_info`
--

DROP TABLE IF EXISTS `area_grid_info`;
CREATE TABLE IF NOT EXISTS `area_grid_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基站信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `area_grid_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `area_info`
--

DROP TABLE IF EXISTS `area_info`;
CREATE TABLE IF NOT EXISTS `area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL COMMENT '父类ID',
  `longitude` double(9,6) NOT NULL COMMENT '中心点经度',
  `latitude` double(8,6) NOT NULL COMMENT '中心点纬度',
  `radius` int(11) NOT NULL COMMENT '半径',
  `order_no` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `area_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `car_info`
--

DROP TABLE IF EXISTS `car_info`;
CREATE TABLE IF NOT EXISTS `car_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `car_no` char(10) NOT NULL,
  `car_model_id` int(11) NOT NULL COMMENT '车型',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查勘车信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `car_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `car_status`
--

DROP TABLE IF EXISTS `car_status`;
CREATE TABLE IF NOT EXISTS `car_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL,
  `is_acc_on` tinyint(1) NOT NULL DEFAULT '0',
  `client_time` datetime NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆状态' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `car_status`
--


-- --------------------------------------------------------

--
-- 表的结构 `city_info`
--

DROP TABLE IF EXISTS `city_info`;
CREATE TABLE IF NOT EXISTS `city_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL,
  `longitude` double(9,6) NOT NULL COMMENT '经度',
  `latitude` double(8,6) NOT NULL COMMENT '纬度',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `city_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `device_data`
--

DROP TABLE IF EXISTS `device_data`;
CREATE TABLE IF NOT EXISTS `device_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `gps_time` datetime NOT NULL COMMENT 'gps 时间',
  `gps_longitude` double(9,4) NOT NULL COMMENT '经度',
  `gps_latitude` double(8,4) NOT NULL COMMENT '纬度',
  `baidu_longitude` double(9,6) NOT NULL,
  `baidu_latitude` double(8,6) NOT NULL,
  `geohash` point DEFAULT NULL COMMENT 'geohash',
  `area_no` char(20) NOT NULL COMMENT '基站区域代码',
  `grid_no` char(20) NOT NULL COMMENT '基站栅格号',
  `mile_meter` int(11) NOT NULL COMMENT '两点之间里程',
  `speed` double(4,1) NOT NULL,
  `temperature` double(3,1) NOT NULL COMMENT '温度',
  `is_charge_voltage` bigint(1) NOT NULL DEFAULT '0' COMMENT '是否使用外部电源',
  `voltage_inner` double(3,2) NOT NULL COMMENT '锂电电压',
  `voltage_outer` double(4,2) NOT NULL COMMENT '车截电池电压',
  `angle_value` double(4,1) NOT NULL COMMENT '磁偏角',
  `angle_direction` enum('E','W') NOT NULL COMMENT '磁偏角方向',
  `client_time` datetime NOT NULL COMMENT '数据存储时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='设备上报数据' AUTO_INCREMENT=30 ;

--
-- 转存表中的数据 `device_data`
--

INSERT INTO `device_data` (`id`, `device_id`, `gps_time`, `gps_longitude`, `gps_latitude`, `baidu_longitude`, `baidu_latitude`, `geohash`, `area_no`, `grid_no`, `mile_meter`, `speed`, `temperature`, `is_charge_voltage`, `voltage_inner`, `voltage_outer`, `angle_value`, `angle_direction`, `client_time`, `update_time`) VALUES
(1, 1, '1970-01-01 10:27:00', 11355.9000, 2232.9000, 113.931700, 22.548333, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.67, 0.0, 'E', '2013-07-26 10:27:05', '2013-07-26 18:32:48'),
(2, 1, '1970-01-01 10:29:00', 11355.9000, 2232.9000, 113.931688, 22.548350, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.73, 0.0, 'E', '2013-07-26 10:29:25', '2013-07-26 18:32:52'),
(3, 1, '1970-01-01 10:29:00', 11355.9000, 2232.9000, 113.931685, 22.548352, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.74, 0.0, 'E', '2013-07-26 10:29:47', '2013-07-26 18:33:13'),
(4, 1, '1970-01-01 10:30:00', 11355.9000, 2232.9000, 113.931677, 22.548358, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.74, 0.0, 'E', '2013-07-26 10:30:08', '2013-07-26 18:33:13'),
(5, 1, '1970-01-01 10:30:00', 11355.9000, 2232.9000, 113.931668, 22.548363, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.74, 0.0, 'E', '2013-07-26 10:30:30', '2013-07-26 18:33:15'),
(6, 1, '1970-01-01 10:30:00', 11355.9000, 2232.9000, 113.931662, 22.548368, NULL, '26230', 'F0D', 0, 0.0, 0.0, 1, 9.99, 3.75, 0.0, 'E', '2013-07-26 10:30:54', '2013-07-26 18:34:06'),
(7, 1, '1970-01-01 10:31:00', 11355.9000, 2232.9000, 113.931657, 22.548370, NULL, '26230', 'F0D', 0, 0.0, 0.0, 1, 9.99, 3.75, 0.0, 'E', '2013-07-26 10:31:16', '2013-07-26 18:34:06'),
(8, 1, '1970-01-01 10:32:00', 11355.8900, 2232.9100, 113.931528, 22.548433, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.76, 0.0, 'E', '2013-07-26 10:32:44', '2013-07-26 18:34:06'),
(9, 1, '1970-01-01 10:32:00', 11355.8900, 2232.9100, 113.931530, 22.548423, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.76, 0.0, 'E', '2013-07-26 10:32:18', '2013-07-26 18:34:06'),
(10, 1, '1970-01-01 10:34:00', 11355.8900, 2232.9100, 113.931517, 22.548473, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.76, 0.0, 'E', '2013-07-26 10:34:08', '2013-07-26 18:34:11'),
(11, 1, '1970-01-01 10:34:00', 11355.8900, 2232.9100, 113.931515, 22.548473, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.76, 0.0, 'E', '2013-07-26 10:34:29', '2013-07-26 18:34:33'),
(12, 1, '1970-01-01 10:34:00', 11355.8900, 2232.9100, 113.931513, 22.548473, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.76, 0.0, 'E', '2013-07-26 10:34:51', '2013-07-26 18:34:54'),
(13, 1, '1970-01-01 10:35:00', 11355.8900, 2232.9100, 113.931512, 22.548472, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:35:12', '2013-07-26 18:35:57'),
(14, 1, '1970-01-01 10:35:00', 11355.8900, 2232.9100, 113.931510, 22.548475, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:35:35', '2013-07-26 18:36:01'),
(15, 1, '1970-01-01 10:35:00', 11355.8900, 2232.9100, 113.931508, 22.548475, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:35:57', '2013-07-26 18:36:44'),
(16, 1, '1970-01-01 10:35:00', 11355.8900, 2232.9100, 113.931508, 22.548475, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:35:57', '2013-07-26 18:36:44'),
(17, 1, '1970-01-01 10:36:00', 11355.8900, 2232.9100, 113.931503, 22.548483, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:36:19', '2013-07-26 18:36:44'),
(18, 1, '1970-01-01 10:36:00', 11355.8900, 2232.9100, 113.931500, 22.548490, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:36:41', '2013-07-26 18:36:44'),
(19, 1, '1970-01-01 10:37:00', 11355.8900, 2232.9100, 113.931495, 22.548492, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:37:03', '2013-07-26 18:37:29'),
(20, 1, '1970-01-01 10:37:00', 11355.8900, 2232.9100, 113.931487, 22.548497, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.77, 315.2, 'E', '2013-07-26 10:37:25', '2013-07-26 18:37:29'),
(21, 1, '1970-01-01 10:38:00', 11355.8900, 2232.9100, 113.931478, 22.548503, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:38:08', '2013-07-26 18:38:35'),
(22, 1, '1970-01-01 10:38:00', 11355.8900, 2232.9100, 113.931478, 22.548503, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:38:08', '2013-07-26 18:38:35'),
(23, 1, '1970-01-01 10:38:00', 11355.8900, 2232.9100, 113.931470, 22.548513, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:38:31', '2013-07-26 18:38:35'),
(24, 1, '1970-01-01 10:38:00', 11355.8900, 2232.9100, 113.931462, 22.548522, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:38:52', '2013-07-26 18:38:54'),
(25, 1, '1970-01-01 10:39:00', 11355.8900, 2232.9100, 113.931423, 22.548532, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:39:15', '2013-07-26 18:39:39'),
(26, 1, '1970-01-01 10:39:00', 11355.8800, 2232.9100, 113.931415, 22.548542, NULL, '26230', 'EE4', 0, 0.0, 0.0, 1, 9.99, 3.78, 315.2, 'E', '2013-07-26 10:39:36', '2013-07-26 18:39:39'),
(27, 1, '1970-01-01 08:17:00', 11355.8900, 2232.9000, 113.931565, 22.548370, NULL, '26230', 'ED9', 0, 0.0, 0.0, 0, 3.74, 0.00, 294.9, 'E', '2013-07-25 08:17:37', '2013-07-29 17:27:39'),
(28, 1, '1970-01-01 08:17:00', 11355.8900, 2232.9000, 113.931565, 22.548370, NULL, '26230', 'ED9', 0, 0.0, 0.0, 0, 3.74, 0.00, 294.9, 'E', '2013-07-25 08:17:37', '2013-07-29 17:58:09'),
(29, 1, '1970-01-01 08:17:00', 11355.8900, 2232.9000, 113.931565, 22.548370, NULL, '26230', 'ED9', 0, 0.0, 0.0, 0, 3.74, 0.00, 294.9, 'E', '2013-07-25 08:17:37', '2013-07-29 18:15:45');

-- --------------------------------------------------------

--
-- 表的结构 `device_info`
--

DROP TABLE IF EXISTS `device_info`;
CREATE TABLE IF NOT EXISTS `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` char(20) NOT NULL,
  `code` char(15) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='设备信息表' AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `device_info`
--

INSERT INTO `device_info` (`id`, `serial_no`, `code`, `create_time`, `update_time`) VALUES
(1, '20130726', '860041022279247', '2013-07-26 17:29:48', '2013-07-26 17:29:53');

-- --------------------------------------------------------

--
-- 表的结构 `device_tag_info`
--

DROP TABLE IF EXISTS `device_tag_info`;
CREATE TABLE IF NOT EXISTS `device_tag_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(10) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='RFID电子标签信息表' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `device_tag_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `event_info`
--

DROP TABLE IF EXISTS `event_info`;
CREATE TABLE IF NOT EXISTS `event_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` char(15) NOT NULL COMMENT '201307260000001',
  `name` char(20) NOT NULL,
  `car_no` char(10) NOT NULL,
  `phone_no` int(13) NOT NULL,
  `longitude` double(9,6) NOT NULL COMMENT '经度',
  `latitude` double(8,6) NOT NULL COMMENT '纬度',
  `geohash` point NOT NULL COMMENT 'GEOHASH',
  `area_info_id` int(11) NOT NULL,
  `op_uid` int(11) NOT NULL COMMENT '录单员',
  `survey_uid` int(11) NOT NULL COMMENT '查勘员',
  `address` char(50) NOT NULL COMMENT '详细地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '案件状态',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报案事件信息表' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `event_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `fence_alarm_info`
--

DROP TABLE IF EXISTS `fence_alarm_info`;
CREATE TABLE IF NOT EXISTS `fence_alarm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_type` enum('in','out','in_and_out') NOT NULL DEFAULT 'in',
  `survey_uid` int(11) NOT NULL,
  `phone_no` char(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `car_no` char(10) NOT NULL,
  `device_id` int(11) NOT NULL,
  `device_code` char(15) NOT NULL,
  `longitude` double(9,3) NOT NULL,
  `latitude` double(8,3) NOT NULL,
  `baidu_longitude` double(9,3) NOT NULL,
  `baidu_latitude` double(8,6) NOT NULL,
  `geohash` point NOT NULL,
  `address` int(100) NOT NULL,
  `description` char(100) NOT NULL,
  `client_time` datetime NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='围栏报警信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `fence_alarm_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `fence_info`
--

DROP TABLE IF EXISTS `fence_info`;
CREATE TABLE IF NOT EXISTS `fence_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子围栏' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `fence_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `fence_point_info`
--

DROP TABLE IF EXISTS `fence_point_info`;
CREATE TABLE IF NOT EXISTS `fence_point_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fence_info_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子围栏-坐标点信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `fence_point_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `log_info`
--

DROP TABLE IF EXISTS `log_info`;
CREATE TABLE IF NOT EXISTS `log_info` (
  `id` int(11) NOT NULL DEFAULT '0',
  `mark` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

--
-- 转存表中的数据 `log_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `op_user_info`
--

DROP TABLE IF EXISTS `op_user_info`;
CREATE TABLE IF NOT EXISTS `op_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` char(20) NOT NULL COMMENT '10',
  `passwd` int(32) NOT NULL,
  `display_name` int(20) NOT NULL,
  `email` char(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `op_user_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `survey_user_info`
--

DROP TABLE IF EXISTS `survey_user_info`;
CREATE TABLE IF NOT EXISTS `survey_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_info_id` int(11) NOT NULL COMMENT '所属片区ID',
  `car_id` int(11) NOT NULL,
  `name` char(20) NOT NULL,
  `phone_no` char(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '工作状态',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查勘员信息' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `survey_user_info`
--


-- --------------------------------------------------------

--
-- 表的结构 `survey_user_work`
--

DROP TABLE IF EXISTS `survey_user_work`;
CREATE TABLE IF NOT EXISTS `survey_user_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_uid` int(11) NOT NULL,
  `ctime` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `survey_user_work`
--


-- --------------------------------------------------------

--
-- 表的结构 `tag_info`
--

DROP TABLE IF EXISTS `tag_info`;
CREATE TABLE IF NOT EXISTS `tag_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` char(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='RFID电子标签信息表' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `tag_info`
--

