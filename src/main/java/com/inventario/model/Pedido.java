package com.inventario.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private int idPedido;
    private Date fechaPedido;
    private Cliente cliente;
    private Usuario usuario;
    private String estado;
    private double total;
    private List<DetallePedido> detalles;

    public Pedido() {
        this.detalles = new ArrayList<>();
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.estado = "pendiente";
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public boolean cambiarEstado(String nuevoEstado) {
        List<String> estadosValidos = List.of("pendiente", "confirmado", "enviado", "entregado", "cancelado");
        if (estadosValidos.contains(nuevoEstado)) {
            this.estado = nuevoEstado;
            return true;
        }
        return false;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public Date getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Date fechaPedido) { this.fechaPedido = fechaPedido; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}
