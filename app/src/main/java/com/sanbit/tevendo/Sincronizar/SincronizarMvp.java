package com.sanbit.tevendo.Sincronizar;

public interface SincronizarMvp {
    interface View {
        void MarcarDesmarcarTodos(boolean bandera);
        void setPresenter(SincronizarMvp.Presenter presenter);
        void ShowMessageResult(String message);
        void ShowSyncroMgs(String message);

    }
    interface Presenter{
        void GuadarDatos(boolean producto, boolean precio, boolean cliente, boolean pedidos);
    }
}
