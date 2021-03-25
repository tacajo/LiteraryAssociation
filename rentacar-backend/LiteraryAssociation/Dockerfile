FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY entrypoint.sh /entrypoint.sh
COPY target/LiteraryAssociation-0.0.1-SNAPSHOT.jar LiteraryAssociation-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]