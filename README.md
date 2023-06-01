# AndroidScoreDemo
选课系统第一次修改，数据库前
# 简单学生选课系统
  >  使用网络编程，需要自己搭建后端服务器和接口
  > 很简易，又很屎，版本有点乱，不过能用。
  
# 一、前言
   本应用为简易学生选课系统，能满足基本的选课系统功能。本应用基于Android 9.0（Pie） 开发，建议使用此版本及其以上设备。Build tools 使用30版本，Gradle插件版本为7.1.2，Gradle版本为7.5。其中数据库搭建在后台服务端中，服务端使用springboot+springmvc+mybatis-plus框架简单搭建。能够响应前端此app的一些资源请求，能够动态更新UI界面，达到实现功能的目的。
# 二、应用程序概述
1、 应用程序名称：学生选课系统

2、 应用程序类型：教育类工具应用

3、 应用程序版本：0.0.3

4、 应用程序功能：用户登录，用户信息修改，学生选课/退课，管理员添加课程，管理员修改选课成绩，管理员添加学生信息等功能;

5、 应用程序界面：底部导航栏包含三个选项，根据用户不同所指向的fragment不同，所展示的内容不同。1界面常用于展示所有课程列表（管理员视角）或者已经选课课程列表（学生视角），界面2展示选课信息修改添加（管理员）或者未选课的课程列表（学生），界面3展示学生信息添加和修改（管理员）或者个人信息展示和修改（学生）。
# 三、应用程序使用方法
1、开启mysql服务（或者其他数据库服务，需要另外在服务器端配置，本应用使用mysql数据库服务，服务器端也只配置了mysql的连接和使用），使用数据库工具运行sql文件studentsystem.sql创建数据库和基本初始数据。

2、Tomcat运行war包文件ScoreService-0.0.1-SNAPSHOT.war，保证后端服务器挂起。后端服务器项目文件在压缩包ScoreService.rar中，可使用java编译器打开并进行再开发。（注意要先创建对应的mysql数据库，否则需要打开项目文件自行更改配置）

3、使用编译器打开android项目文件中压缩包StudentGradeMangeSystem.zip中的Android项目文件，修改app/src/main/java/com/xcu109/student/util/HttpUtil.java中的静态变量url地址为本机ip地址，端口号后端文件中默认设置8888，如有冲突，需要在后端文件中更改。

4、在能够正常运行的情况下，可进行app应用程序的打包，打包成APK文件的release版本方便他人预览使用。（注意别人使用时仍需要访问本地服务器，确保他人设备能够ping得通本机ip，如在同一个校园网下。本地服务器不可关闭，ip地址改变，就需要改变app应用代码中的请求调用地址url。若服务器端配置在云端，则需要改为云端服务器ip地址）

5、程序正常启动后，数据库初始数据管理员用户账号为0，密码000000，学生账户密码默认为123456。界面效果如下：

登录界面

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/a67e2851-3d37-4360-b39e-1590054cea21)

管理员界面

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/92d747e2-5a88-42bf-a4ef-4f1c9bc60e9e)

管理员界面2

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/a7faf2cf-9aaa-4c33-8e2e-21914bce38ed)	

管理员界面3

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/6db33aa3-efa6-4f12-8d12-c07675ddbc70)

学生界面1	

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/3ce1efb2-a452-4c4c-8b59-9fccbe9e351a)

学生界面2

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/7e5cb74b-bf0b-4858-8253-74c759e8e9d1)

学生界面3

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/3768e53d-b288-46d0-b269-9e009690658c)	

修改密码界面

![image](https://github.com/Qnh233/AndroidScoreDemo/assets/83863342/db638ef9-a1d4-48cc-9915-1197bd57b161)

# 四、结束语
以上就是Androidapp使用说明文档的简要介绍，本应用仍处于很初级的状态，其中一些安全性的校验以及异常的捕获都没有进行严格的处理。尤其在Android P版本以上后，主线程中不再允许访问网络，开发过程中会因此出现偶发报错现象。开发中选择使CallbackFuture去获取response来更便利地进行数据处理，这添加了api的特别注解@RequiresApi来让编译进行下去。过低版本的api可能无法运行。

