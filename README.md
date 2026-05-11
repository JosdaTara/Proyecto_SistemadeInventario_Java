# Proyecto Sistema de Inventario y Ventas - Java

## Descripción
Sistema de información para la gestión de inventario, ventas y facturación desarrollado en Java con MySQL.

## Estructura del Proyecto

```
PROYECTO/
├── docs/
│   ├── Documento_Proyecto.md     # Objetivos, justificación y entorno
│   └── Requerimientos.md         # Requerimientos funcionales y no funcionales
├── database/
│   ├── script_bd.sql             # Script de creación BD + datos iniciales
│   └── DER.md                    # Descripción del Diagrama Entidad-Relación
├── diagrams/
│   ├── CasosDeUso.md             # Diagramas de casos de uso
│   └── DiagramaClases.md         # Diagrama de clases UML
└── README.md
```

## Tecnologías
- Java SE 17+
- MySQL 8.0
- JDBC
- Patrón MVC
- Singleton (Conexión BD)
- DAO (Data Access Object)

## Configuración
1. Ejecutar `database/script_bd.sql` en MySQL
2. Configurar credenciales en `DatabaseConnection.java`
3. Compilar y ejecutar la aplicación Java
