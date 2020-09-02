package com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface ProductosImagenesDao {

    @Query("SELECT * FROM imagenes ORDER BY id  DESC")
    LiveData<List<ProductosImagenesEntity>> getAllImagenes();

    @Query("SELECT * FROM imagenes ORDER BY id DESC")
    List<ProductosImagenesEntity> getAllMImagenes();



    @Query("SELECT * FROM imagenes WHERE id =:numi")
    ProductosImagenesEntity getImagenesById(int numi);

    @Query("SELECT * FROM imagenes WHERE id=:numi")
    LiveData<ProductosImagenesEntity> getImagenes(int numi);

    @Insert
    long insert(ProductosImagenesEntity note);

    @Update
    void update(ProductosImagenesEntity note);

    @Delete
    void delete(ProductosImagenesEntity note);

    @Query("DELETE FROM imagenes")
    void deleteAll();
}
