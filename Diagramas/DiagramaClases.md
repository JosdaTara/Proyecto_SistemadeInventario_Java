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

### Inicio de Sesion

```mermaid
sequenceDiagram
    actor U as Usuario
    participant C as Controlador
    participant S as Spring Security
    participant DB as Base de Datos

    U->>C: POST /login (email + password)
    C->>S: Autenticar credenciales
    S->>DB: Buscar usuario por email
    DB-->>S: Usuario + roles
    S->>S: Verificar BCrypt
    alt Credenciales validas
        S-->>U: Redirect segun rol<br/>(/dashboard o /cliente/dashboard)
    else Credenciales invalidas
        S-->>U: Redirect /login?error
    end
```

### Compra (Carrito + Checkout)

```mermaid
sequenceDiagram
    actor C as Cliente
    participant Ctrl as ClienteController
    participant Sess as Sesion HTTP
    participant DB as Base de Datos

    C->>Ctrl: Agregar al carrito
    Ctrl->>DB: Validar producto y stock
    DB-->>Ctrl: Producto OK
    Ctrl->>Sess: Guardar en carrito
    Ctrl-->>C: Redirect a catalogo

    C->>Ctrl: POST /checkout
    Ctrl->>Sess: Obtener items del carrito
    Sess-->>Ctrl: Lista de items
    Ctrl->>DB: Validar stock de cada item
    Ctrl->>DB: Crear Pedido (PENDIENTE)
    Ctrl->>DB: Crear DetallePedido
    Ctrl->>DB: Descontar stock
    Ctrl->>DB: Generar Factura (FAC-XXXXX)
    Ctrl->>Sess: Vaciar carrito
    Ctrl-->>C: Redirect a /cliente/pedidos
```

### Gestion de Productos (Admin)

```mermaid
sequenceDiagram
    actor A as Administrador
    participant C as ProductoController
    participant DB as Base de Datos

    A->>C: GET /productos
    C->>DB: SELECT * FROM productos
    DB-->>C: Lista de productos
    C-->>A: Vista Thymeleaf (listar.html)

    A->>C: GET /productos/nuevo
    C-->>A: Vista formulario (form.html)

    A->>C: POST /productos/guardar
    C->>DB: INSERT/UPDATE producto
    DB-->>C: Producto guardado
    C-->>A: Redirect a /productos + mensaje
```

### Ciclo de Vida del Pedido (Admin)

```mermaid
stateDiagram-v2
    [*] --> PENDIENTE : Cliente realiza compra
    PENDIENTE --> CONFIRMADO : Admin confirma
    CONFIRMADO --> ENVIADO : Admin despacha
    ENVIADO --> ENTREGADO : Cliente recibe
    PENDIENTE --> CANCELADO : Admin cancela
    CONFIRMADO --> CANCELADO : Admin cancela
    ENVIADO --> CANCELADO : Admin cancela
    ENTREGADO --> [*]
    CANCELADO --> [*]
```
