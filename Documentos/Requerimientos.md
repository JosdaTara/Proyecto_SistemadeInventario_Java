# Requerimientos del Sistema de Inventario y Ventas

## Requerimientos Funcionales

### Módulo de Autenticación y Roles
| Código | Descripción |
|--------|-------------|
| RF-01 | El sistema debe permitir el inicio de sesión de usuarios registrados mediante correo electrónico y contraseña. |
| RF-02 | El sistema debe tener dos roles de usuario: Administrador y Cliente. |
| RF-03 | El sistema debe permitir al administrador gestionar (crear, leer, actualizar, desactivar) usuarios del sistema. |
| RF-04 | El sistema debe redirigir a las funcionalidades correspondientes según el rol del usuario autenticado. |

### Módulo de Productos e Inventario
| Código | Descripción |
|--------|-------------|
| RF-05 | El sistema debe permitir al administrador registrar nuevos productos con nombre, descripción, precio, stock y categoría. |
| RF-06 | El sistema debe permitir al administrador actualizar los datos de un producto existente. |
| RF-07 | El sistema debe permitir al administrador eliminar o desactivar productos. |
| RF-08 | El sistema debe mostrar el listado de productos disponibles con su stock actual. |
| RF-09 | El sistema debe permitir categorizar los productos para facilitar su búsqueda. |
| RF-10 | El sistema debe alertar cuando el stock de un producto esté por debajo del mínimo establecido. |

### Módulo de Clientes
| Código | Descripción |
|--------|-------------|
| RF-11 | El sistema debe permitir el registro de nuevos clientes con nombre, dirección, teléfono y correo electrónico. |
| RF-12 | El sistema debe permitir al administrador consultar el listado de clientes registrados. |
| RF-13 | El sistema debe permitir actualizar los datos de un cliente existente. |

### Módulo de Pedidos
| Código | Descripción |
|--------|-------------|
| RF-14 | El sistema debe permitir al cliente crear un nuevo pedido seleccionando productos del catálogo. |
| RF-15 | El sistema debe permitir al cliente consultar el historial de sus pedidos. |
| RF-16 | El sistema debe permitir al administrador consultar todos los pedidos del sistema. |
| RF-17 | El sistema debe actualizar el stock automáticamente al confirmar un pedido. |
| RF-18 | El sistema debe permitir cambiar el estado de un pedido (pendiente, confirmado, enviado, entregado, cancelado). |

### Módulo de Facturación
| Código | Descripción |
|--------|-------------|
| RF-19 | El sistema debe generar automáticamente una factura al confirmar un pedido. |
| RF-20 | El sistema debe permitir al administrador consultar el listado de facturas emitidas. |
| RF-21 | El sistema debe permitir al cliente consultar sus facturas. |
| RF-22 | El sistema debe calcular el total de la factura sumando los subtotales de los productos. |

### Módulo de Reportes
| Código | Descripción |
|--------|-------------|
| RF-23 | El sistema debe generar un reporte de ventas por rango de fechas. |
| RF-24 | El sistema debe generar un reporte del inventario actual. |
| RF-25 | El sistema debe generar un reporte de los pedidos por estado. |

## Requerimientos No Funcionales

| Código | Descripción |
|--------|-------------|
| RNF-01 | **Rendimiento**: El sistema debe responder a las peticiones del usuario en menos de 3 segundos en operaciones estándar. |
| RNF-02 | **Seguridad**: Las contraseñas de los usuarios deben almacenarse encriptadas (hash + salt). |
| RNF-03 | **Usabilidad**: La interfaz debe ser intuitiva y fácil de usar, con mensajes de error claros y ayuda contextual. |
| RNF-04 | **Confiabilidad**: El sistema debe mantener la integridad de los datos mediante transacciones ACID en la base de datos. |
| RNF-05 | **Disponibilidad**: El sistema debe estar disponible durante el horario laboral del negocio (8:00 - 18:00). |
| RNF-06 | **Portabilidad**: La aplicación debe poder ejecutarse en cualquier sistema operativo que soporte Java (Windows, Linux, macOS). |
| RNF-07 | **Mantenibilidad**: El código debe seguir el patrón MVC (Modelo-Vista-Controlador) para facilitar su mantenimiento. |
| RNF-08 | **Escalabilidad**: La base de datos debe diseñarse para soportar al menos 10,000 productos y 50,000 pedidos sin degradación del rendimiento. |
| RNF-09 | **Integridad**: La base de datos debe garantizar la integridad referencial mediante claves foráneas y restricciones. |
| RNF-10 | **Documentación**: El código fuente debe estar documentado siguiendo el estándar Javadoc. |
