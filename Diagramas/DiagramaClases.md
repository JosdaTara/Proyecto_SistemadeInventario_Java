# Diagrama de Clases

## Modelo de Datos (Entidades JPA)

```mermaid
classDiagram
    class Usuario {
        +Integer idUsuario
        +String nombre
        +String email
        +String passwordHash
        +Boolean activo
        +Date fechaRegistro
        +getAuthorities() Collection
        +getNombre() String
    }

    class Rol {
        +Integer idRol
        +String nombre
        +String descripcion
    }

    class Producto {
        +Integer idProducto
        +String nombre
        +String descripcion
        +BigDecimal precio
        +Integer stock
        +String categoria
        +Boolean activo
        +String imagenUrl
    }

    class Pedido {
        +Integer idPedido
        +Date fechaPedido
        +String estado
        +BigDecimal total
    }

    class DetallePedido {
        +Integer idDetalle
        +Integer cantidad
        +BigDecimal precioUnitario
        +BigDecimal subtotal
    }

    class Factura {
        +Integer idFactura
        +String numeroFactura
        +Date fechaEmision
        +BigDecimal total
        +String metodoPago
    }

    Usuario --> Rol : "N"
    Usuario --> Pedido : "1"
    Pedido --> DetallePedido : "N"
    Producto --> DetallePedido : "N"
    Pedido --> Factura : "1"
```

## Capas de la Aplicacion

### Controladores

```mermaid
classDiagram
    class AuthController {
        +login() String
        +registroForm() String
        +registrar() String
        +dashboard() String
    }

    class ProductoController {
        +listar() String
        +nuevoForm() String
        +guardar() String
        +editarForm() String
        +eliminar() String
    }

    class ClienteController {
        +dashboard() String
        +productos() String
        +verCarrito() String
        +agregarCarrito() String
        +actualizarCarrito() String
        +eliminarCarrito() String
        +checkout() String
        +pedidos() String
        +verPedido() String
        +verFactura() String
        +perfil() String
    }

    class PedidoAdminController {
        +listar() String
        +detalle() String
        +actualizarEstado() String
    }
```

### Servicios y Repositorios

```mermaid
classDiagram
    class UsuarioService {
        +registrarUsuario() void
        +buscarPorEmail() Usuario
    }

    class CustomUserDetailsService {
        +loadUserByUsername() UserDetails
    }

    class UsuarioRepository {
        +findByEmail() Usuario
        +existsByEmail() boolean
        +countByRolesNombre() long
    }

    class ProductoRepository {
        +findByActivoTrue() List
        +findByCategoriaAndActivoTrue() List
        +findAllByOrderByNombreAsc() List
    }

    class PedidoRepository {
        +findByUsuarioOrderByFechaPedidoDesc() List
        +findAllByOrderByFechaPedidoDesc() List
    }

    class FacturaRepository {
        +findByPedido() Factura
        +existsByPedido() boolean
    }

    CustomUserDetailsService --> UsuarioRepository
    UsuarioService --> UsuarioRepository
    UsuarioService --> RolRepository
```

## Relaciones entre Entidades

| Tabla | Relacion | Tabla | Tipo |
|-------|----------|-------|------|
| usuarios | M ― M | roles | `usuario_roles` |
| usuarios | 1 ― M | pedidos | Un cliente tiene muchos pedidos |
| pedidos | 1 ― M | detalle_pedidos | Un pedido contiene varios items |
| productos | 1 ― M | detalle_pedidos | Un producto aparece en varios detalles |
| pedidos | 1 ― 1 | facturas | Cada pedido genera una factura |

## Flujo de la Aplicacion

```
   Usuario                    Servidor                  Base de Datos
     │                          │                          │
     ├── Login ────────────────►│                          │
     │                          ├── Buscar email ────────►│
     │                          │◄── Usuario + roles ─────┤
     │                          ├── Verificar BCrypt       │
     │◄── Redirect segun rol ──┤                          │
     │                          │                          │
     ├── CRUD Productos ───────►│                          │
     │                          ├── SELECT/INSERT/UPDATE ─►│
     │◄── Vista Thymeleaf ─────┤                          │
     │                          │                          │
     ├── Carrito / Checkout ───►│                          │
     │                          ├── Validar stock ────────►│
     │                          ├── Crear Pedido ─────────►│
     │                          ├── Descontar stock ──────►│
     │                          ├── Generar Factura ──────►│
     │◄── Confirmacion ────────┤                          │
```
