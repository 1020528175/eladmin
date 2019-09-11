# 基础镜像
FROM openjdk:8-jdk-alpine

# 作者信息
MAINTAINER "masterJ javasupermaster@163.com"

# 添加一个存储空间
#通过dockerfile的 VOLUME 指令可以在镜像中创建挂载点，这样只要通过该镜像创建的容器都有了挂载点。
#还有一个区别是，通过 VOLUME 指令创建的挂载点，无法指定主机上对应的目录，是自动生成的。
#   $docker inspect 容器id   可以看到"Mounts":[{..."Source": "/var/lib/docker/volumes/dae714b58b31b3070cf15a6b7db02456c74641d03d8acdfcf7b95040b948aed5/_data",...}]
VOLUME /tmp

# 暴露8080端口,这里暴露的也就是tomcat的端口
EXPOSE 8080

# 添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
ARG JAR_FILE=/target/eladmin.jar

# 往容器中添加jar包
ADD ${JAR_FILE} app.jar
#ADD /target/eladmin.jar app.jar

# 启动镜像自动运行程序
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/app.jar"]