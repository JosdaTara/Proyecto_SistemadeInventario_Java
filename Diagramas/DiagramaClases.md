# Diagrama de Clases

## Modelo MVC - Sistema de Inventario y Ventas

```

                    DIAGRAMA DE CLASES
              Sistema de Inventario y Ventas


  ┌─────────────────────┐        ┌─────────────────────────┐
  │        Rol          │        │        Usuario          │
  ├─────────────────────┤        ├─────────────────────────┤
  │ - idRol: int        │1      *│ - idUsuario: int        │
  │ - nombre: String    │◄───────│ - nombre: String        │
  │ - descripcion: Str  │        │ - email: String         │
  └─────────────────────┘        │ - passwordHash: String  │
                                  │ - activo: boolean      │
  ┌─────────────────────┐        │ - fechaRegistro: Date   │
  │      Categoria      │        ├─────────────────────────┤
  ├─────────────────────┤        │ + login(): boolean      │
  │ - idCategoria: int  │1       │ + getRoles(): String    │
  │ - nombre: String    │        └─────────────────────────┘
  │ - descripcion: Str  │                   ▲
  └──────────┬──────────┘                  │
             │                             │
             │                             │
             │1                    ┌───────┴────────┐
             │                    │                │
             │                    │                │
  ┌──────────┴──────────┐  ┌─────┴──────┐  ┌──────┴──────┐
  │      Producto       │  │ Administra │  │   Cliente   │
  ├─────────────────────┤  │   -dor     │  │ (usuario)   │
  │ - idProducto: int   │  ├────────────┤  ├─────────────┤
  │ - nombre: String    │  │ + gestionar │  │ + realizar  │
  │ - descripcion: Str  │  │ Usuarios()  │  │ Pedido()    │
  │ - precio: double    │  │ + gestionar │  │ + consultar │
  │ - stock: int        │  │ Productos() │  │ Pedidos()   │
  │ - stockMinimo: int  │  │ + reportes()│  │ + consultar │
  │ - activo: boolean   │  └────────────┘  │ Facturas()  │
  │ - fechaCreacion:Date│                  └─────────────┘
  ├─────────────────────┤
  │ + actualizarStock() │
  │ + disponible():bool │        ┌─────────────────────┐
  └──────────┬──────────┘        │      Cliente        │
             │                   ├─────────────────────┤
             │                   │ - idCliente: int    │
             │                   │ - nombre: String    │
             │                   │ - direccion: String │
             │                   │ - telefono: String  │
             │                   │ - email: String     │
             │                   │ - fechaRegistro:Date│
             │                   └─────────────────────┘
             │                            │
             │                            │1
             │                            │
             │                            ▼
             │                   ┌─────────────────────┐
             │                   │       Pedido         │
             │                   ├─────────────────────┤
             │              *    │ - idPedido: int     │
             └───────────────────│ - fechaPedido: Date │
                  *              │ - estado: Enum      │
                                 │ - total: double     │
                                 ├─────────────────────┤
                                 │ + calcularTotal()   │
                                 │ + cambiarEstado()   │
                                 └──────────┬──────────┘
                                            │
                                            │1
                                            │
                                            ▼
  ┌─────────────────────┐        ┌─────────────────────┐
  │   DetallePedido     │        │      Factura         │
  ├─────────────────────┤        ├─────────────────────┤
  │ - idDetalle: int    │        │ - idFactura: int    │
  │ - cantidad: int     │        │ - fechaEmision: Date│
  │ - precioUnit: double│        │ - total: double     │
  │ - subtotal: double  │        ├─────────────────────┤
  ├─────────────────────┤        │ + generarFactura()  │
  │ + calcularSubtotal()│        └──────────┬──────────┘
  └─────────────────────┘                   │
                                             │1
                                             │
                                             ▼
                                   ┌─────────────────────┐
                                   │       Venta          │
                                   ├─────────────────────┤
                                   │ - idVenta: int      │
                                   │ - fechaVenta: Date  │
                                   │ - total: double     │
                                   └─────────────────────┘
```

## Capa de Presentación (Vistas - Thymeleaf + HTML/CSS/JS)

```
                    VISTAS (Thymeleaf)

  ┌──────────────────────┐  ┌──────────────────────┐
  │   login.html         │  │   dashboard.html     │
  │   (Autenticación)    │  │   (Panel principal)  │
  └──────────────────────┘  └──────────────────────┘

  ┌──────────────────────┐  ┌──────────────────────┐
  │   productos/         │  │   pedidos/           │
  │   listar.html        │  │   listar.html        │
  │   form.html          │  │   detalle.html       │
  └──────────────────────┘  └──────────────────────┘

  ┌──────────────────────┐  ┌──────────────────────┐
  │   clientes/          │  │   facturas/          │
  │   listar.html        │  │   listar.html        │
  │   form.html          │  │   detalle.html       │
  └──────────────────────┘  └──────────────────────┘

  Recursos estáticos: /css/, /js/, /images/
```

## Capa de Control (Controladores Spring MVC)

```
               CONTROLADORES (Spring MVC)

  ┌─────────────────────────┐  ┌─────────────────────────┐
  │ @Controller             │  │ @Controller             │
  │ AuthController          │  │ ProductoController      │
  ├─────────────────────────┤  ├─────────────────────────┤
  │ + login()               │  │ + listar()              │
  │ + procesarLogin()       │  │ + guardar()             │
  │ + registro()            │  │ + editar()              │
  │ + logout()              │  │ + eliminar()            │
  └─────────────────────────┘  └─────────────────────────┘

  ┌─────────────────────────┐  ┌─────────────────────────┐
  │ @Controller             │  │ @Controller             │
  │ PedidoController        │  │ FacturaController       │
  ├─────────────────────────┤  ├─────────────────────────┤
  │ + crear()               │  │ + listar()              │
  │ + listar()              │  │ + detalle()             │
  │ + cambiarEstado()       │  │ + generarPDF()          │
  │ + detalle()             │  └─────────────────────────┘
  └─────────────────────────┘
  ┌─────────────────────────┐
  │ @RestController         │
  │ ProductoRestController  │
  ├─────────────────────────┤
  │ + buscarPorCategoria()  │
  │ + obtenerStock()        │
  └─────────────────────────┘
```

## Capa de Servicios (Spring Service)

```
                    SERVICIOS (@Service)

  ┌──────────────────────┐  ┌──────────────────────┐
  │ @Service             │  │ @Service             │
  │ UsuarioService       │  │ ProductoService      │
  ├──────────────────────┤  ├──────────────────────┤
  │ + autenticar()       │  │ + listarTodos()      │
  │ + registrar()        │  │ + guardar()          │
  │ + listar()           │  │ + actualizar()       │
  │ + desactivar()       │  │ + eliminar()         │
  └──────────────────────┘  │ + buscarPorCategoria()│
                             └──────────────────────┘
  ┌──────────────────────┐  ┌──────────────────────┐
  │ @Service             │  │ @Service             │
  │ PedidoService        │  │ FacturaService       │
  ├──────────────────────┤  ├──────────────────────┤
  │ + crear()            │  │ + emitir()           │
  │ + listar()           │  │ + listar()           │
  │ + cambiarEstado()    │  │ + buscarPorFecha()   │
  │ + calcularTotal()    │  │ + generarPDF()       │
  └──────────────────────┘  └──────────────────────┘
```

## Capa de Acceso a Datos (Spring Data JPA)

```
               REPOSITORIOS (Spring Data JPA)

  ┌──────────────────┐  ┌──────────────────┐
  │ @Repository      │  │ @Repository      │
  │ UsuarioRepo      │  │ ProductoRepo     │
  ├──────────────────┤  ├──────────────────┤
  │ extends          │  │ extends          │
  │ JpaRepository    │  │ JpaRepository    │
  │ + findByEmail()  │  │ + findByCategoria│
  └──────────────────┘  │ + findByStockLess│
                         └──────────────────┘
  ┌──────────────────┐  ┌──────────────────┐
  │ @Repository      │  │ @Repository      │
  │ PedidoRepo       │  │ FacturaRepo      │
  ├──────────────────┤  ├──────────────────┤
  │ extends          │  │ extends          │
  │ JpaRepository    │  │ JpaRepository    │
  │ + findByCliente()│  │ + findByFecha()  │
  │ + findByEstado() │  └──────────────────┘
  └──────────────────┘
```

## Relaciones entre Clases

| Clase A | Relación | Clase B | Descripción |
|---------|----------|---------|-------------|
| Rol | 1 --- * | Usuario | Un rol puede tener muchos usuarios |
| Categoria | 1 --- * | Producto | Una categoría agrupa muchos productos |
| Producto | * --- * | Pedido | A través de DetallePedido |
| Pedido | 1 --- * | DetallePedido | Un pedido tiene muchos detalles |
| Producto | 1 --- * | DetallePedido | Un producto aparece en muchos detalles |
| Cliente | 1 --- * | Pedido | Un cliente puede realizar muchos pedidos |
| Pedido | 1 --- 1 | Factura | Un pedido genera una factura |
| Factura | 1 --- * | Venta | Una factura puede tener varios registros de venta |
| Usuario | 1 --- * | Pedido | Un usuario gestiona muchos pedidos |
| Usuario | 1 --- * | Factura | Un usuario emite muchas facturas |

## Patrón de Diseño

El sistema sigue el patrón **Spring MVC (Modelo-Vista-Controlador)**:

- **Modelo**: Clases de entidad (Rol, Usuario, Producto, Pedido, etc.) + Repositorios JPA + Servicios
- **Vista**: Plantillas Thymeleaf (HTML5 + CSS3 + JavaScript)
- **Controlador**: Controladores anotados con `@Controller` que manejan peticiones HTTP
