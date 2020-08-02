package com.sanbit.tevendo.Clientes.DbLocal.Precios;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "precio")
public class PrecioEntity {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "productoId")
    int productoId;
    @ColumnInfo(name = "categoriaId")
    int categoriaId;
    @ColumnInfo(name = "precio")
    double precio;

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

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
