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
│   │   │   │   ├── 📁 config/        # SecurityConfig, DataInitializer(@Order 1), ProductDataInitializer(@Order 2), CustomAuthenticationSuccessHandler
│   │   │   │   ├── 📁 controller/    # AuthController, ClienteController, ProductoController, PedidoAdminController, ClienteAdminController, FacturaAdminController, ReporteController, AdminUsuarioController
│   │   │   │   ├── 📁 model/         # Usuario, Rol, Producto, Pedido, DetallePedido, Factura
│   │   │   │   ├── 📁 repository/    # UsuarioRepository, RolRepository, ProductoRepository, PedidoRepository, DetallePedidoRepository, FacturaRepository
│   │   │   │   ├── 📁 service/       # UsuarioService, CustomUserDetailsService
│   │   │   │   └── SIstemaGestionInventarioApplication.java
│   │   │   └── 📁 resources/
│   │   │       ├── 📁 static/css/    # style.css (tema verde corporativo)
│   │   │       ├── 📁 templates/
│   │   │       │   ├── login.html    # Inicio de sesión
│   │   │       │   ├── registro.html # Registro de usuarios
│   │   │       │   ├── dashboard.html# Panel de administrador
│   │   │       │   ├── 📁 admin/     # Gestión admin
│   │   │       │   │   ├── pedidos.html
│   │   │       │   │   ├── pedido-detalle.html
│   │   │       │   │   ├── clientes.html
│   │   │       │   │   ├── cliente-detalle.html
│   │   │       │   │   ├── cliente-form.html
│   │   │       │   │   ├── facturas.html
│   │   │       │   │   ├── factura-detalle.html
│   │   │       │   │   ├── reportes.html
│   │   │       │   │   └── 📁 usuarios/
│   │   │       │   │       ├── listar.html
│   │   │       │   │       └── form.html
│   │   │       │   ├── 📁 productos/ # CRUD de productos
│   │   │       │   │   ├── listar.html
│   │   │       │   │   └── form.html
│   │   │       │   └── 📁 cliente/   # Panel de cliente
│   │   │       │       ├── dashboard.html
│   │   │       │       ├── productos.html
│   │   │       │       ├── carrito.html
│   │   │       │       ├── pedidos.html
│   │   │       │       ├── pedido-detalle.html
│   │   │       │       ├── factura.html
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
| **ADMINISTRADOR** | `/dashboard`, `/productos`, `/admin/pedidos`, `/admin/clientes`, `/admin/facturas`, `/admin/reportes`, `/admin/usuarios` | Gestión de inventario, productos, pedidos (cambio estado), clientes (CRUD), facturación, reportes y usuarios (CRUD) |
| **CLIENTE** | `/cliente/dashboard`, `/cliente/productos`, `/cliente/carrito`, `/cliente/pedidos`, `/cliente/perfil` | Catálogo de productos, carrito, pedidos, perfil y facturas |

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

## Funcionalidades

- ✅ Login / Registro con validación y BCrypt (registro siempre como Cliente)
- ✅ Redirección automática según rol (ADMIN → `/dashboard`, CLIENTE → `/cliente/dashboard`)
- ✅ Dashboard Administrador con stats dinámicos y acciones rápidas
- ✅ Dashboard Cliente con catálogo de productos destacados y stats de pedidos
- ✅ Vista de perfil de usuario con datos personales y fecha de registro
- ✅ Tema verde corporativo profesional (primario #166534, secundario #22C55E)
- ✅ CRUD completo de productos (Admin): crear, editar, eliminar, listar
- ✅ Gestión de usuarios (Admin): CRUD completo — listar, crear, editar y eliminar usuarios con rol seleccionable (Cliente o Administrador)
- ✅ Subida de imágenes para productos (formato JPG/PNG, máximo 5MB)
- ✅ Catálogo de productos con imágenes reales o placeholder y precios en pesos colombianos (COP)
- ✅ Carrito de compras en sesión (agregar, actualizar, eliminar)
- ✅ Checkout: pedido se crea directo como **ENVIADO** con descuento de stock automático
- ✅ Facturación automática al crear pedido, con vista imprimible (formato FAC-00001)
- ✅ Historial de pedidos del cliente con detalle y factura
- ✅ Listado de pedidos (Admin): ver todos los pedidos con detalle
- ✅ Gestión de clientes (Admin): CRUD completo — listar, ver detalle con pedidos, editar datos y eliminar clientes
- ✅ Listado de facturas (Admin): ver todas las facturas emitidas
- ✅ Reportes (Admin): estadísticas de pedidos por estado y control de stock bajo
- ✅ Seguridad por ruta según rol (Spring Security)
- ✅ Cache-busting CSS con timestamp, Thymeleaf cache desactivado
- ✅ Precios semilla con valores COP realistas ($4,500,000, $350,000, $85,000, etc.)
