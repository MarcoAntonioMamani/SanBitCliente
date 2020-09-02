package com.sanbit.tevendo.Productos.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.sanbit.tevendo.Productos.Estados.ProductosEstados;
import com.sanbit.tevendo.Productos.Estados.ProductosEstadosMvp;
import com.sanbit.tevendo.Productos.ProductorMvp;
import com.sanbit.tevendo.R;

import java.util.ArrayList;
import java.util.List;

public class ProductosEstadosAdapter extends RecyclerView.Adapter<ProductosEstadosAdapter.EmpresasViewHolder> {
private List<ProductosEstados> listaEmpresas;
private Context context;
private ProductosEstadosMvp.View mview;

private Activity activity;
public ProductosEstadosAdapter(Context ctx, List<ProductosEstados> s, ProductosEstadosMvp.View view, Activity act) {
        this.context = ctx;
        this.listaEmpresas = s;
        this.mview = view;
        this.activity=act;
        }

public ProductosEstadosAdapter(Context ctx) {
        this.context = ctx;

        }

@Override
public int getItemCount() {
        return listaEmpresas.size();
        }


@Override
public void onBindViewHolder(EmpresasViewHolder clientesViewHolder, final int i) {
        clientesViewHolder.tvTitulo.setText((CharSequence) listaEmpresas.get(i).getContenido());


    final LinearLayout card=clientesViewHolder.linearFondo;

    if (listaEmpresas.get(i).getEstado()==0){  //Listar Todos
    clientesViewHolder.linearFondo.setBackground(activity.getResources().getDrawable(R.drawable.animation_bottonprimary));
    }
    if (listaEmpresas.get(i).getEstado()==1){  //Listar Stock Mayor a 0
        clientesViewHolder.linearFondo.setBackground(activity.getResources().getDrawable(R.drawable.animation_bottonestadoactivo));
    }
    if (listaEmpresas.get(i).getEstado()==2){  //Listar Stock Minimo
        clientesViewHolder.linearFondo.setBackground(activity.getResources().getDrawable(R.drawable.animation_botton));
    }
    if (listaEmpresas.get(i).getEstado()==3){  //Listar Stock 0

        clientesViewHolder.linearFondo.setBackground(activity.getResources().getDrawable(R.drawable.animation_bottoncancelrojo));
    }
    clientesViewHolder.linearFondo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        mview.recyclerViewListClicked(v,listaEmpresas.get(i));
        }
    });



        }




@Override
public EmpresasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
        from(viewGroup.getContext()).
        inflate(R.layout.card_producto_estado, viewGroup, false);

        return new EmpresasViewHolder(itemView);
        }


public static class EmpresasViewHolder extends RecyclerView.ViewHolder {


    protected TextView tvTitulo;

   LinearLayout linearFondo;
    public EmpresasViewHolder(View v) {
        super(v);

        tvTitulo=(TextView)v.findViewById(R.id.producto_estado_titulo);
        linearFondo=(LinearLayout)v.findViewById(R.id.linear_producto_estado);
    }
}

    public void setFilter(List<ProductosEstados> ListaFiltrada) {
        this.listaEmpresas = new ArrayList<>();
        this.listaEmpresas.addAll(ListaFiltrada);
        notifyDataSetChanged();
    }

}
