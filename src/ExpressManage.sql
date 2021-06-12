-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: ExpressManage
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Courier`
--

DROP TABLE IF EXISTS `Courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Courier` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '快递员 - 编号',
  `cname` varchar(15) DEFAULT NULL COMMENT '快递员姓名',
  `cphone` varchar(11) DEFAULT NULL COMMENT '快递员手机号',
  `idNumber` varchar(18) DEFAULT NULL COMMENT '快递员身份证号码',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `cNumber` int(11) DEFAULT NULL COMMENT '派件数',
  `cintime` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `lastLogin` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `Courier_idNumber_uindex` (`idNumber`),
  UNIQUE KEY `Courier_cphone_uindex` (`cphone`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Courier`
--

LOCK TABLES `Courier` WRITE;
/*!40000 ALTER TABLE `Courier` DISABLE KEYS */;
INSERT INTO `Courier` VALUES (1,'Hoho','17366221438','310910199107162312','c123123',3,'2021-06-05 09:44:18','2021-06-12 04:11:30'),(2,'Anki','18523418891','210814198908191311','c123456',8,'2021-06-05 10:15:02','2021-06-11 08:41:05'),(8,'John','17164539813','314011199312211613','c123123',8,'2021-06-05 13:50:37','2021-06-11 09:10:48'),(10,'Kills','17562649912','413201199812250419','c123123',0,'2021-06-06 11:20:22',NULL),(11,'Sharks','18876459019','510201199409121716','c123123',0,'2021-06-06 11:21:22',NULL),(13,'Arios','18876524939','413201199411260213','c123123',0,'2021-06-07 13:09:52',NULL),(14,'Luckies','15839370413','410121199207131524','c123124',0,'2021-06-11 04:25:07','2021-06-11 16:20:57'),(15,'Adasa','18009118214','413012199109102914','adas321',0,'2021-06-12 03:52:06',NULL);
/*!40000 ALTER TABLE `Courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eAdmin`
--

DROP TABLE IF EXISTS `eAdmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `eAdmin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `loginip` varchar(20) DEFAULT NULL,
  `logintime` date DEFAULT NULL,
  `createtime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eAdmin_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eAdmin`
--

LOCK TABLES `eAdmin` WRITE;
/*!40000 ALTER TABLE `eAdmin` DISABLE KEYS */;
INSERT INTO `eAdmin` VALUES (1,'murphy','123123','0:0:0:0:0:0:0:1','2021-06-12','2021-05-29'),(2,'admin','123456',NULL,NULL,'2021-05-29'),(3,'user1','123456',NULL,NULL,'2021-05-29');
/*!40000 ALTER TABLE `eAdmin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Express`
--

DROP TABLE IF EXISTS `Express`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Express` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(64) DEFAULT NULL COMMENT '快递单号',
  `username` varchar(32) DEFAULT NULL COMMENT '收件人',
  `userphone` varchar(32) DEFAULT NULL COMMENT '收件人手机号',
  `company` varchar(32) DEFAULT NULL COMMENT '快递公司',
  `code` varchar(32) DEFAULT NULL COMMENT '取件码',
  `intime` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `outtime` timestamp NULL DEFAULT NULL COMMENT '出库时间',
  `status` int(11) DEFAULT NULL COMMENT '状态码',
  `sysPhone` varchar(32) DEFAULT NULL COMMENT '录入员手机号',
  `departure` varchar(10) DEFAULT NULL,
  `destination` varchar(10) DEFAULT NULL COMMENT '收货地',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Express_number_uindex` (`number`),
  UNIQUE KEY `Express_code_uindex` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Express`
--

LOCK TABLES `Express` WRITE;
/*!40000 ALTER TABLE `Express` DISABLE KEYS */;
INSERT INTO `Express` VALUES (1,'JD123456','murphy','15805703033','京东快递',NULL,'2021-06-04 03:47:53','2021-06-09 07:36:05',1,'18888888888','长春','北京'),(2,'JD123123','murphy','15805703033','京东快递',NULL,'2021-06-04 03:48:37','2021-06-04 14:24:08',1,'18888888888','长春','北京'),(3,'JD134134','murphy','15805703033','京东快递',NULL,'2021-06-03 03:49:18','2021-06-09 08:10:38',1,'18888888888','长春','北京'),(11,'SF124990','Aida','17269091231','顺丰快递',NULL,'2021-05-04 08:12:30','2021-06-04 08:21:42',1,'18888889999','长春','北京'),(12,'ST524999','Aida','17269091231','申通快递','879876','2021-05-04 08:54:06',NULL,0,'18888889999','长春','北京'),(115,'ZT812710','chris','16696121481','中通快递','444000','2021-06-04 10:29:36',NULL,0,'18877779999','长春','北京'),(116,'ZT812711','chris','16696121481','中通快递','444001','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','北京'),(117,'ZT812712','chris','16696121481','中通快递','444002','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','北京'),(118,'ZT812713','chris','16696121481','中通快递','444003','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','北京'),(119,'ZT812714','chris','16696121481','中通快递','444004','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','北京'),(120,'ZT812715','chris','16696121481','中通快递','444005','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','北京'),(121,'ZT812716','chris','16696121481','中通快递','444006','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','上海'),(122,'ZT812717','chris','16696121481','中通快递','444007','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','上海'),(123,'ZT812718','chris','16696121481','中通快递','444008','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','上海'),(124,'ZT812719','chris','16696121481','中通快递','444009','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','上海'),(125,'ZT8127110','chris','16696121481','中通快递','444010','2021-06-04 10:29:36',NULL,0,'18877779999','沈阳','上海'),(126,'ZT8127111','chris','16696121481','中通快递','444011','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(127,'ZT8127112','chris','16696121481','中通快递','444012','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(128,'ZT8127113','chris','16696121481','中通快递','444013','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(129,'ZT8127114','chris','16696121481','中通快递','444014','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(130,'ZT8127115','chris','16696121481','中通快递','444015','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(131,'ZT8127116','chris','16696121481','中通快递','444016','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(132,'ZT8127117','chris','16696121481','中通快递','444017','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(133,'ZT8127118','chris','16696121481','中通快递','444018','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(134,'ZT8127119','chris','16696121481','中通快递','444019','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(135,'ZT8127120','chris','16696121481','中通快递','444020','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','南京'),(136,'ZT8127121','chris','16696121481','中通快递','444021','2021-06-04 10:29:36',NULL,0,'18877779999','昆明','杭州'),(137,'ZT8127122','chris','16696121481','中通快递','444022','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(138,'ZT8127123','chris','16696121481','中通快递','444023','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(139,'ZT8127124','chris','16696121481','中通快递','444024','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(140,'ZT8127125','chris','16696121481','中通快递','444025','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(141,'ZT8127126','chris','16696121481','中通快递','444026','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(142,'ZT8127127','chris','16696121481','中通快递','444027','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(143,'ZT8127128','chris','16696121481','中通快递','444028','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(144,'ZT8127129','chris','16696121481','中通快递','444029','2021-06-04 10:29:36',NULL,0,'18877779999','广东','杭州'),(145,'ZT8127130','chris','16696121481','中通快递','444030','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','杭州'),(146,'ZT8127131','chris','16696121481','中通快递','444031','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','杭州'),(147,'ZT8127132','chris','16696121481','中通快递','444032','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','杭州'),(148,'ZT8127133','chris','16696121481','中通快递','444033','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','杭州'),(149,'ZT8127134','chris','16696121481','中通快递','444034','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','杭州'),(150,'ZT8127135','chris','16696121481','中通快递','444035','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','西安'),(151,'ZT8127136','chris','16696121481','中通快递','444036','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','西安'),(152,'ZT8127137','chris','16696121481','中通快递','444037','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','西安'),(153,'ZT8127138','chris','16696121481','中通快递','444038','2021-06-04 10:29:36',NULL,0,'18877779999','厦门','西安'),(154,'ZT8127139','chris','16696121481','中通快递','444039','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','西安'),(155,'ZT8127140','chris','16696121481','中通快递','444040','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','西安'),(156,'ZT8127141','chris','16696121481','中通快递','444041','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','西安'),(157,'ZT8127142','chris','16696121481','中通快递','444042','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(158,'ZT8127143','chris','16696121481','中通快递','444043','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(159,'ZT8127144','chris','16696121481','中通快递','444044','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(160,'ZT8127145','chris','16696121481','中通快递','444045','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(161,'ZT8127146','chris','16696121481','中通快递','444046','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(162,'ZT8127147','chris','16696121481','中通快递','444047','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(163,'ZT8127148','chris','16696121481','中通快递','444048','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(164,'ZT8127149','chris','16696121481','中通快递','444049','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(165,'ZT8127150','chris','16696121481','中通快递','444050','2021-06-04 10:29:36',NULL,0,'18877779999','长沙','深圳'),(166,'ZT8127151','chris','16696121481','中通快递','444051','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','深圳'),(167,'ZT8127152','chris','16696121481','中通快递','444052','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','深圳'),(168,'ZT8127153','chris','16696121481','中通快递','444053','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','深圳'),(169,'ZT8127154','chris','16696121481','中通快递','444054','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','深圳'),(170,'ZT8127155','chris','16696121481','中通快递','444055','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(171,'ZT8127156','chris','16696121481','中通快递','444056','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(172,'ZT8127157','chris','16696121481','中通快递','444057','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(173,'ZT8127158','chris','16696121481','中通快递','444058','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(174,'ZT8127159','chris','16696121481','中通快递','444059','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(175,'ZT8127160','chris','16696121481','中通快递','444060','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(176,'ZT8127161','chris','16696121481','中通快递','444061','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(177,'ZT8127162','chris','16696121481','中通快递','444062','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(178,'ZT8127163','chris','16696121481','中通快递','444063','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(179,'ZT8127164','chris','16696121481','中通快递','444064','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(180,'ZT8127165','chris','16696121481','中通快递','444065','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(181,'ZT8127166','chris','16696121481','中通快递','444066','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(182,'ZT8127167','chris','16696121481','中通快递','444067','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(183,'ZT8127168','chris','16696121481','中通快递','444068','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(184,'ZT8127169','chris','16696121481','中通快递','444069','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(185,'ZT8127170','chris','16696121481','中通快递','444070','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(186,'ZT8127171','chris','16696121481','中通快递','444071','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(187,'ZT8127172','chris','16696121481','中通快递','444072','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(188,'ZT8127173','chris','16696121481','中通快递','444073','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(189,'ZT8127174','chris','16696121481','中通快递','444074','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(190,'ZT8127175','chris','16696121481','中通快递','444075','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(191,'ZT8127176','chris','16696121481','中通快递','444076','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(192,'ZT8127177','chris','16696121481','中通快递','444077','2021-06-04 10:29:36',NULL,0,'18877779999','无锡','合肥'),(193,'ZT8127178','chris','16696121481','中通快递','444078','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','合肥'),(194,'ZT8127179','chris','16696121481','中通快递','444079','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','合肥'),(195,'ZT8127180','chris','16696121481','中通快递','444080','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','合肥'),(196,'ZT8127181','chris','16696121481','中通快递','444081','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','合肥'),(197,'ZT8127182','chris','16696121481','中通快递','444082','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','合肥'),(198,'ZT8127183','chris','16696121481','中通快递','444083','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(199,'ZT8127184','chris','16696121481','中通快递','444084','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(200,'ZT8127185','chris','16696121481','中通快递','444085','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(201,'ZT8127186','chris','16696121481','中通快递','444086','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(202,'ZT8127187','chris','16696121481','中通快递','444087','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(203,'ZT8127188','chris','16696121481','中通快递','444088','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(204,'ZT8127189','chris','16696121481','中通快递','444089','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(205,'ZT8127190','chris','16696121481','中通快递','444090','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','天津'),(206,'ZT8127191','chris','16696121481','中通快递','444091','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','哈尔滨'),(207,'ZT8127192','chris','16696121481','中通快递','444092','2021-06-04 10:29:36',NULL,0,'18877779999','苏州','哈尔滨'),(208,'ZT8127193','chris','16696121481','中通快递','444093','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(209,'ZT8127194','chris','16696121481','中通快递','444094','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(210,'ZT8127195','chris','16696121481','中通快递','444095','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(211,'ZT8127196','chris','16696121481','中通快递','444096','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(212,'ZT8127197','chris','16696121481','中通快递','444097','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(213,'ZT8127198','chris','16696121481','中通快递','444098','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(214,'ZT8127199','chris','16696121481','中通快递','444099','2021-06-04 10:29:36',NULL,0,'18877779999','武汉','哈尔滨'),(315,'SF140189','murphy','15805703033','顺丰速运','233867','2021-06-04 11:04:28',NULL,0,'18888888888','武汉','哈尔滨'),(316,'SF106172','murphy','15805703033','顺丰速运','984538','2021-06-04 11:05:21',NULL,0,'18888888888','武汉','哈尔滨'),(321,'SF499130','murphy','15805703033','顺丰速运','866074','2021-06-06 10:04:27',NULL,0,'18888888888','武汉','哈尔滨'),(322,'AN135811','murphy','15805703033','安能快递',NULL,'2021-06-11 04:26:01','2021-06-11 09:22:07',1,'18888888888',NULL,NULL),(327,'SF199137','murphy','15805703033','顺丰速运','639675','2021-06-11 12:08:40',NULL,0,'17366221438',NULL,NULL),(328,'EMS19187','murphy','15805703033','中国邮政',NULL,'2021-06-11 12:21:26','2021-06-12 04:12:10',1,'17366221438',NULL,NULL),(330,'SF107993','murphy','15805703033','顺丰速运',NULL,'2021-06-12 04:03:07','2021-06-12 04:03:31',1,'17366221438',NULL,NULL);
/*!40000 ALTER TABLE `Express` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `User` (
  `uId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `uName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `uPhone` varchar(30) DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(30) DEFAULT NULL COMMENT '用户密码',
  `idNumber` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `uinTime` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `lastLogin` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`uId`),
  UNIQUE KEY `User_uPhone_uindex` (`uPhone`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'murphy','15805703033','murphy321','310801199912311618','2021-06-07 14:00:25','2021-06-12 04:10:27'),(2,'cici','15805703339','qianzai123','611012200107121423','2021-06-07 14:01:01',NULL),(5,'gugu','17167769223','gugu123','410101200211091413','2021-06-08 00:45:08',NULL),(6,'Jinxx','14569791224','jinxx123','210101200112192427','2021-06-08 00:45:54',NULL),(7,'Jax','18712231009','jax12312','110101200003201517','2021-06-08 00:46:35',NULL),(8,'Monterey','17314587347','monterey123','110821200106261419','2021-06-08 01:10:27',NULL),(10,'Catalina','19109807758','catalina123','710101199109291427','2021-06-08 02:01:46',NULL),(11,'chris','16696121481','chris123','612101199808191426','2021-06-08 09:23:17',NULL),(12,'Linda','18090128131','linda123','530121199903171224','2021-06-11 08:41:55','2021-06-11 15:13:13'),(14,'Aida','17269091231','aida123','710211199701291429','2021-06-11 10:44:56','2021-06-11 15:09:58'),(15,'Steves','15670971922','steve123','321021200108171312','2021-06-11 15:40:35','2021-06-11 16:30:58'),(16,'Hive','13087909918','hive123','440921199609181914','2021-06-11 15:43:46','2021-06-11 16:26:59'),(17,'Alin','17378980910','alin123','520121199803212428','2021-06-12 02:15:16','2021-06-12 02:15:16'),(19,'Wang','18199012021','wang123','310911199706301214','2021-06-12 04:05:33','2021-06-12 04:05:33');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-12 12:16:05
