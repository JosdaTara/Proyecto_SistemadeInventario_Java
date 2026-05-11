# Diagrama de Clases

## Modelo MVC - Sistema de Inventario y Ventas

```
┌─────────────────────────────────────────────────────────────────┐
│                     DIAGRAMA DE CLASES                           │
│               Sistema de Inventario y Ventas                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌─────────────────────┐        ┌─────────────────────────┐     │
│  │        Rol          │        │        Usuario          │     │
│  ├─────────────────────┤        ├─────────────────────────┤     │
│  │ - idRol: int        │1      *│ - idUsuario: int        │     │
│  │ - nombre: String    │◄───────│ - nombre: String        │     │
│  │ - descripcion: Str  │        │ - email: String         │     │
│  └─────────────────────┘        │ - passwordHash: String  │     │
│                                  │ - activo: boolean      │     │
│  ┌─────────────────────┐        │ - fechaRegistro: Date   │     │
│  │      Categoria      │        ├─────────────────────────┤     │
│  ├─────────────────────┤        │ + login(): boolean      │     │
│  │ - idCategoria: int  │1       │ + getRoles(): String    │     │
│  │ - nombre: String    │        └─────────────────────────┘     │
│  │ - descripcion: Str  │                   ▲                    │
│  └──────────┬──────────┘                  │                     │
│             │                             │                     │
│             │                             │                     │
│             │1                    ┌───────┴────────┐            │
│             │                    │                │            │
│             │                    │                │            │
│  ┌──────────┴──────────┐  ┌─────┴──────┐  ┌──────┴──────┐    │
│  │      Producto       │  │ Administra │  │   Cliente   │    │
│  ├─────────────────────┤  │   -dor     │  │ (usuario)   │    │
│  │ - idProducto: int   │  ├────────────┤  ├─────────────┤    │
│  │ - nombre: String    │  │ + gestionar │  │ + realizar  │    │
│  │ - descripcion: Str  │  │ Usuarios()  │  │ Pedido()    │    │
│  │ - precio: double    │  │ + gestionar │  │ + consultar │    │
│  │ - stock: int        │  │ Productos() │  │ Pedidos()   │    │
│  │ - stockMinimo: int  │  │ + reportes()│  │ + consultar │    │
│  │ - activo: boolean   │  └────────────┘  │ Facturas()  │    │
│  │ - fechaCreacion:Date│                  └─────────────┘    │
│  ├─────────────────────┤                                      │
│  │ + actualizarStock() │                                      │
│  │ + disponible():bool │        ┌─────────────────────┐      │
│  └──────────┬──────────┘        │      Cliente        │      │
│             │                   ├─────────────────────┤      │
│             │                   │ - idCliente: int    │      │
│             │                   │ - nombre: String    │      │
│             │                   │ - direccion: String │      │
│             │                   │ - telefono: String  │      │
│             │                   │ - email: String     │      │
│             │                   │ - fechaRegistro:Date│      │
│             │                   └─────────────────────┘      │
│             │                            │                    │
│             │                            │1                   │
│             │                            │                    │
│             │                            ▼                    │
│             │                   ┌─────────────────────┐      │
│             │                   │       Pedido         │      │
│             │                   ├─────────────────────┤      │
│             │              *    │ - idPedido: int     │      │
│             └───────────────────│ - fechaPedido: Date │      │
│                  *              │ - estado: Enum      │      │
│                                 │ - total: double     │      │
│                                 ├─────────────────────┤      │
│                                 │ + calcularTotal()   │      │
│                                 │ + cambiarEstado()   │      │
│                                 └──────────┬──────────┘      │
│                                            │                  │
│                                            │1                 │
│                                            │                  │
│                                            ▼                  │
│  ┌─────────────────────┐        ┌─────────────────────┐      │
│  │   DetallePedido     │        │      Factura         │     │
│  ├─────────────────────┤        ├─────────────────────┤      │
│  │ - idDetalle: int    │        │ - idFactura: int    │      │
│  │ - cantidad: int     │        │ - fechaEmision: Date│     │
│  │ - precioUnit: double│        │ - total: double     │      │
│  │ - subtotal: double  │        ├─────────────────────┤      │
│  ├─────────────────────┤        │ + generarFactura()  │      │
│  │ + calcularSubtotal()│        └──────────┬──────────┘      │
│  └─────────────────────┘                   │                  │
│                                             │1                 │
│                                             │                  │
│                                             ▼                  │
│                                   ┌─────────────────────┐     │
│                                   │       Venta          │    │
│                                   ├─────────────────────┤     │
│                                   │ - idVenta: int      │     │
│                                   │ - fechaVenta: Date  │     │
│                                   │ - total: double     │     │
│                                   └─────────────────────┘     │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘

## Capa de Control (Controladores)

┌───────────────────────────────────────────────────────────────┐
│                    CONTROLADORES (MVC)                         │
├───────────────────────────────────────────────────────────────┤
│                                                                │
│  ┌────────────────────┐  ┌────────────────────┐               │
│  │ UsuarioController  │  │ ProductoController │               │
│  ├────────────────────┤  ├────────────────────┤               │
│  │ + login()          │  │ + listar()         │               │
│  │ + registrarUsuario()│  │ + guardar()        │               │
│  │ + listarUsuarios() │  │ + actualizar()     │               │
│  │ + actualizarUsuario│  │ + eliminar()       │               │
│  └────────────────────┘  │ + buscar()         │               │
│                           └────────────────────┘               │
│  ┌────────────────────┐  ┌────────────────────┐               │
│  │ PedidoController   │  │ FacturaController  │               │
│  ├────────────────────┤  ├────────────────────┤               │
│  │ + crearPedido()    │  │ + emitirFactura()  │               │
│  │ + listarPedidos()  │  │ + listarFacturas() │               │
│  │ + cambiarEstado()  │  │ + buscarPorFecha() │               │
│  │ + detallePedido()  │  └────────────────────┘               │
│  └────────────────────┘                                       │
│                                                                │
└───────────────────────────────────────────────────────────────┘

## Capa de Acceso a Datos (DAO)

┌───────────────────────────────────────────────────────────────┐
│                    DAOs (Data Access Object)                   │
├───────────────────────────────────────────────────────────────┤
│                                                                │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │ UsuarioDAO   │  │ ProductoDAO  │  │ PedidoDAO    │        │
│  ├──────────────┤  ├──────────────┤  ├──────────────┤        │
│  │ + findByEmail│  │ + findAll()  │  │ + save()     │        │
│  │ + save()     │  │ + findById() │  │ + findById() │        │
│  │ + update()   │  │ + save()     │  │ + findAll()  │        │
│  │ + delete()   │  │ + update()   │  │ + update()   │        │
│  └──────────────┘  │ + delete()   │  └──────────────┘        │
│                     └──────────────┘                          │
│  ┌──────────────┐  ┌──────────────┐                          │
│  │ ClienteDAO   │  │ FacturaDAO   │                          │
│  ├──────────────┤  ├──────────────┤                          │
│  │ + save()     │  │ + save()     │                          │
│  │ + findById() │  │ + findById() │                          │
│  │ + findAll()  │  │ + findAll()  │                          │
│  │ + update()   │  │ + findByDate │                          │
│  └──────────────┘  └──────────────┘                          │
│                                                                │
│  ┌─────────────────────────────────────────────────────────┐  │
│  │           DatabaseConnection (Singleton)                │  │
│  ├─────────────────────────────────────────────────────────┤  │
│  │ - connection: Connection                                │  │
│  │ + getConnection(): Connection                           │  │
│  │ + closeConnection(): void                               │  │
│  └─────────────────────────────────────────────────────────┘  │
│                                                                │
└───────────────────────────────────────────────────────────────┘
```

## Relaciones entre Clases

| Clase A | Relación | Clase B | Descripción |
|---------|----------|---------|-------------|
| Rol | 1 ──── * | Usuario | Un rol puede tener muchos usuarios |
| Categoria | 1 ──── * | Producto | Una categoría agrupa muchos productos |
| Producto | * ──── * | Pedido | A través de DetallePedido |
| Pedido | 1 ──── * | DetallePedido | Un pedido tiene muchos detalles |
| Producto | 1 ──── * | DetallePedido | Un producto aparece en muchos detalles |
| Cliente | 1 ──── * | Pedido | Un cliente puede realizar muchos pedidos |
| Pedido | 1 ──── 1 | Factura | Un pedido genera una factura |
| Factura | 1 ──── * | Venta | Una factura puede tener varios registros de venta |
| Usuario | 1 ──── * | Pedido | Un usuario gestiona muchos pedidos |
| Usuario | 1 ──── * | Factura | Un usuario emite muchas facturas |

## Patrón de Diseño

El sistema sigue el patrón **MVC (Modelo-Vista-Controlador)**:

- **Modelo**: Clases de entidad (Rol, Usuario, Producto, Pedido, etc.) + DAOs
- **Vista**: Interfaces gráficas (Swing/JavaFX)
- **Controlador**: Clases que manejan la lógica de negocio y comunicación entre vista y modelo
