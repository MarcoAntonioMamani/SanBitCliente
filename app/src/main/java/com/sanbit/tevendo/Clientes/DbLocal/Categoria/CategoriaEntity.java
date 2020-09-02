package com.sanbit.tevendo.Clientes.DbLocal.Categoria;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "categoria")
public class CategoriaEntity implements Cloneable  {

    @PrimaryKey()
    @ColumnInfo(name = "Id")
    int Id;
    @ColumnInfo(name = "nombreCategoria")
    String nombreCategoria;
    @ColumnInfo(name = "descripcionCategoria")
    String descripcionCategoria;
    @ColumnInfo(name = "imagen")
    String imagen;

    @Ignore
    int Estado;

    public CategoriaEntity() {
    }

    public CategoriaEntity(int id, String nombreCategoria, String descripcionCategoria, String imagen, int estado) {
        Id = id;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.imagen = imagen;
        Estado = estado;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public CategoriaEntity clone() throws CloneNotSupportedException {
        return (CategoriaEntity) super.clone();
    }
}
