# Используем образ Gradle для сборки
FROM gradle:8.11-jdk17-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY build.gradle .
COPY settings.gradle .
COPY application/build.gradle ./application/
COPY application/src ./application/src
#COPY application .
#COPY src src

# Собираем приложение
RUN gradle :application:bootJar -x test

# Используем образ OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл
COPY --from=build /app/application/build/libs/application-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 8080

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM gradle:8.11-jdk17-alpine
#RUN mkdir -p /bookStore
#COPY build/ /bookStore/
#COPY src/main/resources/application.properties /bookStore/config/application.properties
#EXPOSE 8080
#ENTRYPOINT exec java -jar /bookStore/libs/application-0.0.1-SNAPSHOT.jar --spring.config.location=/bookStore/config/application.properties

