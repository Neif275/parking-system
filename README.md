# Sistema de Gestión de Estacionamiento - Arquitectura de Microservicios

## Descripción

Sistema completo de gestión de estacionamiento desarrollado bajo arquitectura de microservicios. La solución permite administrar vehículos, espacios de estacionamiento, tarifas, entradas y salidas, pagos, reservaciones, notificaciones y reportes de forma distribuida y escalable.

## Estudiante

- Diego Neif Reyes Godoy

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

## Ejecución Local (Docker)

Requisitos previos: Docker Desktop instalado y corriendo.

1. Clonar el repositorio:
   ```
   git clone https://github.com/Neif275/parking-system.git
   cd parking-system
   ```
2. Levantar todo el ecosistema (MySQL, Eureka, los 10 microservicios de negocio y el Gateway):
   ```
   docker compose up -d --build
   ```
3. Verificar que todos los contenedores estén arriba:
   ```
   docker compose ps
   ```
4. Acceder a través del API Gateway en `http://localhost:9091` (ver rutas disponibles más abajo), o directamente a cada microservicio en su puerto individual (ver tabla de "Orden de Ejecución de Microservicios").
5. Para detener el ecosistema:
   ```
   docker compose down
   ```

> Nota: si Docker Desktop se reinicia o se detiene abruptamente mientras el stack está corriendo, los contenedores quedan en estado `Exited` con el mismo nombre. Antes de volver a levantar el stack, correr `docker compose down` (o `docker rm -f $(docker ps -aq)` si el nombre del proyecto cambió) para evitar errores de `container name already in use`.

## Rutas Principales del API Gateway

Todas las rutas se exponen a través de `http://localhost:9091`, enrutando por descubrimiento de servicio vía Eureka:

| Ruta | Microservicio destino |
|---|---|
| `/auth/**` | ms-auth |
| `/admin/**` | ms-auth |
| `/api/v1/users/**` | ms-user |
| `/api/v1/vehicles/**` | ms-vehicle |
| `/api/v1/floors/**` | ms-parking |
| `/api/v1/zones/**` | ms-parking |
| `/api/v1/slots/**` | ms-parking |
| `/api/v1/slot-types/**` | ms-parking |
| `/api/v1/tariffs/**` | ms-tariff |
| `/api/v1/entry-exit/**` | ms-entry-exit |
| `/api/v1/payments/**` | ms-payment |
| `/api/v1/reservations/**` | ms-reservation |
| `/api/v1/notifications/**` | ms-notification |
| `/api/v1/reports/**` | ms-report |

## Acceso a Documentación (Swagger / OpenAPI)

Cada microservicio expone su propia documentación interactiva en `/swagger-ui/index.html`:

- ms-auth: http://localhost:9080/swagger-ui/index.html
- ms-user: http://localhost:9081/swagger-ui/index.html
- ms-vehicle: http://localhost:9082/swagger-ui/index.html
- ms-parking: http://localhost:9083/swagger-ui/index.html
- ms-tariff: http://localhost:9084/swagger-ui/index.html
- ms-entry-exit: http://localhost:9085/swagger-ui/index.html
- ms-payment: http://localhost:9086/swagger-ui/index.html
- ms-reservation: http://localhost:9087/swagger-ui/index.html
- ms-notification: http://localhost:9088/swagger-ui/index.html
- ms-report: http://localhost:9089/swagger-ui/index.html

## Patrón de Arquitectura

Todos los microservicios implementan el patrón CSR (Controller-Service-Repository) con separación clara de responsabilidades y paquetes por capa.
