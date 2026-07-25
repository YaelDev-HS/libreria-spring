# Sistema de Gestión de Librería - Backend (Spring Boot)

Aplicación de backend desarrollada con Java 21 y Spring Boot 3 para la gestión de catálogo de libros, préstamos e historial de usuarios con control de acceso basado en roles (ADMIN, LIBRARIAN, USER).

## Stack Tecnológico
- Java 21
- Spring Boot 3.4.3
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- Flyway DB (Migraciones)
- MySQL 8

## Requisitos Previos
- Java 21 instalado
- Docker Desktop (para la base de datos MySQL)

## Pasos para la Ejecución

1. Renombrar el archivo `.env.copy` a `.env`.

2. Levantar el contenedor de MySQL con Docker Compose:
   ```bash
   docker compose up -d
   ```

3. Ejecutar la aplicación con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   El servidor estará corriendo en `http://localhost:8080` o el puerto indicado en `.env`.

## Semilla de Datos (Seed)
Para poblar la base de datos con los usuarios de prueba, lista inicial de libros y solicitudes (PENDING, APPROVED, REJECTED, RETURNED), realizar una petición POST hacia:

```http
POST http://localhost:8080/v1/api/seed
```

### Usuarios generados por la semilla:
- Administrador: `admin@gmail.com` (definido en `.env`) / `admin123` (definido en `.env`)
- Bibliotecario: `bibliotecario@libreria.com` / `Librarian123!`
- Usuario Lector 1: `carlos@gmail.com` / `User1234!`
- Usuario Lector 2: `ana@gmail.com` / `User1234!`
