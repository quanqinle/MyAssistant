/*
Source Server         : myDB
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : myworld

Target Server Type    : MYSQL
 */

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