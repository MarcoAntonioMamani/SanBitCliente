package com.sanbit.tevendo.ListadoVentas;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


import com.google.android.gms.common.internal.Preconditions;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoListViewModel;

import java.util.ArrayList;
import java.util.List;

public class PedidosPresenter implements PedidosMvp.Presenter {

    private final PedidosMvp.View mPedidosView;
    private final Context mContext;
    private PedidoListViewModel viewModel;
    private FragmentActivity activity;
    int Estado;
    public PedidosPresenter(PedidosMvp.View pedidosView, Context context, PedidoListViewModel viewModel, FragmentActivity activity, int Estado){
        mPedidosView = Preconditions.checkNotNull(pedidosView);
        mPedidosView.setPresenter(this);
        this.mContext=context;
        this.Estado=Estado;
        this.viewModel=viewModel;
        this.activity=activity;
        viewModel = ViewModelProviders.of(activity).get(PedidoListViewModel.class);
    }
    @Override
    public void CargarPedidos() {



            viewModel.getPedidos().observe((LifecycleOwner) activity, new Observer<List<PedidoEntity>>() {
                @Override
                public void onChanged(@Nullable List<PedidoEntity> notes) {
                    try{
                        if (notes.size()>0){

                            mPedidosView.MostrarPedidos(notes);
                        }
                    }catch(Exception e){

                    }



                }  });


    }

}
