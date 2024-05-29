# 学生管理系统

## 基于JAVA SWING 和 MySQL

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

