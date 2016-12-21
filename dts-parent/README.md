##1.工程说明
本工程提供三个war包如下：
dts-server-web.war：dts-server端部署war
dts-local-example.war：local模式测试war
dts-remote-example.war：remote模式测试war
本工程提供三个jar包如下：
dts-core.jar：提供核心的dts功能，嵌入业务系统使用
dts-schedule.jar：提供核心的事务恢复功能，嵌入业务系统使用
dts-server-share.jar：内部使用jar，业务系统可以不关注
##2.系统搭建
1)数据库初始化，执行init.sql文件创建相应的数据库
2)数据初始化，执行init.data文件初始化相应表数据
##3.创建测试日志目录
mkdir /alidata1/admin/dtslog
##4.测试连接：
http://ip:port/product/purchase?productName=爆炒牛肚&orderAmount=23&currentAmount=50

###5.开发计划
1.新增dts-admin目录，采用spring-boot完成