package com.inventario.dao;

import com.inventario.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection connection;

    public ProductoDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.activo = true";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                productos.add(mapProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public Producto findById(int id) {
        String sql = "SELECT p.*, c.nombre as categoria_nombre FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapProducto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, stock_minimo, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getStockMinimo());
            stmt.setInt(6, producto.getCategoria().getIdCategoria());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                producto.setIdProducto(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=?, stock=?, stock_minimo=?, id_categoria=? WHERE id_producto=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getStockMinimo());
            stmt.setInt(6, producto.getCategoria().getIdCategoria());
            stmt.setInt(7, producto.getIdProducto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Producto mapProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setStockMinimo(rs.getInt("stock_minimo"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return producto;
    }
}
