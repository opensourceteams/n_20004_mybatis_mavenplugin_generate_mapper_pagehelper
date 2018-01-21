# mybatis 通用mapper、自动生成
## 功能
- mybatis 自动生成代码(maven插件自动生成)
- 通用mapper
- git 地址 :  https://github.com/opensourceteams/n_20004_mybatis_mavenplugin_generate_mapper_pagehelper

## 自动生成
### 自动生成表配置文件 generatorConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <properties resource="config.properties"></properties>
    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">

        <plugin type="${mapper.plugin}">
            <property name="mappers" value="${mapper.Mapper}"/>
            <!-- caseSensitive默认false，当数据库表名区分大小写时，可以将该属性设置为true -->
            <property name="caseSensitive" value="true"/>
        </plugin>
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />

        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.user}"
                        password="${jdbc.password}">
        </jdbcConnection>
        <!--生成 po -->
        <javaModelGenerator targetPackage="com.opensourceteam.modules.business.test.po" targetProject="src\main\java"/>
        <!--生成mapper.xml文件 -->
        <sqlMapGenerator targetPackage="com.opensourceteam.modules.business.test.mapper"  targetProject="src\main\resources"/>
        <!--生成dao -->
        <javaClientGenerator targetPackage="com.opensourceteam.modules.business.test.dao" targetProject="src\main\java" type="XMLMAPPER" />

        <table tableName="t_test" >
            <!-- mysql 主键自增长-->
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>

        <table tableName="t_test_uuid" >
            <!-- uuid-->
            <generatedKey column="id" sqlStatement="select replace(uuid(), ''-'', '''')" identity="false" type="pre"/>
        </table>

    </context>
</generatorConfiguration>
```


### 数据库连接配置
```java
# 数据库配置
jdbc.driverClass = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/db_mybatis
jdbc.user = root
jdbc.password = 000000

#c3p0
jdbc.maxPoolSize=50
jdbc.minPoolSize=10
jdbc.maxStatements=100
jdbc.testConnection=true

# 通用Mapper配置
mapper.plugin = tk.mybatis.mapper.generator.MapperPlugin
mapper.Mapper = tk.mybatis.mapper.common.Mapper
```
### mybatis 配置
```xml
<?xml version="1.0" encoding="UTF-8" ?>


<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <settings>
        <setting name="cacheEnabled" value="true"/>
        <setting name="lazyLoadingEnabled" value="false"/>
        <setting name="aggressiveLazyLoading" value="true"/>
       <!-- <setting name="logImpl" value="LOG4J"/>-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

    <typeAliases>
        <package name="com.opensourceteam.modules.business.test.po"/>
    </typeAliases>

    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <property name="helperDialect" value="mysql"/>
            <property name="offsetAsPageNum" value="true"/>
            <property name="rowBoundsWithCount" value="true"/>
            <property name="pageSizeZero" value="true"/>
        </plugin>
    </plugins>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
                <property name="" value=""/>
            </transactionManager>
            <dataSource type="UNPOOLED">
                <!--<property name="driver" value="net.sourceforge.jtds.jdbc.Driver"/>
                <property name="url" value="jdbc:jtds:sqlserver://192.168.1.211:1433/master"/>
                <property name="username" value="sa"/>
                <property name="password" value="jj"/>-->
                <property name="driver" value="com.mysql.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://localhost:3306/db_mybatis" />
                <property name="username" value="root" />
                <property name="password" value="000000" />
                <!--<property name="driver" value="com.mysql.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://192.168.16.137:3306/test" />
                <property name="username" value="root" />
                <property name="password" value="" />-->
            </dataSource>
        </environment>
    </environments>

    <mappers>

        <mapper class="com.opensourceteam.modules.business.test.dao.TTestMapper"/>
        <mapper class="com.opensourceteam.modules.business.test.dao.TTestUuidMapper"/>


    </mappers>


</configuration>

```




### pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.opensourceteam</groupId>
  <artifactId>maven-project-logback</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>${artifactId}</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <jdk.version>1.8</jdk.version>
    <logback.version>1.2.3</logback.version>



    <!--  依赖版本  -->
    <mapper.version>3.5.0</mapper.version>
    <mysql.version>5.1.42</mysql.version>
  </properties>


  <dependencies>

    <!-- logback 依赖的包-->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>${logback.version}</version>
    </dependency>
    <!-- junit 单元测试-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>

    <!-- mybatis-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.4.5</version>
    </dependency>
    <!-- mybatis mapper插件，通用mapper-->
    <dependency>
      <groupId>tk.mybatis</groupId>
      <artifactId>mapper</artifactId>
      <version>3.5.0</version>
    </dependency>

    <dependency>
      <groupId>javax.persistence</groupId>
      <artifactId>persistence-api</artifactId>
      <version>1.0.2</version>
    </dependency>
    <!-- mybatis 自动生成类库-->
    <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.6</version>
      <scope>compile</scope>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.freemarker</groupId>
      <artifactId>freemarker</artifactId>
      <version>2.3.23</version>
      <scope>compile</scope>
      <optional>true</optional>
    </dependency>
    <!-- mysql -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.42</version>
    </dependency>


    <!-- mysql -->
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.1.2</version>
    </dependency>
</dependencies>

  <build>
    <sourceDirectory>src/main/java</sourceDirectory>
    <testSourceDirectory>src/test/java</testSourceDirectory>
    <resources>
      <resource>
        <directory>src/main/resources</directory>
      </resource>
    </resources>
    <plugins>
      <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.6</version>
        <configuration>
          <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
          <overwrite>true</overwrite>
          <verbose>true</verbose>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
          </dependency>
          <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${mapper.version}</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </build>
</project>


```

### mybatis generate 命令
```shell
set MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn  -Dmybatis.generator.overwrite=true  mybatis-generator:generate
```

ｅｎｄ