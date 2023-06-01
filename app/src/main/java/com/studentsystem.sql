/*
Navicat MySQL Data Transfer

Source Server         : lige
Source Server Version : 50719
Source Host           : localhost:3308
Source Database       : studentsystem

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2023-05-21 20:21:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `credit` int(11) NOT NULL,
  `image_id` varchar(10) DEFAULT 'id',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('10001', '数学', '5', 'math');
INSERT INTO `course` VALUES ('10002', '语文', '5', 'chinese');
INSERT INTO `course` VALUES ('10003', '英语', '5', 'english');
INSERT INTO `course` VALUES ('10004', '物理', '5', 'physic');
INSERT INTO `course` VALUES ('10005', '体育', '5', 'sport');
INSERT INTO `course` VALUES ('10006', '数据库', '5', 'course');
INSERT INTO `course` VALUES ('10007', '系统结构', '6', 'course');
INSERT INTO `course` VALUES ('10008', '组成原理', '5', 'course');
INSERT INTO `course` VALUES ('10009', '程序设计', '6', 'course');
INSERT INTO `course` VALUES ('10010', '历史', '3', 'history');
INSERT INTO `course` VALUES ('10011', '心理学', '5', 'health');
INSERT INTO `course` VALUES ('10012', '地理', '3', 'geography');
INSERT INTO `course` VALUES ('10013', '化学', '5', 'chemistry');
INSERT INTO `course` VALUES ('10014', '發過火', '2', 'course');
INSERT INTO `course` VALUES ('10015', '非中介', '2', 'course');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `student_id` bigint(20) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`student_id`,`course_id`),
  KEY `fk_CID` (`course_id`),
  CONSTRAINT `fk_CID` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_SID` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('5001200111', '10001', '95');
INSERT INTO `score` VALUES ('5001200111', '10002', '95');
INSERT INTO `score` VALUES ('5001200111', '10003', '50');
INSERT INTO `score` VALUES ('5001200111', '10008', '0');
INSERT INTO `score` VALUES ('5001200111', '10009', '0');
INSERT INTO `score` VALUES ('5001200111', '10012', '0');
INSERT INTO `score` VALUES ('5001200111', '10013', '0');
INSERT INTO `score` VALUES ('5001200112', '10001', '95');
INSERT INTO `score` VALUES ('5001200112', '10006', '95');
INSERT INTO `score` VALUES ('5001200112', '10007', '95');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` varchar(1) NOT NULL,
  `age` int(11) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `image_id` varchar(10) DEFAULT 'id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('0', '管理员', '男', '0', '0000000', 'id');
INSERT INTO `student` VALUES ('5001200111', '镜子问', '男', '20', '13223788888', 'health');
INSERT INTO `student` VALUES ('5001200112', '小王', '男', '20', '123456789', 'id');
INSERT INTO `student` VALUES ('5001200113', '小张', '女', '20', '123456789', 'id');
INSERT INTO `student` VALUES ('5001200114', '小周', '男', '20', '123456789', 'id');
INSERT INTO `student` VALUES ('5001200115', '小吾', '女', '20', '123456789', 'id');
INSERT INTO `student` VALUES ('5001200116', '小贾', '男', '20', '123456789', 'id');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ID` FOREIGN KEY (`id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0', '000000');
INSERT INTO `user` VALUES ('5001200111', '123456');
INSERT INTO `user` VALUES ('5001200112', '123456');
INSERT INTO `user` VALUES ('5001200113', '123456');
INSERT INTO `user` VALUES ('5001200114', '123456');
INSERT INTO `user` VALUES ('5001200115', '123456');
INSERT INTO `user` VALUES ('5001200116', '123456');
