# Proyecto Sistema de Inventario y Ventas

## Descripción
Sistema de información para la gestión de inventario, ventas y facturación. Aplicación web desarrollada con Spring Boot, Thymeleaf, HTML/CSS/JS y MySQL.

## Estructura del Proyecto

```
PROYECTO/
├── 📁 Base de Datos/
│   ├── script_bd.sql             # Script de creación BD + datos iniciales
│   └── DER.md                    # Descripción del Diagrama Entidad-Relación
├── 📁 Diagramas/
│   ├── CasosDeUso.md             # Diagramas de casos de uso
│   └── DiagramaClases.md         # Diagrama de clases UML
├── 📁 Documentos/
│   ├── Documento_Proyecto.docx   # Documento oficial (Word)
│   ├── Documento_Proyecto.md     # Objetivos, justificación y entorno
│   └── Requerimientos.md         # Requerimientos funcionales y no funcionales
└── README.md
```

## Tecnologías

### Backend
- Java 17
- Spring Boot 3.x
- Spring MVC
- Spring Data JPA / Hibernate
- Spring Security
- Maven

### Frontend
- HTML5, CSS3, Bootstrap 5
- JavaScript
- Thymeleaf

### Base de Datos
- MySQL 8.0

## Configuración
1. Ejecutar `Base de Datos/script_bd.sql` en MySQL
2. Configurar `application.properties` con credenciales de BD
3. Ejecutar con Maven: `mvn spring-boot:run`
