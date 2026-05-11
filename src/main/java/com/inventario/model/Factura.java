package com.inventario.model;

import java.util.Date;

public class Factura {
    private int idFactura;
    private Pedido pedido;
    private Date fechaEmision;
    private double total;
    private Usuario usuario;

    public Factura() {}

    public Factura(Pedido pedido, Usuario usuario) {
        this.pedido = pedido;
        this.usuario = usuario;
        this.total = pedido.getTotal();
        this.fechaEmision = new Date();
    }

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
