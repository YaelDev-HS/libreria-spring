# Prueba tecnica de libreria

## (requerido) Ejecutar aplicacion
1. (requerido) Renombrar el archivo ```.env.copy``` por ```.env```
2. (requerido) Levantar docker con el comando ```docker compose up -d```
3. (opcional) Ejecutar semilla mediante ```POST``` hacia
```bash
http://localhost:8080/v1/api/seed
```
La semilla crea el usuario admin con las credenciales del archivo ```.env``` e informacion de libros, bibliotecarios y usuarios con ordenes pendientes,
aceptadas, rechazadas y devueltas.

### Stack utilizado:
1. Java ```21```
2. Mysql
3. Autenticacion mediante JWT.
