FROM jdk1.8
ADD blog-article.jar blog-article.jar
# 声明端口映射
EXPOSE 9004
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Xms200m","-Xmx500m","-jar","blog-article.jar"]