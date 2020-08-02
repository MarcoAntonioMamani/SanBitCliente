package com.sanbit.tevendo.Clientes.DbLocal.Stock;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stock")
public class StockEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "productoId")
    int productoId;
    @ColumnInfo(name = "cantidad")
    double cantidad;
    @ColumnInfo(name = "almacen")
    int almacen;

    public StockEntity(int id, int productoId, double cantidad, int almacen) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.almacen = almacen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }
}
