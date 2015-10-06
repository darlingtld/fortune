/*
Navicat MySQL Data Transfer

Source Server         : Songda Database
Source Server Version : 50543
Source Host           : 120.26.37.219:3306
Source Database       : createsh

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2015-09-29 07:41:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `username` varchar(45) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('admin', 'createsh', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for bill_order
-- ----------------------------
DROP TABLE IF EXISTS `bill_order`;
CREATE TABLE `bill_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `wechat_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `order_ts` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `delivery_ts` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyer_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `buyer_address` text CHARACTER SET utf8,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `consignee_contact` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirm_code` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `confirm_bill` text CHARACTER SET utf8,
  `confirm_ts` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bill_order
-- ----------------------------
INSERT INTO `bill_order` VALUES ('79', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"amount\":2,\"description\":\"粮源食品碾米机 品质保证 健康食品保证 \",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"碾米机\",\"productPrice\":1299,\"productUnit\":\"台\"}],\"totalAmount\":2,\"totalPrice\":2598}', '2015-09-05 07:53:02', '2015/09/05 07:52', 'lingda', '长阳路', '灵达', '13402188638', '未配送', '724733796', null, null);
INSERT INTO `bill_order` VALUES ('80', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"amount\":2,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":2000,\"productUnit\":\"台\"}],\"totalAmount\":2,\"totalPrice\":4000}', '2015-09-06 11:51:29', '2015/09/06 11:50', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '951940405', null, null);
INSERT INTO `bill_order` VALUES ('83', '李含嘉', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', '{\"items\":[{\"$$hashKey\":\"object:34\",\"amount\":3,\"description\":\"精选优品寒地富硒谷\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":218,\"productName\":\"优品寒地富硒谷\",\"productPrice\":15,\"productUnit\":\"斤\"},{\"$$hashKey\":\"object:35\",\"amount\":\"1\",\"description\":\"精选优品黑土小珍珠\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":7,\"productUnit\":\"斤\"}],\"totalAmount\":4,\"totalPrice\":52}', '2015-09-15 13:43:17', '2015/09/16 15:40', '李杰', '上海市普陀区中山北路1715号', '李杰', '02161171230', '已配送（已付款）', '062769990', null, null);
INSERT INTO `bill_order` VALUES ('84', 'Mz、', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '{\"items\":[{\"$$hashKey\":\"object:18\",\"amount\":4,\"description\":\"精选优品五常稻花香\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":217,\"productName\":\"优品五常稻花香\",\"productPrice\":12,\"productUnit\":\"斤\"}],\"totalAmount\":4,\"totalPrice\":48}', '2015-09-17 10:23:29', '2015/09/18 10:33', '张守艳', '普陀区中山北路1715号805', '张守艳', '13917444535', '已配送（已付款）', '620090244', null, null);
INSERT INTO `bill_order` VALUES ('86', 'Mz、', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '{\"items\":[{\"$$hashKey\":\"object:14\",\"amount\":\"1\",\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":1,\"totalPrice\":0}', '2015-09-21 09:39:13', '2015/09/23 09:39', '张守艳', '普陀区中山北路1715号805', '张守艳', '13917444535', '已配送（已付款）', '096618956', null, null);
INSERT INTO `bill_order` VALUES ('87', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-24 20:08:15', '2015/09/24 20:08', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '968237643', null, null);
INSERT INTO `bill_order` VALUES ('88', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:37\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-24 20:10:21', '2015/09/24 20:10', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '316403470', null, null);
INSERT INTO `bill_order` VALUES ('89', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-24 20:12:28', '2015/09/24 20:12', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '在线支付', '009053185', null, null);
INSERT INTO `bill_order` VALUES ('90', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:8\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-24 20:15:33', '2015/09/24 20:15', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '961018080', null, null);
INSERT INTO `bill_order` VALUES ('91', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":2,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":2,\"totalPrice\":0}', '2015-09-24 20:39:37', '2015/09/24 20:39', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '已支付（未配送）', '808928857', null, null);
INSERT INTO `bill_order` VALUES ('92', 'Monique', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', '{\"items\":[{\"$$hashKey\":\"object:28\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-25 10:04:12', '2015/10/25 10:00', '杨文琴', '上海', '杨文琴', '14818761275', '已配送（未付款）', '542764606', null, null);
INSERT INTO `bill_order` VALUES ('93', '李含嘉', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', '{\"items\":[{\"$$hashKey\":\"object:17\",\"amount\":3,\"description\":\"精选优品寒地富硒谷\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":218,\"productName\":\"优品寒地富硒谷\",\"productPrice\":15,\"productUnit\":\"斤\"},{\"$$hashKey\":\"object:18\",\"amount\":\"1\",\"description\":\"精选优品黑土小珍珠\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":7,\"productUnit\":\"斤\"},{\"$$hashKey\":\"object:19\",\"amount\":1,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":5,\"totalPrice\":1552}', '2015-09-25 10:08:48', '2015/09/26 10:07', '李杰', '上海市普陀区中山北路1715号浦发广场e座805', '李杰', '13817476348', '未配送', '758162253', null, null);
INSERT INTO `bill_order` VALUES ('94', '汪立雄', 'oh88lw58rD8OStujg82PIQyXO-M4', 'oh88lw58rD8OStujg82PIQyXO-M4', '{\"items\":[{\"$$hashKey\":\"object:17\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-25 10:17:33', '2015/10/25 10:15', '汪立雄', '上海市普陀区中江路1067弄63号601', '汪立雄', '15921621515', '未配送', '481009944', null, null);
INSERT INTO `bill_order` VALUES ('95', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":2,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":2,\"totalPrice\":0}', '2015-09-26 10:59:11', '2015/09/26 10:58', 'abc', 'abc', 'abc', 'abc', '未配送', '456591928', null, null);
INSERT INTO `bill_order` VALUES ('96', '开心每一天', 'oh88lw_qAufZMHXTwTV1xV7wfpe8', 'oh88lw_qAufZMHXTwTV1xV7wfpe8', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":3,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic65766716962940809215432950151443097768472.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":3,\"totalPrice\":0}', '2015-09-26 11:05:09', '2015/09/26 11:04', '好', '好', '好', '好', '未配送', '202756318', null, null);
INSERT INTO `bill_order` VALUES ('97', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:29\",\"amount\":1,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"},{\"$$hashKey\":\"object:30\",\"amount\":1,\"description\":\"精选优品原种黑土小珍珠 5KG装\",\"picurl\":\"product_images/pic-1991289397122836835412761009031443085655264.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":70,\"productUnit\":\"5KG/袋\"}],\"totalAmount\":2,\"totalPrice\":70}', '2015-09-26 18:27:32', '2015/09/26 18:27', 'ABC', 'ABC', 'ABC', 'ABC', '已支付（未配送）', '912855715', null, null);
INSERT INTO `bill_order` VALUES ('98', 'na', 'oh88lw3T3gM0BvtR1dhMuJlg2ECc', 'oh88lw3T3gM0BvtR1dhMuJlg2ECc', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":\"1\",\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic65766716962940809215432950151443097768472.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":1,\"totalPrice\":0}', '2015-09-26 18:29:56', '2015/09/26 18:29', '在', '于', '人', '啊', '未配送', '356454469', null, null);
INSERT INTO `bill_order` VALUES ('99', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":1,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic65766716962940809215432950151443097768472.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"}],\"totalAmount\":1,\"totalPrice\":0}', '2015-09-26 19:10:46', '2015/09/26 19:09', '唐灵达', '长阳路', '灵达', '长阳路', '未配送', '604940401', null, null);
INSERT INTO `bill_order` VALUES ('100', '梁源', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":0,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"},{\"$$hashKey\":\"object:13\",\"amount\":0,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"},{\"$$hashKey\":\"object:20\",\"amount\":0,\"description\":\"精选优品黑土小珍珠\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":7,\"productUnit\":\"斤\"}],\"totalAmount\":3,\"totalPrice\":0}', '2015-09-27 07:22:15', '2015/09/28 10:31', '邵建飞', '中山北路1715号', '邵建飞', '13761708222', '未配送', '000494903', null, null);
INSERT INTO `bill_order` VALUES ('101', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:15\",\"amount\":2,\"description\":\"精选优品原种黑土小珍珠 5KG装\",\"picurl\":\"product_images/pic-1991289397122836835412761009031443085655264.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":70,\"productUnit\":\"5KG/袋\"},{\"$$hashKey\":\"object:54\",\"amount\":2,\"description\":\"精选优品原种五常稻花香 8KG装\",\"picurl\":\"product_images/pic575183062-88351057212761009031442893441267.jpg\",\"productId\":217,\"productName\":\"优品五常稻花香\",\"productPrice\":192,\"productUnit\":\"8KG/袋\"}],\"totalAmount\":4,\"totalPrice\":524}', '2015-09-27 08:28:08', '2015/09/27 08:24', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '369181165', null, null);
INSERT INTO `bill_order` VALUES ('102', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:20\",\"amount\":\"1\",\"description\":\"精选优品原种黑土小珍珠 5KG装\",\"picurl\":\"product_images/pic-1991289397122836835412761009031443085655264.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":70,\"productUnit\":\"5KG/袋\"}],\"totalAmount\":1,\"totalPrice\":70}', '2015-09-27 08:40:44', '2015/09/27 08:40', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '983053932', null, null);
INSERT INTO `bill_order` VALUES ('103', '梁源', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":0,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"},{\"$$hashKey\":\"object:13\",\"amount\":0,\"description\":\"精选优品黑土小珍珠\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":7,\"productUnit\":\"斤\"},{\"$$hashKey\":\"object:14\",\"amount\":0,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":3,\"totalPrice\":0}', '2015-09-28 09:11:45', '2015/09/29 10:19', '邵建飞', '中山北路1715号', '创恒', '61392723', '未配送', '565810722', null, null);
INSERT INTO `bill_order` VALUES ('104', 'Monique', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":1,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 10:03:28', '2015/10/28 10:01', '杨文琴', '上海市普陀区中山北路1715号浦发广场805', '杨文琴', '13818761275', '已支付（未配送）', '884055369', null, null);
INSERT INTO `bill_order` VALUES ('105', 'Monique', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":1,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 10:05:47', '2015/10/28 10:04', 'Monique', '上海', 'yang', '杨文琴', '未配送', '998241509', null, null);
INSERT INTO `bill_order` VALUES ('106', '汪立雄', 'oh88lw58rD8OStujg82PIQyXO-M4', 'oh88lw58rD8OStujg82PIQyXO-M4', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 10:11:37', '2015/09/28 10:11', '汪立雄', '上海市普陀区中江路1067弄63号601', '汪立雄', '15921621515', '已支付（未配送）', '365705713', null, null);
INSERT INTO `bill_order` VALUES ('107', '汪立雄', 'oh88lw58rD8OStujg82PIQyXO-M4', 'oh88lw58rD8OStujg82PIQyXO-M4', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 10:12:56', '2015/09/29 10:12', '汪立雄', '上海市普陀区中江路1067弄63号601', '汪立雄', '15921621515', '未配送', '991853899', null, null);
INSERT INTO `bill_order` VALUES ('108', '灵达', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:13\",\"amount\":2,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":3000}', '2015-09-28 12:56:13', '2015/09/28 12:56', '新中源大楼', '长阳路1934号', '灵达', '13402188638', '未配送', '201553021', null, null);
INSERT INTO `bill_order` VALUES ('109', 'lingda', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '{\"items\":[{\"$$hashKey\":\"object:23\",\"amount\":7,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":10500}', '2015-09-28 13:14:26', '2015/09/28 13:14', '新中源大楼123', '长阳路1930号123', '灵达123', '1340218863813', '未配送', '984889746', null, null);
INSERT INTO `bill_order` VALUES ('110', 'Mz、?', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '{\"items\":[{\"$$hashKey\":\"object:29\",\"amount\":1,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 16:34:50', '2015/09/29 16:34', '张', '浦发广场', '张', '13917444535', '未配送', '594122211', null, null);
INSERT INTO `bill_order` VALUES ('111', '梁源', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '{\"items\":[{\"$$hashKey\":\"object:25\",\"amount\":0,\"description\":\"新用户0元下单，免费送货上门并办理会员\",\"picurl\":\"product_images/pic486904869015432950151442575811039.jpg\",\"productId\":223,\"productName\":\"新用户直通车\",\"productPrice\":0,\"productUnit\":\"份\"},{\"$$hashKey\":\"object:62\",\"amount\":0,\"description\":\"精选优品黑土小珍珠\",\"picurl\":\"product_images/youpinheituxiaozhenzhu.jpg\",\"productId\":216,\"productName\":\"优品黑土小珍珠\",\"productPrice\":7,\"productUnit\":\"斤\"},{\"$$hashKey\":\"object:63\",\"amount\":0,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"},{\"$$hashKey\":\"object:64\",\"amount\":0,\"description\":\"精选优品原种寒地富硒谷 8KG装\",\"picurl\":\"product_images/pic-650244726125528028812761009031442893502852.jpg\",\"productId\":218,\"productName\":\"优品寒地富硒谷\",\"productPrice\":240,\"productUnit\":\"8KG/袋\"}],\"totalAmount\":4,\"totalPrice\":0}', '2015-09-28 18:53:23', '2015/09/28 18:52', '创恒', '中山北路1715号', '创恒', '61392725', '未配送', '251319509', null, null);
INSERT INTO `bill_order` VALUES ('112', '梁源', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '{\"items\":[{\"$$hashKey\":\"object:14\",\"amount\":\"1\",\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 19:14:36', '2015/09/29 19:13', '创恒', '中山北路1715号', '创恒', '61392725', '未配送', '074695274', null, null);
INSERT INTO `bill_order` VALUES ('113', 'Mz、?', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '{\"items\":[{\"$$hashKey\":\"object:24\",\"amount\":1,\"description\":\"家用鲜米机\",\"picurl\":\"product_images/nianmiji.jpg\",\"productId\":215,\"productName\":\"家用鲜米机\",\"productPrice\":1500,\"productUnit\":\"台\"}],\"totalAmount\":1,\"totalPrice\":1500}', '2015-09-28 19:27:59', '2015/09/30 19:25', '张', '浦发广场', '张', '13917444535', '未配送', '231111026', null, null);

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_type` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `openid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `reached_money` double(255,2) DEFAULT NULL,
  `deducted_money` double(255,2) DEFAULT NULL,
  `discount_factor` double(255,2) DEFAULT NULL,
  `used` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for cs_message
-- ----------------------------
DROP TABLE IF EXISTS `cs_message`;
CREATE TABLE `cs_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cs_message
-- ----------------------------
INSERT INTO `cs_message` VALUES ('1', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'abc', '2015-01-01 08:00:00', null, null);
INSERT INTO `cs_message` VALUES ('2', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'bca', '2015-01-01 08:00:00', 'http://wx.qlogo.cn/mmopen/wzJhLVPsrd06ia1IOb3oxvTsaxO3MzicglGboAtSwia6e9eIMu61N0jXe6LnMwsOZrFKFOctnbUX4q6Bx3dR2XyRianmwgcXEaicib/0', 'lingda');
INSERT INTO `cs_message` VALUES ('3', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '我就爱听', '2015-09-28 17:59:59', null, null);
INSERT INTO `cs_message` VALUES ('4', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '哈喽', '2015-09-28 18:07:39', null, null);
INSERT INTO `cs_message` VALUES ('5', 'oh88lw58rD8OStujg82PIQyXO-M4', '？', '2015-09-28 18:09:46', null, null);
INSERT INTO `cs_message` VALUES ('6', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '1', '2015-09-28 18:10:05', null, null);
INSERT INTO `cs_message` VALUES ('7', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'test', '2015-09-28 18:29:12', 'http://wx.qlogo.cn/mmopen/wzJhLVPsrd06ia1IOb3oxvTsaxO3MzicglGboAtSwia6e9eIMu61N0jXe6LnMwsOZrFKFOctnbUX4q6Bx3dR2XyRianmwgcXEaicib/0', '灵达');
INSERT INTO `cs_message` VALUES ('8', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', '1', '2015-09-28 18:38:30', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLB0O1bphE5hn8xFlhCjS1fBsxwTSIE7hacoURN2ly0fI4LT7pPN6HW6EgkJ4zh9dtvdo5XicUHsbmg/0', 'Mz、?');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('1', '%E4%B8%8D%E9%94%99=');
INSERT INTO `feedback` VALUES ('2', '不错');
INSERT INTO `feedback` VALUES ('3', 'bc');
INSERT INTO `feedback` VALUES ('4', 'bc啊啊啊');
INSERT INTO `feedback` VALUES ('5', '');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL,
  `content` text,
  `ts` datetime DEFAULT NULL,
  `has_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('20', 'oh88lw5qzIAlCg6kVI6S3W2XYXNc', '欢迎您关注我们', '2015-09-06 16:30:45', '0');
INSERT INTO `message` VALUES ('21', 'oh88lwxkHk93He21A7lmJLuJmmz0', '欢迎您关注我们', '2015-09-07 22:07:43', '0');
INSERT INTO `message` VALUES ('22', 'oh88lwwjau2kNlgNNecFq2tjPg-I', '欢迎您关注我们', '2015-09-08 12:51:37', '0');
INSERT INTO `message` VALUES ('23', 'oh88lw_1PzVA5DGhGz2nJNAWLZF4', '欢迎您关注我们', '2015-09-09 09:20:07', '0');
INSERT INTO `message` VALUES ('24', 'oh88lw1SvRk0c7HNJl7uhJWiEV_w', '欢迎您关注我们', '2015-09-09 12:59:58', '0');
INSERT INTO `message` VALUES ('25', 'oh88lw12KqKj-5BrXPTB_EJvWTIU', '欢迎您关注我们', '2015-09-09 14:47:16', '0');
INSERT INTO `message` VALUES ('26', 'oh88lw6vJG7g441gJMGHzGh2j9Og', '欢迎您关注我们', '2015-09-09 15:20:42', '0');
INSERT INTO `message` VALUES ('27', 'oh88lwyCXh16XMjCtxLlJhqEgQxU', '欢迎您关注我们', '2015-09-11 20:27:11', '0');
INSERT INTO `message` VALUES ('28', 'oh88lw0WbfC8VYiTOBd4NpogMODE', '欢迎您关注我们', '2015-09-15 20:56:11', '0');
INSERT INTO `message` VALUES ('29', 'oh88lw8WC6we-aXAVMppyByuEQ7s', '欢迎您关注我们', '2015-09-16 16:29:42', '0');
INSERT INTO `message` VALUES ('30', 'oh88lw2hOzkugJQSF22kqZpF0-iQ', '欢迎您关注我们', '2015-09-16 16:33:10', '0');
INSERT INTO `message` VALUES ('31', 'oh88lw6_JSjwYTLKFJxHHCV3OZyw', '欢迎您关注我们', '2015-09-16 16:35:11', '0');
INSERT INTO `message` VALUES ('32', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '欢迎您关注我们', '2015-09-17 18:17:10', '0');
INSERT INTO `message` VALUES ('33', 'oh88lw45fMV8l0epZSf1K9SqWtK4', '欢迎您关注我们', '2015-09-17 21:45:38', '0');
INSERT INTO `message` VALUES ('34', 'oh88lwyy9zRJFdRNnoQTktd_A5bI', '欢迎您关注我们', '2015-09-18 20:06:47', '0');
INSERT INTO `message` VALUES ('35', 'oh88lw9blqe1Jl9eh0jgBZE002ZI', '欢迎您关注我们', '2015-09-20 20:50:01', '0');
INSERT INTO `message` VALUES ('36', 'oh88lw0h01o65D0OOu1Ptl55W60g', '欢迎您关注我们', '2015-09-24 13:55:39', '0');
INSERT INTO `message` VALUES ('37', 'oh88lw70K8lEYUrJVQuUv3cuNzns', '欢迎您关注我们', '2015-09-24 14:48:21', '0');
INSERT INTO `message` VALUES ('38', 'oh88lw6yN110ch8CtuuqtXnMJA3s', '欢迎您关注我们', '2015-09-26 14:02:42', '0');
INSERT INTO `message` VALUES ('39', 'oh88lwzCFC3SLW0wb2OTaUyvqtIg', '欢迎您关注我们', '2015-09-27 14:09:56', '0');
INSERT INTO `message` VALUES ('40', 'oh88lw58rD8OStujg82PIQyXO-M4', '欢迎您关注我们', '2015-09-28 09:54:53', '0');

-- ----------------------------
-- Table structure for procurement
-- ----------------------------
DROP TABLE IF EXISTS `procurement`;
CREATE TABLE `procurement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `procprice` double DEFAULT NULL,
  `procindex` double DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procurement
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `unit` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picurl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picurl_zoom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `onsale` int(1) DEFAULT '0',
  `data_change_last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_index` int(11) DEFAULT NULL,
  `detail` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('215', '家用鲜米机', '家用鲜米机', 'MACHINE', 'NIANMIJI', '1500', '台', 'product_images/nianmiji.jpg', null, '0', '2015-09-22 11:08:40', '1', '<img src=\'images/pic1442891288487669842397.jpg\'>');
INSERT INTO `product` VALUES ('216', '优品黑土小珍珠', '精选优品原种黑土小珍珠 5KG装', 'MIMIANLIANGYOU', 'DAMI', '70', '5KG/袋', 'product_images/pic-1991289397122836835412761009031443085655264.jpg', null, '0', '2015-09-24 17:07:35', '0', '<img src=\'images/pic1442890551597-621242209.jpg\'>\n<img src=\'images/pic1442890563491-620318688.jpg\'>');
INSERT INTO `product` VALUES ('217', '优品五常稻花香', '精选优品原种五常稻花香 8KG装', 'MIMIANLIANGYOU', 'DAMI', '192', '8KG/袋', 'product_images/pic575183062-88351057212761009031442893441267.jpg', null, '0', '2015-09-24 09:26:11', '1', '<img src=\'images/pic1442890647245-619430146.jpg\'>\n<img src=\'images/pic1442890659382-618506625.jpg\'>');
INSERT INTO `product` VALUES ('218', '优品寒地富硒谷', '精选优品原种寒地富硒谷 8KG装', 'MIMIANLIANGYOU', 'DAMI', '240', '8KG/袋', 'product_images/pic-650244726125528028812761009031442893502852.jpg', null, '0', '2015-09-24 09:26:33', '2', '<img src=\'images/pic14428907778391858393296.jpg\'>\n<img src=\'images/pic14428907892771859316817.jpg\'>');
INSERT INTO `product` VALUES ('223', '新用户直通车', '新用户0元下单，免费送货上门并办理会员', 'MACHINE', 'NIANMIJI', '0', '份', 'product_images/pic65766716962940809215432950151443404702679.jpg', null, '0', '2015-09-28 09:45:02', '0', '<p>买谷卡，免费送鲜米机</p>\n<img src=\'images/pic14425755444971182717720.jpg\'>');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `role` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `headimgurl` text CHARACTER SET utf8,
  `consignee` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `consignee_contact` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `buyer_info` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `buyer_address` text CHARACTER SET utf8,
  `account` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('26', 'oh88lwyr0lDwuey9tr3o1hUIajPA', 'oh88lwyr0lDwuey9tr3o1hUIajPA', '123456', null, '灵达', 'USER', 'http://wx.qlogo.cn/mmopen/wzJhLVPsrd06ia1IOb3oxvTsaxO3MzicglGboAtSwia6e9eIMu61N0jXe6LnMwsOZrFKFOctnbUX4q6Bx3dR2XyRianmwgcXEaicib/0', '灵达', '13402188638', '新中源大楼', '长阳路1930号', '1070.00');
INSERT INTO `user` VALUES ('27', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'oh88lw9CkJc9ByMyA7lhNCqtC4wc', 'zhang228701', null, 'Mz、?', 'USER', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLB0O1bphE5hn8xFlhCjS1fBsxwTSIE7hacoURN2ly0fI4LT7pPN6HW6EgkJ4zh9dtvdo5XicUHsbmg/0', '张', '13917444535', '张', '浦发广场', '0.00');
INSERT INTO `user` VALUES ('29', 'oh88lw_1PzVA5DGhGz2nJNAWLZF4', 'oh88lw_1PzVA5DGhGz2nJNAWLZF4', '731127', null, '天行健', 'USER', 'http://wx.qlogo.cn/mmopen/a5ejNnmFDsCT6dlRFqyeNl18asrcX0rUdyaz6tC4Y4eFwe43SOtr3tyXibvrkNYlcWibwicpASibn2ImgJLFpSL6hCcewicSFJqAh/0', null, null, null, null, '0.00');
INSERT INTO `user` VALUES ('31', 'oh88lwyy9zRJFdRNnoQTktd_A5bI', 'oh88lwyy9zRJFdRNnoQTktd_A5bI', null, null, '品味人生', 'USER', 'http://wx.qlogo.cn/mmopen/CpUViaz60DZZ1tujqss2Y7PMBajfjzrTShkQNXzOnrnqfAJFN67jJKcQNgcguricc96vNibGq3JqFebZAOlXOFGN5cvuRgichaml/0', null, null, null, null, '0.00');
INSERT INTO `user` VALUES ('32', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', 'oh88lw-vDzuoM7HmbNB-NwnbIES4', '86378229', null, '李含嘉', null, 'http://wx.qlogo.cn/mmopen/a5ejNnmFDsCT6dlRFqyeNv38SDhoqH4ApVzEt9NkO6p3NicIiciaqYBKwpOytk0VKTvcH9fl2K0ERzRakCX8WN76uZKMCMEFHFia/0', '李杰', '13817476348', '李杰', '上海市普陀区中山北路1715号浦发广场e座805', '2000.00');
INSERT INTO `user` VALUES ('33', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', 'oh88lw7YHK44AVgj_Ip8AGGWR4aI', '940816', null, '梁源', null, 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM4fWa4lmhVtBv2Lia2MnFU2kqCzEc7H0PkjgnahlZ07mYkDRxaRxevichic4fM0USvLoBZ5tXAD6mKRN8fDibFyNLXqy4O8reicWUkU/0', '创恒', '61392725', '创恒', '中山北路1715号', '2000.00');
INSERT INTO `user` VALUES ('34', 'oh88lw9blqe1Jl9eh0jgBZE002ZI', 'oh88lw9blqe1Jl9eh0jgBZE002ZI', '123456', null, '徐荣彪', 'USER', 'http://wx.qlogo.cn/mmopen/a5ejNnmFDsCT6dlRFqyeNgMG7F8VP880NufkIuERrYenNXKHTAYWx9pogjm9urqfzHGhUJE6ooF0EkoC3sWzicjlzGm8SqJ43/0', null, null, null, null, '0.00');
INSERT INTO `user` VALUES ('35', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', 'oh88lw-MVuTWCnuehJqWkkh0CRaY', '66837416', null, 'Monique', null, 'http://wx.qlogo.cn/mmopen/dvomsdosF1icpx63EgTaYicm4ibG7eKmZ0USQYTW0CIgPnR1NTWyM2A8plXibxzCICiatL8ia5pIIicUULwibpIURglmGgHA0XgiaHiciaN/0', 'yang', '杨文琴', '杨文琴', '上海', '1500.00');
INSERT INTO `user` VALUES ('36', 'oh88lw58rD8OStujg82PIQyXO-M4', 'oh88lw58rD8OStujg82PIQyXO-M4', '123456', null, '汪立雄', 'USER', 'http://wx.qlogo.cn/mmopen/a5ejNnmFDsCT6dlRFqyeNgBArRJSGB9FyM6LYWxvjtEmQovqYLHKfRHL04XJ9GJ80Kdb0htAq82UmQsLYagJxGfK73IkaaI6/0', '汪立雄', '15921621515', '汪立雄', '上海市普陀区中江路1067弄63号601', '1500.00');
INSERT INTO `user` VALUES ('37', 'oh88lw_qAufZMHXTwTV1xV7wfpe8', 'oh88lw_qAufZMHXTwTV1xV7wfpe8', '000000', null, '开心每一天', 'USER', 'http://wx.qlogo.cn/mmopen/a5ejNnmFDsCT6dlRFqyeNo4OuEtsk7ttdUB1ianojraa2zcXnmhTc6e2oWylp2Wymh17NvoIxLfDHaC5Nr1gkialpcafLxjJ99/0', '好', '好', '好', '好', '0.00');
INSERT INTO `user` VALUES ('38', 'oh88lw3T3gM0BvtR1dhMuJlg2ECc', 'oh88lw3T3gM0BvtR1dhMuJlg2ECc', '000000', null, 'na', null, null, '人', '啊', '在', '于', '0.00');
INSERT INTO `user` VALUES ('39', 'oh88lwzCFC3SLW0wb2OTaUyvqtIg', 'oh88lwzCFC3SLW0wb2OTaUyvqtIg', '000000', null, 'Vincent wang', 'USER', 'http://wx.qlogo.cn/mmopen/PiajxSqBRaEJxdWSBrL3KBhX20UL994MRMEYdwaTuwGlwe98NstibxxsJAChx17ulLRUxhDibuSribuo8PAoQvFaZg/0', null, null, null, null, '0.00');
