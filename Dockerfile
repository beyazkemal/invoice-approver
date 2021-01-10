FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/invoice-approver-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]