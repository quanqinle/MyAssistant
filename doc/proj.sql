/*
Source Server         : myDB
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : myworld

Target Server Type    : MYSQL
Date: 2018-07-27 10:27:40
*/

CREATE SCHEMA `myworld` DEFAULT CHARACTER SET utf8mb4 ;

/*---- 系统字典 ----*/
DROP TABLE IF EXISTS system_dict;
CREATE TABLE system_dict (
  id bigint(20) not null AUTO_INCREMENT,
  key VARCHAR(20) not NULL COMMENT '键',
  value VARCHAR(20) DEFAULT NULL comment '值',
  default_value VARCHAR(20) DEFAULT NULL comment '默认值',
  status TINYINT UNSIGNED DEFAULT 1 COMMENT '1有效，0删除',
  comment VARCHAR(100) DEFAULT NULL COMMENT '备注',
  create_time DATE,
  update_time DATE,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数字典';

/*---- 个税 ----*/
# DROP TABLE IF EXISTS t_individual_income_tax_rate;
DROP TABLE IF EXISTS tax_rate;
CREATE TABLE tax_rate (
  id bigint(20) not null AUTO_INCREMENT,
  rate float(7,4) DEFAULT 0 comment '税率',
  range_lowest float(7,2) DEFAULT 0 comment '左区间',
  range_highest float(7,2) DEFAULT 0 comment '右区间',
  quick_deduction float(7,2) DEFAULT 0 comment '速算数',
  status TINYINT UNSIGNED DEFAULT 1 COMMENT '1有效，0删除',
  effective_date VARCHAR(10) DEFAULT NULL COMMENT '费率生效日期',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='税-个税税率表';

insert into tax_rate values (1, 0.03, 0, 1500, 0, 0, '2011-09-01');
insert into tax_rate values (2, 0.10, 1500, 4500, 105, 0, '2011-09-01');
insert into tax_rate values (3, 0.20, 4500, 9000, 555, 0, '2011-09-01');
insert into tax_rate values (4, 0.25, 9000, 35000, 1005, 0, '2011-09-01');
insert into tax_rate values (5, 0.30, 35000, 55000, 2755, 0, '2011-09-01');
insert into tax_rate values (6, 0.35, 55000, 80000, 5505, 0, '2011-09-01');
insert into tax_rate values (7, 0.45, 80000, -1, 13505, 0, '2011-09-01');

insert into tax_rate values (8, 0.03, 0, 3000, 0, 1, '2018-10-1');
insert into tax_rate values (9, 0.10, 3000, 12000, 210, 1, '2018-10-1');
insert into tax_rate values (10, 0.20, 12000, 25000, 1410, 1, '2018-10-1');
insert into tax_rate values (11, 0.25, 25000, 35000, 2660, 1, '2018-10-1');
insert into tax_rate values (12, 0.30, 35000, 55000, 4410, 1, '2018-10-1');
insert into tax_rate values (13, 0.35, 55000, 80000, 7160, 1, '2018-10-1');
insert into tax_rate values (14, 0.45, 80000, -1, 15160, 1, '2018-10-1');

/* ---- 博客 ----*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL,
  name varchar(20) NOT NULL COMMENT '用户名',
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


-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS estate_community;
CREATE TABLE estate_community (
  id int(11) NOT NULL AUTO_INCREMENT,
  xqid varchar(10) NOT NULL COMMENT '小区id',
  xqmc varchar(50) NOT NULL COMMENT '小区名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-小区信息';

-- ----------------------------
-- Table structure for 挂牌信息接口返回
-- ----------------------------
DROP TABLE IF EXISTS estate_secondhand_listing;
CREATE TABLE estate_secondhand_listing (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  fwtybh varchar(20) NOT NULL COMMENT '房源核验统一编码',
  jzmj varchar(10) NOT NULL COMMENT '建筑面积',
  wtcsjg varchar(20) NOT NULL COMMENT '委托出售价格',
  accountid varchar(20) DEFAULT NULL COMMENT '账号id',
  accountname varchar(20) DEFAULT NULL COMMENT '账号名称',
  cjsj varchar(20) DEFAULT NULL COMMENT '创建时间',
  cqmc varchar(20) DEFAULT NULL COMMENT '城区名称',
  cqsj varchar(20) DEFAULT NULL COMMENT '城区速记？',
  cyrybh varchar(20) DEFAULT NULL COMMENT '从业人员编号',
  czfs varchar(20) DEFAULT NULL COMMENT '',
  dqlc varchar(20) DEFAULT NULL COMMENT '地区？',
  fbzt varchar(20) DEFAULT NULL COMMENT '发布状态',
  fczsh varchar(50) DEFAULT NULL COMMENT '房产证书',
  fwyt varchar(20) DEFAULT NULL COMMENT '房屋用途id',
  fwytValue varchar(20) DEFAULT NULL COMMENT '房屋用途',
  gisx varchar(20) DEFAULT NULL COMMENT '',
  gisy varchar(20) DEFAULT NULL COMMENT '',
  gpfyid varchar(20) DEFAULT NULL COMMENT '挂牌房屋id',
  gphytgsj varchar(20) DEFAULT NULL COMMENT '挂牌行业提供数据？',
  gpid varchar(20) DEFAULT NULL COMMENT '挂牌id',
  gplxrcode varchar(20) DEFAULT NULL COMMENT '挂牌联系人代码',
  gplxrdh varchar(15) DEFAULT NULL COMMENT '挂牌联系人电话',
  gplxrxm varchar(10) DEFAULT NULL COMMENT '挂牌联系人姓名',
  gply varchar(20) DEFAULT NULL COMMENT '挂牌来源',
  gpzt varchar(10) DEFAULT NULL COMMENT '挂牌状态id',
  gpztValue varchar(10) DEFAULT NULL COMMENT '挂牌状态',
  hxs varchar(20) DEFAULT NULL COMMENT '户型？',
  hxt varchar(20) DEFAULT NULL COMMENT '',
  hxw varchar(20) DEFAULT NULL COMMENT '',
  hyid varchar(20) DEFAULT NULL COMMENT '',
  hyjzsj varchar(20) DEFAULT NULL COMMENT '',
  isnew varchar(20) DEFAULT NULL COMMENT '是否新房？',
  mdmc varchar(50) DEFAULT NULL COMMENT '门店名称',
  qyid varchar(20) DEFAULT NULL COMMENT '签约id',
  qyzt varchar(20) DEFAULT NULL COMMENT '签约状态',
  scgpshsj varchar(20) DEFAULT NULL COMMENT '首次挂牌上市时间',
  sellnum varchar(20) DEFAULT NULL COMMENT '',
  sqhysj varchar(20) DEFAULT NULL COMMENT '',
  szlc varchar(20) DEFAULT NULL COMMENT '所在楼层',
  szlcname varchar(20) DEFAULT NULL COMMENT '所在楼层名称',
  tygpbh varchar(20) DEFAULT NULL COMMENT '统一挂牌编号',
  wtdqts varchar(20) DEFAULT NULL COMMENT '委托地区？',
  wtxybh varchar(20) DEFAULT NULL COMMENT '委托协议编号',
  wtxycode varchar(20) DEFAULT NULL COMMENT '委托协议代码',
  wtxyid varchar(20) DEFAULT NULL COMMENT '委托协议id',
  xqid varchar(20) DEFAULT NULL COMMENT '小区id',
  xqmc varchar(20) DEFAULT NULL COMMENT '小区名称',
  xzqh varchar(10) DEFAULT NULL COMMENT '行政区划id',
  xzqhname varchar(10) DEFAULT NULL COMMENT '行政区划名称',
  zzcs varchar(10) DEFAULT NULL COMMENT '住宅层数？',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房挂牌信息';

-- ----------------------------
-- Table structure for 二手房房屋信息
-- ----------------------------
DROP TABLE IF EXISTS estate_secondhand_house;
CREATE TABLE estate_secondhand_house (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  house_unique_id varchar(20) NOT NULL COMMENT '房源核验统一编码',
  covered_area varchar(10) NOT NULL COMMENT '建筑面积',
  district varchar(20) DEFAULT NULL COMMENT '市辖区（县级行政区）',
  house_property_ownership_certificate varchar(50) DEFAULT NULL COMMENT '房产证书',
  community_id varchar(20) DEFAULT NULL COMMENT '小区id',
  community_name varchar(50) DEFAULT NULL COMMENT '小区名称',
  city_code varchar(10) DEFAULT NULL COMMENT '行政区划编码',
  city_name varchar(10) DEFAULT NULL COMMENT '行政区划名称（地区级行政区）',
  CONSTRAINT pk_house PRIMARY KEY (id),
  CONSTRAINT uc_house UNIQUE (house_unique_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房房屋信息';
-- CREATE TABLE estate_secondhand_housing (
--   id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--   fwtybh varchar(20) NOT NULL COMMENT '房源核验统一编码',
--   jzmj varchar(10) NOT NULL COMMENT '建筑面积',
--   cqmc varchar(20) DEFAULT NULL COMMENT '城区名称',
--   fczsh varchar(50) DEFAULT NULL COMMENT '房产证书',
--   xqid varchar(20) DEFAULT NULL COMMENT '小区id',
--   xqmc varchar(50) DEFAULT NULL COMMENT '小区名称',
--   xzqh varchar(10) DEFAULT NULL COMMENT '行政区划id',
--   xzqhname varchar(10) DEFAULT NULL COMMENT '行政区划名称',
--   CONSTRAINT pk_housing PRIMARY KEY (id),
--   CONSTRAINT uc_housing UNIQUE (fwtybh)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房房屋信息';

-- ----------------------------
-- Table structure for 二手房挂牌信息（价格、委托方等）
-- ----------------------------
DROP TABLE IF EXISTS estate_secondhand_price;
CREATE TABLE estate_secondhand_price (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  house_unique_id varchar(20) NOT NULL COMMENT '房源核验统一编码',
  sale_price varchar(20) NOT NULL COMMENT '委托出售价格',
  entrust_time varchar(20) DEFAULT NULL COMMENT '委托协议创建时间',
  listing_house_id varchar(20) DEFAULT NULL COMMENT '挂牌房屋id',
  listing_id varchar(20) NOT NULL COMMENT '挂牌id',
  listing_contact_name varchar(10) DEFAULT NULL COMMENT '挂牌联系人姓名',
  listing_status varchar(10) DEFAULT NULL COMMENT '挂牌状态id',
  listing_status_value varchar(10) DEFAULT NULL COMMENT '挂牌状态',
  real_estate_agency varchar(50) DEFAULT NULL COMMENT '门店名称',
  listing_time varchar(20) DEFAULT NULL COMMENT '首次挂牌上市时间（政府网站公示）',
  listing_unique_id varchar(20) DEFAULT NULL COMMENT '统一挂牌编号',
  entrust_agreement_id varchar(20) DEFAULT NULL COMMENT '委托协议编号',
  CONSTRAINT pk_price PRIMARY KEY (id),
  CONSTRAINT uc_price UNIQUE (house_unique_id, listing_id),
  CONSTRAINT fk_price FOREIGN KEY (house_unique_id) REFERENCES estate_secondhand_house(house_unique_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房挂牌信息';
-- CREATE TABLE estate_secondhand_price (
--   id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--   fwtybh varchar(20) NOT NULL COMMENT '房源核验统一编码',
--   wtcsjg varchar(20) NOT NULL COMMENT '委托出售价格',
--   cjsj varchar(20) DEFAULT NULL COMMENT '创建时间',
--   gpfyid varchar(20) DEFAULT NULL COMMENT '挂牌房屋id',
--   gpid varchar(20) DEFAULT NULL COMMENT '挂牌id',
--   gplxrxm varchar(10) DEFAULT NULL COMMENT '挂牌联系人姓名',
--   gpzt varchar(10) DEFAULT NULL COMMENT '挂牌状态id',
--   gpztValue varchar(10) DEFAULT NULL COMMENT '挂牌状态',
--   mdmc varchar(50) DEFAULT NULL COMMENT '门店名称',
--   scgpshsj varchar(20) DEFAULT NULL COMMENT '首次挂牌上市时间',
--   tygpbh varchar(20) DEFAULT NULL COMMENT '统一挂牌编号',
--   wtxybh varchar(20) DEFAULT NULL COMMENT '委托协议编号',
--   CONSTRAINT pk_price PRIMARY KEY (id),
--   CONSTRAINT uc_price UNIQUE (fwtybh, gpid),
--   CONSTRAINT fk_price FOREIGN KEY (fwtybh) REFERENCES estate_secondhand_housing(fwtybh)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房挂牌信息';


CREATE TABLE estate_secondhand_listing_rest (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  fwtybh varchar(20) NOT NULL COMMENT '房源核验统一编码',
  accountid varchar(20) DEFAULT NULL COMMENT '账号id',
  accountname varchar(20) DEFAULT NULL COMMENT '账号名称',
  cqsj varchar(20) DEFAULT NULL COMMENT '城区速记？',
  cyrybh varchar(20) DEFAULT NULL COMMENT '从业人员编号',
  czfs varchar(20) DEFAULT NULL COMMENT '',
  dqlc varchar(20) DEFAULT NULL COMMENT '地区？',
  fbzt varchar(20) DEFAULT NULL COMMENT '发布状态',
  fwyt varchar(20) DEFAULT NULL COMMENT '房屋用途id',
  fwytValue varchar(20) DEFAULT NULL COMMENT '房屋用途',
  gisx varchar(20) DEFAULT NULL COMMENT '',
  gisy varchar(20) DEFAULT NULL COMMENT '',
  gphytgsj varchar(20) DEFAULT NULL COMMENT '挂牌行业提供数据？',
  gplxrcode varchar(20) DEFAULT NULL COMMENT '挂牌联系人代码',
  gplxrdh varchar(15) DEFAULT NULL COMMENT '挂牌联系人电话',
  gply varchar(20) DEFAULT NULL COMMENT '挂牌来源',
  hxs varchar(20) DEFAULT NULL COMMENT '户型？',
  hxt varchar(20) DEFAULT NULL COMMENT '',
  hxw varchar(20) DEFAULT NULL COMMENT '',
  hyid varchar(20) DEFAULT NULL COMMENT '',
  hyjzsj varchar(20) DEFAULT NULL COMMENT '',
  isnew varchar(20) DEFAULT NULL COMMENT '是否新房？',
  qyid varchar(20) DEFAULT NULL COMMENT '签约id',
  qyzt varchar(20) DEFAULT NULL COMMENT '签约状态',
  sellnum varchar(20) DEFAULT NULL COMMENT '',
  sqhysj varchar(20) DEFAULT NULL COMMENT '',
  szlc varchar(20) DEFAULT NULL COMMENT '所在楼层',
  szlcname varchar(20) DEFAULT NULL COMMENT '所在楼层名称',
  wtdqts varchar(20) DEFAULT NULL COMMENT '委托地区？',
  wtxycode varchar(20) DEFAULT NULL COMMENT '委托协议代码',
  wtxyid varchar(20) DEFAULT NULL COMMENT '委托协议id',
  zzcs varchar(10) DEFAULT NULL COMMENT '',
  PRIMARY KEY (id),
  CONSTRAINT pk_rest PRIMARY KEY (id),
  CONSTRAINT uc_rest UNIQUE (fwtybh)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房挂牌信息（剩余部分）';