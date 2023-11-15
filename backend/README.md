# 统一身份验证验证系统-后台

基于 SpringBoot 、MySQL、MybatisPlus 和 openLDAP，使用 Maven 进行项目管理。 

## 1. 环境准备：

1.  jdk 11，新版openLDAP适配的最低jdk版本
2.  maven
3.  MySQL
4. openLDAP

## 2.修改数据库源：

打开 application.yml 文件，修改 spring 配置项中的 mysql 和 ldap 为你主机的配置，也可以不改，直接使用我的数据源即可：

``` yaml
spring:
  application:
    name: UnifiedLoginVerificationSystem
  mvc:
    static-path-pattern: /static/**
  # mysql setting
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://139.9.44.141:3306/db_auth?serverTimezone=UTC
    username: root
    password: 123456
    # 配置Druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
  # ldap setting
  ldap:
    urls: ldap://139.9.44.141:389 
    base: dc=yatnam,dc=com
    username: cn=admin,dc=yatnam,dc=com
    password: 123456
```

## 3. 启动项目：

点击idea的启动即可。