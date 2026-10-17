# Proyecto de Investigación Aplicada: Sistema de Gestion de Encomiendas y Courier (RHL Express)

## Información del Proyecto
- Institución: CIBERTEC - Escuela de Tecnología
- Carrera: Computación e Informática
- Curso: Desarrollo de Aplicaciones Web II (Sección 4697)
- Ciclo: Sexto Ciclo
- Plataforma: RHL Express (Logística & Courier Internacional)

## Descripción General
El presente proyecto consiste en el desarrollo de un sistema web empresarial orientado a la gestión logística y despacho de encomiendas. La solución permite el registro de paquetes, generación automática de códigos de seguimiento, control de estados de envío, trazabilidad en vivo con bitácora cronológica y autenticación segura bajo roles de acceso definidos.

## Arquitectura y Componentes
El sistema está diseñado sobre una arquitectura de microservicios desacoplados:
- **Frontend SPA (Angular 22 Standalone):** Interfaz web corporativa con diseño de alta fidelidad inspirado en DHL (RHL Express). Incluye portal público de rastreo con stepper interactivo de hitos, directorio nacional de sucursales, catálogo de servicios, buzón de contacto, autenticación JWT para colaboradores y panel de control operativo con métricas y operaciones CRUD completas.
- **Puerta de Enlace (api-gateway - Puerto 8080):** Punto de entrada único basado en Spring Cloud Gateway que gestiona el enrutamiento centralizado hacia los microservicios internos y las políticas de seguridad CORS para Angular (`http://localhost:4200`).
- **Microservicio de Envíos (ms-envios - Puerto 8081):** Módulo principal que gestiona la lógica de negocio, persistencia de encomiendas con Spring Data JPA, administración de usuarios con contraseñas cifradas en `BCryptPasswordEncoder` y generación de tokens JWT.
- **Microservicio de Seguimiento (ms-tracking - Puerto 8082):** Módulo encargado de la bitácora cronológica de estados, consumo asíncrono de eventos mediante RabbitMQ y comunicación sincrónica tolerante a fallos mediante OpenFeign y Circuit Breaker (Resilience4j).
- **Infraestructura de Contenedores:** Orquestación modular con Docker Compose para MySQL 8.0 (puerto 5510), RabbitMQ (puertos 5672 y 15672) y los contenedores de los microservicios.

## Tecnologías Empleadas
- **Frontend:** Angular 22 (Standalone Components, Signals, Reactive Forms, SCSS Corporativo RHL, TypeScript).
- **Backend:** Java 17 y Spring Boot 3.
- **Microservicios & Nube:** Spring Cloud Gateway, OpenFeign, Resilience4j (Circuit Breaker con fallback).
- **Persistencia & Seguridad:** Spring Data JPA, Hibernate, Spring Security, BCrypt, JWT, MySQL 8.0.
- **Mensajería Distribuida:** RabbitMQ (AMQP).
- **DevOps & QA:** Docker, Docker Compose, JUnit 5 (@DataJpaTest, @SpringBootTest), Project Lombok.

## Guía de Ejecución del Frontend en Visual Studio Code
1. Abrir la carpeta `sistema-courier/frontend` en Visual Studio Code.
2. Abrir la terminal integrada (`Ctrl + ~`) y ejecutar:
   ```bash
   npm start
   ```
3. Abrir el navegador en: `http://localhost:4200`
   - **Rastreo Público:** Probar con los códigos de prueba `ENV-1001`, `ENV-1002`, `ENV-1003`.
   - **Acceso Colaboradores:** Ir a `/login` con los usuarios demo:
     - Administrador: `admin` / `admin123`
     - Operador: `operador` / `123456`
   - **Dashboard Administrativo:** Crear nuevos envíos, editar estados y anular encomiendas en tiempo real.
