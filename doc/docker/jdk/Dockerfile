# 依赖镜像名称和ID
FROM ubuntu
# 指定镜像创建者信息
MAINTAINER chenmou
# 切换工作目录
RUN mkdir /usr/local/jdk
WORKDIR /usr/local/jdk
# ADD 是对相对路径jar, 把jdk添加到容器中
ADD jdk-8u221-linux-x64.tar.gz /usr/local/jdk

# 配置java环境变量
ENV JAVA_HOME /usr/local/jdk/jdk1.8.0_221
ENV JRE_HOME $JAVA_HOME/jre
ENV CLASSPATH $JAVA_HOME/bin/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib:$CLASSPATH
ENV PATH $JAVA_HOME/bin:$PATH
