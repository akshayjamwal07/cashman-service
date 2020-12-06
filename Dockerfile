# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY ./build/libs/ /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/cashman-service-1.0.0.jar"]
