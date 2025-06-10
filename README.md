<p align="center">
  <img src="./nx.png" alt="Nx Inventory System" width="120" />
</p>

<h1 align="center">Nx Inventory System Backend</h1>

Bienvenido al sistema de gestión de inventario para el sector automotriz, desarrollado con
**Spring Boot**, **JWT**, **PostgreSQL**. Esta aplicación backend permite 
registrar y consultar productos, administrar roles de usuarios y proteger el acceso a 
funcionalidades mediante autenticación basada en tokens.
---

## Evidencia General (Incluye imágenes de pruebas funcionales y ERD

🔍 [Ver carpeta de evidencias](./Evidencia_General/)

## 🛠️ Requisitos

- Java 21 (JDK)
- Maven 3.8+
- PostgreSQL 15
- Opcional: navegador para pruebas API (Postman, Insomnia)

Verifica que tienes Maven y Java instalados:

```bash
java -version
mvn -v
```

---

## ⚙️ Instalación local

### 1. Clonar el repositorio

```bash
git clone https://github.com/CAndres438/nx-inventory-system.git
cd nx-inventory-system
```

### 2. Configurar la base de datos

Crea una base de datos PostgreSQL llamada `inventory_system`. 
Luego configura tus credenciales en `src/main/resources/application-dev.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_system
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=none
spring.profiles.active=dev
```


### 3. Levantar la aplicación

## 💡 Sugerencia: abrir con IntelliJ IDEA

Si usas IntelliJ IDEA, puedes abrir y ejecutar el proyecto fácilmente:

1. Abre IntelliJ.
2. Ve a **File > Open** y selecciona la carpeta raíz del proyecto.
3. IntelliJ reconocerá el proyecto como Maven automáticamente.
4. Si no lo hace, clic derecho en `pom.xml` → **Add as Maven Project**.
5. Espera a que descargue las dependencias.
6. Ejecuta desde el botón verde o con `mvn spring-boot:run`.

> También puedes usar cualquier otro editor compatible con Maven y Java 21.

```bash
mvn spring-boot:run
```

La app estará disponible por defecto en `http://localhost:8080`

puedes cambiar con server.port si el puerto está ocupado

---

## 🔐 Autenticación JWT

### Registro de usuario

```http
POST /auth/register
```

```json
{
  "name": "Carlos Ortiz",
  "username": "caop",
  "password": "caop123",
  "age": 27,
  "roleId": 4
}
```

> Originalmente la aplicación tendrá tres roles de Admin, Ventas y Soporte

### Inicio de sesión

```http
POST /auth/login
```

```json
{
  "username": "admin1",
  "password": "admin123"
}
```
Este es uno de los usarios que vienen por defecto

Devuelve un token JWT para usar en `Authorization: Bearer <token>`

---

## 👥 Usuarios predefinidos (opcional con DatabaseInitializer)

| Rol           | Username           | Contraseña    |
|---------------| ------------------ | ----------    |
| ADMIN         | admin1             | admin123      |
| SALES         | user1              | user123       |
| SUPPPORT      | support1           | soporte123    |

---

## 📦 API - Inventario

Todas las rutas requieren autenticación con JWT. Para el ejercicio todos
los usuarios pueden crear, editar y eliminar productos del inventario.

👉 El login devuelve un token JWT que debe enviarse como header `Authorization: Bearer <token>`

### Crear producto

```http
POST /api/inventory
```

```json
{
  "name": "Filtro de aceite",
  "dateIn": "2025-06-10T05:00:00.000Z",
  "quantity": 15
}
```

### Listar productos

```http
POST /api/inventory/filter
```

> Soporta filtros por nombre, paginación, fechas, y usuario creador:

```json
{
  "name": "",
  "startDate": null,
  "endDate": "",
  "createdBy": null
}
```

⚠ Los filtros vacíos traeran todo el inventario

### Actualizar producto

```http
PUT /api/inventory/{id}
```

```json
{
  "dateIn": "2025-06-10",
  "name": "Filtro de aceite",
  "quantity": 16
}
```

⚠ La edición marcará internamente el último usuario y la última actualización

### Eliminar producto

```http
DELETE /api/inventory/{id}
```

⚠ La eliminación solo funciona si se es el creador del producto, de lo contrario manejará la excepción

---

## 🛡️ Administración de Roles (solo ADMIN)

### Listar roles

```http
GET /api/roles
```

### Cambiar estado de rol

```http
PUT /api/roles/{id}/status
```

```json
{
  "active": false
}
```

### Crear role

```http
POST /api/roles
```

```json
{
  "displayName": "Contabilidad",
  "systemName": "Contable"
}
```
---


## 🧪 Ejecución de tests

Este proyecto incluye pruebas unitarias para el servicio principal (`InventoryService`).

### Ejecutar tests desde el IDE

Puedes correr los tests directamente desde tu IDE (IntelliJ, Eclipse, etc.):

- Haz clic derecho sobre la clase de test (por ejemplo: `InventoryServiceTest`)
- Elige `Run 'InventoryServiceTest'`

### Ejecutar tests por consola

```bash
mvn test

```

## 🧪 Pruebas manuales rápidas

1. Registrar usuario
2. Hacer login para obtener token
3. como cualquier usuario autenticado realizar acciones de inventario
4. Como USER: intentar acceder a `/api/roles` con `POST`, debe fallar
5. Loguearse como ADMIN (admin1)
6. Crear, eliminar y actualizar status de roles

---

## 🧼 Buenas prácticas implementadas

✅ DTOs para entrada y salida  
✅ Validaciones con `javax.validation`  
✅ Manejo global de errores con `@ControllerAdvice`  
✅ Separación por capas: controllers, services, repositories  
✅ Seguridad con JWT y filtros personalizados  
✅ Roles y permisos controlados por anotaciones `@PreAuthorize`  
✅ Inicialización segura de datos

---

## 👨‍💻 Autor

**Carlos Andrés Ortiz Peña**  
Senior Fullstack Developer 💻 | Apasionado por construir soluciones eficientes 🚀

---
