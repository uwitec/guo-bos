/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : bos32

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-26 20:35:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bc_decidedzone
-- ----------------------------
DROP TABLE IF EXISTS `bc_decidedzone`;
CREATE TABLE `bc_decidedzone` (
  `id` varchar(32) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `staff_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh0xplk12o52a6eryw4hcqnfwt` (`staff_id`),
  CONSTRAINT `FK_decidedzone_staff` FOREIGN KEY (`staff_id`) REFERENCES `bc_staff` (`id`),
  CONSTRAINT `FKh0xplk12o52a6eryw4hcqnfwt` FOREIGN KEY (`staff_id`) REFERENCES `bc_staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_decidedzone
-- ----------------------------
INSERT INTO `bc_decidedzone` VALUES ('aa', 'aaaa', '402881e45e09462a015e0946f0720000');
INSERT INTO `bc_decidedzone` VALUES ('bbbb', 'bb', '402881e85e0d1142015e0d173d1c0000');
INSERT INTO `bc_decidedzone` VALUES ('xxxxxxxx', 'xx', '402881e45e09462a015e0946f0720000');
INSERT INTO `bc_decidedzone` VALUES ('xxxxxxxxxxxxxxxxxx', '！！！！！！！', '402881e45e0850bf015e08540e770000');
INSERT INTO `bc_decidedzone` VALUES ('zz', 'zz', '402881e45e0850bf015e08540e770000');

-- ----------------------------
-- Table structure for bc_region
-- ----------------------------
DROP TABLE IF EXISTS `bc_region`;
CREATE TABLE `bc_region` (
  `id` varchar(32) NOT NULL,
  `province` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `postcode` varchar(50) DEFAULT NULL,
  `shortcode` varchar(30) DEFAULT NULL,
  `citycode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_region
-- ----------------------------
INSERT INTO `bc_region` VALUES ('QY001', '北京市xxxxxx', '北京市', '东城区', '110101', 'BJSxxxxxBJDC', 'beijing');
INSERT INTO `bc_region` VALUES ('QY002', '北京市', '北京市', '西城区', '110102', 'BJBJXC', 'beijing');
INSERT INTO `bc_region` VALUES ('QY003', '北京市', '北京市', '朝阳区', '110105', 'BJBJCY', 'beijing');
INSERT INTO `bc_region` VALUES ('QY004', '北京市', '北京市', '丰台区', '110106', 'BJBJFT', 'beijing');
INSERT INTO `bc_region` VALUES ('QY005', '北京市', '北京市', '石景山区', '110107', 'BJBJSJS', 'beijing');
INSERT INTO `bc_region` VALUES ('QY006', '北京市', '北京市', '海淀区', '110108', 'BJBJHD', 'beijing');
INSERT INTO `bc_region` VALUES ('QY007', '北京市', '北京市', '门头沟区', '110109', 'BJBJMTG', 'beijing');
INSERT INTO `bc_region` VALUES ('QY008', '北京市', '北京市', '房山区', '110111', 'BJBJFS', 'beijing');
INSERT INTO `bc_region` VALUES ('QY009', '北京市', '北京市', '通州区', '110112', 'BJBJTZ', 'beijing');
INSERT INTO `bc_region` VALUES ('QY010', '北京市', '北京市', '顺义区', '110113', 'BJBJSY', 'beijing');
INSERT INTO `bc_region` VALUES ('QY011', '北京市', '北京市', '昌平区', '110114', 'BJBJCP', 'beijing');
INSERT INTO `bc_region` VALUES ('QY012', '北京市', '北京市', '大兴区', '110115', 'BJBJDX', 'beijing');
INSERT INTO `bc_region` VALUES ('QY013', '北京市', '北京市', '怀柔区', '110116', 'BJBJHR', 'beijing');
INSERT INTO `bc_region` VALUES ('QY014', '北京市', '北京市', '平谷区', '110117', 'BJBJPG', 'beijing');
INSERT INTO `bc_region` VALUES ('QY015', '北京市', '北京市', '密云县', '110228', 'BJBJMY', 'beijing');
INSERT INTO `bc_region` VALUES ('QY016', '北京市', '北京市', '延庆县', '110229', 'BJBJYQ', 'beijing');
INSERT INTO `bc_region` VALUES ('QY017', '河北省', '石家庄市', '长安区', '130102', 'HBSJZZA', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY018', '河北省', '石家庄市', '桥东区', '130103', 'HBSJZQD', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY019', '河北省', '石家庄市', '桥西区', '130104', 'HBSJZQX', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY020', '河北省', '石家庄市', '新华区', '130105', 'HBSJZXH', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY021', '河北省', '石家庄市', '井陉矿区', '130107', 'HBSJZJXK', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY022', '河北省', '石家庄市', '裕华区', '130108', 'HBSJZYH', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY023', '河北省', '石家庄市', '井陉县', '130121', 'HBSJZJX', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY024', '河北省', '石家庄市', '正定县', '130123', 'HBSJZZD', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY025', '河北省', '石家庄市', '栾城县', '130124', 'HBSJZLC', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY026', '河北省', '石家庄市', '行唐县', '130125', 'HBSJZXT', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY027', '河北省', '石家庄市', '灵寿县', '130126', 'HBSJZLS', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY028', '河北省', '石家庄市', '高邑县', '130127', 'HBSJZGY', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY029', '河北省', '石家庄市', '深泽县', '130128', 'HBSJZSZ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY030', '河北省', '石家庄市', '赞皇县', '130129', 'HBSJZZH', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY031', '河北省', '石家庄市', '无极县', '130130', 'HBSJZWJ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY032', '河北省', '石家庄市', '平山县', '130131', 'HBSJZPS', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY033', '河北省', '石家庄市', '元氏县', '130132', 'HBSJZYS', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY034', '河北省', '石家庄市', '赵县', '130133', 'HBSJZZ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY035', '河北省', '石家庄市', '辛集市', '130181', 'HBSJZXJ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY036', '河北省', '石家庄市', '藁城市', '130182', 'HBSJZGC', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY037', '河北省', '石家庄市', '晋州市', '130183', 'HBSJZJZ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY038', '河北省', '石家庄市', '新乐市', '130184', 'HBSJZXL', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY039', '河北省', '石家庄市', '鹿泉市', '130185', 'HBSJZLQ', 'shijiazhuang');
INSERT INTO `bc_region` VALUES ('QY040', '天津市', '天津市', '和平区', '120101', 'TJTJHP', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY041', '天津市', '天津市', '河东区', '120102', 'TJTJHD', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY042', '天津市', '天津市', '河西区', '120103', 'TJTJHX', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY043', '天津市', '天津市', '南开区', '120104', 'TJTJNK', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY044', '天津市', '天津市', '河北区', '120105', 'TJTJHB', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY045', '天津市', '天津市', '红桥区', '120106', 'TJTJHQ', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY046', '天津市', '天津市', '滨海新区', '120116', 'TJTJBHX', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY047', '天津市', '天津市', '东丽区', '120110', 'TJTJDL', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY048', '天津市', '天津市', '西青区', '120111', 'TJTJXQ', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY049', '天津市', '天津市', '津南区', '120112', 'TJTJJN', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY050', '天津市', '天津市', '北辰区', '120113', 'TJTJBC', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY051', '天津市', '天津市', '武清区', '120114', 'TJTJWQ', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY052', '天津市', '天津市', '宝坻区', '120115', 'TJTJBC', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY053', '天津市', '天津市', '宁河县', '120221', 'TJTJNH', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY054', '天津市', '天津市', '静海县', '120223', 'TJTJJH', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY055', '天津市', '天津市', '蓟县', '120225', 'TJTJJ', 'tianjin');
INSERT INTO `bc_region` VALUES ('QY056', '山西省', '太原市', '小店区', '140105', 'SXTYXD', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY057', '山西省', '太原市', '迎泽区', '140106', 'SXTYYZ', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY058', '山西省', '太原市', '杏花岭区', '140107', 'SXTYXHL', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY059', '山西省', '太原市', '尖草坪区', '140108', 'SXTYJCP', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY060', '山西省', '太原市', '万柏林区', '140109', 'SXTYWBL', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY061', '山西省', '太原市', '晋源区', '140110', 'SXTYJY', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY062', '山西省', '太原市', '清徐县', '140121', 'SXTYQX', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY063', '山西省', '太原市', '阳曲县', '140122', 'SXTYYQ', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY064', '山西省', '太原市', '娄烦县', '140123', 'SXTYLF', 'taiyuan');
INSERT INTO `bc_region` VALUES ('QY065', '山西省', '太原市', '古交市', '140181', 'SXTYGJ', 'taiyuan');

-- ----------------------------
-- Table structure for bc_staff
-- ----------------------------
DROP TABLE IF EXISTS `bc_staff`;
CREATE TABLE `bc_staff` (
  `id` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `haspda` char(1) DEFAULT NULL,
  `deltag` char(1) DEFAULT NULL,
  `station` varchar(40) DEFAULT NULL,
  `standard` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_staff
-- ----------------------------
INSERT INTO `bc_staff` VALUES ('402880e75e05feb4015e05ff481a0000', '郭小旭', '15771805379', '0', '1', 'Google', '标准一');
INSERT INTO `bc_staff` VALUES ('402881e45e0850bf015e08540e770000', 'xiaoxu', '15771805379', '1', '0', 'Twitter', '标准二');
INSERT INTO `bc_staff` VALUES ('402881e45e09462a015e0946f0720000', 'guoxiaox', '15771805379', '1', '0', 'Twitter', '标准三');
INSERT INTO `bc_staff` VALUES ('402881e85e0d1142015e0d173d1c0000', 'Xiaoxu Guowww', '15771805379', '1', '0', 'guo', 'wwww');

-- ----------------------------
-- Table structure for bc_subarea
-- ----------------------------
DROP TABLE IF EXISTS `bc_subarea`;
CREATE TABLE `bc_subarea` (
  `id` varchar(32) NOT NULL,
  `decidedzone_id` varchar(32) DEFAULT NULL,
  `region_id` varchar(32) DEFAULT NULL,
  `addresskey` varchar(100) DEFAULT NULL,
  `startnum` varchar(30) DEFAULT NULL,
  `endnum` varchar(30) DEFAULT NULL,
  `single` char(1) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlndbc8oldgbg3k1x63n3n6t7n` (`decidedzone_id`),
  KEY `FKcjwxm19mx5njn3xyqgqp431mb` (`region_id`),
  CONSTRAINT `FK_area_decidedzone` FOREIGN KEY (`decidedzone_id`) REFERENCES `bc_decidedzone` (`id`),
  CONSTRAINT `FK_area_region` FOREIGN KEY (`region_id`) REFERENCES `bc_region` (`id`),
  CONSTRAINT `FKcjwxm19mx5njn3xyqgqp431mb` FOREIGN KEY (`region_id`) REFERENCES `bc_region` (`id`),
  CONSTRAINT `FKlndbc8oldgbg3k1x63n3n6t7n` FOREIGN KEY (`decidedzone_id`) REFERENCES `bc_decidedzone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_subarea
-- ----------------------------
INSERT INTO `bc_subarea` VALUES ('402880e75e13e5b5015e13e652910000', 'zz', 'QY004', '丰台区', '11', '11', '0', '11');
INSERT INTO `bc_subarea` VALUES ('402880e75e13e5b5015e13e699660001', 'zz', 'QY004', '11', '11', '11', '1', '11');
INSERT INTO `bc_subarea` VALUES ('402880e75e178f6d015e1796c5900000', 'bbbb', 'QY002', 'sssddd', 'ss', 'ss', '0', 'ssss');
INSERT INTO `bc_subarea` VALUES ('402880e75e178f6d015e17b3f8db0001', 'xxxxxxxx', 'QY006', 'aa', 'aa', 'aa', '0', 'aa');
INSERT INTO `bc_subarea` VALUES ('402881e55e11ef16015e120271080001', 'xxxxxxxxxxxxxxxxxx', 'QY002', '西城区', '1', 'zz', '1', '北京西城区');
INSERT INTO `bc_subarea` VALUES ('402881e55e1238bf015e123985360000', 'aa', 'QY003', '朝阳区区', '1', 'aa', '1', '北京朝阳区');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salary` double DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `station` varchar(40) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', null, null, null, null, null, null);
INSERT INTO `t_user` VALUES ('2', '123', '123', null, null, null, null, null, null);
