package com.sanbit.tevendo.Productos.Estados;

import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Productos.ProductorMvp;

import java.util.List;

public interface ProductosEstadosMvp {

    interface View {
        void recyclerViewListClicked(android.view.View v, ProductosEstados producto);


    }

}
