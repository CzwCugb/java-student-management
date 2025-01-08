# 学校教务管理系统

本学校教务管理系统基于Java Swing和MySQL开发

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

## 注意

- 导入data中的sql语句进行建表
- 修改MySqlConnector中的数据库名称、账户密码
- 演示学生账户：账号-S20240001；密码-88888888
- 演示教师账户：账号-T20020001；密码-88888888
- 演示管理员账户：账户-O20020001；密码-88888888
