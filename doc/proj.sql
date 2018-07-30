/*
Source Server         : myDB
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : myworld

Target Server Type    : MYSQL
Date: 2018-07-27 10:27:40
*/

CREATE SCHEMA `myworld` DEFAULT CHARACTER SET utf8mb4 ;

/* ---- 个税 ----*/
# DROP TABLE IF EXISTS t_individual_income_tax_rate;
DROP TABLE IF EXISTS tax_rate;
CREATE TABLE tax_rate (
  id int(2) not null,
  rate float(7,4) DEFAULT 0 comment '税率',
  range_lowest float(7,2) DEFAULT 0 comment '左区间',
  range_highest float(7,2) DEFAULT 0 comment '右区间',
  quick_deduction float(7,2) DEFAULT 0 comment '速算数',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into tax_rate values (1, 0.03, 0, 1500, 0);
insert into tax_rate values (2, 0.10, 1500, 4500, 105);
insert into tax_rate values (3, 0.20, 4500, 9000, 555);
insert into tax_rate values (4, 0.25, 9000, 35000, 1005);
insert into tax_rate values (5, 0.30, 35000, 55000, 2755);
insert into tax_rate values (6, 0.35, 55000, 80000, 5505);
insert into tax_rate values (7, 0.45, 80000, -1, 13505);

/* ---- 博客 ----*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL,
  username varchar(20) NOT NULL COMMENT '用户名',
  password varchar(50) DEFAULT NULL,
  enabled varchar(5) DEFAULT '0' COMMENT '是否被禁用',
  credential varchar(5) DEFAULT '0' COMMENT '凭证是否过期',
  locked varchar(5) DEFAULT '0' COMMENT '是否被锁',
  expired varchar(5) DEFAULT '0' COMMENT '是否过期',
  create_time datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_info_username_uindex (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('1', 'admin', 'FA5A66466E9006215E3F54BF5B2BEEA3', 'false', 'false', 'false', 'false', '2017-10-13 18:18:18');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS user_info;
CREATE TABLE user_info (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT NULL,
  avatar varchar(255) DEFAULT NULL COMMENT '头像',
  nickname varchar(20) DEFAULT NULL COMMENT '昵称',
  phone char(11) DEFAULT NULL COMMENT '电话号码',
  email varchar(50) DEFAULT NULL COMMENT '邮箱',
  signature varchar(2000) DEFAULT NULL COMMENT '个性签名',
  address varchar(50) DEFAULT NULL COMMENT '地址',
  announcement varchar(2000) DEFAULT NULL COMMENT '公告',
  wechart varchar(20) DEFAULT NULL COMMENT '微信账号',
  PRIMARY KEY (id),
  UNIQUE KEY user_info_userid_uindex (user_id),
  UNIQUE KEY user_info_nickname_uindex (nickname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO user_info VALUES ('1', '1', 'http://img0.ph.126.net/YOMXarD6Oe_W0vsIuxIg4w==/6597401819170413279.jpg', '放开朕', '18000000000', 'zhen@gmail.com', '再看，再看我把你吃掉', '中国 - 杭州', '这里是公告板', '不告诉你');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS article;
CREATE TABLE article (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '文章Id',
  title varchar(40) NOT NULL COMMENT '标题',
  content longtext NOT NULL COMMENT '内容',
  description varchar(500) NOT NULL COMMENT '文章简介，用于列表显示',
  category_id int(11) NOT NULL COMMENT '分类Id',
  status int(11) NOT NULL DEFAULT '0' COMMENT '状态 0：正常，1：不可用',
  create_time datetime NOT NULL COMMENT '发表时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  show_count int(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
  user_id int(11) DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '分类Id',
  category_name varchar(20) NOT NULL COMMENT '分类名称。唯一',
  alias_name varchar(20) NOT NULL COMMENT '别名  唯一  比如新闻 就用News 代替  栏目Id不显示在url中',
  sort int(11) NOT NULL DEFAULT '0' COMMENT '排序（0-10）',
  PRIMARY KEY (id),
  UNIQUE KEY aliasName_UNIQUE (alias_name),
  UNIQUE KEY categoryName_UNIQUE (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表  只支持一级分类  如果需要分多个层次 用标签来协助实现';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO category VALUES ('1', 'default', '未分类', '0');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  id int(11) NOT NULL AUTO_INCREMENT,
  tag_name varchar(20) NOT NULL COMMENT '标签名称  唯一',
  alias_name varchar(20) NOT NULL COMMENT '标签别名 唯一',
  PRIMARY KEY (id),
  UNIQUE KEY tagName_UNIQUE (tag_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO tag VALUES ('1', 'default', '默认');

-- ----------------------------
-- Table structure for articletag
-- ----------------------------
DROP TABLE IF EXISTS articletag;
CREATE TABLE articletag (
  id int(11) NOT NULL AUTO_INCREMENT,
  tag_id int(11) NOT NULL COMMENT '标签Id',
  article_id int(11) NOT NULL COMMENT '文章Id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签中间表';

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id int(20) NOT NULL AUTO_INCREMENT,
  username varchar(50) DEFAULT NULL,
  url varchar(1024) DEFAULT NULL,
  ip varchar(20) DEFAULT NULL,
  method varchar(255) DEFAULT NULL,
  args varchar(255) DEFAULT NULL,
  class_method varchar(255) DEFAULT NULL,
  exception varchar(2000) DEFAULT NULL,
  operate_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';


-- ----------------------------
-- Table structure for partner
-- ----------------------------
DROP TABLE IF EXISTS partner;
CREATE TABLE partner (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(15) NOT NULL COMMENT '站点名',
  site_url varchar(45) NOT NULL COMMENT '站点地址',
  site_desc varchar(45) NOT NULL COMMENT '站点描述  简单备注 ',
  sort int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='合作伙伴';
