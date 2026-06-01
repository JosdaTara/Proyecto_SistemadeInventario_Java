# Diagrama de Clases

> Los diagramas están renderizados con [Mermaid](https://mermaid.js.org/). Se visualizan automáticamente en GitHub y en editores compatibles.

## Modelo del Dominio (Entidades JPA)

```mermaid
classDiagram
    class Usuario {
        - Integer idUsuario
        - String nombre
        - String email
        - String passwordHash
        - Boolean activo
        - Date fechaRegistro
        + getAuthorities() Collection
    }

    class Rol {
        - Integer idRol
        - String nombre
        - String descripcion
    }

    class Producto {
        - Integer idProducto
        - String nombre
        - String descripcion
        - BigDecimal precio
        - Integer stock
        - String categoria
        - Boolean activo
        - String imagenUrl
    }

    class Pedido {
        - Integer idPedido
        - Date fechaPedido
        - String estado
        - BigDecimal total
    }

    class DetallePedido {
        - Integer idDetalle
        - Integer cantidad
        - BigDecimal precioUnitario
        - BigDecimal subtotal
    }

    class Factura {
        - Integer idFactura
        - String numeroFactura
        - Date fechaEmision
        - BigDecimal total
        - String metodoPago
    }

    Usuario "1" --> "*" Rol : tiene
    Usuario "1" --> "*" Pedido : realiza
    Pedido "1" --> "*" DetallePedido : contiene
    Producto "1" --> "*" DetallePedido : aparece en
    Pedido "1" --> "1" Factura : genera
```

## Capa de Control (Controladores Spring MVC)

```mermaid
classDiagram
    class AuthController {
        + login() String
        + registroForm() String
        + registrar() String
        + dashboard() String
    }

    class ProductoController {
        + listar() String
        + nuevoForm() String
        + guardar() String
        + editarForm() String
        + eliminar() String
    }

    class ClienteController {
        + dashboard() String
        + productos() String
        + verCarrito() String
        + agregarCarrito() String
        + actualizarCarrito() String
        + eliminarCarrito() String
        + checkout() String
        + pedidos() String
        + verPedido() String
        + verFactura() String
        + perfil() String
    }

    class PedidoAdminController {
        + listar() String
        + detalle() String
        + actualizarEstado() String
    }

    AuthController --> UsuarioService
    AuthController --> ProductoRepository
    AuthController --> UsuarioRepository
    AuthController --> PedidoRepository
    ProductoController --> ProductoRepository
    ClienteController --> UsuarioRepository
    ClienteController --> ProductoRepository
    ClienteController --> PedidoRepository
    ClienteController --> FacturaRepository
    PedidoAdminController --> PedidoRepository
```

## Capa de Servicios

```mermaid
classDiagram
    class UsuarioService {
        + registrarUsuario() void
        + buscarPorEmail() Usuario
    }

    class CustomUserDetailsService {
        + loadUserByUsername() UserDetails
    }

    CustomUserDetailsService --> UsuarioRepository
    UsuarioService --> UsuarioRepository
    UsuarioService --> RolRepository
    UsuarioService --> BCryptPasswordEncoder
```

## Capa de Acceso a Datos (Repositorios)

```mermaid
classDiagram
    class UsuarioRepository {
        <<JpaRepository>>
        + findByEmail() Optional~Usuario~
        + existsByEmail() boolean
        + countByRolesNombre() long
    }

    class RolRepository {
        <<JpaRepository>>
        + findByNombre() Optional~Rol~
    }

    class ProductoRepository {
        <<JpaRepository>>
        + findByActivoTrue() List~Producto~
        + findByCategoriaAndActivoTrue() List~Producto~
        + findAllByOrderByNombreAsc() List~Producto~
    }

    class PedidoRepository {
        <<JpaRepository>>
        + findByUsuarioOrderByFechaPedidoDesc() List~Pedido~
        + findAllByOrderByFechaPedidoDesc() List~Pedido~
    }

    class FacturaRepository {
        <<JpaRepository>>
        + findByPedido() Optional~Factura~
        + existsByPedido() boolean
    }

    class DetallePedidoRepository {
        <<JpaRepository>>
    }
```

## Vistas (Thymeleaf)

```
├── login.html              # Inicio de sesión
├── registro.html           # Registro de usuarios
├── dashboard.html          # Dashboard administrador
├── admin/
│   ├── pedidos.html        # Lista de pedidos (admin)
│   └── pedido-detalle.html # Detalle + cambio de estado
├── productos/
│   ├── listar.html         # CRUD productos
│   └── form.html           # Crear/editar producto
├── cliente/
│   ├── dashboard.html      # Dashboard cliente
│   ├── productos.html      # Catálogo de productos
│   ├── carrito.html        # Carrito de compras
│   ├── pedidos.html        # Mis pedidos
│   ├── pedido-detalle.html # Detalle del pedido
│   ├── factura.html        # Factura imprimible
│   └── perfil.html         # Perfil de usuario
```

## Relaciones entre Entidades

| Entidad A | Relación | Entidad B | Tipo |
|-----------|----------|-----------|------|
| Usuario | 1 ― * | Rol | ManyToMany |
| Usuario | 1 ― * | Pedido | OneToMany |
| Pedido | 1 ― * | DetallePedido | OneToMany (cascada) |
| Producto | 1 ― * | DetallePedido | OneToMany |
| Pedido | 1 ― 1 | Factura | OneToOne |

## Patrón Arquitectónico

**Spring MVC (Modelo-Vista-Controlador)** con capas adicionales:

- **Modelo**: Entidades JPA (`@Entity`) en `model/`
- **Repositorio**: Interfaces Spring Data JPA en `repository/`
- **Servicio**: Lógica de negocio (`@Service`) en `service/`
- **Controlador**: Manejadores de peticiones (`@Controller`) en `controller/`
- **Vista**: Plantillas Thymeleaf en `templates/`
