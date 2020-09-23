package com.sanbit.tevendo.Clientes.DbLocal.Pedido;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "pedido")
public class PedidoEntity implements Comparable<PedidoEntity> {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "oanumi")
    String oanumi;
    @ColumnInfo(name = "fechaVenta")
    Date fechaVenta;
    @ColumnInfo(name = "hora")
    String hora;
    @ColumnInfo(name = "clienteId")
    String clienteId;
    @ColumnInfo(name = "cliente")
    String cliente;
    @ColumnInfo(name = "personalId")
    int personalId;
    @ColumnInfo(name = "estadoPedido")
    int estadoPedido;
    @ColumnInfo(name = "observacion")
    String observacion;
    @ColumnInfo(name = "latitud")
    Double latitud;
    @ColumnInfo(name = "longitud")
    Double longitud;
    @ColumnInfo(name = "total")
    Double total;
    @ColumnInfo(name = "tipocobro")
    int tipocobro;
    @ColumnInfo(name = "totalcredito")
    Double totalcredito;
    @ColumnInfo(name = "estado")
    int estado;
    @ColumnInfo(name = "codigogenerado")
    String codigogenerado;
    @ColumnInfo(name = "estadoupdate")
    int estadoUpdate;
    @ColumnInfo(name = "TipoVenta")
    int TipoVenta;
    @ColumnInfo(name = "Utilidad")
    Double Utilidad;
public PedidoEntity(){

}

    public int getTipoVenta() {
        return TipoVenta;
    }

    public void setTipoVenta(int tipoVenta) {
        TipoVenta = tipoVenta;
    }

    public Double getUtilidad() {
        return Utilidad;
    }

    public void setUtilidad(Double utilidad) {
        Utilidad = utilidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOanumi() {
        return oanumi;
    }

    public void setOanumi(String oanumi) {
        this.oanumi = oanumi;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public int getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(int estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getTipocobro() {
        return tipocobro;
    }

    public void setTipocobro(int tipocobro) {
        this.tipocobro = tipocobro;
    }

    public Double getTotalcredito() {
        return totalcredito;
    }

    public void setTotalcredito(Double totalcredito) {
        this.totalcredito = totalcredito;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCodigogenerado() {
        return codigogenerado;
    }

    public void setCodigogenerado(String codigogenerado) {
        this.codigogenerado = codigogenerado;
    }

    public int getEstadoUpdate() {
        return estadoUpdate;
    }

    public void setEstadoUpdate(int estadoUpdate) {
        this.estadoUpdate = estadoUpdate;
    }

    @Override
    public int compareTo(PedidoEntity pedidoEntity) {
        int thisVal = this.getId();
        int anotherVal = pedidoEntity.getId();
        return (thisVal>anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    }
}
