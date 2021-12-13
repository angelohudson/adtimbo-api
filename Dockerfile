FROM openjdk:11
ADD target/api-0.0.1-SNAPSHOT.jar api-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["sh", "-c", "java -jar /api-0.0.1-SNAPSHOT.jar"]
