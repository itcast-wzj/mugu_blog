FROM jdk1.8
ADD blog-user-boot.jar blog-user-boot.jar
# 声明端口映射
EXPOSE 9002
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Xms200m","-Xmx300m","-jar","blog-user-boot.jar"]