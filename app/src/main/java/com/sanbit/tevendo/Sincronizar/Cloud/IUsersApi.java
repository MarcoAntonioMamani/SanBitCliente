package com.sanbit.tevendo.Sincronizar.Cloud;


import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PrecioEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUsersApi {


    @GET("/api/sanbit/clientes")
    Call<List<ClienteEntity>> ObtenerClientes();
    @GET("/api/sanbit/precios")
    Call<List<PrecioEntity>> ObtenerPrecios();

    @GET("/api/sanbit/productos")
    Call<List<ProductoEntity>> ObtenerProductos();
    @GET("/api/sanbit/pedidos")
    Call<List<PedidoEntity>> ObtenerPedidos();
    @GET("/api/sanbit/detalles")
    Call<List<DetalleEntity>> ObtenerDetalles();
    @GET("/api/sanbit/categorias")
    Call<List<CategoriaEntity>> ObtenerCategorias();

    @GET("/api/sanbit/productosImagenes")
    Call<List<ProductosImagenesEntity>> ObtenerImagenes();

    @GET("/api/sanbit/stock")
    Call<List<StockEntity>> ObtenerStocks();


    @POST("/api/repartidor/login")
    Call<ResponseLogin> LoginUser(@Body Bodylogin user);

    @POST("/api/repartidor/clients/{idrepartidor}")
    Call<ResponseLogin> InsertUser(@Body ClienteEntity user, @Path("idrepartidor") String idRepartidor);

    @PUT("/api/repartidor/clients")
    Call<ResponseLogin> UpdateUser(@Body ClienteEntity user);
    @POST("/api/repartidor/pedido")
    Call<ResponseLogin> InsertPedido(@Body PedidoEntity user);

    @PUT("/api/repartidor/pedido")
    Call<ResponseLogin> UpdatePedido(@Body PedidoEntity user);

    @POST("/api/repartidor/detalle/{oanumi}")
    Call<ResponseLogin> InsertDetalle(@Body List<DetalleEntity> listDetalle, @Path("oanumi") String oanumi);

    @PUT("/api/repartidor/detalle/{oanumi}")
    Call<ResponseLogin> UpdateDetalle(@Body List<DetalleEntity> listDetalle, @Path("oanumi") String oanumi);

    @POST("/api/repartidor/tracking")
    Call<ResponseLogin> InsertTracking(@Body BodyLocation user);
}
