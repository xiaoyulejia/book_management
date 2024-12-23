/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : springboot-vue

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 26/06/2024 13:32:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图书编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出版社',
  `create_time` date NULL DEFAULT NULL COMMENT '出版时间',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '0：未归还 1：已归还',
  `borrownum` int NOT NULL COMMENT '此书被借阅次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (9, '12341541321', '十万个为什么', 12.00, '苏格与', '长理出版社', '2014-12-16', '0', 8);
INSERT INTO `book` VALUES (10, '2312315132131', '五万个为什么', 15.00, '苏格与', '长理出版社', '2014-12-24', '0', 4);
INSERT INTO `book` VALUES (11, '25213121232', '一万个为什么', 15.00, '苏格与', '长理出版社', '2014-12-16', '0', 6);
INSERT INTO `book` VALUES (12, '3213123123', '操作系统', 15.00, '苏格与', '长理出版社', '2014-12-16', '0', 9);
INSERT INTO `book` VALUES (13, '345621212321', '伊索寓言', 15.00, '苏格与', '长理出版社', '2014-12-16', '0', 10);
INSERT INTO `book` VALUES (15, '54112312321', '格林童话', 15.00, '苏格与', '长理出版社', '2014-12-16', '0', 11);

-- ----------------------------
-- Table structure for bookwithuser
-- ----------------------------
DROP TABLE IF EXISTS `bookwithuser`;
CREATE TABLE `bookwithuser`  (
  `id` bigint NOT NULL COMMENT '读者id',
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书编号',
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图书名',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '读者姓名',
  `lendtime` datetime(0) NULL DEFAULT NULL COMMENT '借阅时间',
  `deadtime` datetime(0) NULL DEFAULT NULL COMMENT '应归还时间',
  `prolong` int NULL DEFAULT NULL COMMENT '续借次数',
  PRIMARY KEY (`book_name`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bookwithuser
-- ----------------------------
INSERT INTO `bookwithuser` VALUES (14, '2312315132131', '五万个为什么', '123456', '2023-12-20 15:05:14', '2024-02-18 15:05:14', 0);
INSERT INTO `bookwithuser` VALUES (14, '345621212321', '伊索寓言', '123456', '2023-12-20 15:04:56', '2024-02-18 15:04:56', 0);
INSERT INTO `bookwithuser` VALUES (14, '12341541321', '十万个为什么', '123456', '2023-12-20 15:05:14', '2024-02-18 15:05:14', 0);
INSERT INTO `bookwithuser` VALUES (14, '3213123123', '操作系统', '123456', '2023-12-20 15:04:59', '2024-02-18 15:04:59', 0);
INSERT INTO `bookwithuser` VALUES (16, '54112312321', '格林童话', '12345', '2023-12-21 10:29:53', '2024-01-20 10:29:53', 1);

-- ----------------------------
-- Table structure for lend_record
-- ----------------------------
DROP TABLE IF EXISTS `lend_record`;
CREATE TABLE `lend_record`  (
  `reader_id` bigint NOT NULL COMMENT '读者id',
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图书编号',
  `bookname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书名',
  `lend_time` datetime(0) NULL DEFAULT NULL COMMENT '借书日期',
  `return_time` datetime(0) NULL DEFAULT NULL COMMENT '还书日期',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '0：未归还 1：已归还',
  `borrownum` int NOT NULL COMMENT '此书被借阅次数'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lend_record
-- ----------------------------
INSERT INTO `lend_record` VALUES (13, '465132123123', '狂人日记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (13, '54156461231', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (13, '54156461231', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 5);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-04 10:57:02', '2023-12-19 16:48:09', '0', 3);
INSERT INTO `lend_record` VALUES (13, '54156461231', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 6);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 5);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 6);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (13, '92392321222', '算法笔记', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (14, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (14, '2312315132131', '五万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (14, '25213121232', '一万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (14, '25213121232', '一万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (14, '345621212321', '伊索寓言', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (15, '345621212321', '伊索寓言', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (14, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (14, '25213121232', '一万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 3);
INSERT INTO `lend_record` VALUES (14, '2312315132131', '五万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 2);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 3);
INSERT INTO `lend_record` VALUES (14, '345621212321', '伊索寓言', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 3);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 5);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 6);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (14, '25213121232', '一万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (14, '345621212321', '伊索寓言', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-19 10:57:02', '2023-12-20 14:56:47', '1', 8);
INSERT INTO `lend_record` VALUES (14, '345621212321', '伊索寓言', '2023-12-19 10:57:02', '2023-12-20 14:56:49', '1', 9);
INSERT INTO `lend_record` VALUES (16, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 3);
INSERT INTO `lend_record` VALUES (16, '2312315132131', '五万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 3);
INSERT INTO `lend_record` VALUES (16, '25213121232', '一万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 5);
INSERT INTO `lend_record` VALUES (17, '54112312321', '格林童话', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 1);
INSERT INTO `lend_record` VALUES (16, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 4);
INSERT INTO `lend_record` VALUES (16, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2024-01-06 16:48:09', '1', 5);
INSERT INTO `lend_record` VALUES (16, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2023-12-20 16:48:09', '1', 6);
INSERT INTO `lend_record` VALUES (16, '12341541321', '十万个为什么', '2023-12-19 10:57:02', '2023-12-20 16:48:09', '1', 7);
INSERT INTO `lend_record` VALUES (14, '345621212321', '伊索寓言', '2023-12-20 15:04:56', NULL, '0', 10);
INSERT INTO `lend_record` VALUES (14, '3213123123', '操作系统', '2023-12-20 15:04:59', NULL, '0', 9);
INSERT INTO `lend_record` VALUES (14, '25213121232', '一万个为什么', '2023-12-20 15:05:12', NULL, '0', 6);
INSERT INTO `lend_record` VALUES (14, '2312315132131', '五万个为什么', '2023-12-20 15:05:14', NULL, '0', 4);
INSERT INTO `lend_record` VALUES (14, '12341541321', '十万个为什么', '2023-12-20 15:05:14', NULL, '0', 8);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-20 19:33:47', '2023-12-20 19:33:50', '1', 2);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-20 19:33:51', '2023-12-20 19:33:54', '1', 3);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-20 19:51:31', '2023-12-21 10:24:48', '1', 4);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:26:32', '2023-12-21 10:26:55', '1', 5);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:27:09', '2023-12-21 10:27:44', '1', 6);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:27:46', '2023-12-21 10:27:54', '1', 7);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:27:55', '2023-12-21 10:27:58', '1', 8);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:27:59', '2023-12-21 10:28:34', '1', 9);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:28:36', '2023-12-21 10:29:51', '1', 10);
INSERT INTO `lend_record` VALUES (16, '54112312321', '格林童话', '2023-12-21 10:29:53', NULL, '0', 11);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话号码',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `role` int NOT NULL COMMENT '角色、1：管理员 2：普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (5, 'admin', 'admin', 'star', '18321299982', '男', '尊贵的长理\n', 1);
INSERT INTO `user` VALUES (13, '2656454', '123456', '小娱乐家', '12313282823', '男', '长理', 2);
INSERT INTO `user` VALUES (15, '542212', '12345', 'wp', '12313282823', '女', '长理', 2);
INSERT INTO `user` VALUES (16, '12345', '12345', 'test', '13213232989', '男', '长理', 2);

SET FOREIGN_KEY_CHECKS = 1;
