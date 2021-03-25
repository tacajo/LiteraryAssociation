FROM openjdk:8-jdk-alpine
EXPOSE 8081
COPY entrypoint.sh /entrypoint.sh
COPY target/bank-service-0.0.1-SNAPSHOT.jar bankservice-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]