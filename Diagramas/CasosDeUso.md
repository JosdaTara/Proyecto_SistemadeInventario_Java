# Diagramas de Casos de Uso

## Administrador

```mermaid
graph TB
    subgraph SISTEMA["SISTEMA DE GESTION COMERCIAL"]
        direction TB
        
        subgraph Auth["Autenticacion"]
            L1[("Iniciar Sesion")]
        end
        
        subgraph Gestion["Gestion"]
            P1[("Gestionar Productos")]
            P2[("Gestionar Pedidos")]
            P3[("Gestionar Clientes")]
        end
        
        subgraph Reports["Reportes"]
            R1[("Generar Reportes")]
        end
    end

    Admin((Administrador)) --> L1
    Admin --> P1
    Admin --> P2
    Admin --> P3
    Admin --> R1
```

## Cliente

```mermaid
graph TB
    subgraph SISTEMA["SISTEMA DE GESTION COMERCIAL"]
        direction TB
        
        subgraph Auth2["Autenticacion"]
            L2[("Iniciar Sesion")]
        end
        
        subgraph Compras["Compras"]
            C1[("Consultar Catalogo")]
            C2[("Gestionar Carrito")]
            C3[("Realizar Pedido")]
        end
        
        subgraph Seguimiento["Seguimiento"]
            S1[("Consultar Pedidos")]
            S2[("Ver Factura")]
        end
        
        subgraph PerfilSec["Perfil"]
            U1[("Mi Perfil")]
        end
    end

    Cliente((Cliente)) --> L2
    Cliente --> C1
    Cliente --> C2
    Cliente --> C3
    Cliente --> S1
    Cliente --> S2
    Cliente --> U1
```

## Casos de Uso Detallados

### CU-01: Iniciar Sesion
```
Actor:      Administrador, Cliente
Precond:    Usuario no autenticado
Flujo:      Ingresa email + password
            Sistema valida con BCrypt
            Redirige segun rol:
              Admin  -> /dashboard
              Cliente -> /cliente/dashboard
Postcond:   Sesion iniciada
```

### CU-02: Gestionar Productos (Administrador)
```
Actor:      Administrador
Rutas:      GET/POST /productos
Acciones:   Listar productos
            Crear nuevo (nombre, precio, stock, categoria)
            Editar existente
            Eliminar producto
Postcond:   BD actualizada
```

### CU-03: Gestionar Carrito (Cliente)
```
Actor:      Cliente
Rutas:      POST /cliente/carrito/agregar
            POST /cliente/carrito/actualizar
            POST /cliente/carrito/eliminar
Almacen:    Sesion HTTP (carrito)
Acciones:   Agregar producto con cantidad
            Actualizar cantidad
            Eliminar item
```

### CU-04: Realizar Pedido (Cliente)
```
Actor:      Cliente
Ruta:       POST /cliente/checkout
Precond:    Carrito no vacio, stock suficiente
Flujo:      Validar stock de cada producto
            Crear Pedido (estado PENDIENTE)
            Descontar stock de productos
            Generar Factura (FAC-XXXXX)
            Vaciar carrito
Postcond:   Pedido + Factura creados
```

### CU-05: Gestionar Pedidos (Administrador)
```
Actor:      Administrador
Rutas:      GET /admin/pedidos
            POST /admin/pedidos/{id}/estado
Estados:    PENDIENTE -> CONFIRMADO -> ENVIADO -> ENTREGADO
            Cualquier estado -> CANCELADO
Acciones:   Ver lista de pedidos
            Ver detalle del pedido
            Cambiar estado
```

### CU-06: Ver Factura
```
Actor:      Cliente
Ruta:       GET /cliente/factura/{id}
Generacion: Automatica al crear pedido
Formato:    Vista imprimible
Datos:      Numero FAC-XXXXX
            Datos del cliente
            Detalle de productos
            Total
```

## Matriz de Relacion

| Funcionalidad | Admin | Cliente | Sistema |
|---------------|:-----:|:-------:|:-------:|
| Login | X | X | |
| CRUD Productos | X | | |
| Gestionar Pedidos | X | | |
| Consultar Catalogo | | X | |
| Carrito de Compras | | X | |
| Realizar Pedido | | X | |
| Ver Pedidos | X | X | |
| Ver Factura | | X | |
| Generar Factura | | | X |
| Mi Perfil | | X | |
