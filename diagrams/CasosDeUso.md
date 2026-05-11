# Diagramas de Casos de Uso

## Actor: Administrador

```
┌─────────────────────────────────────────────────────────────┐
│                    SISTEMA DE INVENTARIO                     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────┐                                            │
│  │             │  ┌──────────────────┐   ┌──────────────┐  │
│  │  ADMINIS-   │──│ Gestionar Usuarios│──│ Gestionar    │  │
│  │  TRADOR     │  │ (CRUD)           │  │ Productos    │  │
│  │             │  └──────────────────┘   │ (CRUD)       │  │
│  └──────┬──────┘                         └──────────────┘  │
│         │                                                    │
│         │  ┌──────────────────┐   ┌──────────────────────┐  │
│         ├──│ Gestionar Pedidos│   │ Gestionar Facturas   │  │
│         │  │ (CRUD + Estados) │   │ (Consultar/Anular)   │  │
│         │  └──────────────────┘   └──────────────────────┘  │
│         │                                                    │
│         │  ┌──────────────────┐   ┌──────────────────────┐  │
│         ├──│ Gestionar        │   │ Generar Reportes     │  │
│         │  │ Clientes (CRUD)  │   │ (Ventas/Inventario)  │  │
│         │  └──────────────────┘   └──────────────────────┘  │
│         │                                                    │
│         │  ┌──────────────────┐                              │
│         └──│ Iniciar Sesión   │                              │
│            └──────────────────┘                              │
└─────────────────────────────────────────────────────────────┘
```

## Actor: Cliente

```
┌─────────────────────────────────────────────────────────────┐
│                    SISTEMA DE INVENTARIO                     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────┐                                            │
│  │             │  ┌──────────────────┐   ┌──────────────┐  │
│  │   CLIENTE   │──│ Consultar        │──│ Realizar      │  │
│  │             │  │ Catálogo         │   │ Pedido       │  │
│  └──────┬──────┘  │ Productos        │   └──────────────┘  │
│         │         └──────────────────┘                      │
│         │                                                    │
│         │  ┌──────────────────┐   ┌──────────────────────┐  │
│         ├──│ Consultar        │   │ Consultar Facturas   │  │
│         │  │ Mis Pedidos      │   │                      │  │
│         │  └──────────────────┘   └──────────────────────┘  │
│         │                                                    │
│         │  ┌──────────────────┐                              │
│         └──│ Iniciar Sesión   │                              │
│            └──────────────────┘                              │
└─────────────────────────────────────────────────────────────┘
```

## Descripción de Casos de Uso Principales

### CU-01: Iniciar Sesión
- **Actor**: Administrador, Cliente
- **Descripción**: El usuario ingresa sus credenciales (email y contraseña) para acceder al sistema.
- **Flujo Principal**:
  1. El usuario ingresa su email y contraseña
  2. El sistema valida las credenciales
  3. El sistema redirige según el rol del usuario
- **Flujo Alternativo**: Si las credenciales son incorrectas, se muestra un mensaje de error.

### CU-02: Gestionar Productos (CRUD)
- **Actor**: Administrador
- **Descripción**: El administrador puede crear, leer, actualizar y desactivar productos.
- **Flujo Principal**:
  1. El administrador accede al módulo de productos
  2. Puede registrar un nuevo producto con nombre, descripción, precio, stock y categoría
  3. Puede modificar los datos de un producto existente
  4. Puede desactivar un producto (ocultarlo del catálogo)

### CU-03: Realizar Pedido
- **Actor**: Cliente
- **Descripción**: El cliente selecciona productos del catálogo y crea un pedido.
- **Flujo Principal**:
  1. El cliente consulta el catálogo de productos disponibles
  2. Agrega productos al carrito de compras
  3. Confirma el pedido
  4. El sistema registra el pedido con estado "pendiente"

### CU-04: Gestionar Pedidos
- **Actor**: Administrador
- **Descripción**: El administrador gestiona los pedidos y cambia su estado.
- **Flujo Principal**:
  1. El administrador consulta la lista de pedidos
  2. Visualiza los detalles de un pedido
  3. Cambia el estado del pedido (confirmado, enviado, entregado, cancelado)

### CU-05: Generar Factura
- **Actor**: Sistema (Automático)
- **Descripción**: Al confirmar un pedido, el sistema genera automáticamente la factura correspondiente.
- **Flujo Principal**:
  1. El pedido cambia a estado "confirmado"
  2. El sistema calcula el total
  3. El sistema genera la factura con los datos del pedido

### CU-06: Generar Reportes
- **Actor**: Administrador
- **Descripción**: El administrador genera reportes de ventas e inventario.
- **Flujo Principal**:
  1. El administrador selecciona el tipo de reporte
  2. Define filtros (fechas, categorías, etc.)
  3. El sistema genera y muestra el reporte
