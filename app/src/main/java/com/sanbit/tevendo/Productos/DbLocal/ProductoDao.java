package com.sanbit.tevendo.Productos.DbLocal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductoDao {

    @Query("SELECT * FROM producto ORDER BY id  DESC")
    LiveData<List<ProductoEntity>> getAllProductos();

    @Query("SELECT * FROM producto ORDER BY id DESC")
    List<ProductoEntity> getAllMProductos();

   /* @Query("SELECT distinct p.id,p.codigo,p.nameProducto ,p.descripcionCorta,p.categoriaId ,p.nameCategoria ,precio.chprecio as precio, (" +
            "select r.cantidad  from (select  MAx(st.id),st.cantidad   from stock as st where st.codigoProducto=p.numi) as r ) as stock " +
            ",p.familia " +
            "FROM producto as p inner join precio on precio.chcprod =p.numi " +
            " WHERE precio.chcatcl=:numi")
    List<ProductoEntity> getProductoByCliente(int numi);*/

    @Query("SELECT * FROM producto WHERE id =:numi")
    ProductoEntity getProductoById(int numi);

    @Query("SELECT * FROM producto WHERE id=:numi")
    LiveData<ProductoEntity> getProducto(int numi);

    @Insert
    long insert(ProductoEntity note);

    @Update
    void update(ProductoEntity note);

    @Delete
    void delete(ProductoEntity note);

    @Query("DELETE FROM producto")
    void deleteAll();
}
