# Sistema de Gestión de Estacionamiento - Arquitectura de Microservicios

## Descripción

Sistema completo de gestión de estacionamiento desarrollado bajo arquitectura de microservicios. La solución permite administrar vehículos, espacios de estacionamiento, tarifas, entradas y salidas, pagos, reservaciones, notificaciones y reportes de forma distribuida y escalable.

## Dependencias Principales

- Java 25
- Spring Boot 4.0.6
- Spring Cloud 2025.1.1
- Spring Cloud Gateway MVC
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL 8.0
- Docker
- Eureka Service Discovery
- OpenAPI 3.0 / Swagger (springdoc-openapi-starter-webmvc-ui v2.8.9)
- JUnit 5
- Mockito
- Flyway
- Maven

## Orden de Ejecución de Microservicios

1. MySQL 8.0 - Base de datos
2. ms-eureka-server (Puerto 9090) - Servidor de descubrimiento de servicios
3. ms-auth (Puerto 9080) - Autenticación
4. ms-user (Puerto 9081) - Gestión de usuarios
5. ms-vehicle (Puerto 9082) - Gestión de vehículos
6. ms-parking (Puerto 9083) - Gestión de estacionamiento
7. ms-tariff (Puerto 9084) - Gestión de tarifas
8. ms-entry-exit (Puerto 9085) - Registro de entradas y salidas
9. ms-payment (Puerto 9086) - Procesamiento de pagos
10. ms-reservation (Puerto 9087) - Sistema de reservaciones
11. ms-notification (Puerto 9088) - Notificaciones
12. ms-report (Puerto 9089) - Generación de reportes
13. ms-gateway (Puerto 9091) - API Gateway centralizado

## Bases de Datos

- users_db
- vehicles_db
- parking_db
- tariff_db
- entryexit_db
- payment_db
- reservation_db
- notification_db
- report_db

## Acceso a Documentación

La documentación de cada microservicio está disponible a través de OpenAPI 3.0 / Swagger en:

- http://localhost:PUERTO/swagger-ui/index.html

Donde PUERTO corresponde al puerto de cada microservicio listado anteriormente.

## Patrón de Arquitectura

Todos los microservicios implementan el patrón CSR (Controller-Service-Repository) con separación clara de responsabilidades y paquetes por capa.
