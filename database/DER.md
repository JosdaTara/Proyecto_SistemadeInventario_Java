# Diagrama Entidad-Relación (DER)

## Estructura de la Base de Datos: inventario_ventas

### Tablas y Relaciones

```
┌─────────────┐       ┌──────────────┐       ┌────────────────┐
│    roles    │       │   usuarios   │       │   clientes     │
├─────────────┤       ├──────────────┤       ├────────────────┤
│ PK id_rol   │◄──────│ FK id_rol    │       │ PK id_cliente  │
│ nombre      │       │ id_usuario PK│       │ nombre         │
│ descripcion │       │ nombre       │       │ direccion      │
└─────────────┘       │ email        │       │ telefono       │
                      │ password_hash│       │ email          │
                      │ activo       │       │ fecha_registro │
                      │ fecha_registro│      └────────────────┘
                      └──────────────┘               │
                              │                      │
                              │                      │
                     ┌────────┴────────┐            │
                     │                 │            │
                     ▼                 ▼            │
              ┌──────────────┐  ┌─────────────┐     │
              │   facturas   │  │   pedidos   │     │
              ├──────────────┤  ├─────────────┤◄────┤
              │ PK id_factura│  │ PK id_pedido│     │
              │ FK id_pedido │◄─┤ FK id_cliente─────┘
              │ FK id_usuario│  │ FK id_usuario│
              │ fecha_emision│  │ fecha_pedido │
              │ total        │  │ estado       │
              └──────┬───────┘  │ total        │
                     │          └──────┬───────┘
                     │                 │
                     │                 │
                     ▼                 ▼
              ┌──────────────┐  ┌──────────────────┐
              │    ventas    │  │ detalle_pedidos   │
              ├──────────────┤  ├──────────────────┤
              │ PK id_venta  │  │ PK id_detalle    │
              │ FK id_factura│  │ FK id_pedido     │
              │ fecha_venta  │  │ FK id_producto   │
              │ total        │  │ cantidad          │
              └──────────────┘  │ precio_unitario   │
                                │ subtotal (gen)    │
                                └────────┬──────────┘
                                         │
                                         ▼
                                ┌──────────────────┐
                                │    productos      │
                                ├──────────────────┤
                                │ PK id_producto   │
                                │ FK id_categoria  │
                                │ nombre           │
                                │ descripcion      │
                                │ precio           │
                                │ stock            │
                                │ stock_minimo     │
                                │ activo           │
                                │ fecha_creacion   │
                                └────────┬─────────┘
                                         │
                                         ▼
                                ┌──────────────────┐
                                │   categorias      │
                                ├──────────────────┤
                                │ PK id_categoria  │
                                │ nombre           │
                                │ descripcion      │
                                └──────────────────┘
```

### Leyenda
- PK = Primary Key (Clave Primaria)
- FK = Foreign Key (Clave Foránea)
- (gen) = Columna generada automáticamente

### Relaciones
1. **roles** 1 ──── * **usuarios** (Un rol puede tener muchos usuarios)
2. **usuarios** 1 ──── * **pedidos** (Un usuario puede gestionar muchos pedidos)
3. **usuarios** 1 ──── * **facturas** (Un usuario puede emitir muchas facturas)
4. **clientes** 1 ──── * **pedidos** (Un cliente puede tener muchos pedidos)
5. **categorias** 1 ──── * **productos** (Una categoría puede tener muchos productos)
6. **productos** 1 ──── * **detalle_pedidos** (Un producto puede estar en muchos detalles)
7. **pedidos** 1 ──── * **detalle_pedidos** (Un pedido tiene muchos detalles)
8. **pedidos** 1 ──── 1 **facturas** (Un pedido genera una factura)
9. **facturas** 1 ──── * **ventas** (Una factura puede tener muchas ventas registradas)
