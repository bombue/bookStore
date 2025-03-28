# Используем образ Gradle для сборки
FROM gradle:8.11-jdk17-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle .
COPY settings.gradle .
COPY /src ./src
#COPY ws-book/build.gradle ./ws-book/
#COPY ws-book/src ./ws-book/src

# Собираем приложение
RUN gradle :bootJar -x test

# Используем образ OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл
COPY --from=build /app/build/libs/ws-book-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 8082

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
