/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : homework_manager

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2019-05-02 18:22:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_number` varchar(100) NOT NULL COMMENT '课程编号',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `teacher_id` int(11) DEFAULT NULL COMMENT '任课教师（用户id）',
  `class_ids` text COMMENT '班级id集合',
  `class_time` varchar(100) DEFAULT NULL COMMENT '上课时间',
  `semester` varchar(100) DEFAULT NULL COMMENT '开课学期',
  `credit` int(2) DEFAULT NULL COMMENT '学分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '作业标题',
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `assign_time` datetime NOT NULL COMMENT '作业发布时间',
  `deadline` datetime NOT NULL COMMENT '作业截止时间',
  `question_ids` text NOT NULL COMMENT '题目id集合',
  `is_automatic` int(1) NOT NULL COMMENT '是否自动批改: 1是，0否',
  `is_assign` int(1) NOT NULL COMMENT '是否发布 1是，0否 , 2再次发布',
  `remark` varchar(200) DEFAULT NULL COMMENT '自定义附加描述；备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业表';

-- ----------------------------
-- Table structure for homework_score
-- ----------------------------
DROP TABLE IF EXISTS `homework_score`;
CREATE TABLE `homework_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学生（用户id）',
  `homework_id` int(11) NOT NULL COMMENT '作业id',
  `answer` text COMMENT '作业答案',
  `submit_time` datetime NOT NULL COMMENT '上交时间',
  `correct_rate` double(11,2) DEFAULT NULL COMMENT '正确率(百分制）',
  `score` int(3) DEFAULT NULL COMMENT '成绩(百分制)',
  `evaluate` varchar(200) DEFAULT NULL COMMENT '评价',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `file_path` text COMMENT '文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业成绩表';

-- ----------------------------
-- Table structure for item_bank
-- ----------------------------
DROP TABLE IF EXISTS `item_bank`;
CREATE TABLE `item_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL COMMENT '类别名称',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题库表';

-- ----------------------------
-- Table structure for language_mark
-- ----------------------------
DROP TABLE IF EXISTS `language_mark`;
CREATE TABLE `language_mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mark` varchar(100) NOT NULL COMMENT '语言标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目语言登记表';

-- ----------------------------
-- Table structure for major_class
-- ----------------------------
DROP TABLE IF EXISTS `major_class`;
CREATE TABLE `major_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_number` varchar(100) NOT NULL COMMENT '班级编号',
  `class_name` varchar(100) NOT NULL COMMENT '班级名字',
  `grade` varchar(100) NOT NULL COMMENT '入学时间/年级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Table structure for message_interaction
-- ----------------------------
DROP TABLE IF EXISTS `message_interaction`;
CREATE TABLE `message_interaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `homework_id` int(11) NOT NULL COMMENT '作业id',
  `student_id` int(11) NOT NULL COMMENT '学生（用户id）',
  `user_id` int(11) NOT NULL COMMENT '用户id(教师或者学生）',
  `message` text NOT NULL COMMENT '留言',
  `message_time` datetime NOT NULL COMMENT '留言时间',
  `is_reply` int(1) NOT NULL COMMENT '是留言还是回复：1、是回复；0、是留言',
  `is_see` int(1) NOT NULL COMMENT '是否查看过：1、查看过；0、没有查看过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业留言表';

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_number` varchar(100) NOT NULL COMMENT '题目编号',
  `question_name` varchar(100) NOT NULL COMMENT '题目名称',
  `description` text NOT NULL COMMENT '详细内容描述',
  `item_id` int(11) NOT NULL COMMENT '题库id',
  `answer` text COMMENT '答案',
  `language_id` int(11) DEFAULT NULL COMMENT '所属语言id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for test_data
-- ----------------------------
DROP TABLE IF EXISTS `test_data`;
CREATE TABLE `test_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL COMMENT '题目id',
  `input` text COMMENT '一组输入',
  `output` text COMMENT '一组输出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试数据表';

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `account` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `user_name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `user_number` varchar(100) DEFAULT NULL COMMENT '编号',
  `class_id` int(11) DEFAULT NULL COMMENT '所在班级id',
  `sex` int(1) DEFAULT NULL COMMENT '性别，1代表男，0代表女',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `enabled` int(1) NOT NULL COMMENT '是否启用，1为启用，0为不启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
