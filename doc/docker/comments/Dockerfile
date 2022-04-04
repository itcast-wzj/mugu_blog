FROM jdk1.8
ADD blog-comments.jar blog-comments.jar
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Xms200m","-Xmx300m","-jar","blog-comments.jar"]