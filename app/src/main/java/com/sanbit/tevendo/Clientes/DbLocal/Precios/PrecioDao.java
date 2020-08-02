package com.sanbit.tevendo.Clientes.DbLocal.Precios;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PrecioDao {

    @Query("SELECT * FROM precio ORDER BY id DESC")
    LiveData<List<PrecioEntity>> getAllPrecios();
    @Query("SELECT * FROM precio")
    List<PrecioEntity> getPrecioAll();

    @Query("SELECT * FROM precio WHERE id=:numi")
    PrecioEntity getPrecioById(int numi);

    @Query("SELECT * FROM precio WHERE id=:numi")
    LiveData<PrecioEntity> getPrecio(int numi);

    @Insert
    long insert(PrecioEntity note);


    @Update
    void update(PrecioEntity note);

    @Update
    void update(PrecioEntity... note);

    @Delete
    void delete(PrecioEntity note);

    @Query("DELETE FROM precio")
    void deleteAll();
}
