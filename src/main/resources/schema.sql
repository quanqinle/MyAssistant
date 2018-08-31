CREATE TABLE person (
  id int(10) not null,
  name varchar(20) NOT NULL COMMENT '用户名',
  age int(3) DEFAULT 0 comment '年龄',
  nation varchar(10) DEFAULT '' COMMENT '民族',
  address varchar(10) DEFAULT '' COMMENT '住址',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;