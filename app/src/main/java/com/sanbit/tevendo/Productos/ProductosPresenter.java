package com.sanbit.tevendo.Productos;

import android.content.Context;

import com.google.android.gms.common.internal.Preconditions;
import com.google.gson.Gson;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaListViewModel;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductosListViewModel;


import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosPresenter  implements ProductorMvp.Presenter{


    private final ProductorMvp.View mSincronizarProductos;
    private final ProductosListViewModel viewModelProducto;
    private final CategoriaListViewModel  viewModelCategoria;

    private final Context mContext;


 int contador=0;
    public ProductosPresenter(ProductorMvp.View Sincronizarview, Context context,ProductosListViewModel mproducto,CategoriaListViewModel  mcategoria) {

        this.viewModelProducto=mproducto;
        this.mContext=context;
        this.viewModelCategoria=mcategoria;
        mSincronizarProductos = Preconditions.checkNotNull(Sincronizarview);
        mSincronizarProductos.setPresenter(this);
    }

    @Override
    public void GetDatos() {
        contador=0;
        try {
          List<ProductoEntity>  listProducto=  viewModelProducto.getMAllProducto(1);
          List<CategoriaEntity> listCategoria=viewModelCategoria.getMAllCategorias(1);
            mSincronizarProductos.MostrarDatos(listProducto,listCategoria);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GetImagenes();

    }

    public void GetImagenes(){

        GetCategorias();
    }

    public void GetCategorias(){

    }
}
