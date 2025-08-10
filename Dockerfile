FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle/
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

RUN --mount=type=cache,target=/root/.gradle ./gradlew build --no-daemon -x test

COPY src ./src

RUN --mount=type=cache,target=/root/.gradle ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy

RUN groupadd --system --gid 1001 spring && useradd --system --gid spring --uid 1001 spring
USER spring:spring

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_TOOL_OPTIONS="-Xmx512m -Xms256m"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]