/*
SQLyog Trial v13.1.8 (64 bit)
MySQL - 8.0.36 : Database - school
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`school` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `school`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `cNo` varchar(20) NOT NULL,
  `tNo` varchar(20) NOT NULL,
  `cName` varchar(10) NOT NULL,
  `cYear` int DEFAULT NULL,
  `cTerm` varchar(2) DEFAULT NULL,
  `cCredit` int NOT NULL DEFAULT '1',
  `cAmount` int unsigned DEFAULT NULL,
  `cCurrentSum` int DEFAULT NULL,
  `cCollege` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`cNo`),
  KEY `tNo` (`tNo`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`tNo`) REFERENCES `teacher` (`tNo`),
  CONSTRAINT `course_chk_1` CHECK (((`cTerm` = _utf8mb3'春') or (`cTerm` = _utf8mb3'夏') or (`cTerm` = _utf8mb3'秋') or (`cTerm` = _utf8mb3'冬'))),
  CONSTRAINT `course_chk_2` CHECK (((`cYear` >= 2000) and (`cYear` <= 2024))),
  CONSTRAINT `course_chk_3` CHECK (((`cCredit` >= 1) and (`cCredit` <= 12))),
  CONSTRAINT `course_chk_4` CHECK (((`cCurrentSum` >= 0) and (`cCurrentSum` <= `cAmount`)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `course` */

insert  into `course`(`cNo`,`tNo`,`cName`,`cYear`,`cTerm`,`cCredit`,`cAmount`,`cCurrentSum`,`cCollege`) values 
('C20231005','T20020001','程序设计基础',2023,'夏',3,100,83,'信息工程学院'),
('C20231006','T20130011','大学物理',2023,'秋',3,80,73,'数理学院'),
('C20231007','T19990012','政治经济学',2024,'秋',3,100,72,'经济管理学院'),
('C20231008','T20020009','计算机网络',2023,'夏',3,105,90,'信息工程学院'),
('C20231009','T20130011','量子物理',2023,'秋',3,85,78,'数理学院'),
('C20231010','T19990004','宏观经济学',2024,'秋',3,105,75,'经济管理学院'),
('C20241001','T20120006','离散数学',2024,'秋',4,110,1,'信息工程学院'),
('C20241002','T20130003','概率论',2024,'秋',3,110,0,'数理学院'),
('C20241003','T20120002','高等数学',2024,'夏',6,120,120,'信息工程学院'),
('C20241004','T20020001','数据结构',2024,'秋',4,120,53,'信息工程学院'),
('C20241005','T20130007','线性代数',2024,'秋',3,110,1,'数理学院'),
('C20241006','T20120006','微积分',2024,'夏',6,130,129,'信息工程学院');

/*Table structure for table `official` */

DROP TABLE IF EXISTS `official`;

CREATE TABLE `official` (
  `oNo` varchar(20) NOT NULL,
  `oName` varchar(10) NOT NULL,
  `oSex` varchar(2) DEFAULT NULL,
  `oAge` int unsigned DEFAULT NULL,
  `oTelephone` varchar(20) DEFAULT NULL,
  `oEmail` varchar(30) DEFAULT NULL,
  `oPassword` varchar(20) DEFAULT '88888888',
  PRIMARY KEY (`oNo`),
  CONSTRAINT `official_chk_1` CHECK (((`oSex` = _utf8mb3'男') or (`oSex` = _utf8mb3'女'))),
  CONSTRAINT `official_chk_2` CHECK (((`oAge` >= 1) and (`oAge` <= 150))),
  CONSTRAINT `official_chk_3` CHECK ((regexp_like(`oTelephone`,_utf8mb3'^[0-9]+$') and (length(`oTelephone`) >= 10) and (length(`oTelephone`) <= 20))),
  CONSTRAINT `official_chk_4` CHECK ((locate(_utf8mb3'@',`oEmail`) > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `official` */

insert  into `official`(`oNo`,`oName`,`oSex`,`oAge`,`oTelephone`,`oEmail`,`oPassword`) values 
('O19990003','王胜利','男',33,'13900139006','wangshengli@123.com','88888888'),
('O20020001','李花贤','男',35,'13900139002','lihuaxian@123.com','88888888'),
('O20100004','徐一峰','男',36,'13900139007','xuyifeng@123.com','88888888'),
('O20130002','赵柳','女',38,'13900139003','zhaoliu@123.com','88888888');

/*Table structure for table `sc` */

DROP TABLE IF EXISTS `sc`;

CREATE TABLE `sc` (
  `sNo` varchar(20) NOT NULL,
  `cNo` varchar(20) NOT NULL,
  `scScore` int DEFAULT NULL,
  PRIMARY KEY (`sNo`,`cNo`),
  KEY `fk_SC_cNo` (`cNo`),
  CONSTRAINT `fk_SC_cNo` FOREIGN KEY (`cNo`) REFERENCES `course` (`cNo`),
  CONSTRAINT `fk_SC_sNo` FOREIGN KEY (`sNo`) REFERENCES `student` (`sNo`),
  CONSTRAINT `sc_chk_1` CHECK (((`scScore` >= 0) and (`scScore` <= 100)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sc` */

insert  into `sc`(`sNo`,`cNo`,`scScore`) values 
('S20230016','C20241004',NULL),
('S20240001','C20231005',NULL),
('S20240001','C20231006',88),
('S20240001','C20241001',86),
('S20240001','C20241003',87),
('S20240001','C20241005',NULL),
('S20240017','C20231005',NULL),
('S20240017','C20241004',NULL),
('S20240018','C20231005',NULL),
('S20240018','C20241004',NULL),
('S20240019','C20231005',NULL),
('S20240019','C20241004',NULL),
('S20240020','C20231005',NULL),
('S20240020','C20241004',NULL),
('S20240021','C20231005',NULL),
('S20240021','C20241004',NULL),
('S20240022','C20231005',NULL),
('S20240022','C20241004',NULL),
('S20240023','C20231005',NULL),
('S20240023','C20241004',NULL),
('S20240024','C20231005',NULL),
('S20240024','C20241004',NULL),
('S20240025','C20231005',NULL),
('S20240025','C20241004',NULL),
('S20240026','C20231005',NULL),
('S20240026','C20241004',NULL),
('S20240027','C20231005',NULL),
('S20240027','C20241004',NULL),
('S20240028','C20231005',NULL),
('S20240028','C20241004',NULL),
('S20240029','C20231005',NULL),
('S20240029','C20241004',NULL),
('S20240030','C20231005',NULL),
('S20240030','C20241004',NULL),
('S20240031','C20231005',NULL),
('S20240031','C20241004',NULL),
('S20240032','C20231005',NULL),
('S20240032','C20241004',NULL),
('S20240033','C20231005',NULL),
('S20240033','C20241004',NULL),
('S20240034','C20231005',NULL),
('S20240034','C20241004',NULL),
('S20240035','C20231005',NULL),
('S20240035','C20241004',NULL),
('S20240036','C20231005',NULL),
('S20240036','C20241004',NULL),
('S20240037','C20231005',NULL),
('S20240037','C20241004',NULL),
('S20240038','C20231005',NULL),
('S20240038','C20241004',NULL),
('S20240039','C20231005',NULL),
('S20240039','C20241004',NULL),
('S20240040','C20231005',NULL),
('S20240040','C20241004',NULL),
('S20240041','C20231005',NULL),
('S20240041','C20241004',NULL),
('S20240042','C20231005',NULL),
('S20240042','C20241004',NULL),
('S20240043','C20231005',NULL),
('S20240043','C20241004',NULL),
('S20240044','C20231005',NULL),
('S20240044','C20241004',NULL),
('S20240045','C20231005',NULL),
('S20240045','C20241004',NULL),
('S20240046','C20231005',NULL),
('S20240046','C20241004',NULL),
('S20240047','C20231005',NULL),
('S20240047','C20241004',NULL),
('S20240048','C20231005',NULL),
('S20240048','C20241004',NULL),
('S20240049','C20231005',NULL),
('S20240049','C20241004',NULL),
('S20240050','C20231005',NULL),
('S20240050','C20241004',NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `sNo` varchar(20) NOT NULL COMMENT '学生编号',
  `sName` varchar(10) NOT NULL COMMENT '学生名字',
  `sSex` varchar(2) DEFAULT NULL COMMENT '学生性别',
  `sAge` int DEFAULT NULL COMMENT '学生年龄',
  `sTelephone` varchar(20) DEFAULT NULL COMMENT '学生电话',
  `sEmail` varchar(30) DEFAULT NULL COMMENT '学生邮箱',
  `sYear` date DEFAULT NULL COMMENT '入学时间',
  `sMajor` varchar(10) DEFAULT NULL COMMENT '学生专业',
  `sCollege` varchar(10) DEFAULT NULL COMMENT '学生所在学院',
  `sPassword` varchar(20) DEFAULT '88888888',
  PRIMARY KEY (`sNo`),
  CONSTRAINT `student_chk_1` CHECK (((`sSex` = _utf8mb3'男') or (`sSex` = _utf8mb3'女'))),
  CONSTRAINT `student_chk_2` CHECK (((`sAge` >= 1) and (`sAge` <= 150))),
  CONSTRAINT `student_chk_3` CHECK ((regexp_like(`sTelephone`,_utf8mb3'^[0-9]+$') and (length(`sTelephone`) >= 10) and (length(`sTelephone`) <= 20))),
  CONSTRAINT `student_chk_4` CHECK ((locate(_utf8mb3'@',`sEmail`) > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `student` */

insert  into `student`(`sNo`,`sName`,`sSex`,`sAge`,`sTelephone`,`sEmail`,`sYear`,`sMajor`,`sCollege`,`sPassword`) values 
('S20220008','王晓琳','女',19,'17703458002','wangxiaoling@stu.com','2022-09-01','工商管理','经济管理学院','88888888'),
('S20220010','张雨田','女',20,'17705458002','zhangyutian@stu.com','2022-09-01','会计学','经济管理学院','88888888'),
('S20230009','刘硕','男',20,'13803458002','liuxuhua@stu.com','2023-09-01','会计学','经济管理学院','88888888'),
('S20230014','刘思思','女',19,'15600138040','liusisi@stu.com','2023-09-03','数学与应用数学','数理学院','88888888'),
('S20230015','张一凡','男',19,'17802138201','zhangyifang@stu.com','2023-09-01','物理学','数理学院','88888888'),
('S20230016','王迪','男',20,'15800138062','wangdi@stu.com','2023-09-02','化学','数理学院','88888888'),
('S20240001','张三','男',17,'13800138111','zhangsan@stu.com','2024-09-03','计算机科学与技术','信息工程学院','88888888'),
('S20240002','李小明','女',19,'13800138001','lixiaoming@stu.com','2024-09-01','软件工程','信息工程学院','88888888'),
('S20240003','王晓迪','男',20,'13800138022','wangxiaodi@stu.com','2024-09-02','人工智能','信息工程学院','88888888'),
('S20240004','赵六一','女',18,'15200138043','zhaoliuyi@stu.com','2024-09-01','大数据科学与技术','信息工程学院','88888888'),
('S20240005','刘一山','男',18,'18903438000','zhaoyishan@stu.com','2024-09-03','计算机科学与技术','信息工程学院','88888888'),
('S20240006','王柳','女',19,'16300135001','wangliu@stu.com','2024-09-02','软件工程','信息工程学院','88888888'),
('S20240007','刘旭华','男',20,'13803458002','liuxuhua@stu.com','2024-09-01','人工智能','信息工程学院','88888888'),
('S20240011','刘宇飞','男',20,'16603458002','liuyufei@stu.com','2024-09-01','法学','经济管理学院','88888888'),
('S20240012','陈冠宇','男',20,'13803458002','chenguanyu@stu.com','2024-09-01','信息管理与信息系统','经济管理学院','88888888'),
('S20240013','赵王泽','女',18,'19800143023','zhaowangze@stu.com','2024-09-01','大数据科学与技术','信息工程学院','88888888'),
('S20240017','李华夏','男',19,'13800138017','lihua@stu.com','2024-09-01','计算机科学与技术','信息工程学院','88888888'),
('S20240018','王芳','女',20,'13800138018','wangfang@stu.com','2024-09-02','软件工程','信息工程学院','88888888'),
('S20240019','张飞翔','男',18,'13800138019','zhangwei@stu.com','2024-09-03','人工智能','信息工程学院','88888888'),
('S20240020','刘洋','女',19,'13800138020','liuyang@stu.com','2024-09-01','大数据科学与技术','信息工程学院','88888888'),
('S20240021','赵敏','男',20,'13800138021','zhaomin@stu.com','2024-09-02','计算机科学与技术','信息工程学院','88888888'),
('S20240022','李静静','女',18,'13800138022','lijing@stu.com','2024-09-03','软件工程','信息工程学院','88888888'),
('S20240023','王磊','男',19,'13800138023','wanglei@stu.com','2024-09-01','人工智能','信息工程学院','88888888'),
('S20240024','刘丽阳','女',20,'13800138024','liuli@stu.com','2024-09-02','大数据科学与技术','信息工程学院','88888888'),
('S20240025','张洋洋','男',18,'13800138025','zhangyang@stu.com','2024-09-03','计算机科学与技术','信息工程学院','88888888'),
('S20240026','李天一','男',19,'13800138026','litianyi@stu.com','2024-09-01','计算机科学与技术','信息工程学院','88888888'),
('S20240027','王小明','女',20,'13800138027','wangxiaoming@stu.com','2024-09-02','软件工程','信息工程学院','88888888'),
('S20240028','张晓华','男',18,'13800138028','zhangxiaohua@stu.com','2024-09-03','人工智能','信息工程学院','88888888'),
('S20240029','刘一飞','女',19,'13800138029','liuyifei@stu.com','2024-09-01','大数据科学与技术','信息工程学院','88888888'),
('S20240030','赵天宇','男',20,'13800138030','zhaotianyu@stu.com','2024-09-02','计算机科学与技术','信息工程学院','88888888'),
('S20240031','李小花','女',18,'13800138031','lixiaohua@stu.com','2024-09-03','软件工程','信息工程学院','88888888'),
('S20240032','王一飞','男',19,'13800138032','wangyifei@stu.com','2024-09-01','人工智能','信息工程学院','88888888'),
('S20240033','张天宇','女',20,'13800138033','zhangtianyu@stu.com','2024-09-02','大数据科学与技术','信息工程学院','88888888'),
('S20240034','刘小明','男',18,'13800138034','liuxiaoming@stu.com','2024-09-03','计算机科学与技术','信息工程学院','88888888'),
('S20240035','赵一飞','女',19,'13800138035','zhaoyifei@stu.com','2024-09-01','软件工程','信息工程学院','88888888'),
('S20240036','李晓华','男',20,'13800138036','lixiaohua@stu.com','2024-09-02','人工智能','信息工程学院','88888888'),
('S20240037','王天宇','女',18,'13800138037','wangtianyu@stu.com','2024-09-03','大数据科学与技术','信息工程学院','88888888'),
('S20240038','张一飞','男',20,'13800138038','zhangyifei@stu.com','2024-09-01','计算机科学与技术','信息工程学院','88888888'),
('S20240039','刘小花','女',20,'13800138039','liuxiaohua@stu.com','2024-09-02','软件工程','信息工程学院','88888888'),
('S20240040','赵晓华','男',18,'13800138040','zhaoxiaohua@stu.com','2024-09-03','人工智能','信息工程学院','88888888'),
('S20240041','李一飞','女',19,'13800138041','liuyifei@stu.com','2024-09-01','大数据科学与技术','信息工程学院','88888888'),
('S20240042','王小明','男',20,'13800138042','wangxiaoming@stu.com','2024-09-02','计算机科学与技术','信息工程学院','88888888'),
('S20240043','张天宇','女',18,'13800138043','zhangtianyu@stu.com','2024-09-03','软件工程','信息工程学院','88888888'),
('S20240044','刘晓华','男',19,'13800138044','liuxiaohua@stu.com','2024-09-01','人工智能','信息工程学院','88888888'),
('S20240045','赵一飞','女',20,'13800138045','zhaoyifei@stu.com','2024-09-02','大数据科学与技术','信息工程学院','88888888'),
('S20240046','李小明','男',18,'13800138046','lixiaoming@stu.com','2024-09-03','计算机科学与技术','信息工程学院','88888888'),
('S20240047','王天宇','女',19,'13800138047','wangtianyu@stu.com','2024-09-01','软件工程','信息工程学院','88888888'),
('S20240048','张一飞','男',20,'13800138048','zhangyifei@stu.com','2024-09-02','人工智能','信息工程学院','88888888'),
('S20240049','刘小花','女',18,'13800138049','liuxiaohua@stu.com','2024-09-03','大数据科学与技术','信息工程学院','88888888'),
('S20240050','赵晓华','男',19,'13800138050','zhaoxiaohua@stu.com','2024-09-01','计算机科学与技术','信息工程学院','88888888');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `tNo` varchar(20) NOT NULL COMMENT '教师编号',
  `tName` varchar(10) NOT NULL COMMENT '教师名字',
  `tSex` varchar(2) DEFAULT NULL COMMENT '教师性别',
  `tAge` int DEFAULT NULL COMMENT '教师年龄',
  `tTelephone` varchar(20) DEFAULT NULL COMMENT '教师电话',
  `tEmail` varchar(30) DEFAULT NULL COMMENT '教师邮箱',
  `tCollege` varchar(10) DEFAULT NULL COMMENT '所在学院',
  `tPassword` varchar(20) DEFAULT '88888888',
  PRIMARY KEY (`tNo`),
  CONSTRAINT `teacher_chk_1` CHECK (((`tSex` = _utf8mb3'男') or (`tSex` = _utf8mb3'女'))),
  CONSTRAINT `teacher_chk_2` CHECK (((`tAge` >= 1) and (`tAge` <= 150))),
  CONSTRAINT `teacher_chk_3` CHECK ((regexp_like(`tTelephone`,_utf8mb3'^[0-9]+$') and (length(`tTelephone`) >= 10) and (length(`tTelephone`) <= 20))),
  CONSTRAINT `teacher_chk_4` CHECK ((locate(_utf8mb3'@',`tEmail`) > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `teacher` */

insert  into `teacher`(`tNo`,`tName`,`tSex`,`tAge`,`tTelephone`,`tEmail`,`tCollege`,`tPassword`) values 
('T19990004','赵柳','女',38,'16600167005','zhaoliu@tch.com','经济管理学院','88888888'),
('T19990008','王莉莉','女',39,'16600167009','wangfang@tch.com','经济管理学院','88888888'),
('T19990012','张芳','女',40,'16600167013','zhangfang@tch.com','经济管理学院','88888888'),
('T20020001','李胜利','男',42,'13902739000','lishengli@tch.com','信息工程学院','88888888'),
('T20020005','刘平发','男',46,'13902739006','liuqiang@tch.com','信息工程学院','88888888'),
('T20020009','孙涛','男',47,'13902739010','suntao@tch.com','信息工程学院','88888888'),
('T20120002','张佳怡','女',42,'17703839201','zhangjiayi@tch.com','信息工程学院','88888888'),
('T20120006','李楠慧','女',43,'17703839207','lina@tch.com','信息工程学院','88888888'),
('T20120010','赵丽','女',44,'17703839211','zhaoli@tch.com','信息工程学院','88888888'),
('T20130003','王旭华','男',40,'18902139304','wanshi@tch.com','数理学院','88888888'),
('T20130007','张伟达','男',41,'18902139308','zhangwei@tch.com','数理学院','88888888'),
('T20130011','李伟泽','男',42,'18902139312','liwei@tch.com','数理学院','88888888'),
('T20240013','刘华强','男',45,'13902739014','liuhuaqiang@tch.com','信息工程学院','88888888'),
('T20240014','张美丽','女',42,'17703839215','zhangmeili@tch.com','信息工程学院','88888888'),
('T20240015','王天明','男',40,'18902139316','wangtianming@tch.com','数理学院','88888888'),
('T20240016','赵晓红','女',38,'16600167017','zhaoxiaohong@tch.com','经济管理学院','88888888'),
('T20240017','孙大山','男',46,'13902739018','sundashan@tch.com','信息工程学院','88888888'),
('T20240018','李青云','女',43,'17703839219','liqingyun@tch.com','信息工程学院','88888888'),
('T20240019','张海洋','男',41,'18902139320','zhanghaiyang@tch.com','数理学院','88888888'),
('T20240020','王晓芳','女',39,'16600167021','wangxiaofang@tch.com','经济管理学院','88888888'),
('T20240021','孙小明','男',47,'13902739022','sunxiaoming@tch.com','信息工程学院','88888888'),
('T20240022','赵丽丽','女',44,'17703839223','zhaolili@tch.com','信息工程学院','88888888');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
