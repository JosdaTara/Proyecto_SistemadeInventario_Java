package com.inventario.model;

import java.util.Date;

public class Venta {
    private int idVenta;
    private Factura factura;
    private Date fechaVenta;
    private double total;

    public Venta() {}

    public Venta(Factura factura) {
        this.factura = factura;
        this.total = factura.getTotal();
        this.fechaVenta = new Date();
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
