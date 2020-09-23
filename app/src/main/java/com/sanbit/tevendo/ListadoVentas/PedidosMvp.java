package com.sanbit.tevendo.ListadoVentas;


import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;

import java.util.List;

public interface PedidosMvp {
    interface View {
        void recyclerViewListClicked(android.view.View v, PedidoEntity pedido);
        void MostrarPedidos(List<PedidoEntity> clientes);
        void setPresenter(Presenter presenter);

    }
    interface Presenter{
        void CargarPedidos();
    }
}
