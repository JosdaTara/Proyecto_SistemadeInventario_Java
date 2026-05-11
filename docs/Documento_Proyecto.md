# Documento del Proyecto: Sistema de Inventario y Ventas

## 1. Objetivos del Proyecto

### Objetivo General
Desarrollar un sistema de información para la gestión de inventario, ventas y facturación que permita optimizar los procesos operativos de un negocio, facilitando el control de productos, pedidos y roles de usuario (cliente y administrador) mediante una aplicación desarrollada en Java con base de datos relacional.

### Objetivos Específicos
- Diseñar e implementar una base de datos relacional que almacene la información de productos, clientes, pedidos, facturas y usuarios del sistema.
- Desarrollar módulos funcionales para el registro y control de inventario de productos.
- Implementar un módulo de ventas que permita registrar pedidos y generar facturas automáticamente.
- Crear un sistema de autenticación y roles de usuario (administrador y cliente) para garantizar la seguridad y privacidad de la información.
- Diseñar una interfaz gráfica intuitiva que facilite la interacción del usuario con el sistema.
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

### Lenguaje de Programación
- **Java SE** (versión 17 o superior) - Lenguaje principal para la lógica del negocio y la interfaz de usuario.

### Entorno de Desarrollo
- **IDE**: Apache NetBeans o IntelliJ IDEA Community Edition
- **JDK**: Java Development Kit 17 LTS

### Base de Datos
- **SGBD**: MySQL 8.0 o superior
- **Conector JDBC**: mysql-connector-java 8.x

### Herramientas de Diseño
- **MySQL Workbench** - Para el diseño del modelo entidad-relación (DER)
- **Lucidchart / draw.io** - Para los diagramas de casos de uso y clases

### Control de Versiones
- **Git** - Sistema de control de versiones
- **GitHub** - Plataforma de alojamiento del repositorio remoto

### Sistema Operativo
- Windows 11 / 10 (entorno de desarrollo)
- Multiplataforma (ejecución de la aplicación Java)

### Librerías y Dependencias
- MySQL Connector/J (JDBC)
- JCalendar (para selección de fechas)
- iTextPDF (generación de reportes en PDF, opcional)

---

*Proyecto desarrollado para la asignatura de Programación en Java - UTS*
