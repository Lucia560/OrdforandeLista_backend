FROM maven:3.9.8-eclipse-temurin-22 AS build
WORKDIR /build
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]