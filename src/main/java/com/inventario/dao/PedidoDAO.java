package com.inventario.dao;

import com.inventario.model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private Connection connection;

    public PedidoDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pedidos.add(mapPedido(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public Pedido findById(int id) {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPedido(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_cliente, id_usuario, estado, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getCliente().getIdCliente());
            if (pedido.getUsuario() != null) {
                stmt.setInt(2, pedido.getUsuario().getIdUsuario());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setIdPedido(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEstado(int idPedido, String estado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("id_pedido"));
        pedido.setFechaPedido(rs.getTimestamp("fecha_pedido"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setTotal(rs.getDouble("total"));
        return pedido;
    }
}
