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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Productos.ProductorMvp;
import com.sanbit.tevendo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.EmpresasViewHolder> {
private List<ProductoEntity> listaEmpresas;
private Context context;
private ProductorMvp.View mview;

private Activity activity;
public ProductosAdapter(Context ctx, List<ProductoEntity> s, ProductorMvp.View view,Activity act) {
        this.context = ctx;
        this.listaEmpresas = s;
        this.mview = view;
        this.activity=act;
        }

public ProductosAdapter(Context ctx) {
        this.context = ctx;

        }

@Override
public int getItemCount() {
        return listaEmpresas.size();
        }


@Override
public void onBindViewHolder(EmpresasViewHolder clientesViewHolder, final int i) {
        clientesViewHolder.tvNameMarca.setText((CharSequence) listaEmpresas.get(i).getNameCategoria());
        clientesViewHolder.tvNombreProducto.setText((CharSequence) listaEmpresas.get(i).getNameProducto());
        clientesViewHolder.tvPrecios .setText(""+ listaEmpresas.get(i).getPrecioVenta()+" Bs");
        clientesViewHolder.tvCantidad.setText(""+listaEmpresas.get(i).getStock());

    final LinearLayout card=clientesViewHolder.linearFondo;

    if (listaEmpresas.get(i).getStock()<=0){
    clientesViewHolder.linearstock.setBackground(activity.getResources().getDrawable(R.drawable.animation_bottoncancelrojo));
    }
    if (listaEmpresas.get(i).getStock()>0 && listaEmpresas.get(i).getStock()>listaEmpresas.get(i).getStockMinimo()){
        clientesViewHolder.linearstock.setBackground(activity.getResources().getDrawable(R.drawable.animation_bottonestadoactivo));
    }
    if (listaEmpresas.get(i).getStock()>0 && listaEmpresas.get(i).getStock()<=listaEmpresas.get(i).getStockMinimo()){
        clientesViewHolder.linearstock.setBackground(activity.getResources().getDrawable(R.drawable.animation_botton));
    }
        Glide.with(activity.getApplicationContext())
                .load(listaEmpresas.get(i).getImageProducto())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .override(300, 200)//Target.SIZE_ORIGINAL
                .placeholder(R.drawable.noimage)
                .into(clientesViewHolder.ivAdapterImg);



        clientesViewHolder.cardProductos.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //mview.recyclerViewListClicked(v, listaEmpresas.get(i));
        }
        });

        clientesViewHolder.linearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview.recyclerViewListClicked(v, listaEmpresas.get(i));
            }
        });

        }




@Override
public EmpresasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
        from(viewGroup.getContext()).
        inflate(R.layout.card_productos, viewGroup, false);

        return new EmpresasViewHolder(itemView);
        }


public static class EmpresasViewHolder extends RecyclerView.ViewHolder {

    protected ImageView ivAdapterImg;
    protected TextView tvNameMarca;
    protected  TextView tvNombreProducto;
    protected TextView tvPrecios;
protected TextView tvCantidad;
    protected CardView cardProductos;
   LinearLayout linearFondo;
   LinearLayout linearProducto;
   LinearLayout linearstock;
    public EmpresasViewHolder(View v) {
        super(v);
        ivAdapterImg = (ImageView) v.findViewById(R.id.producto_img);
        tvNameMarca = (TextView) v.findViewById(R.id.nombre_marca);
        tvNombreProducto = (TextView) v.findViewById(R.id.nombre_Producto);
        tvPrecios = (TextView) v.findViewById(R.id.producto_precio);
        tvCantidad=(TextView)v.findViewById(R.id.producto_cantidad);
        linearFondo=(LinearLayout)v.findViewById(R.id.linear_fondo);
        cardProductos = (CardView) v.findViewById(R.id.card_Productos);
        linearProducto=(LinearLayout)v.findViewById(R.id.linear_producto);
        linearstock=(LinearLayout)v.findViewById(R.id.linear_producto_stock);
    }
}

    public void setFilter(List<ProductoEntity> ListaFiltrada) {
        this.listaEmpresas = new ArrayList<>();
        this.listaEmpresas.addAll(ListaFiltrada);
        notifyDataSetChanged();
    }

}
