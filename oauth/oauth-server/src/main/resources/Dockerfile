FROM jdk1.8
ADD blog-oauth-server.jar blog-oauth-server.jar
# 声明端口映射
EXPOSE 9001
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Xms200m","-Xmx300m","-jar","blog-oauth-server.jar"]