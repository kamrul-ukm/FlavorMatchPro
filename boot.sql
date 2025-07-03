/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : boot

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 03/07/2025 17:31:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车项ID，主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `food_id` int(11) NOT NULL COMMENT '美食ID，外键关联food表',
  `quantity` int(11) NOT NULL DEFAULT 1 COMMENT '商品数量',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (7, 10, 3, 2, '2025-07-02 23:52:20');
INSERT INTO `cart` VALUES (8, 10, 1, 1, '2025-07-02 23:52:22');
INSERT INTO `cart` VALUES (9, 13, 3, 1, '2025-07-02 23:57:10');
INSERT INTO `cart` VALUES (10, 13, 1, 1, '2025-07-02 23:57:11');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (4, 'Western', 'Food');
INSERT INTO `category` VALUES (5, 'Chinese', 'Food');
INSERT INTO `category` VALUES (6, 'Indian', 'Food3');
INSERT INTO `category` VALUES (7, 'Malay', 'Food0');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配置表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'zfb', '51851003-472e-45ac-a2ba-9dd82348ad65.png');

-- ----------------------------
-- Table structure for consultation
-- ----------------------------
DROP TABLE IF EXISTS `consultation`;
CREATE TABLE `consultation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '咨询唯一标识',
  `user_id` int(11) NOT NULL COMMENT '用户唯一标识（关联用户表）',
  `question` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户咨询的问题',
  `reply` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '管理员的回复',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '咨询时间',
  `raw` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '二进制文件',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uc`(`user_id`) USING BTREE,
  CONSTRAINT `uc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线咨询' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of consultation
-- ----------------------------
INSERT INTO `consultation` VALUES (81, 12, 'hr', NULL, '2025-07-03 17:23:44', NULL);

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '美食ID，主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '美食名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '美食描述',
  `price` decimal(10, 2) NOT NULL COMMENT '价格（单位：元）',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `category_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '美食表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (1, '薯条', '美味的薯条', 1.00, '2bc298a3-1a5f-45de-8414-de749edae504.jpg', 4);
INSERT INTO `food` VALUES (3, 'Bc', 'Chicken nuggets in creamy tomato sauce.', 20.00, 'e8030001-6550-4da9-b91e-bde8d1b64c77.png', 6);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单项ID，主键',
  `order_id` int(11) NOT NULL COMMENT '订单ID，外键关联orders表',
  `food_id` int(11) NOT NULL COMMENT '美食ID，外键关联food表',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `price` decimal(10, 2) NOT NULL COMMENT '商品下单时单价（单位：元）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1, 1, 1, 7, 1.00);
INSERT INTO `order_item` VALUES (2, 2, 1, 3, 1.00);
INSERT INTO `order_item` VALUES (3, 3, 1, 1, 1.00);
INSERT INTO `order_item` VALUES (4, 3, 3, 1, 20.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID，主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `total_price` decimal(10, 2) NOT NULL COMMENT '订单总金额（单位：元）',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '待付款' COMMENT '订单状态（如：已支付、已发货、已完成）',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '配餐时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 10, 7.00, '已完成', '2025-07-01 14:49:05', '2025-07-01 22:50:00', 'aaaaaa', '吴总', '13800000000', '0101010201201');
INSERT INTO `orders` VALUES (2, 10, 3.00, '已完成', '2025-07-01 15:43:42', '2025-07-02 00:30:00', '不要辣椒', '吴总', '13800000000', '010101afdasfa');
INSERT INTO `orders` VALUES (3, 12, 21.00, '已支付', '2025-07-03 16:55:14', '2025-07-04 01:10:00', 'no spicy', 't1', '13112345678', 'ukmt1');

-- ----------------------------
-- Table structure for slide
-- ----------------------------
DROP TABLE IF EXISTS `slide`;
CREATE TABLE `slide`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '轮播图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of slide
-- ----------------------------
INSERT INTO `slide` VALUES (4, 'jiaozi', '8055594a-1022-4ddb-a254-9e3753a534b5.png');
INSERT INTO `slide` VALUES (5, 'BC', '80a1dac8-4301-4df8-b48b-0a7de9144d77.png');
INSERT INTO `slide` VALUES (6, 'CL', '55d90329-c097-4bc1-ac08-964991715be0.png');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '管理员', 'H1SoQxKhjf0zr6Y3AIFb1Q==', '男', NULL, '13800000000', '4151b534-a721-416f-a3bc-bc0983d27788.jpg', 'none', 'admin');
INSERT INTO `user` VALUES (12, 'Test1', 't1', 'kKqcWiCDrjRpdWhDLrCufA==', '男', 'ukmt1', '13112345678', 'fd77ec10-3492-4fb3-adac-9192a829a025.png', 'ukmstudent1', 'user');
INSERT INTO `user` VALUES (13, 'Test2', 't2', 'CZq7LBMtIEpQaU7B3loQlg==', '女', 'ukmt2', '13212345678', NULL, NULL, 'user');

SET FOREIGN_KEY_CHECKS = 1;
