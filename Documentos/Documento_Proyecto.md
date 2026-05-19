# Documento del Proyecto: Sistema de Inventario y Ventas

## 1. Objetivos del Proyecto

### Objetivo General
Desarrollar un sistema de información para la gestión de inventario, ventas y facturación que permita optimizar los procesos operativos de un negocio, facilitando el control de productos, pedidos y roles de usuario (cliente y administrador) mediante una aplicación web desarrollada con Spring Boot, Thymeleaf y MySQL.

### Objetivos Específicos
- Diseñar e implementar una base de datos relacional que almacene la información de productos, clientes, pedidos, facturas y usuarios del sistema.
- Desarrollar módulos funcionales para el registro y control de inventario de productos.
- Implementar un módulo de ventas que permita registrar pedidos y generar facturas automáticamente.
- Crear un sistema de autenticación y roles de usuario (administrador y cliente) para garantizar la seguridad y privacidad de la información.
- Diseñar una interfaz web responsiva con HTML, CSS, Bootstrap y Thymeleaf.
- Generar reportes básicos de ventas, inventario y facturación.

## 2. Justificación

En la actualidad, los negocios requieren herramientas tecnológicas que les permitan gestionar sus procesos de manera eficiente. El control manual de inventarios, ventas y facturación conlleva errores humanos, pérdida de tiempo y dificultad para tomar decisiones informadas.

La implementación de un sistema de inventario y ventas permitirá:
- **Automatizar procesos**: Reducción de errores en el registro de ventas y control de stock.
- **Mejorar la toma de decisiones**: Acceso a información actualizada sobre inventario y ventas.
- **Optimizar recursos**: Disminución del tiempo empleado en tareas administrativas.
- **Seguridad de la información**: Control de acceso mediante roles de usuario.
- **Escalabilidad**: Posibilidad de crecimiento y adaptación a futuras necesidades del negocio.

## 3. Entorno de Trabajo

### Backend
- **Java 17** - Lenguaje principal para la lógica del negocio
- **Spring Boot 5.11** - Framework principal para el desarrollo de la aplicación
- **Spring MVC** - Patrón de arquitectura para la capa web
- **Spring Data JPA / Hibernate** - ORM para el acceso a datos
- **Spring Security** - Autenticación y control de acceso por roles
- **Lombok** - Reducción de código boilerplate

### Frontend
- **HTML5** - Estructura de las vistas
- **CSS3 / Bootstrap 5** - Estilos y diseño responsive
- **JavaScript** - Interactividad en el cliente
- **Thymeleaf** - Motor de plantillas para las vistas del lado del servidor

### Entorno de Desarrollo
- **IDE**: Spring Tools Suite 4 (STS4) o VS Code con extensiones de Spring
- **JDK**: Java Development Kit 17 LTS
- **Maven** - Gestor de dependencias y construcción del proyecto

### Base de Datos
- **SGBD**: MySQL 8.0 o superior
- **MySQL Workbench** - Diseño del modelo entidad-relación (DER)

### Herramientas de Diseño
- **Lucidchart / draw.io** - Diagramas de casos de uso y clases

### Control de Versiones
- **Git** - Sistema de control de versiones
- **GitHub** - Plataforma de alojamiento del repositorio remoto

### Sistema Operativo
- Windows 11 / 10 (entorno de desarrollo)
- Multiplataforma (ejecución de la aplicación)

---

*Proyecto desarrollado para la asignatura de Programación en Java - UTS*
