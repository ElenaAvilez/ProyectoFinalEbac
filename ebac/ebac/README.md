# README – Backend REST API

# Proyecto Backend – Proveedores, Productos y Login

## Descripción general

Este proyecto corresponde al backend principal del sistema integral desarrollado con Java y Spring Boot.

La aplicación expone una API REST encargada de:

* Gestionar proveedores
* Gestionar productos
* Validar autenticación de usuarios
* Persistir información en base de datos
* Aplicar validaciones de negocio
* Manejar relaciones entre entidades

La arquitectura implementada sigue una estructura por capas utilizando:

* Controller
* Service
* Repository
* DTO/Entity

---

# Tecnologías utilizadas

| Tecnología      | Versión  |
| --------------- | -------- |
| Java            | 17       |
| Spring Boot     | 3.3.5    |
| Spring Web      | Incluido |
| Spring Data JPA | Incluido |
| H2 Database     | 2.2.224  |
| MySQL Connector | Runtime  |
| Lombok          | 1.18.44  |
| Maven           | 3+       |
| Hibernate       | 6.x      |

---

# Arquitectura del proyecto

```text
src/main/java
│
├── controller
│   ├── ProveedorController
│   ├── ProductoController
│   └── LoginController
│
├── service
│   ├── ProveedorService
│   ├── ProductoService
│   └── LoginUsuarioService
│
├── repository
│   ├── ProveedorRepository
│   ├── ProductoRepository
│   └── LoginUsuarioRepository
│
├── dto
│   ├── Proveedor
│   ├── Producto
│   ├── LoginUsuario
│   ├── LoginRequest
│   ├── ResponseWrapper
│   └── ResponseEntity
```

---

# Modelo de datos

# Proveedor

Representa un proveedor dentro del sistema.

## Campos

| Campo       | Tipo           |
| ----------- | -------------- |
| idProveedor | Long           |
| nombre      | String         |
| direccion   | String         |
| telefono    | String         |
| productos   | List<Producto> |

## Relación

```text
Proveedor 1 --- N Producto
```

---

# Producto

Representa un producto asociado a un proveedor.

## Campos

| Campo       | Tipo      |
| ----------- | --------- |
| idProducto  | Long      |
| nombre      | String    |
| costo       | Float     |
| proveedores | Proveedor |

---

# LoginUsuario

Entidad utilizada para autenticación.

## Campos

| Campo     | Tipo   |
| --------- | ------ |
| idUsuario | Long   |
| username  | String |
| password  | String |

---

# Configuración de base de datos

# H2 Database

## application.properties

```properties
spring.datasource.url=jdbc:h2:file:./data/proveedores
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

## Acceso a consola H2

```text
http://localhost:8080/h2-console
```

## JDBC URL

```text
jdbc:h2:file:./data/proveedores
```

---

# MySQL (Opcional)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/proyectoFinal
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
```

---

# Configuración del login

# Tabla usuario

```sql
CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
```

## Usuario inicial

```sql
INSERT INTO usuario (username, password)
VALUES ('karen', '123');
```

---

# Endpoints REST

# Login

## Login

```http
POST /login
```

## Body

```json
{
  "username": "karen",
  "password": "123"
}
```

---

# Proveedores

## Obtener todos los proveedores

```http
GET /proveedores
```

## Obtener proveedor por ID

```http
GET /proveedores/{id}
```

## Crear proveedor

```http
POST /proveedores
```

## Actualizar proveedor

```http
PUT /proveedores/{id}
```

## Eliminar proveedor

```http
DELETE /proveedores/{id}
```

---

# Productos

## Obtener todos los productos

```http
GET /productos
```

## Obtener producto por ID

```http
GET /productos/{id}
```

## Crear producto

```http
POST /productos
```

## Actualizar producto

```http
PUT /productos/{id}
```

## Eliminar producto

```http
DELETE /productos/{id}
```

---

# Validaciones implementadas

# Backend

## Login

* Usuario requerido
* Password requerido
* Validación contra tabla usuario

## Proveedores

* Nombre requerido
* Dirección requerida
* Teléfono requerido
* Al menos un producto

## Productos

* Nombre requerido
* Costo requerido
* Costo mayor a cero

---

# Lógica de negocio

# Relación Proveedor -> Productos

Antes de guardar un proveedor:

```java
producto.setProveedores(proveedor);
```

Esto garantiza:

* Persistencia correcta
* Relación bidireccional
* Integridad de datos

---

# Manejo de respuestas

La aplicación utiliza:

```text
ResponseWrapper<T>
```

## Estructura

```json
{
  "success": true,
  "message": "Proveedor creado exitosamente",
  "responseEntity": {
    "body": {}
  }
}
```

---

# Ejecución del proyecto

# Requisitos

* Java 17
* Maven 3+
* IntelliJ IDEA recomendado

---

# Compilar proyecto

```bash
mvn clean install
```

## Ignorar tests

```bash
mvn clean install -DskipTests
```

---

# Levantar aplicación

```bash
mvn spring-boot:run
```

---

# Puerto de ejecución

```text
http://localhost:8080
```

---

# Dependencias principales

```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
h2
mysql-connector-j
lombok
```

# Buenas prácticas implementadas

* Arquitectura por capas
* DTOs separados
* Validaciones backend
* Manejo centralizado de respuestas
* Uso de JPA
* Uso de Lombok
* Manejo de relaciones OneToMany
* Separación de lógica de negocio

---
# Autor

Karen Elena Avilez Velazquez
