-- MySQL dump 10.11
--
-- Host: localhost    Database: webshop
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `member_id` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `member_level` tinyint(4) NOT NULL,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `isblocked` bit(1) NOT NULL,
  PRIMARY KEY  (`member_id`),
  UNIQUE KEY `unique name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('63c3fc67-4b65-4a99-aaea-d5211239502c','admin','Ð3â*ãH®µfÂ\nì5…M©—',2,'Administrator','Admin','admin@gnomeshop.com','32632543','\0'),('700d8ccf-bb13-4310-a820-17fe639b949b','kop','ÔKb€P¾âj¬˜ÆYÕÉïM˜k',2,'kop','kop','kop','kop','\0'),('781c090f-2b7b-4ecb-9090-113e46c8cfa4','guest','5g^hôµ¯{™]’­Ä8BñdP',1,'guest','guest','guest@guest.com','21326353','\0'),('8c8b7930-6ba6-454d-bde6-2edf7488e747','wei','¨ƒŠ¸3‡®†xEõB¹þœ Hsð',1,'zhenfang','wei','wei@kop.com','43628394','\0'),('a039daf3-ef91-4119-9ed3-dbe7a4cae64d','jjyy','|\"/?}?Y!4?$|\r',2,'jj','yy','jj@yy.com','12345678','\0'),('ca591d25-d065-4daa-85cf-65820043d80b','yy','²¨ülÝÛ]ùIÅhË\\…bÎ',1,'yi','yan','yan@yan.com','1323232','\0'),('d62699c3-b1fd-4d04-8f44-07832e8fe02b','scott','@½\0c_ÃQe2ž¡ÿ\\^ËÛ¾ï',1,'Scott','Tiger','scott@tiger.com','134348475','\0');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE `orderdetails` (
  `Id` varchar(64) NOT NULL,
  `OrderId` varchar(64) NOT NULL,
  `ProductId` varchar(64) NOT NULL,
  `ProductName` varchar(64) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` float NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `ProductId` (`ProductId`),
  KEY `OrderId` (`OrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` VALUES ('316116bb-21f3-4562-9ac1-32265307252e','a86d730a-4f39-4b46-be07-24bd94723b5d',' 2ce31b47-2a90-45c7-8b5a-7a3aa27736d3',' Yello Gnome',10,70),('6dcc72fb-66ac-4730-a23d-ac97188178b9','cf2c9fda-9439-4b41-ac15-9556897c2d19',' c4683e29-a7e9-438a-90ef-f0af5a78a269',' Yellow Gnome',3,34),('714118f9-063c-4837-8fcb-11e07284e8e2','cf2c9fda-9439-4b41-ac15-9556897c2d19',' c3a8c9ae-3349-4531-b24e-d11d1207b6b7',' Blue Gnome',5,50),('719a11ac-c9be-4e9a-a37b-d064e7ccdbcf','214c0f47-a13a-43c6-bc10-7f380e5715b3',' 2ce31b47-2a90-45c7-8b5a-7a3aa27736d3',' Yello Gnome',30,70),('81ec4680-fdbc-438e-8a47-ec9eca7be6ff','1c738fb6-d841-4686-8e49-5217375903bd',' 057fa27a-3969-4690-a8eb-47d73ce89f21',' Green Gnome',30,56),('8949cbd9-f13d-4cd7-80a0-8cf624473075','214c0f47-a13a-43c6-bc10-7f380e5715b3',' 072361a2-96e7-47e1-8937-739e6208037f',' Pink Gnome',15,40),('b29a18b0-91bb-4eb9-bd34-084a6b2bb165','62e56e17-6229-44c7-9155-fdb9f4e76d81',' 072361a2-96e7-47e1-8937-739e6208037f',' Pink Gnome',30,40);
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `OrderId` varchar(64) NOT NULL,
  `member_id` varchar(64) NOT NULL,
  `ContactName` varchar(64) NOT NULL,
  `DeliveryAddress` varchar(1024) NOT NULL,
  `CCName` varchar(64) NOT NULL,
  `CCNumber` varchar(64) NOT NULL,
  `CCExpiryDate` varchar(64) NOT NULL,
  PRIMARY KEY  (`OrderId`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('1c738fb6-d841-4686-8e49-5217375903bd','700d8ccf-bb13-4310-a820-17fe639b949b','Wei','Kista Allevaeg','visa','4739847364253','08-30-09'),('214c0f47-a13a-43c6-bc10-7f380e5715b3','8c8b7930-6ba6-454d-bde6-2edf7488e747','Wei Zhenfang','Kista Alevaeg','visa','64387740947465','07-30-10'),('62e56e17-6229-44c7-9155-fdb9f4e76d81','8c8b7930-6ba6-454d-bde6-2edf7488e747','Wei ','Stockholm','visa','7438238463721920','08-10-11'),('cf2c9fda-9439-4b41-ac15-9556897c2d19','781c090f-2b7b-4ecb-9090-113e46c8cfa4','guest','Kista forum','master','43445564534343','05-04-10');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `ProductId` varchar(64) NOT NULL,
  `CategoryId` varchar(64) default NULL,
  `name` varchar(64) NOT NULL,
  `Description` varchar(4096) default NULL,
  `Price` float NOT NULL,
  `Quantity` int(11) NOT NULL,
  PRIMARY KEY  (`ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('057fa27a-3969-4690-a8eb-47d73ce89f21',NULL,'Green Gnome','Green',56,100),('655e58a2-ae88-4dce-b01d-046630a34aa0',NULL,'Pink Gnome','Pink Gnome',34,45),('ad589d9d-6283-4d01-b4a9-8a9a9ecae568',NULL,'Grey Gnome','Bad',30,50),('B0F58565-4B6F-40b9-B64B-D376DBCA0DA9',NULL,'Red Gnome','Red Gnome',40,97),('c3a8c9ae-3349-4531-b24e-d11d1207b6b7',NULL,'Blue Gnome','Blue gnome',50,37),('c4683e29-a7e9-438a-90ef-f0af5a78a269',NULL,'Yellow Gnome','Yellow Gnome',34,42),('e75ff40d-4035-42bb-9bff-069babe64fee',NULL,'White Gnome','Special Edition',34,5);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2007-12-10 23:28:34
