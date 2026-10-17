# Proyecto de Investigación Aplicada: Sistema de Gestion de Encomiendas y Courier

## Información del Proyecto

* Institución: CIBERTEC - Escuela de Tecnología
* Carrera: Computación e Informatica
* Curso: Desarrollo de Aplicaciones Web II (Sección 4697)
* Ciclo: Sexto Ciclo

## Descripción General

El presente proyecto consiste en el desarrollo de un sistema web empresarial orientado a la gestion logística y despacho de encomiendas. La solución permite el registro de paquetes, generación automática de códigos de seguimiento, control de estados de envió y autenticación segura de usuarios bajo roles de acceso definidos.

## Arquitectura y Componentes

El sistema esta diseñado sobre una arquitectura de microservicios desacoplados:

* Microservicio de Envíos (ms-envios): Modulo principal que gestiona la lógica de negocio, la persistencia de encomiendas, la administración de usuarios y la seguridad de acceso mediante tokens JWT.
* Microservicio de Seguimiento (ms-tracking): Modulo encargado de la bitácora cronológica de estados, consumo asíncrono de eventos mediante RabbitMQ y comunicación sincrónica tolerante a fallos mediante OpenFeign y Circuit Breaker (Resilience4j).
* Puerta de Enlace (api-gateway): Punto de entrada único basado en Spring Cloud Gateway que gestiona el enrutamiento hacia los microservicios internos y las políticas de seguridad CORS.
* Infraestructura de Contenedores: Orquestación modular con Docker Compose para MySQL 8.0, RabbitMQ y el despliegue de los servicios.

## Tecnologías Empleadas

* Java 17 y Spring Boot 3
* Spring Cloud Gateway y OpenFeign
* Resilience4j (Circuit Breaker con fallback)
* Spring Data JPA y Hibernate
* Spring Security con cifrado BCryptPasswordEncoder y JWT
* RabbitMQ (AMQP) para mensajería asíncrona
* Base de Datos Relacional MySQL 8.0
* Docker y Docker Compose
* JUnit 5 y Spring Boot Test
* Project Lombok y Jakarta Validation

