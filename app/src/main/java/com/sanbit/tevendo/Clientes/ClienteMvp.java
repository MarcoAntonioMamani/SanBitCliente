package com.sanbit.tevendo.Clientes;


import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;

public interface ClienteMvp {
    interface View {
        void recyclerViewListClicked(android.view.View v, ClienteEntity cliente);
        void showCellPhone(android.view.View v,ClienteEntity i);
        void sendMessageWhatsapp(android.view.View v,ClienteEntity i);
        void viewUbicacion(android.view.View v,ClienteEntity i);
    }
    interface Presenter{
        void GuadarDatos();
    }
}
