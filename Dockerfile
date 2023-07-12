FROM adoptopenjdk/openjdk11:alpine-jre
COPY  target/crop-recommendation-backend-0.0.1-SNAPSHOT.jar crop-recommendation-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "crop-recommendation-backend.jar"]