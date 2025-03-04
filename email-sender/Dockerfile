# Используем образ Gradle для сборки
FROM gradle:8.11-jdk17-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle .
COPY settings.gradle .
COPY email-sender/build.gradle ./email-sender/
COPY email-sender/src ./email-sender/src
#COPY email-sender .
#COPY email-sender/src src

# Собираем приложение
RUN gradle :email-sender:bootJar -x test

# Используем образ OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл
COPY --from=build /app/email-sender/build/libs/email-sender-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 8081

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]