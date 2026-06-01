# Diagrama de Clases

## Entidades del Modelo

```mermaid
classDiagram
    class Usuario {
        Integer idUsuario
        String nombre
        String email
        String passwordHash
        Boolean activo
        Date fechaRegistro
    }

    class Rol {
        Integer idRol
        String nombre
        String descripcion
    }

    class Producto {
        Integer idProducto
        String nombre
        String descripcion
        BigDecimal precio
        Integer stock
        String categoria
        Boolean activo
        String imagenUrl
    }

    class Pedido {
        Integer idPedido
        Date fechaPedido
        String estado
        BigDecimal total
    }

    class DetallePedido {
        Integer idDetalle
        Integer cantidad
        BigDecimal precioUnitario
        BigDecimal subtotal
    }

    class Factura {
        Integer idFactura
        String numeroFactura
        Date fechaEmision
        BigDecimal total
        String metodoPago
    }

    Usuario "*" --> "*" Rol : tiene
    Usuario "1" --> "*" Pedido : realiza
    Pedido "1" --> "*" DetallePedido : contiene
    Producto "1" --> "*" DetallePedido : aparece
    Pedido "1" --> "1" Factura : genera
```

## Relaciones

| Entidad A | Relación | Entidad B |
|-----------|----------|-----------|
| Usuario | * ― * | Rol |
| Usuario | 1 ― * | Pedido |
| Pedido | 1 ― * | DetallePedido |
| Producto | 1 ― * | DetallePedido |
| Pedido | 1 ― 1 | Factura |

## Arquitectura General

```
┌─────────────────────────────────────────────────┐
│                   VISTAS                         │
│          (Thymeleaf Templates)                   │
│  login │ registro │ dashboard │ productos       │
│  carrito │ pedidos │ factura │ perfil           │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│               CONTROLADORES                      │
│  AuthController │ ProductoController             │
│  ClienteController │ PedidoAdminController       │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│                SERVICIOS                         │
│  UsuarioService │ CustomUserDetailsService       │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│              REPOSITORIOS JPA                    │
│  UsuarioRepository │ ProductoRepository          │
│  PedidoRepository │ FacturaRepository            │
└─────────────────────────────────────────────────┘
                   │
                   ▼
           ┌─────────────┐
           │   MySQL DB  │
           └─────────────┘
```
