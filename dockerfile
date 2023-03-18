FROM openjdk:17
VOLUME /stock-service
ARG JAR_FILE=target/stocks-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} stock-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/stock-service.jar"]
