-- ============================================
-- SCRIPT DE CREACIÓN DE LA BASE DE DATOS
-- Sistema de Inventario y Ventas
-- ============================================

CREATE DATABASE IF NOT EXISTS inventario_ventas;
USE inventario_ventas;

-- ============================================
-- TABLA: roles
-- ============================================
CREATE TABLE roles (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- ============================================
-- TABLA: usuarios
-- ============================================
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- ============================================
-- TABLA: clientes
-- ============================================
CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA: categorias
-- ============================================
CREATE TABLE categorias (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- ============================================
-- TABLA: productos
-- ============================================
CREATE TABLE productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 5,
    id_categoria INT,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- ============================================
-- TABLA: pedidos
-- ============================================
CREATE TABLE pedidos (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT NOT NULL,
    id_usuario INT,
    estado ENUM('pendiente', 'confirmado', 'enviado', 'entregado', 'cancelado') DEFAULT 'pendiente',
    total DECIMAL(12, 2) DEFAULT 0.00,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- ============================================
-- TABLA: detalle_pedidos
-- ============================================
CREATE TABLE detalle_pedidos (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- ============================================
-- TABLA: facturas
-- ============================================
CREATE TABLE facturas (
    id_factura INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL UNIQUE,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(12, 2) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- ============================================
-- TABLA: ventas (resumen)
-- ============================================
CREATE TABLE ventas (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    id_factura INT NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura)
);

-- ============================================
-- INSERCIÓN DE REGISTROS INICIALES
-- ============================================

-- Roles
INSERT INTO roles (nombre, descripcion) VALUES
('Administrador', 'Acceso total al sistema. Gestión de usuarios, productos, pedidos y reportes.'),
('Cliente', 'Acceso limitado a consulta de productos, creación de pedidos y consulta de facturas.');

-- Usuarios (contraseña hasheada: "admin123" y "cliente123" - BCrypt)
INSERT INTO usuarios (nombre, email, password_hash, id_rol) VALUES
('Administrador Principal', 'admin@inventario.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1),
('Juan Pérez', 'juan@correo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2),
('María García', 'maria@correo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2);

-- Clientes
INSERT INTO clientes (nombre, direccion, telefono, email) VALUES
('Juan Pérez', 'Calle 123 #45-67, Ciudad', '3001234567', 'juan@correo.com'),
('María García', 'Carrera 89 #12-34, Ciudad', '3007654321', 'maria@correo.com'),
('Carlos López', 'Av. Principal #56-78, Ciudad', '3109876543', 'carlos@correo.com');

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Electrónicos', 'Productos electrónicos y dispositivos tecnológicos'),
('Ropa y Accesorios', 'Prendas de vestir y accesorios personales'),
('Alimentos y Bebidas', 'Productos alimenticios y bebidas'),
('Hogar', 'Artículos para el hogar y decoración'),
('Deportes', 'Equipamiento y accesorios deportivos');

-- Productos
INSERT INTO productos (nombre, descripcion, precio, stock, stock_minimo, id_categoria) VALUES
('Laptop HP ProBook', 'Laptop HP ProBook 450 G8, 8GB RAM, 256GB SSD', 3500000.00, 10, 3, 1),
('Mouse Inalámbrico', 'Mouse óptico inalámbrico USB', 45000.00, 50, 10, 1),
('Teclado Mecánico', 'Teclado mecánico RGB retroiluminado', 120000.00, 30, 5, 1),
('Camiseta Algodón', 'Camiseta de algodón 100%,\talla M-L', 35000.00, 100, 20, 2),
('Jeans Clásicos', 'Jeans corte clásico, talla 30-36', 85000.00, 60, 10, 2),
('Café Premium 500g', 'Café tostado molido premium 500g', 18000.00, 200, 30, 3),
('Chocolate Tableta', 'Tableta de chocolate amargo 70% cacao', 8500.00, 150, 20, 3),
('Lámpara LED', 'Lámpara de escritorio LED ajustable', 65000.00, 25, 5, 4),
('Balón de Fútbol', 'Balón de fútbol profesional talla 5', 55000.00, 40, 8, 5),
('Pesas 5kg', 'Par de pesas de 5kg cada una', 90000.00, 20, 4, 5);

-- Pedidos de ejemplo
INSERT INTO pedidos (id_cliente, id_usuario, estado, total) VALUES
(1, 2, 'entregado', 3545000.00),
(2, 3, 'confirmado', 150000.00),
(3, NULL, 'pendiente', 120500.00);

-- Detalle de pedidos
INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 3500000.00),
(1, 2, 1, 45000.00),
(2, 5, 1, 85000.00),
(2, 6, 2, 18000.00),
(2, 7, 2, 8500.00),
(3, 3, 1, 120000.00),
(3, 8, 1, 500.00);

-- Facturas
INSERT INTO facturas (id_pedido, total, id_usuario) VALUES
(1, 3545000.00, 1),
(2, 150000.00, 1);

-- Ventas
INSERT INTO ventas (id_factura, total) VALUES
(1, 3545000.00),
(2, 150000.00);
