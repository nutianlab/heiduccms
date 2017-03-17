# Heiduc CMS 是一个CMS；

# Heiduc CMS 是一个用java编写的CMS；

# Heiduc CMS 是一个使用MongoDB的CMS；

# Heiduc CMS 是一个基于VosaoCMS的CMS；




###关于Vosaocms

>简单来说vosaocms是面向GAE（Google App Engine）的java开源cms
>
>详细信息请查看<a href="https://github.com/vosaocms/vosao">VosaoCMS</a>



### 与VosaoCMS的区别
>HeiducCMS数据库从BigTable迁移到MongoDB
>
>HeiducCMS移除了所有GAE依赖，可运行在Jetty/Tomcat/Jboss等容器中
>
>集成了resteasy框架
>
>后台管理界面用bootstrap重新实现
>
>包结构改变
>

###编译运行
>1、数据库mongodb安装（略）
>
>2、jdk版本>=1.7(1.7以上才支持jsr107）,maven 安装(略)
>
>3、修改kernel/kernel/src/main/resources/config.properties配置文件
>

>4、运行项目根目录make.bat或make.sh
>
>5、运行web目录run.bat或run.sh
>
>6、打开浏览器访问，例如<a>http://localhost:8080/</a>,默认用户dev@heiduc.com/admin
>
>7、进入后台界面》配置》还原网站默认值
>
>8、收工





