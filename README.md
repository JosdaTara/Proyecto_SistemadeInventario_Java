# GesCom - Gestión Comercial

## Descripción
Sistema de información para la gestión de inventario, ventas, facturación y catálogo de productos. Aplicación web con roles de **Administrador** (gestión interna) y **Cliente** (catálogo, pedidos y perfil).

Desarrollada con Spring Boot 3.4.5, Thymeleaf, Bootstrap 5 y MySQL.

## Estructura del Proyecto

```
PROYECTO/
├── 📁 Base de Datos/
│   ├── script_bd.sql             # Script de creación BD + datos iniciales
│   └── inventario_ventas.sql     # Export de la base de datos
├── 📁 Diagramas/
│   ├── CasosDeUso.md             # Diagramas de casos de uso
│   └── DiagramaClases.md         # Diagrama de clases UML
├── 📁 Documentos/
│   ├── Documento_Proyecto.docx   # Documento oficial (Word)
│   ├── Documento_Proyecto.md     # Objetivos, justificación y entorno
│   └── Requerimientos.md         # Requerimientos funcionales y no funcionales
├── 📁 SIGV/                      # Código fuente (Maven / Spring Boot)
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/uts/edu/java/
│   │   │   │   ├── 📁 config/        # SecurityConfig, DataInitializer, AuthSuccessHandler
│   │   │   │   ├── 📁 controller/    # AuthController, ClienteController
│   │   │   │   ├── 📁 model/         # Usuario, Rol, Producto
│   │   │   │   ├── 📁 repository/    # UsuarioRepository, RolRepository, ProductoRepository
│   │   │   │   ├── 📁 service/       # UsuarioService, CustomUserDetailsService
│   │   │   │   └── SIstemaGestionInventarioApplication.java
│   │   │   └── 📁 resources/
│   │   │       ├── 📁 static/css/    # style.css (tema verde corporativo)
│   │   │       ├── 📁 templates/
│   │   │       │   ├── login.html    # Inicio de sesión
│   │   │       │   ├── registro.html # Registro de usuarios
│   │   │       │   ├── dashboard.html# Panel de administrador
│   │   │       │   └── 📁 cliente/   # Panel de cliente
│   │   │       │       ├── dashboard.html
│   │   │       │       ├── productos.html
│   │   │       │       ├── pedidos.html
│   │   │       │       └── perfil.html
│   │   │       └── application.properties
│   │   └── 📁 test/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── .mvn/
├── .gitignore
└── README.md
```

## Tecnologías

### Backend
- Java 17+
- Spring Boot 3.4.5
- Spring MVC
- Spring Data JPA / Hibernate
- Spring Security (BCrypt, roles)
- Maven

### Frontend
- HTML5, CSS3, Bootstrap 5.3
- Bootstrap Icons
- Thymeleaf 3.1

### Base de Datos
- MySQL 8.0

## Roles del Sistema

| Rol | Acceso | Descripción |
|-----|--------|-------------|
| **ADMINISTRADOR** | `/dashboard` | Gestión de inventario, ventas, facturación |
| **CLIENTE** | `/cliente/dashboard` | Catálogo de productos, pedidos, perfil |

## Usuarios por Defecto
- **Admin:** `admin@admin.com` / `admin123` (se crea automáticamente al iniciar)
- **Cliente:** registrarse en `/registro`

## Configuración

### Requisitos
- Java 17+ (JDK)
- MySQL 8.0 (XAMPP recomendado)
- Maven (incluye `mvnw`)

### Pasos
1. Iniciar MySQL (XAMPP → Start MySQL)
2. Crear la base de datos:
   ```sql
   CREATE DATABASE inventario_ventas;
   ```
3. Ubicarse en la carpeta del proyecto:
   ```bash
   cd SIGV
   ./mvnw spring-boot:run
   ```
4. Abrir `http://localhost:8080`

### application.properties
Las credenciales por defecto:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventario_ventas
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

## Funcionalidades (Prototipo)

- ✅ Login / Registro con validación
- ✅ Roles: Administrador y Cliente
- ✅ Dashboard Administrador con sidebar, stats y acciones rápidas
- ✅ Dashboard Cliente con catálogo de productos
- ✅ Vista de perfil de usuario
- ✅ Tema verde corporativo profesional
- 🔄 Catálogo de productos con imágenes (placeholder)
- 🔄 Pedidos y facturación (en desarrollo)
