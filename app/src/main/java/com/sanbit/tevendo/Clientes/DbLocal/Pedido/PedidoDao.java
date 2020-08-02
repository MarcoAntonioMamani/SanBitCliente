package com.sanbit.tevendo.Clientes.DbLocal.Pedido;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PedidoDao {

    @Query("SELECT * FROM pedido ORDER BY oanumi DESC")
    LiveData<List<PedidoEntity>> getAllPedidos();

    @Query("SELECT * FROM pedido where estadoPedido=2 ORDER BY oanumi DESC")
    LiveData<List<PedidoEntity>> getAllPedidosEntregados();

    @Query("SELECT * FROM pedido")
    List<PedidoEntity> getPedidoAll();

    @Query("SELECT * FROM pedido where estado=0")
    List<PedidoEntity> getPedidoAllState();

    @Query("SELECT * FROM pedido where estado=2")
    List<PedidoEntity> getPedidoAllState02();

    @Query("SELECT * FROM pedido WHERE oanumi=:numi")
    PedidoEntity getPedidoById(String numi);

    @Query("SELECT * FROM pedido WHERE clienteId=:numi")
    List<PedidoEntity> getPedidoByIdCliente(String numi);

    @Query("SELECT * FROM pedido WHERE clienteId=:numi and estadoPedido in (1,2)")
    List<PedidoEntity> getPedidoByCodeClienteForstate(String numi);

    @Query("SELECT * FROM pedido WHERE oanumi=:numi")
    LiveData<PedidoEntity> getPedido(int numi);

    @Insert
    long insert(PedidoEntity note);


    @Update
    void update(PedidoEntity note);

    @Update
    void update(PedidoEntity... note);

    @Delete
    void delete(PedidoEntity note);

    @Query("DELETE FROM pedido")
    void deleteAll();
}
