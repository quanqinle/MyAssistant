/*
Source Server         : myDB
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : mysecretary

Target Server Type    : MYSQL
Date                  : 2021-07-05 10:27:40
*/

CREATE SCHEMA IF NOT EXISTS `myassistant`
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_0900_ai_ci;

/*---- 系统字典 ----*/
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  dict_key VARCHAR(20) NOT NULL COMMENT '键',
  dict_value VARCHAR(100) DEFAULT NULL comment '值',
  state TINYINT UNSIGNED DEFAULT 1 COMMENT '1有效，0删除',
  dict_comment VARCHAR(100) DEFAULT NULL COMMENT '备注',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_dict PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数字典';

/*---- 个税 ----*/
DROP TABLE IF EXISTS tax_rate;
CREATE TABLE tax_rate (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
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

insert into tax_rate values (8, 0.03, 0, 3000, 0, 1, '2018-10-01');
insert into tax_rate values (9, 0.10, 3000, 12000, 210, 1, '2018-10-01');
insert into tax_rate values (10, 0.20, 12000, 25000, 1410, 1, '2018-10-01');
insert into tax_rate values (11, 0.25, 25000, 35000, 2660, 1, '2018-10-01');
insert into tax_rate values (12, 0.30, 35000, 55000, 4410, 1, '2018-10-01');
insert into tax_rate values (13, 0.35, 55000, 80000, 7160, 1, '2018-10-01');
insert into tax_rate values (14, 0.45, 80000, -1, 15160, 1, '2018-10-01');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL COMMENT '用户名',
  password varchar(50) DEFAULT NULL,
  enabled varchar(5) DEFAULT '0' COMMENT '是否被禁用',
  credential varchar(5) DEFAULT '0' COMMENT '凭证是否过期',
  locked varchar(5) DEFAULT '0' COMMENT '是否被锁',
  expired varchar(5) DEFAULT '0' COMMENT '是否过期',
  create_time datetime DEFAULT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT uc_user UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('1', 'admin', 'FA5A66466E9006215E3F54BF5B2BEEA3', 'false', 'false', 'false', 'false', '2017-10-13 18:18:18');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
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
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS estate_community;
CREATE TABLE estate_community (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  xqid varchar(10) NOT NULL COMMENT '小区id',
  xqmc varchar(50) NOT NULL COMMENT '小区名称',
  CONSTRAINT pk_community PRIMARY KEY (id),
  CONSTRAINT uc_community UNIQUE (xqid)
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
  sjhm varchar(20) DEFAULT NULL COMMENT '？',
  CONSTRAINT pk_listing PRIMARY KEY (id),
  CONSTRAINT uc_listing UNIQUE (fwtybh, gpid)
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
  per_square_meter_price varchar(20) DEFAULT NULL COMMENT '每平方米单价',
  listing_time varchar(20) DEFAULT NULL COMMENT '首次挂牌上市时间（政府网站公示）',
  listing_id varchar(20) NOT NULL COMMENT '挂牌id',
  listing_house_id varchar(20) DEFAULT NULL COMMENT '挂牌房屋id',
  entrust_time varchar(20) DEFAULT NULL COMMENT '委托协议创建时间',
  listing_contact_name varchar(10) DEFAULT NULL COMMENT '挂牌联系人姓名',
  listing_status varchar(10) DEFAULT NULL COMMENT '挂牌状态id',
  listing_status_value varchar(10) DEFAULT NULL COMMENT '挂牌状态',
  real_estate_agency varchar(50) DEFAULT NULL COMMENT '门店名称',
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
  CONSTRAINT pk_rest PRIMARY KEY (id),
  CONSTRAINT uc_rest UNIQUE (fwtybh)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='房产-二手房挂牌信息（剩余部分）';


-- ----------------------------
-- Table structures for video
-- ----------------------------
DROP TABLE IF EXISTS video_site;
CREATE TABLE video_site (
  site_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '网站id',
  site_name varchar(20) NOT NULL COMMENT '网站名称',
  home_url varchar(100) DEFAULT NULL COMMENT '网站官方url',
  download_url varchar(100) DEFAULT NULL COMMENT '下载url',
  upload_url varchar(100) DEFAULT NULL COMMENT '上传url',
  site_desc varchar(255) DEFAULT NULL COMMENT '网站url',
  cookie TEXT DEFAULT NULL COMMENT '登录cookie',
  CONSTRAINT pk_site PRIMARY KEY (site_id),
  CONSTRAINT uc_site UNIQUE (site_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频相关网站';

DROP TABLE IF EXISTS video_info;
CREATE TABLE video_info (
  video_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  video_name varchar(255) NOT NULL COMMENT '文件名，含后缀',
  video_sn varchar(50) DEFAULT NULL COMMENT '网址中的序列号',
  source_site_id TINYINT DEFAULT NULL COMMENT '来源网站id',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_video PRIMARY KEY (video_id),
  CONSTRAINT uc_video UNIQUE (video_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频信息表';

DROP TABLE IF EXISTS video_upload;
CREATE TABLE video_upload (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  video_id BIGINT NOT NULL COMMENT '视频id',
  site_id TINYINT NOT NULL COMMENT '上传目标网站id',
  post_name varchar(255) NOT NULL COMMENT '文章名',
  state TINYINT NOT NULL COMMENT '状态 0发布成功1发布中2发布失败3删除',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_upload PRIMARY KEY (id),
  CONSTRAINT uc_vid_sid UNIQUE KEY (video_id, site_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频上传情况表';


/* Chinese history */
DROP TABLE IF EXISTS chinese_dynasty;
CREATE TABLE chinese_dynasty (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name varchar(16) NOT NULL COMMENT '朝代名',
  year_start varchar(16) NOT NULL COMMENT '起始年份',
  year_end varchar(16) NOT NULL COMMENT '终止年份',
  term varchar(16) DEFAULT NULL COMMENT '持续时间',
  years varchar(64) NOT NULL COMMENT '朝代年份',
  founder varchar(16) DEFAULT NULL COMMENT '建国君王',
  capital varchar(16) DEFAULT NULL COMMENT '国都',
  now_location varchar(64) DEFAULT NULL COMMENT '当今的位置',
  parent_name varchar(16) DEFAULT NULL COMMENT '父级朝代名',
  ethnic varchar(16) DEFAULT NULL COMMENT '民族',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_dynasty PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='中国朝代';

/* 英语单词、短语、句型、句子 */
DROP TABLE IF EXISTS english_word;
CREATE TABLE english_word (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  word varchar(256) NOT NULL COMMENT '字词句',
  type TINYINT UNSIGNED DEFAULT 0 COMMENT '类型 0未分类 1单词 2词组 3句型 4谚语成语 5单词对比 6词组对比 7句子 8语法',
  IPA varchar(32) DEFAULT NULL COMMENT '音标',
  meaning TEXT DEFAULT NULL COMMENT '含义',
  example TEXT DEFAULT NULL COMMENT '示例。如果和 meaning 必须有一个为空，请让 example 为空',
  category varchar(32) DEFAULT NULL COMMENT '分类。重要性，难度水平，来源集合 等',
  others varchar(32) DEFAULT NULL COMMENT '其他',
  state TINYINT UNSIGNED DEFAULT 1 COMMENT '状态：0删除，1有效',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_english PRIMARY KEY (id),
  CONSTRAINT uc_english UNIQUE (word)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='英语字、词、句';

/* 中国行政区划代码 */
DROP TABLE IF EXISTS chinese_region;
CREATE TABLE chinese_region (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  code varchar(6) NOT NULL COMMENT '编码。GB2260',
  name varchar(32) DEFAULT NULL COMMENT '名称。GB2260',
  type TINYINT UNSIGNED NOT NULL COMMENT '类型：1省 2市 3区县',
  revision varchar(16) DEFAULT NULL COMMENT '修订版本号',
  province varchar(16) DEFAULT NULL COMMENT '省',
  city varchar(32) DEFAULT NULL COMMENT '市',
  full_name varchar(128) DEFAULT NULL COMMENT '完整名称',
  state TINYINT UNSIGNED DEFAULT 1 COMMENT '0删除，1有效',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT pk_ch_region PRIMARY KEY (id),
  CONSTRAINT uc_ch_region UNIQUE (code,revision)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='中国行政区划代码';
