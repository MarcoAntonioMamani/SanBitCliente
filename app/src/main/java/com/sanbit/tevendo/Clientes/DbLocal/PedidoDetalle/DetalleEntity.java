package com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "detalle")
public class DetalleEntity implements Cloneable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "pedidoId")
    String pedidoId;
    @ColumnInfo(name = "productoId")
    int productoId;
    @ColumnInfo(name = "descripcion")
    String descripcion;
    @ColumnInfo(name = "cantidad")
    double cantidad;
    @ColumnInfo(name = "precio")
    double precio;
    @ColumnInfo(name = "subTotal")
    double subTotal;
    @ColumnInfo(name = "descuento")
    double descuento;
    @ColumnInfo(name = "total")
    double total;
    @ColumnInfo(name = "estado")
    boolean estado;

    @ColumnInfo(name = "obupdate")
    int obupdate;

    @ColumnInfo(name = "stock")
    double stock;
    public DetalleEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getObupdate() {
        return obupdate;
    }

    public void setObupdate(int obupdate) {
        this.obupdate = obupdate;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public DetalleEntity clone() throws CloneNotSupportedException {
        return (DetalleEntity) super.clone();
    }
}

