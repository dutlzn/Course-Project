---
typora-root-url: /images
---

# 视频项目

# 背景

企业级在线视频技术

知识付费时代 + 主流技术

SpringCloud + Vue

**注重在线视频系统的技术，视频文件的处理，文件上传，断点续传，文件存储，视频点播，视频加密，极速秒传**

两个使用端，内部人员使用的后台管理，外部学员用的网站，+ 服务端

# 技术



* 微服务框架spring cloud 基于springboot，使用Hoxton.RELEASE ,项目中兼容老版
* 单应用框架 spring boot
* 持久层框架 mybatis + generator 官方代码生成器
* 分页插件pageHelper: mybatis分页有两种实现，一种是只做mybatis generator插件，让其生成的代码含分页插件，另一种方法就是直接使用Mybatis 插件PageHelper 这里采用第二种
*  数据库MySQL
* 前端框架Vue
* 前端响应式框架，Bootstrap,兼容pc pad 移动端
* 模板工具Freemarker，应用非常广泛，可以和Thymeleaf一样用于做前端页面，或用于生成静态页面等，在本项目中用于代码生成器
* 项目构建和管理工具Maven，常见的jar依赖及父子项目的搭建
* 代码管理工具 git
* 开发工具的高效使用idea
* 分布式缓存redis，登录信息的存储，热点数据缓存 秒杀计数器，分布式锁
* 阿里云服务OSS和视频点播，数据库、缓存、服务器、文件存储、视频加密、短信、负载均衡等都可以交给阿里云服务，相当于有一个强大的运维团队
* 流程图编写
* 严谨的代码编写
* 敏捷开发

# 业务

* 登录、 注册

* 单点登录

* 图形验证码验证

* 短信验证码验证

* 权限设计，纯手工打造一个通用的权限管理功能，不依赖任何第三方框架，可以细粒度控制菜单，按钮、接口的权限

* 文件上传，上传图片、视频，并可以实时预览

* 大文件的断点续传，对文件做分片处理再上传，可以实时显示上传速度

* 极速秒传：一个文件传过一次之后，下次再选中同一个文件，会显示极速秒传

* 视频加密

* 加密视频授权播放

* 代码生成器

  

# 开发流程

1. 前后端分离架构搭建
2. 单表功能
3. 通用组件
4. 代码生成器
5. 核心业务功能
6. 文件上传
7. 视频加密
8. 登录与权限
9. 网站开发



# 使用Maven搭建SpringCloud项目

## 使用Eureka搭建注册中心

### SpringCloud Maven

Maven两大核心功能： 依赖管理（jar包管理） 构建项目（项目打包） 

同类型的还有ant 和 grandle  ant 用的不是很多了. 国内用的maven的比较多

SB是但应用开发框架,SC是管理多个SB应用的微服务框架

### 用官网创建sb

https://start.spring.io/

我们搭建一个注册中心服务端,所以只需要选择 Eureka Server

![](/1.png)



打开idea 导入项目 pom形式导入

启动项目 springboot 自带了logback

报错

etflix.discovery.shared.transport.TransportException: Cannot execute request on any known server

项目右键 new module

![](/2.png)

把父模块pom.xml中的

```xml
<dependency>  
    <groupId>org.springframework.cloud</groupId>   
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

放到eureka中 eureka.pom.xml变成这样

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>course</artifactId>
        <groupId>com.lzn</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>eureka</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

    </dependencies>


</project>
```





把原来父模块的启动类,放到eureka中

并改名, EurekaApplication.java

启动这个类,就可以正常启动了



删除夫模块的src



### 搭建业务模块-system

解决注册中心服务启动失败的问题

修改配置 美化启动日志

在eureka中新建application.properties

```
# 应用名字
spring.application.name=eureka
# 启动端口
server.port=8761
# 获取注册中心(因为本身就是注册中心，不需要获取)
eureka.client.fetch-registry=false
# 注册到注册中心(因为本身就是注册中心，不需要注册)
eureka.client.register-with-eureka=false
```

```java
package com.lzn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

}

```

可以正常启动注册中心子模块了

美化日志

修改启动类

```java
package com.lzn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {


	private static final Logger LOG = LoggerFactory.getLogger(EurekaApplication.class);

	public static void main(String[] args) {
//		SpringApplication.run(EurekaApplication.class, args);
		SpringApplication app = new SpringApplication(EurekaApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("Eureka地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}

```

在子模块中的资源文件中,新建logback.xml对日志进行配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 修改一下路径-->
    <property name="PATH" value="D:\code\course-project\eureka"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %blue(%-50logger{50}:%-4line) %msg%n</Pattern>-->
            <Pattern>%d{ss.SSS} %highlight(%-5level) %blue(%-30logger{30}:%-4line) %msg%n</Pattern>
        </encoder>
    </appender>

    <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/trace.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/trace.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
    </appender>

    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/error.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <root level="ERROR">
        <appender-ref ref="ERROR_FILE" />
    </root>

    <root level="TRACE">
        <appender-ref ref="TRACE_FILE" />
    </root>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```



新建maven子项目system

还是和之前一样,根目录右键new module maven system

讲system注册到注册中心

新建的system要作为注册中心的客户端 首先要添加system的相关依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>course</artifactId>
        <groupId>com.lzn</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>system</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

    </dependencies>


</project>
```



system 的属性配置

```
# 应用名字
spring.application.name=system
# 业务端口
server.port=9001
## 获取注册中心(因为本身就是注册中心，不需要获取)
#eureka.client.fetch-registry=false
## 注册到注册中心(因为本身就是注册中心，不需要注册)
#eureka.client.register-with-eureka=false
```



添加system日志

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 修改一下路径-->
    <property name="PATH" value="D:/code/course-project/log/system"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %blue(%-50logger{50}:%-4line) %msg%n</Pattern>-->
            <Pattern>%d{ss.SSS} %highlight(%-5level) %blue(%-30logger{30}:%-4line) %msg%n</Pattern>
        </encoder>
    </appender>

    <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/trace.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/trace.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
    </appender>

    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/error.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <root level="ERROR">
        <appender-ref ref="ERROR_FILE" />
    </root>

    <root level="TRACE">
        <appender-ref ref="TRACE_FILE" />
    </root>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

log配置里面 斜杠方向变一下

修改system 启动类

```java
package com.lzn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SystemApplication {


	private static final Logger LOG = LoggerFactory.getLogger(SystemApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SystemApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("System地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
	}

}

```



新建一个测试类 system

```java
package com.lzn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}

```

将system 注册到注册中心

修改system 配置属性

```
spring.application.name=system
server.servlet.context-path=/system
server.port=9001
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```



修改 system 启动类

```java
package com.lzn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient // 注册到注册中心
public class SystemApplication {


	private static final Logger LOG = LoggerFactory.getLogger(SystemApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SystemApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("System地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
	}

}

```



okkk



### 搭建路由模块Gateway

spring cloud 的网关组件可以用gateway或者zuul,最早使用的是zuul 后面spring自己出了gateway

网关主要功能: 

限流 ( 流量控制)

重试(请求失败时重试,慎用) 

跨域(前后端不在一个域)

路由(转发请求) 

鉴权(登录校验,签名校验)等

新建maven gateway

添加网关依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>course</artifactId>
        <groupId>com.lzn</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>gateway</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>

</project>
```



添加网关启动类

```java
package com.lzn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
//		SpringApplication.run(EurekaApplication.class, args);
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("Gateway: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}

```

属性

```
# 应用名字
spring.application.name=gateway
# 启动端口
server.port=9000
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```



日志

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 修改一下路径-->
    <property name="PATH" value="D:/code/course-project/log/gateway"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %blue(%-50logger{50}:%-4line) %msg%n</Pattern>-->
            <Pattern>%d{ss.SSS} %highlight(%-5level) %blue(%-30logger{30}:%-4line) %msg%n</Pattern>
        </encoder>
    </appender>

    <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/trace.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/trace.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
    </appender>

    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/error.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-8X{UUID}) %msg%n</pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <root level="ERROR">
        <appender-ref ref="ERROR_FILE" />
    </root>

    <root level="TRACE">
        <appender-ref ref="TRACE_FILE" />
    </root>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```



路由转发

将外部请求转发到实际的业务模块进行处理

目的 将9001地址对外隐藏, 访问的是9000/system/** 实际上访问的是9001/sytem/**

启动顺序: 先启动注册中心 -> system -> gateway

# SpringBoot技术整合



## 集成持久层框架MyBatis

负责数据持久化,将数据存储到数据库或硬盘,断电也不会丢失数据

ORM对象关系映射,hibernate 是全自动orm,mybatis是半自动orm ,mybatis可以操作的花样更多,是首选的持久层框架



装mysql5.7

创建数据库

uft8是三个字节,支持的字符有限,mysql在5.5.3之后增加了uft8mb4的编码,支持更多的字符,比如emoji小表情

创建course数据库专用的用户,用户名就叫做courseroot

用navicat 用户功能

密码 

@aA123aJLJL1123

填localhost  表示创建的这个用户,只能本机登录数据库,远程是不能登录的,填%表示允许本地登录和远程登录数据库

用户- 权限 添加权限 选择数据库 全选中操作

上面这一部分 不是很需要 补充即可



引入mysql mybatis包

在根pom.xml文件中引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
    <packaging>pom</packaging>
    <modules>
        <module>eureka</module>
		<module>system</module>
		<module>gateway</module>
	</modules>
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.lzn</groupId>
	<artifactId>course</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>course</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
		<spring-cloud.version>Hoxton.SR8</spring-cloud.version>
	</properties>



	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>


			<!-- 集成mybatis -->
			<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>1.3.2</version>
			</dependency>
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
				<version>5.1.37</version>
			</dependency>



		</dependencies>


	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```



system 下面的pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>course</artifactId>
        <groupId>com.lzn</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>system</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 集成mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>


    </dependencies>


</project>
```



引入的时候注意去掉版本号

system 属性配置

```
spring.datasource.url=jdbc:mysql://192.168.56.101:3306/course?characterEncoding=UTF8&autoReconnect=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```



