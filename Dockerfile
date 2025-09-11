# 1. 使用 Gradle + JDK 21 建置 jar
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# 2. 用更輕量的 JDK 21 來執行
FROM eclipse-temurin:21-jdk
WORKDIR /app

# 複製上一步產生的 jar
COPY --from=builder /app/build/libs/*.jar app.jar

# Render 會自動提供 $PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]