FROM eclipse-temurin:17-jre-alpine

WORKDIR /

COPY /build/libs/app.jar ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
