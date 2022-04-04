FROM jdk1.8
ADD blog-gateway.jar blog-gateway.jar
# 声明端口映射
EXPOSE 9000
# sentinel交互的端口
EXPOSE 8719
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Xms200m","-Xmx300m","-jar","blog-gateway.jar"]