/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 5.7.10-log : Database - basicplatform
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`basicplatform` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `basicplatform`;

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `createtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app` */

insert  into `app`(`id`,`name`,`description`,`status`,`createtime`) values (1,'企业1','1','启用','2014-05-28');

/*Table structure for table `authority` */

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `authority` */

insert  into `authority`(`id`,`name`,`description`,`status`) values (1,'ROLE_USER','用户',1),(2,'ROLE_ADMIN','管理员',1),(3,'ROLE_ANONYMOUS','游客',1);

/*Table structure for table `authority_power` */

DROP TABLE IF EXISTS `authority_power`;

CREATE TABLE `authority_power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authorityId` bigint(20) DEFAULT NULL,
  `powerId` bigint(20) DEFAULT NULL,
  `powerResource` varchar(255) DEFAULT NULL,
  `authorityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `authority_power` */

insert  into `authority_power`(`id`,`authorityId`,`powerId`,`powerResource`,`authorityName`) values (1,1,1,'/rs/**','ROLE_USER'),(2,1,4,'/index.jsp','ROLE_USER'),(4,2,1,'/rs/**','ROLE_ADMIN'),(5,2,2,'/user.html','ROLE_ADMIN'),(6,2,3,'/admin.html','ROLE_ADMIN'),(7,2,4,'/index.jsp','ROLE_ADMIN'),(8,2,15,'cas/**','ROLE_ADMIN'),(9,3,5,'/rs/anonymous/**','ROLE_ANONYMOUS'),(10,3,6,'/rs/anonymousUser/**','ROLE_ANONYMOUS');

/*Table structure for table `power` */

DROP TABLE IF EXISTS `power`;

CREATE TABLE `power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `power` */

insert  into `power`(`id`,`resource`,`type`,`description`) values (1,'/rs/**','resource','所有rest服务'),(2,'/user.html','url',NULL),(3,'/admin.html','url',NULL),(4,'/index.jsp','url',NULL),(5,'/rs/anonymous/**','resource','匿名rest服务'),(6,'/rs/anonymousUser/**','resource','匿名rest服务'),(15,'cas/**','service','cas client test from android');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `appId` bigint(20) DEFAULT '1',
  `status` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`sex`,`role`,`appId`,`status`,`image`) values (1,'xiaozhujun','e10adc3949ba59abbe56e057f20f883e','男','ROLE_USER;ROLE_ADMIN;ROLE_ANONYMOUS',1,'启用',NULL),(2,'zhangsan','e10adc3949ba59abbe56e057f20f883e','男','ROLE_USER;ROLE_ANONYMOUS',1,'启用',NULL),(3,'sunhui','e68fa2bc61b75b8a06766e25905052c7','男','ROLE_USER',1,'启用',NULL),(4,'liujinxia','c99c1cbefe13019978d90cb442cb8f78','女','ROLE_ADMIN',1,'启用',NULL);

/*Table structure for table `user_authority` */

DROP TABLE IF EXISTS `user_authority`;

CREATE TABLE `user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `authorityId` bigint(20) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `authorityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `user_authority` */

insert  into `user_authority`(`id`,`userId`,`authorityId`,`userName`,`authorityName`) values (1,1,1,'xiaozhujun','ROLE_USER'),(2,1,2,'xiaozhujun','ROLE_ADMIN'),(3,1,3,'xiaozhujun','ROLE_ANONYMOUS'),(4,2,3,'zhangsan','ROLE_ANONYMOUS'),(5,2,1,'zhangsan','ROLE_USER'),(6,3,1,'sunhui','ROLE_USER'),(7,4,2,'liujinxia','ROLE_ADMIN');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
