# README – WebApp

# Proyecto WebApp – Proveedores, Productos y Login

## Descripción general

Este proyecto corresponde a la aplicación web del sistema integral.

La WebApp consume el backend mediante OpenFeign y permite:

* Login de usuarios
* Visualizar proveedores
* Crear proveedores
* Actualizar proveedores
* Registrar múltiples productos
* Mostrar productos asociados
* Validar formularios

La interfaz está construida utilizando Thymeleaf, Bootstrap y JQuery.

---

# Tecnologías utilizadas

| Tecnología  | Versión  |
| ----------- | -------- |
| Java        | 17       |
| Spring Boot | 4.0.6    |
| Thymeleaf   | Incluido |
| OpenFeign   | 12.2     |
| JQuery      | Incluido |
| Bootstrap   | Incluido |
| Lombok      | 1.18.44  |
| Maven       | 3+       |

---

# Arquitectura del proyecto

```text
src/main/java
│
├── controller
│   ├── LoginController
│   ├── IndexController
│   └── ProveedorController
│
├── dto
│   ├── Proveedor
│   ├── Producto
│   ├── LoginRequest
│   ├── LoginUsuario
│   ├── ResponseWrapper
│   └── ResponseEntity
│
├── feign
│   ├── FeignConfiguration
│   ├── FeignApiClientProveedores
│   ├── FeignApiClientProductos
│   ├── FeignApiClientLogin
│   ├── FeignServiceProveedores
│   ├── FeignServiceProductos
│   └── FeignLoginService
```

---

# Funcionalidades

# Login

* Validación de usuario
* Consumo del backend mediante Feign
* Manejo de sesión HTTP
* Logout
* Mensajes de error

---

# Gestión de proveedores

* Visualización de proveedores
* Registro de proveedores
* Actualización de proveedores
* Visualización de productos

---

# Gestión de productos

* Registro de múltiples productos
* Eliminación dinámica de productos
* Validaciones frontend

---

# Configuración de aplicación

# application.properties

```properties
api.url=http://localhost:8080
server.port=8181
```

---

# Flujo de login

1. Usuario captura credenciales.
2. WebApp envía request a backend.
3. Backend valida usuario.
4. Se genera sesión HTTP.
5. Usuario accede al dashboard.

---

# Configuración Feign

## FeignConfiguration

Registra:

* FeignApiClientProveedores
* FeignApiClientProductos
* FeignApiClientLogin

---

# Formularios dinámicos

La aplicación permite:

* Agregar múltiples productos
* Eliminar productos dinámicamente
* Validar información antes de enviar

---

# Validaciones frontend

## Proveedor

* Nombre requerido
* Dirección requerida
* Teléfono requerido

## Productos

* Nombre requerido
* Costo requerido
* Costo numérico

---

# Vistas Thymeleaf

## pagina-login.html

Pantalla de autenticación.

## index.html

Dashboard principal.

## formulario-proveedor.html

Formulario de creación/actualización.

## proveedor.html

Detalle de proveedor y productos.

---

# Ejecución del proyecto

# Requisitos

* Java 17
* Maven 3+
* Backend ejecutándose en puerto 8080

---

# Compilar

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

# Acceso WebApp

```text
http://localhost:8181
```

---

# Dependencias principales

```xml
spring-boot-starter-web
spring-boot-starter-thymeleaf
feign-core
feign-jackson
lombok
```



# Error Login 500

## Posibles causas

* Tabla usuario inexistente
* Columnas incorrectas
* Usuario no registrado
* Backend apagado

---

# Buenas prácticas implementadas

* Separación frontend/backend
* Consumo desacoplado mediante Feign
* Arquitectura MVC
* Validaciones frontend y backend
* Manejo centralizado de respuestas
* Formularios dinámicos
* Reutilización de DTOs
* Uso de Thymeleaf

---

# Autor

Karen Elena Avilez Velazquez
