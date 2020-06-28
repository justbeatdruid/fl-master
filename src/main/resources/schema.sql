CREATE DATABASE IF NOT EXISTS `federated_learning`;

use `federated_learning`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_algorithm
-- ----------------------------
DROP TABLE IF EXISTS `tb_algorithm`;
CREATE TABLE `tb_algorithm`  (
  `id` int(0) NOT NULL COMMENT '算法ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '算法名',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法描述',
  `template` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法模板',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `algorithm_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '算法信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_dataset
-- ----------------------------
DROP TABLE IF EXISTS `tb_dataset`;
CREATE TABLE `tb_dataset`  (
  `id` int(0) NOT NULL COMMENT '数据ID',
  `federation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联邦ID',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '数据类型（0:训练，1:预测）',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `upload_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传文件路径',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据大小',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '数据状态（0:未完成，1:完成）',
  `data_indicator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据标识（table_name和namespace）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_federation
-- ----------------------------
DROP TABLE IF EXISTS `tb_federation`;
CREATE TABLE `tb_federation`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uuid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_at` date NULL DEFAULT NULL,
  `type` tinyint(0) NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `guest` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `hosts` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `status` tinyint(0) NULL DEFAULT NULL,
  `data_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `algorithm_id` int(0) NULL DEFAULT NULL,
  `param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_predict
-- ----------------------------
DROP TABLE IF EXISTS `tb_predict`;
CREATE TABLE `tb_predict`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '预测记录ID',
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预测任务UUID',
  `federation_uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联邦UUID',
  `train_uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '训练记录UUID',
  `algorithm_id` int(0) NULL DEFAULT NULL COMMENT '算法ID',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '预测状态(0:运行中，1:成功，2:失败)',
  `job_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预测详情URL',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '预测开始时间',
  `duration` datetime(0) NULL DEFAULT NULL COMMENT '预测耗时',
  `predict_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '预测参数(包括模型、数据、运行时参数)',
  `job_id` int(0) NULL DEFAULT NULL COMMENT '预测任务ID（用于导出数据）',
  `output_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导出文件路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_train
-- ----------------------------
DROP TABLE IF EXISTS `tb_train`;
CREATE TABLE `tb_train`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '训练任务ID',
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '训练记录UUID',
  `federation_uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联邦UUID',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '训练状态(0:运行中，1:成功，2:失败)',
  `job_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '训练详情URL',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '训练开始时间',
  `duration` datetime(0) NULL DEFAULT NULL COMMENT '训练耗时',
  `AUC` float(6, 0) NULL DEFAULT NULL COMMENT 'AUC值',
  `accuracy` float(6, 0) NULL DEFAULT NULL COMMENT '准确率',
  `algorithm_id` int(0) NULL DEFAULT NULL COMMENT '算法模型ID',
  `train_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '训练参数（包括数据与运行时参数）',
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输出模型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `uuid` varchar(32) DEFAULT NULL COMMENT '用户UUID',
  `party_id` int(11) DEFAULT NULL COMMENT '物理机ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '用户电话',
  `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
  `role` varchar(255) DEFAULT NULL COMMENT '用户角色',
  `del_flag` int(11) DEFAULT '0' COMMENT '标识位',
  `address` varchar(255) DEFAULT NULL COMMENT '用户地址',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `company_phone` varchar(255) DEFAULT NULL COMMENT '公司电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- --------------------------------------
-- Table structure for tb_user_federation
-- --------------------------------------
DROP TABLE IF EXISTS `tb_user_federation`;
CREATE TABLE `tb_user_federation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `federation_uuid` varchar(255) DEFAULT NULL COMMENT '联邦UUID',
  `status` varchar(32) DEFAULT NULL COMMENT '0:参与者,1:创建者',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `federation_id` (`federation_uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT = '用户联邦关系表' ROW_FORMAT = Dynamic;

-- ---------------------------------------
-- Table structure for tb_role
-- ---------------------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `rolename` varchar(32) DEFAULT NULL COMMENT '角色名',
  `keyword` varchar(64) DEFAULT NULL COMMENT '关键字',
  `description` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ---------------------------------------
-- Table structure for tb_role_menu
-- ---------------------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `menu_id` (`menu_id`),
  KEY `tb_role_menu_ibfk_1` (`role_id`),
  CONSTRAINT `tb_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`),
  CONSTRAINT `tb_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `tb_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ---------------------------------------
-- Table structure for tb_menu
-- ---------------------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `permissioncode` varchar(255) DEFAULT NULL COMMENT '权限码',
  `name` varchar(32) DEFAULT NULL COMMENT '权限名',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;
