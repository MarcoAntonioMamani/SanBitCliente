package com.sanbit.tevendo.ListadoVentas.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.ListadoVentas.PedidosMvp;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.ShareMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marco on 2016-08-01.
 */
public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.PedidosViewHolder> {
    private List<PedidoEntity> listaPedidos;
    private List<ClienteEntity> listClientes;
    private Context context;
private PedidosMvp.View mview;
    public AdapterPedidos(Context ctx, List<PedidoEntity> s, PedidosMvp.View view, List<ClienteEntity> listcliente) {
        this.context = ctx;
        this.listaPedidos = s;
        this.mview=view;
        this.listClientes=listcliente;
    }
    public AdapterPedidos(Context ctx) {
        this.context = ctx;

    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }


    @Override
    public void onBindViewHolder(PedidosViewHolder clientesViewHolder, final int i) {

        clientesViewHolder.TvNombreCliente.setText((CharSequence) listaPedidos.get(i).getCliente());
        clientesViewHolder.TvDireccionCliente.setText((CharSequence) ObtenerDireccionCliente(listaPedidos.get(i).getClienteId()));
        clientesViewHolder.TvFecha.setText((CharSequence) ShareMethods.ObtenerFecha(listaPedidos.get(i).getFechaVenta())+" \n         "+listaPedidos.get(i).getHora());
        clientesViewHolder.TvTotal.setText((CharSequence) ShareMethods.ObtenerDecimalToString( listaPedidos.get(i).getTotal(),2)+" Bs");
        ColorGenerator generator = ColorGenerator.MATERIAL;
        clientesViewHolder.tvTelefono.setText((CharSequence) "Telf: "+ObtenerTelefonoCliente(listaPedidos.get(i).getClienteId()));
        clientesViewHolder.CardViewPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.recyclerViewListClicked(v, listaPedidos.get(i));
            }
        });
        if (listaPedidos.get(i).getTipoVenta()==1){
            clientesViewHolder.TvNroPedido.setText((CharSequence) ("Venta Contado # "+listaPedidos.get(i).getOanumi()));
            clientesViewHolder.linearMonto.setBackground(context.getResources().getDrawable(R.drawable.animation_riple_maps));
        }else{
            clientesViewHolder.TvNroPedido.setText((CharSequence) ("Venta Credito # "+listaPedidos.get(i).getOanumi()));
            clientesViewHolder.linearMonto.setBackground(context.getResources().getDrawable(R.drawable.animation_botton));
        }

    }
public String ObtenerDireccionCliente(String numi){
    for (int i = 0; i < listClientes.size(); i++) {
        ClienteEntity cliente=listClientes.get(i);
        if (cliente.getCodigogenerado().trim().equals(numi.trim())){
            return cliente.getDireccion();
        }
    }
    return "S/D";
}
    public String ObtenerTelefonoCliente(String numi){
        for (int i = 0; i < listClientes.size(); i++) {
            ClienteEntity cliente=listClientes.get(i);
            if (cliente.getCodigogenerado().trim().equals(numi.trim())){
                return cliente.getTelefono();
            }
        }
        return "S/N";
    }
    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_pedidos , viewGroup, false);

        return new PedidosViewHolder(itemView);
    }



    public static class PedidosViewHolder extends RecyclerView.ViewHolder {

        protected TextView TvNroPedido;
        protected TextView TvNombreCliente;
        protected TextView TvDireccionCliente;
        protected TextView TvFecha;
        protected TextView TvTotal;
        protected TextView tvTelefono;
        protected CardView CardViewPedidos;
        protected LinearLayout linearMonto;
        public PedidosViewHolder(View v) {
            super(v);

            TvNroPedido = (TextView) v.findViewById(R.id.view_pedido_nro);
            TvNombreCliente=(TextView) v.findViewById(R.id.view_pedido_nombre);
            TvDireccionCliente = (TextView) v.findViewById(R.id.view_pedido_direccion);
            TvFecha = (TextView) v.findViewById(R.id.view_pedido_fecha);
            TvTotal = (TextView) v.findViewById(R.id.view_pedido_total);
            tvTelefono=(TextView)v.findViewById(R.id.view_pedido_Telefono);
            CardViewPedidos=(CardView)v.findViewById(R.id.view_pedidos_card);
            linearMonto=(LinearLayout)v.findViewById(R.id.linear_monto);
        }
    }

    public void setFilter(List<PedidoEntity> ListaFiltrada){
        this.listaPedidos =new ArrayList<>();
        this.listaPedidos.addAll(ListaFiltrada);
        notifyDataSetChanged();
    }
}
