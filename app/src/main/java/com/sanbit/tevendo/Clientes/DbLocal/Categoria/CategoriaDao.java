package com.sanbit.tevendo.Clientes.DbLocal.Categoria;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

public interface CategoriaDao {

    @Query("SELECT * FROM categoria ORDER BY id  DESC")
    LiveData<List<CategoriaEntity>> getAllCategorias();

    @Query("SELECT * FROM categoria ORDER BY id DESC")
    List<CategoriaEntity> getAllMCategorias();



    @Query("SELECT * FROM categoria WHERE id =:numi")
    CategoriaEntity getCategoriaById(int numi);

    @Query("SELECT * FROM categoria WHERE id=:numi")
    LiveData<CategoriaEntity> getCategoria(int numi);

    @Insert
    long insert(CategoriaEntity note);

    @Update
    void update(CategoriaEntity note);

    @Delete
    void delete(CategoriaEntity note);

    @Query("DELETE FROM categoria")
    void deleteAll();
}
