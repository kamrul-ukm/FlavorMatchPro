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

 Date: 06/07/2025 23:53:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'cart id',
  `user_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1,
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'to cart time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'CART' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'CATEGORY' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (9, 'CHINESE', 'Chinese');
INSERT INTO `category` VALUES (10, 'MALAY', 'Malay');
INSERT INTO `category` VALUES (11, 'INDIAN', 'Indian');
INSERT INTO `category` VALUES (12, 'HARAM', 'Chinese');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'CONFIG\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'Alipay', '274d022f-0b0a-4b24-8f22-4e1bb6762523.png');

-- ----------------------------
-- Table structure for consultation
-- ----------------------------
DROP TABLE IF EXISTS `consultation`;
CREATE TABLE `consultation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'consultation id',
  `user_id` int(11) NOT NULL COMMENT 'consultation user',
  `question` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reply` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'consulation time',
  `raw` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uc`(`user_id`) USING BTREE,
  CONSTRAINT `uc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'CONSULTATION' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of consultation
-- ----------------------------
INSERT INTO `consultation` VALUES (10, 20, 'nice food', NULL, '2025-07-06 17:49:28', NULL);
INSERT INTO `consultation` VALUES (11, 20, NULL, 'thanks', '2025-07-06 17:49:40', NULL);

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'food id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'food name',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'food descri',
  `price` decimal(10, 2) NOT NULL COMMENT 'price rm',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'FOOD' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (14, 'Jiaozi', 'Dough wrappers filled with various ingredients, boiled, steamed or pan-fried; a Northern Chinese staple.', 22.00, '812bc5ff-f1e7-416a-977e-dd81ec325293.png', 9);
INSERT INTO `food` VALUES (15, 'Kung Pao Chicken', 'Stir-fried diced chicken, peanuts, and dried chilies.', 24.00, '1ce48b74-1c40-4b1b-913d-140281e5e533.png', 9);
INSERT INTO `food` VALUES (16, 'Mapo Tofu', 'Classic Sichuan dish; tofu with minced meat, numbing and spicy.', 25.00, '5a6a4280-bc60-4e2d-8fc8-09c94dbbe08f.png', 9);
INSERT INTO `food` VALUES (17, 'Nasi Lemak', 'Rice cooked in coconut milk, served with sambal chili paste, anchovies, peanuts, cucumber, and egg.', 12.00, 'cae8b013-dcd0-4a7a-b800-90d74918b3a9.png', 10);
INSERT INTO `food` VALUES (18, 'Rendang', 'Meat slow-cooked for a long time with various spices and coconut milk.', 26.00, '57d33ecd-5f29-406e-9cd7-966d22167d57.png', 10);
INSERT INTO `food` VALUES (19, 'Roti Canai', 'Flaky flatbread, served with curry sauce or other dips.', 13.00, '041d87fb-5fcb-40c6-bf8e-f9517fcb5cfd.png', 10);
INSERT INTO `food` VALUES (20, 'Satay', 'Charcoal-grilled marinated meat skewers served with peanut sauce.', 5.00, '0538ecf8-a626-42aa-8a4c-583a0cb93390.png', 10);
INSERT INTO `food` VALUES (21, 'Sweet and Sour Pork', 'Fried pork chunks tossed in a sweet and sour sauce.', 29.00, 'b185c8d0-fbd9-4e85-aeca-ac039aca23a0.png', 12);
INSERT INTO `food` VALUES (22, 'Butter Chicken', 'Chicken nuggets in creamy tomato sauce.', 20.00, '8d6a5ffe-0f57-4c96-bd77-80741594ec6a.png', 11);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'order id',
  `order_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `food_id`(`food_id`) USING BTREE,
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ORDER' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (18, 16, 21, 2, 29.00);
INSERT INTO `order_item` VALUES (19, 16, 22, 1, 20.00);
INSERT INTO `order_item` VALUES (20, 16, 17, 1, 12.00);
INSERT INTO `order_item` VALUES (21, 16, 14, 1, 22.00);
INSERT INTO `order_item` VALUES (22, 16, 16, 1, 25.00);
INSERT INTO `order_item` VALUES (23, 17, 14, 1, 22.00);
INSERT INTO `order_item` VALUES (24, 17, 15, 1, 24.00);
INSERT INTO `order_item` VALUES (25, 17, 16, 2, 25.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'order id',
  `user_id` int(11) NOT NULL,
  `total_price` decimal(10, 2) NOT NULL,
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'PAY' COMMENT 'order state',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'order time',
  `send_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ORDER' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (16, 20, 137.00, 'COMPLETED', '2025-07-06 17:48:23', '2025-07-07 02:20:00', '', 't1', '13112345678', 'DAIN IN');
INSERT INTO `orders` VALUES (17, 20, 96.00, 'COMPLETED', '2025-07-06 22:29:42', '2025-07-07 06:50:00', '', 't1', '13112345678', 'ukmshape1');

-- ----------------------------
-- Table structure for slide
-- ----------------------------
DROP TABLE IF EXISTS `slide`;
CREATE TABLE `slide`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'SLIDE' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of slide
-- ----------------------------
INSERT INTO `slide` VALUES (18, 'Butter Chicken', '6efed1a8-a024-42bc-8972-04c7ed894b1e.png');
INSERT INTO `slide` VALUES (19, 'Char Kway Teow', '589145c2-8ec8-48da-a324-87959f36a80e.png');
INSERT INTO `slide` VALUES (20, 'Curry Laksa', '1ec665f3-7a1f-485d-bfc9-9359c2aa7821.png');

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
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'USER' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'MANAGER NO.2', '11fgmIJqFoWBBD3JQLH1xQ==', 'FEMALE', NULL, '13800000002', 'da990d6f-04a5-44f2-bd50-abccb08b81b9.png', '!!! DO NOT DELETE THIS ACCOUNT', 'admin');
INSERT INTO `user` VALUES (20, 'Test1', 't1', 'kKqcWiCDrjRpdWhDLrCufA==', 'FEMALE', 'ukmshape1', '13112345678', 'd19772d4-affc-478e-8bb5-1d1e08db79cd.png', NULL, 'user');

SET FOREIGN_KEY_CHECKS = 1;
