/*
Navicat MySQL Data Transfer

Source Server         : local mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : fortune

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-10-13 17:11:15
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_user_pgroup
-- ----------------------------
INSERT INTO `t_user_pgroup` VALUES ('4', '1', '4');
INSERT INTO `t_user_pgroup` VALUES ('5', '3', '4');
INSERT INTO `t_user_pgroup` VALUES ('6', '2', '4');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lingda', '123', 'NORMAL_USER', '2015-10-13 17:10:51', '10');
INSERT INTO `user` VALUES ('2', 'darling', '456', 'NORMAL_USER', '2015-10-13 16:46:26', '0');
INSERT INTO `user` VALUES ('3', 'darling', '456', 'NORMAL_USER', '2015-10-13 16:48:40', '0');
INSERT INTO `user` VALUES ('4', 'darling tang', '12345', 'NORMAL_USER', '2015-10-13 17:05:13', '0');
INSERT INTO `user` VALUES ('5', 'darling tang', '12345', 'NORMAL_USER', '2015-10-13 17:07:15', '0');
