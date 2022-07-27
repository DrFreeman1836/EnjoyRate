# EnjoyRate
Web приложение, обращающееся к сервису курсов валют, и отдает gif в ответ.
Основной стек: Spring boot, Feign, MockMvc, Java17


# Инструкция по запуску
Перед запуском указать в конфигурационном файле app_id, api_key и валюту, по отношению к которой запрашивается курс.

Сборка gradle: "./gradlew build"
Запустить: "java -jar enjoyrate-1.0-snapshot.jar"

Сервис будет запущен на порту 8080.

Docker:

docker run -d -p 8080:8080 -t EnjoyRate:1.0

