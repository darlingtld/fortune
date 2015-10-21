/*
Navicat MySQL Data Transfer

Source Server         : local mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : fortune

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-10-20 17:47:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gamble_bet_lottery_mark_six
-- ----------------------------
DROP TABLE IF EXISTS `gamble_bet_lottery_mark_six`;
CREATE TABLE `gamble_bet_lottery_mark_six` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `pgroup_id` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `one` int(11) DEFAULT NULL,
  `one_color` varchar(255) DEFAULT NULL,
  `two` int(11) DEFAULT NULL,
  `two_color` varchar(255) DEFAULT NULL,
  `three` int(11) DEFAULT NULL,
  `three_color` varchar(255) DEFAULT NULL,
  `four` int(11) DEFAULT NULL,
  `four_color` varchar(255) DEFAULT NULL,
  `five` int(11) DEFAULT NULL,
  `five_color` varchar(255) DEFAULT NULL,
  `six` int(11) DEFAULT NULL,
  `six_color` varchar(255) DEFAULT NULL,
  `special` int(11) DEFAULT NULL,
  `special_color` varchar(255) DEFAULT NULL,
  `stakes` double DEFAULT NULL,
  `issue` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gamble_bet_lottery_mark_six
-- ----------------------------
INSERT INTO `gamble_bet_lottery_mark_six` VALUES ('1', '1', '4', '2015-10-19 10:08:53', '30', 'GREEN', '1', 'BLUE', '33', 'BLUE', '17', 'GREEN', '36', 'RED', '21', 'RED', '27', 'BLUE', '63270.90531424412', '1445220', 'HEWEIDA');
INSERT INTO `gamble_bet_lottery_mark_six` VALUES ('2', '1', '4', '2015-10-20 17:45:39', '24', 'BLUE', '42', 'GREEN', '26', 'GREEN', '39', 'RED', '14', 'GREEN', '28', 'GREEN', '25', 'BLUE', '98261.36224764088', '293', 'HEWEIDA');

-- ----------------------------
-- Table structure for lottery_mark_six
-- ----------------------------
DROP TABLE IF EXISTS `lottery_mark_six`;
CREATE TABLE `lottery_mark_six` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `one` int(11) DEFAULT NULL,
  `one_color` varchar(255) DEFAULT NULL,
  `two` int(11) DEFAULT NULL,
  `two_color` varchar(255) DEFAULT NULL,
  `three` int(11) DEFAULT NULL,
  `three_color` varchar(255) DEFAULT NULL,
  `four` int(11) DEFAULT NULL,
  `four_color` varchar(255) DEFAULT NULL,
  `five` int(11) DEFAULT NULL,
  `five_color` varchar(255) DEFAULT NULL,
  `six` int(11) DEFAULT NULL,
  `six_color` varchar(255) DEFAULT NULL,
  `special` int(11) DEFAULT NULL,
  `special_color` varchar(255) DEFAULT NULL,
  `issue` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_mark_six
-- ----------------------------
INSERT INTO `lottery_mark_six` VALUES ('3', '28', 'GREEN', '45', 'RED', '41', 'RED', '47', 'RED', '35', 'BLUE', '48', 'GREEN', '4', 'BLUE', '1445131', '2015-10-18 09:26:15');
INSERT INTO `lottery_mark_six` VALUES ('4', '1', 'GREEN', '45', 'GREEN', '37', 'GREEN', '30', 'GREEN', '34', 'RED', '17', 'GREEN', '8', 'BLUE', '1445131', '2015-10-18 09:27:19');
INSERT INTO `lottery_mark_six` VALUES ('5', '24', 'BLUE', '13', 'BLUE', '5', 'BLUE', '27', 'GREEN', '22', 'GREEN', '23', 'RED', '20', 'RED', '1445131', '2015-10-18 09:27:32');

-- ----------------------------
-- Table structure for odds
-- ----------------------------
DROP TABLE IF EXISTS `odds`;
CREATE TABLE `odds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_id` int(11) DEFAULT NULL,
  `pgroup_id` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of odds
-- ----------------------------
INSERT INTO `odds` VALUES ('1', '2', '3', '10', '2015-10-16 17:15:20');
INSERT INTO `odds` VALUES ('2', '2', '3', '10', '2015-10-16 17:19:21');

-- ----------------------------
-- Table structure for pgroup
-- ----------------------------
DROP TABLE IF EXISTS `pgroup`;
CREATE TABLE `pgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of pgroup
-- ----------------------------
INSERT INTO `pgroup` VALUES ('3', 'hongkong');
INSERT INTO `pgroup` VALUES ('4', 'hongkong');

-- ----------------------------
-- Table structure for t_user_pgroup
-- ----------------------------
DROP TABLE IF EXISTS `t_user_pgroup`;
CREATE TABLE `t_user_pgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `pgroupid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_user_pgroup
-- ----------------------------
INSERT INTO `t_user_pgroup` VALUES ('4', '1', '4');
INSERT INTO `t_user_pgroup` VALUES ('5', '3', '4');
INSERT INTO `t_user_pgroup` VALUES ('6', '2', '4');
INSERT INTO `t_user_pgroup` VALUES ('7', '1', '3');
INSERT INTO `t_user_pgroup` VALUES ('8', '3', '3');
INSERT INTO `t_user_pgroup` VALUES ('9', '5', '3');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `account` double(11,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lingda', '$2a$10$miuPjytd/BpclHpiY/jKbO31qcg6JVr6GYd2IbzEp6M2u6./oN.na', 'NORMAL_USER', '2015-10-19 17:50:14', '10');
INSERT INTO `user` VALUES ('2', 'darling', '$2a$10$tivdgootjezOVMvzf5Dgc.IIhIBAnCmyO1YQzb7wlb.nw7xOM8Sdy', 'NORMAL_USER', '2015-10-13 16:46:26', '0');
INSERT INTO `user` VALUES ('3', 'darling1', '$2a$10$tivdgootjezOVMvzf5Dgc.IIhIBAnCmyO1YQzb7wlb.nw7xOM8Sdy', 'NORMAL_USER', '2015-10-13 16:48:40', '0');
INSERT INTO `user` VALUES ('4', 'darling tang', '$2a$10$2e598txs8FLEpoYyCU9QW.0KB7EfN8/2ueVrFVME/KAL0LmfVXfuS', 'NORMAL_USER', '2015-10-13 17:05:13', '0');
INSERT INTO `user` VALUES ('5', 'darling tang1', '$2a$10$7sTrLvT2sTtiXZhWUCcsQuj7YIOe5vsAWINmzuiwaurKotGJg0EzO', 'NORMAL_USER', '2015-10-13 17:07:15', '0');
INSERT INTO `user` VALUES ('6', 'lingda tang', '$2a$10$XWHkwpDL.wYEcDI7kHC4..cl2k8QKUani5vR0S8TXEUWBu5PWVxRy', 'NORMAL_USER', '2015-10-13 17:31:35', '0');
INSERT INTO `user` VALUES ('7', 'haha', '$2a$10$yOsy/zkTcOwHDJdjP87xI.jStl649aNeEnJbumW6ru3eb.YRxw/ZO', 'NORMAL_USER', null, '0');
