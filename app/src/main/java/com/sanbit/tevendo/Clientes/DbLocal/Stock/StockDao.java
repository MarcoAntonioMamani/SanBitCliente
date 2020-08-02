package com.sanbit.tevendo.Clientes.DbLocal.Stock;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock ORDER BY productoId  DESC")
    LiveData<List<StockEntity>> getAllStock();

    @Query("SELECT * FROM stock WHERE productoId=:id")
    StockEntity getStockById(int id);

    @Query("SELECT * FROM stock WHERE productoId=:id")
    LiveData<StockEntity> getStock(int id);

    @Insert
    long insert(StockEntity stock);

    @Update
    void update(StockEntity stock);


    @Delete
    void delete(StockEntity stock);

    @Query("DELETE FROM stock")
    void deleteAll();

    @Query("SELECT * FROM stock")
    List<StockEntity> getStockAll();
}
