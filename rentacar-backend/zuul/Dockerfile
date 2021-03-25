FROM openjdk:8-jdk-alpine
EXPOSE 8084
COPY entrypoint.sh /entrypoint.sh
COPY target/zuul-0.0.1-SNAPSHOT.jar zuul-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]