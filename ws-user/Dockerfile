# Используем образ Gradle для сборки
FROM gradle:8.11-jdk17-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle .
COPY settings.gradle .
COPY /src ./src
#COPY application/build.gradle ./application/
#COPY application/src ./application/src

# Собираем приложение
RUN gradle :bootJar -x test

# Используем образ OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл
COPY --from=build /app/build/libs/ws-user-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 8083

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
