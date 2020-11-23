/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 192.168.56.101:3306
 Source Schema         : course

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 22/11/2020 20:55:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter`  (
  `id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `course_id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '大章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chapter
-- ----------------------------
INSERT INTO `chapter` VALUES ('00000000', '0000000', '测试大章1');
INSERT INTO `chapter` VALUES ('00000001', '00000000', '测试大章2');
INSERT INTO `chapter` VALUES ('00000002', '00000002', '测试大章3');
INSERT INTO `chapter` VALUES ('00000003', '00000003', '测试大章4');
INSERT INTO `chapter` VALUES ('00000004', '00000004', '测试大章5');
INSERT INTO `chapter` VALUES ('00000005', '00000005', '测试大章6');
INSERT INTO `chapter` VALUES ('00000006', '00000006', '测试大章7');
INSERT INTO `chapter` VALUES ('00000007', '00000007', '测试大章8');
INSERT INTO `chapter` VALUES ('00000008', '00000008', '测试大章9');
INSERT INTO `chapter` VALUES ('00000009', '00000009', '测试大章10');
INSERT INTO `chapter` VALUES ('00000010', '00000010', '测试大章11');
INSERT INTO `chapter` VALUES ('00000011', '00000011', '测试大章12');
INSERT INTO `chapter` VALUES ('00000012', '00000012', '测试大章13');
INSERT INTO `chapter` VALUES ('00000013', '00000013', '测试大章14');
INSERT INTO `chapter` VALUES ('00000014', '00000014', '测试大章15');
INSERT INTO `chapter` VALUES ('00000015', '00000015', '测试大章16');
INSERT INTO `chapter` VALUES ('00000016', '00000016', '测试大章17');
INSERT INTO `chapter` VALUES ('00000017', '00000017', '测试大章18');
INSERT INTO `chapter` VALUES ('00000018', '00000018', '测试大章19');
INSERT INTO `chapter` VALUES ('00000019', '00000019', '测试大章20');
INSERT INTO `chapter` VALUES ('00000020', '00000020', '测试大章21');
INSERT INTO `chapter` VALUES ('00000021', '00000021', '测试大章22');
INSERT INTO `chapter` VALUES ('P3IWcuCB', 'f38m2JxZ', '测试课程02-大章1');
INSERT INTO `chapter` VALUES ('WVAy1f6y', 'f38m2JxZ', '测试课程02-大章2');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `summary` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '概述',
  `time` int(11) NULL DEFAULT 0 COMMENT '时长|单位秒',
  `price` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '价格（元）',
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `level` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '级别|枚举[CourseLevelEnum]：ONE(\"1\", \"初级\"),TWO(\"2\", \"中级\"),THREE(\"3\", \"高级\")',
  `charge` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费|枚举[CourseChargeEnum]：CHARGE(\"C\", \"收费\"),FREE(\"F\", \"免费\")',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态|枚举[CourseStatusEnum]：PUBLISH(\"P\", \"发布\"),DRAFT(\"D\", \"草稿\")',
  `enroll` int(11) NULL DEFAULT 0 COMMENT '报名数',
  `sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `created_at` datetime(3) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime(3) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('00000001', '测试课程01', '这是一门测试课程', 0, 19.90, '', '1', 'C', 'P', 100, 0, '2020-11-22 05:28:27.000', '2020-11-22 20:36:48.564');
INSERT INTO `course` VALUES ('f38m2JxZ', '测试课程02', NULL, 400, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-11-22 18:29:44.000', '2020-11-22 20:36:54.072');

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section`  (
  `id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'id',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `course_id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程|course.id',
  `chapter_id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '大章|chapter.id',
  `video` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频',
  `time` int(11) NULL DEFAULT NULL COMMENT '时长|单位秒',
  `charge` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费|C 收费；F 免费',
  `sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `created_at` datetime(3) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime(3) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小节' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES ('00000001', '测试小节01', '00000001', '00000000', '', 500, 'f', 1, '2020-11-22 05:28:27.000', '2020-11-22 05:28:27.000');
INSERT INTO `section` VALUES ('1T3ekRgd', '小节', '00000001', '5BCI9dyd', NULL, NULL, NULL, NULL, '2020-11-22 19:33:32.613', '2020-11-22 19:33:32.613');
INSERT INTO `section` VALUES ('2Na6JxGq', '1', '00000001', 'Cr6qp6ZK', NULL, NULL, NULL, NULL, '2020-11-22 19:36:39.627', '2020-11-22 19:36:39.627');
INSERT INTO `section` VALUES ('3Y2Lb5hL', '测试课程02-大章1-小节1', 'f38m2JxZ', 'P3IWcuCB', NULL, 100, NULL, NULL, '2020-11-22 20:01:11.000', '2020-11-22 20:37:02.944');
INSERT INTO `section` VALUES ('aSfIe3tL', '课程2-test1-1', 'f38m2JxZ', 'RyRmbGIE', NULL, NULL, NULL, NULL, '2020-11-22 19:39:55.523', '2020-11-22 19:39:55.523');
INSERT INTO `section` VALUES ('iKlJ8hOI', '测试课程02-大章1-小节2', 'f38m2JxZ', 'P3IWcuCB', NULL, 200, NULL, NULL, '2020-11-22 20:37:28.048', '2020-11-22 20:37:28.048');
INSERT INTO `section` VALUES ('jitfB4Lh', '1243', '00000001', '5BCI9dyd', NULL, NULL, NULL, NULL, '2020-11-22 19:35:53.631', '2020-11-22 19:35:53.631');
INSERT INTO `section` VALUES ('SNoIloMv', '测试课程02-大章2-小节1', 'f38m2JxZ', 'WVAy1f6y', NULL, 100, NULL, NULL, '2020-11-22 20:42:10.501', '2020-11-22 20:42:10.501');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '测试');
INSERT INTO `test` VALUES ('3', '测试2');

SET FOREIGN_KEY_CHECKS = 1;
