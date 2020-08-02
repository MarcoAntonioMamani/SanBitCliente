package com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DetalleDao {

    @Query("SELECT * FROM detalle ORDER BY pedidoId DESC")
    LiveData<List<DetalleEntity>> getAllDetalle();
    @Query("SELECT * FROM detalle")
    List<DetalleEntity> getDetalleAll();


    @Query("SELECT * FROM detalle where estado=0")
    List<DetalleEntity> getDetalleAllState();

    @Query("SELECT * FROM detalle WHERE pedidoId=:numi")
    List<DetalleEntity> getDetalleById(String numi);

    @Query("SELECT * FROM detalle WHERE pedidoId=:numi and productoId=:producto")
    DetalleEntity getDetalleByIdProducto(int numi, int producto);

    @Query("SELECT * FROM detalle WHERE pedidoId=:numi")
    LiveData<DetalleEntity> getDetalle(int numi);

    @Insert
    long insert(DetalleEntity note);


    @Update
    void update(DetalleEntity note);

    @Update
    void update(DetalleEntity... note);

    @Delete
    void delete(DetalleEntity note);

    @Query("DELETE FROM detalle")
    void deleteAll();
}
