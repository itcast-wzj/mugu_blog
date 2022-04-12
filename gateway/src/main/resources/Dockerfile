FROM jdk1.8
# 切换目录
WORKDIR /usr/local
# 创建skywalking的目录，需要挂载
RUN mkdir /usr/local/skywalking
ADD blog-gateway.jar blog-gateway.jar
# 日志、skywalking的挂挂载点
VOLUME ["/usr/local/logs","/usr/local/skywalking"]
# 运行的端口
ENV SERVER_PORT 9000
# skywalking中的oap的地址
ENV SKYWALKING_COLLECTOR_BACKEND_SERVICE 127.0.0.1:11800
# 声明端口映射
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Dserver.port=${SERVER_PORT}","-javaagent:/usr/local/skywalking/agent/skywalking-agent.jar","-Dskywalking.agent.service_name=blog-gateway","-Dskywalking.collector.backend_service=${SKYWALKING_COLLECTOR_BACKEND_SERVICE}","-Xms200m","-Xmx300m","-jar","blog-gateway.jar"]