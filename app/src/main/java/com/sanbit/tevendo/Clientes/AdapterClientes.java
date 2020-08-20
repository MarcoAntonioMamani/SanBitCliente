package com.sanbit.tevendo.Clientes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pdfjet.Line;
import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.R;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marco on 2016-08-01.
 */
public class AdapterClientes extends RecyclerView.Adapter<AdapterClientes.ClientesViewHolder> {
    private List<ClienteEntity> listaCliente;
    private Context context;
private ClienteMvp.View mview;
    public AdapterClientes(Context ctx, List<ClienteEntity> s, ClienteMvp.View view) {
        this.context = ctx;
        this.listaCliente = s;
        this.mview=view;
    }
    public AdapterClientes(Context ctx) {
        this.context = ctx;

    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }


    @Override
    public void onBindViewHolder(ClientesViewHolder clientesViewHolder, final int i) {
        clientesViewHolder.TvAdapterNombre.setText((CharSequence) listaCliente.get(i).getNamecliente());
        clientesViewHolder.TvAdapterDireccion.setText((CharSequence) listaCliente.get(i).getDireccion());

        clientesViewHolder.TvAdapterNombre.setTag(listaCliente.get(i));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(listaCliente.get(i).getNamecliente());

        clientesViewHolder.ivAdapterImg.setImageDrawable(TextDrawable.builder().buildRound(listaCliente.get(i).getNamecliente().substring(0, 1), color));
        clientesViewHolder.cardCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.recyclerViewListClicked(v, listaCliente.get(i));
            }
        });
        if (listaCliente.get(i).getEstado()==1){
            clientesViewHolder.TvAdapterNombre.setTextColor(Color.BLACK);
        }else{
            clientesViewHolder.TvAdapterNombre.setTextColor(Color.RED);
        }
        if (listaCliente.get(i).getLatitud()==null || (listaCliente.get(i).getLatitud()==0 && listaCliente.get(i).getLongitud()==0)){
            clientesViewHolder.linearUbicacion.setVisibility(View.GONE);
        }else{
            clientesViewHolder.linearUbicacion.setVisibility(View.VISIBLE);
        }
        if (listaCliente.get(i).getTelefono().isEmpty()){
            clientesViewHolder.linearLlamar.setVisibility(View.GONE);
            clientesViewHolder.linearWhatsapp.setVisibility(View.GONE);
        }else{
            try {
                if (Integer.parseInt(listaCliente.get(i).getTelefono().substring(0)) <6){
                    clientesViewHolder.linearWhatsapp.setVisibility(View.GONE);
                    clientesViewHolder.linearLlamar.setVisibility(View.VISIBLE);
                }else{
                    clientesViewHolder.linearWhatsapp.setVisibility(View.VISIBLE);
                    clientesViewHolder.linearLlamar.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){

            }

        }
        clientesViewHolder.linearLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.showCellPhone(v, listaCliente.get(i));
            }
        });
        clientesViewHolder.linearWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.sendMessageWhatsapp(v,listaCliente.get(i));
            }
        });
        clientesViewHolder.linearUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.viewUbicacion(v,listaCliente.get(i));
            }
        });
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_cliente, viewGroup, false);

        return new ClientesViewHolder(itemView);
    }



    public static class ClientesViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivAdapterImg;
        protected TextView TvAdapterNombre;
        protected TextView TvAdapterDireccion;
        protected LinearLayout linearUbicacion;
        protected  LinearLayout linearLlamar;
        protected  LinearLayout linearWhatsapp;
        protected CardView cardCliente;
        public ClientesViewHolder(View v) {
            super(v);
            ivAdapterImg = (ImageView) v.findViewById(R.id.row_cliente_img);
            TvAdapterNombre = (TextView) v.findViewById(R.id.row_cliente_name);
            TvAdapterDireccion=(TextView) v.findViewById(R.id.row_cliente_direccion);
            linearUbicacion=(LinearLayout)v.findViewById(R.id.listcliente_ubicacion);
            linearLlamar=(LinearLayout)v.findViewById(R.id.listcliente_llamar);
            linearWhatsapp=(LinearLayout)v.findViewById(R.id.listcliente_wp);
            cardCliente=(CardView)v.findViewById(R.id.id_cardview_cliente);
        }
    }

    public void setFilter(List<ClienteEntity> ListaFiltrada){
        this.listaCliente =new ArrayList<>();
        this.listaCliente.addAll(ListaFiltrada);
        notifyDataSetChanged();
    }
}
