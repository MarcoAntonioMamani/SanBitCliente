package com.sanbit.tevendo.Mapas.Mapas;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.DataCache;


/**
 * Created by usuario on 23/11/2017.
 */

public class CustomInfoWindowAdapterMapa implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener{
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    private ClienteEntity mclient;
    ImageView img ;
    RelativeLayout panel;
    public CustomInfoWindowAdapterMapa(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        String posicion =m.getSnippet();
        View v = inflater.inflate(R.layout.info_windows_layout, null);
        mclient= DataCache.cliente;
        panel=(RelativeLayout)v.findViewById(R.id.info_cliente);
        img=(ImageView) v.findViewById(R.id.id_img_client);

        panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int i=0;
            }
        });
        m.setTag(mclient);
        /*
        Glide.with(v.getContext())
                .load("")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .override(200, 105)//Target.SIZE_ORIGINAL
                .placeholder(R.drawable.ic_client)
                .into(img);*/
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(mclient.getNamecliente());

        img.setImageDrawable(TextDrawable.builder().buildRound(mclient.getNamecliente().substring(0, 1), color));


        ((TextView)v.findViewById(R.id.view_cliente_nombre)).setText(mclient.getNamecliente());
        ((TextView)v.findViewById(R.id.view_cliente_direccion)).setText(mclient.getDireccion());
        ((TextView)v.findViewById(R.id.view_cliente_telefono)).setText("Telf: "+mclient.getTelefono());
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {

        return null;
    }


    @Override
    public void onInfoWindowClick(Marker marker){
        Object obj=marker;
    }

}
