package com.sanbit.tevendo.Productos.DbLocal;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "producto")
public class ProductoEntity implements Cloneable {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "codigo")
    String codigo;
    @ColumnInfo(name = "nameProducto")
    String nameProducto;
    @ColumnInfo(name = "descripcionCorta")
    String descripcionCorta;
    @ColumnInfo(name = "categoriaId")
    int categoriaId;
    @ColumnInfo(name = "nameCategoria")
    String nameCategoria;
    @ColumnInfo(name = "precioCosto")
    double precioCosto;
    @ColumnInfo(name = "precioVenta")
    double precioVenta;
    @ColumnInfo(name = "stock")
    double stock;

    public ProductoEntity(int id, String codigo, String nameProducto, String descripcionCorta, int categoriaId, String nameCategoria, double precioCosto, double precioVenta, double stock) {
        this.id = id;
        this.codigo = codigo;
        this.nameProducto = nameProducto;
        this.descripcionCorta = descripcionCorta;
        this.categoriaId = categoriaId;
        this.nameCategoria = nameCategoria;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNameProducto() {
        return nameProducto;
    }

    public void setNameProducto(String nameProducto) {
        this.nameProducto = nameProducto;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNameCategoria() {
        return nameCategoria;
    }

    public void setNameCategoria(String nameCategoria) {
        this.nameCategoria = nameCategoria;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public ProductoEntity clone() throws CloneNotSupportedException {
        return (ProductoEntity) super.clone();
    }
}
