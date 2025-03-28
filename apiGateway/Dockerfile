# Используем образ Gradle для сборки
FROM gradle:8.11-jdk17-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle .
COPY settings.gradle .
COPY /src ./src
#COPY configurationServer/build.gradle ./configurationServer/
#COPY configurationServer/settings.gradle ./configurationServer/
#COPY configurationServer/src ./configurationServer/src

# Собираем приложение
RUN gradle :bootJar -x test

# Используем образ OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл
COPY --from=build /app/build/libs/apiGateway-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 8020

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
