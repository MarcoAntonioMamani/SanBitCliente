package com.sanbit.tevendo.Productos.Estados;

public class ProductosEstados {

    int Id;
    int estado;
    String Contenido;

    public ProductosEstados(int id, int estado, String contenido) {
        Id = id;
        this.estado = estado;
        Contenido = contenido;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
    }
}
