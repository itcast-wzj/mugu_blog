docker run -di --name blog-article -p 9004:9004 -v /usr/local/logs:/usr/local/logs -v /usr/local/skywalking:/usr/local/skywalking -e SKYWALKING_COLLECTOR_BACKEND_SERVICE=172.21.21.27:11800

docker run -di --name blog-gateway -p 9000:9000 -v /usr/local/logs:/usr/local/logs -v /usr/local/skywalking:/usr/local/skywalking -e SKYWALKING_COLLECTOR_BACKEND_SERVICE=172.21.21.27:11800