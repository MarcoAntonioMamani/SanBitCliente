package com.sanbit.tevendo.Sincronizar.Cloud;

import android.content.Context;

import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PrecioEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Sincronizar.DataPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static IUsersApi service;
    public static ApiManager apiManager;
private static Context mcontext;
    private ApiManager(Context context) {
        String Url="";
        if (DataPreferences.getPref("servicio",context)==null){
           Url="http://192.168.0.12:3050";
        }else{
            Url=DataPreferences.getPref("servicio",context);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(IUsersApi.class);
    }

    public static ApiManager getInstance(Context context) {
       if (apiManager == null) {
            apiManager = new ApiManager( context);
        }
        mcontext=context;
        return apiManager;
    }

    public void LoginUser(Bodylogin user, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.LoginUser(user);
        userCall.enqueue(callback);
    }
    public void InsertUser(ClienteEntity user, String idRepartidor, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.InsertUser(user,idRepartidor);
        userCall.enqueue(callback);
    }
    public void UpdateUser(ClienteEntity user, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.UpdateUser(user);
        userCall.enqueue(callback);
    }
    public void InsertTracking(BodyLocation user, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.InsertTracking(user);
        userCall.enqueue(callback);
    }

    public void InsertPedido(PedidoEntity user, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.InsertPedido(user);
        userCall.enqueue(callback);
    }
    public void UpdatePedido(PedidoEntity user, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.UpdatePedido(user);
        userCall.enqueue(callback);
    }
    public void InsertDetalle(List<DetalleEntity> user, String oanumi, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.InsertDetalle(user,oanumi);
        userCall.enqueue(callback);
    }

    public void UpdateDetalle(List<DetalleEntity> user, String oanumi, Callback<ResponseLogin> callback) {
        Call<ResponseLogin> userCall = service.UpdateDetalle(user,oanumi);
        userCall.enqueue(callback);
    }
    public void ObtenerClientes( Callback<List<ClienteEntity>> callback) {
        Call<List<ClienteEntity>> userCall = service.ObtenerClientes();
        userCall.enqueue(callback);
    }


    public void ObtenerPrecios( Callback<List<PrecioEntity>> callback) {
        Call<List<PrecioEntity>> userCall = service.ObtenerPrecios();
        userCall.enqueue(callback);
    }
    public void ObtenerProductos( Callback<List<ProductoEntity>> callback) {
        Call<List<ProductoEntity>> userCall = service.ObtenerProductos();
        userCall.enqueue(callback);
    }
    public void ObtenerPedidos(Callback<List<PedidoEntity>> callback) {
        Call<List<PedidoEntity>> userCall = service.ObtenerPedidos();
        userCall.enqueue(callback);
    }
    public void ObtenerDetalles(Callback<List<DetalleEntity>> callback) {
        Call<List<DetalleEntity>> userCall = service.ObtenerDetalles();
        userCall.enqueue(callback);
    }

    public void ObtenerStock(Callback<List<StockEntity>> callback) {
        Call<List<StockEntity>> userCall = service.ObtenerStocks();
        userCall.enqueue(callback);
    }
}
