package com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "imagenes")
public class ProductosImagenesEntity {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "ProductoId")
    int ProductoId;
    @ColumnInfo(name = "NombreImage")
    String NombreImage;

    public ProductosImagenesEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public String getNombreImage() {
        return NombreImage;
    }

    public void setNombreImage(String nombreImage) {
        NombreImage = nombreImage;
    }
}
