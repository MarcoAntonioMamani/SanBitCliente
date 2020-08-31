package com.sanbit.tevendo.Productos;

import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;

import java.util.List;

public interface ProductorMvp {

    interface View {
        void recyclerViewListClicked(android.view.View v, ProductoEntity empresa);
        void recyclerViewListClickedCategoria(android.view.View v, CategoriaEntity empresa, TextView tvCategoria);
        void setPresenter(ProductorMvp.Presenter presenter);
        void ShowMessageResult(String message);
        void MostrarDatos(List<ProductoEntity> listProducto,List<CategoriaEntity>  listCategoria);
        void AddCantidadProducto(ElegantNumberButton v, int valor, ProductoEntity producto);
        void ShowSyncroMgs(String message);

    }
    interface Presenter{
        void GetDatos();
    }
}
