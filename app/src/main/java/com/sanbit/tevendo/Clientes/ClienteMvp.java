package com.sanbit.tevendo.Clientes;


import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;

public interface ClienteMvp {
    interface View {
        void recyclerViewListClicked(android.view.View v, ClienteEntity cliente);

    }
    interface Presenter{
        void GuadarDatos();
    }
}
