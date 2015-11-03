/*
Navicat MySQL Data Transfer

Source Server         : local mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : fortune

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-11-03 14:53:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lottery_mark_six_stat
-- ----------------------------
DROP TABLE IF EXISTS `lottery_mark_six_stat`;
CREATE TABLE `lottery_mark_six_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_mark_six_id` int(11) DEFAULT NULL,
  `total_stakes` double DEFAULT NULL,
  `user_result` double DEFAULT NULL,
  `pgroup_result` double DEFAULT NULL,
  `zoufei_stakes` double DEFAULT NULL,
  `zoufei_result` double DEFAULT NULL,
  `pgroup_total_result` double DEFAULT NULL,
  `remark` text,
  `pgroup_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_mark_six_stat
-- ----------------------------
INSERT INTO `lottery_mark_six_stat` VALUES ('1', '4', '1000', '20000', '299', '123', '345', '199', null, '563338f6e708fad8259ea83f');
