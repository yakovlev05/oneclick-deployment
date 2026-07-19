FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app
COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -B
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/oneclick-deployment-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
