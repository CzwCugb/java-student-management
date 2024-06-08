# 学校教务管理系统

> 本学校教务管理系统基于Java Swing和MySQL开发
>
> 注意：如需登录，请修改MySqlConnector中的静态参数（数据库地址、密码等），并创建对应表

## Java架构

### 登录页面与数据库

>LoginIn
>
>​	----LoginIn：登录验证
>
>​	----MySqlConnector：数据库连接

### 学生端

>StudentManager
>
>​	----MessageOerator : 查询/修改个人信息
>
>​	----PickCourseOperator ：学生选课
>
>​	----QueryScoreOperator ：查询成绩

### 教师端

>TeacherManager
>
>​	----MessageOerator : 查询/修改个人信息
>
>​	----QueryTasksOperator ：查询教学计划
>
>​	----SetScoreOperator: 设置分数

### 管理员端

>OfficialManager
>
>​	----MessageOerator : 查询/修改个人信息
>
>​	----SetStudentOperator：学生管理
>
>​	----SetTeacherOperator：教师管理
>
>​	----SetCourseOperator：课程管理

## SQL数据库架构

建表语句：

### Student

```sql
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Teacher

```sql
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Course

```sql
CREATE TABLE `Course` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### SC

```sql
CREATE TABLE `sc` (
  `sNo` varchar(20) NOT NULL,
  `cNo` varchar(20) NOT NULL,
  `scScore` int DEFAULT NULL,
  PRIMARY KEY (`sNo`,`cNo`),
  KEY `fk_SC_cNo` (`cNo`),
  CONSTRAINT `fk_SC_cNo` FOREIGN KEY (`cNo`) REFERENCES `course` (`cNo`),
  CONSTRAINT `fk_SC_sNo` FOREIGN KEY (`sNo`) REFERENCES `student` (`sNo`),
  CONSTRAINT `sc_chk_1` CHECK (((`scScore` >= 0) and (`scScore` <= 100)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Official

```sql
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

