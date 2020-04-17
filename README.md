# sansan
工程由springmvc+mybatis构建，用于展示IG指定用户的时间线
地址入口：http://47.240.171.251:8080/sansan/ig/explore

## sql
帖子表定义

DROP TABLE IF EXISTS ig_post;
CREATE TABLE ig_post (
id varchar(20) PRIMARY KEY,
username varchar(32) NOT NULL,
publishTime bigint(0) NOT NULL,
createTime bigint(0) NOT NULL,
smallImg longtext ,
link varchar(32),
text longtext 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

用户表

DROP TABLE IF EXISTS ig_user;
CREATE TABLE ig_user (
userId varchar(20) ,
username varchar(32) PRIMARY KEY,
nickName text DEFAULT NULL,
smallHeadImg longtext,
headImg longtext,
postCount int,
followerCount int,
following int,
updateTime bigint(0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
