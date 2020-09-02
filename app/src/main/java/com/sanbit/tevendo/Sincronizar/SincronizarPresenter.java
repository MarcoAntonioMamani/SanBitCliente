package com.sanbit.tevendo.Sincronizar;

import android.app.Activity;
import android.content.Context;


import com.google.android.gms.common.internal.Preconditions;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ClientesListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleEntity;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PrecioEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PreciosListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockListViewModel;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductosListViewModel;
import com.sanbit.tevendo.Sincronizar.Cloud.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizarPresenter implements SincronizarMvp.Presenter {
    private final SincronizarMvp.View mSincronizarview;
    private final Context mContext;
    private final ClientesListViewModel viewModel;
    private final PreciosListViewModel viewModelPrecios;
    private final ProductosListViewModel viewModelProductos;
    private final PedidoListViewModel viewModelPedidos;
    private final DetalleListViewModel viewModelDetalles;
    private final StockListViewModel viewModelStock;
    private final CategoriaListViewModel viewModelCategoria;
    private final ProductosImagenesListViewModel viewModelImagenes;

    private final Activity activity;
     int cantidadCliente = 0;
    int cantidadProducto=0;
    int cantidadPrecio=0;
    int cantidadPedidos=0;
    String Mensaje="";
    int CantidadPenticiones=0;
    int Contador=0;


    public SincronizarPresenter(SincronizarMvp.View sincronizarView, Context context, ClientesListViewModel viewModel, Activity activity, PreciosListViewModel
                                viewModelPrecios, ProductosListViewModel viewModelProductos, PedidoListViewModel viewModelPedidos,
                                DetalleListViewModel viewModelDetalles, StockListViewModel stock,CategoriaListViewModel cate,
                                ProductosImagenesListViewModel imagenes){
        mSincronizarview = Preconditions.checkNotNull(sincronizarView);
        mSincronizarview.setPresenter(this);
        this.viewModel=viewModel;
        this.mContext=context;
        this.activity=activity;
        this.viewModelPrecios=viewModelPrecios;
        this.viewModelProductos=viewModelProductos;
        this.viewModelPedidos=viewModelPedidos;
        this.viewModelDetalles=viewModelDetalles;
        this.viewModelStock=stock;
        this.viewModelCategoria=cate;
        this.viewModelImagenes=imagenes;
         cantidadCliente = 0;
       cantidadProducto=0;
         cantidadPrecio=0;
         CantidadPenticiones=0;
        Contador=0;
    }
    @Override
    public void GuadarDatos(boolean producto,boolean personal,boolean cliente,boolean pedidos) {
        CantidadPenticiones=0;
        Contador=0;
        Mensaje="";
        cantidadPedidos=0;
        CantidadPenticiones=(producto==true? 1:0)+(personal==true? 1:0)+(cliente==true? 1:0)+(pedidos==true? 1:0);
        String Mensaje="";
        if (cliente==true ){
            _DescargarClientes();
        }
        if ( personal==true){
            _DecargarPrecios();
        }
        if (producto== true){
            _DecargarProductos();
        }
        if (pedidos== true){
            _DecargarPedidos();
        }
    }

    public void _DecargarPrecios(){
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerPrecios( new Callback<List<PrecioEntity>>() {
            @Override
            public void onResponse(Call<List<PrecioEntity>> call, Response<List<PrecioEntity>> response) {
                final List<PrecioEntity> responseUser = (List<PrecioEntity>) response.body();
                if (response.code()==404){
                    mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    try {
                        List<PrecioEntity> listCliente = viewModelPrecios.getMAllPrecio(1);
                        if (listCliente.size() <= 0) {
                            for (int i = 0; i < responseUser.size(); i++) {
                                PrecioEntity precio = responseUser.get(i);
                                viewModelPrecios.insertPrecio(precio);
                            }
                           cantidadPrecio+=responseUser.size();
                           // mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }else{
                              viewModelPrecios.deleteAllPrecios();
                           List<PrecioEntity> listupdate=new ArrayList<>();
                           List<PrecioEntity> listinsert=new ArrayList<>();
                            for (int i = 0; i < responseUser.size(); i++) {
                                PrecioEntity precio = responseUser.get(i);
                                //viewModel.insertCliente(cliente);
                                PrecioEntity dbprecio=viewModelPrecios.getPrecio(precio.getId());
                                if (dbprecio==null){
                                    viewModelPrecios.insertPrecio(precio);
                                }else{

                                            listupdate.add(precio);
                                    //viewModelPrecios.updatePrecio(precio);
                                }

                            }
                        if (listupdate.size()>0){
                            PrecioEntity[]  listu=new PrecioEntity[listupdate.size()];
                            for (int i = 0; i < listupdate.size(); i++) {
                                listu[i]=listupdate.get(i);
                            }
                            viewModelPrecios.updateListPrecio(listu);
                        }
                          //  mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }

                        Contador+=1;
                        if (Contador==CantidadPenticiones){
                            Mensaje+=" "+responseUser.size()+" Precios";
                            mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + getMensaje());
                        }else{
                            Mensaje+=" "+responseUser.size()+" Precios , ";
                        }
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Precios : "+e.getMessage());
                    } catch (InterruptedException e) {
                        //   e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Precios: "+e.getMessage());
                    }



                } else {
                    mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Precios");
                }
            }

            @Override
            public void onFailure(Call<List<PrecioEntity>> call, Throwable t) {
                mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio.");
            }
        });
    }
    public void _DescargarClientes(){
        ApiManager apiManager= ApiManager.getInstance(mContext);
        apiManager.ObtenerClientes( new Callback<List<ClienteEntity>>() {
            @Override
            public void onResponse(Call<List<ClienteEntity>> call, Response<List<ClienteEntity>> response) {
                final List<ClienteEntity> responseUser = (List<ClienteEntity>) response.body();
                if (response.code()==404){
                    mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    try {
                        List<ClienteEntity> listCliente = viewModel.getMAllCliente(1);
                        if (listCliente.size() <= 0) {
                            for (int i = 0; i < responseUser.size(); i++) {
                                ClienteEntity cliente = responseUser.get(i);
                                viewModel.insertCliente(cliente);
                            }
                            cantidadCliente=responseUser.size();
                            //mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Clientes");
                        }else{
                            viewModel.deleteAllClientes();
                            List<ClienteEntity> listupdate=new ArrayList<>();

                            for (int i = 0; i < responseUser.size(); i++) {
                                ClienteEntity cliente = responseUser.get(i);
                                //viewModel.insertCliente(cliente);
                                ClienteEntity dbcliente=viewModel.getClienteNumi(cliente.getNumi());
                                if (dbcliente==null){
                                    viewModel.insertCliente(cliente);
                                }else{

                                    listupdate.add(cliente);
                                    //viewModelPrecios.updatePrecio(precio);
                                }

                            }
                            if (listupdate.size()>0){
                                ClienteEntity[]  listu=new ClienteEntity[listupdate.size()];
                                for (int i = 0; i < listupdate.size(); i++) {
                                    listu[i]=listupdate.get(i);
                                }
                                viewModel.updateListCliente(listu);
                            }
                           // mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Clientes");
                        }
                        Contador+=1;
                        if (Contador==CantidadPenticiones){
                            Mensaje+=" "+responseUser.size()+" Clientes";
                            mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + getMensaje());
                        }else{
                            Mensaje+=" "+responseUser.size()+" Clientes , ";
                        }
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Clientes: "+e.getMessage());
                    } catch (InterruptedException e) {
                        //   e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Clientes : "+e.getMessage());
                    }



                } else {
                    mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Clientes");
                }
            }

            @Override
            public void onFailure(Call<List<ClienteEntity>> call, Throwable t) {
                mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio.");
            }
        });
    }
    public void _DecargarProductos(){
        _DescargarCategoria();
        _DescargarImagenes();
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerProductos( new Callback<List<ProductoEntity>>() {
            @Override
            public void onResponse(Call<List<ProductoEntity>> call, Response<List<ProductoEntity>> response) {
                final List<ProductoEntity> responseUser = (List<ProductoEntity>) response.body();
                if (response.code()==404){
                    mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    _DescargarStock();
                    try {
                        List<ProductoEntity> listCliente = viewModelProductos.getMAllProducto(1);
                        if (listCliente.size() <= 0) {
                            for (int i = 0; i < responseUser.size(); i++) {
                                ProductoEntity producto = responseUser.get(i);
                                viewModelProductos.insertProducto(producto);
                            }
                            cantidadProducto+=responseUser.size();
                            // mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }else{
                          /*  viewModelProductos.deleteAllProductos();
                            for (int i = 0; i < responseUser.size(); i++) {
                                ProductoEntity producto = responseUser.get(i);
                                viewModelProductos.insertProducto(producto);
                            }*/
                          viewModelProductos.deleteAllProductos();
                            for (int i = 0; i < responseUser.size(); i++) {
                                ProductoEntity producto = responseUser.get(i);
                                //viewModel.insertCliente(cliente);
                                ProductoEntity dbproducto=viewModelProductos.getProducto(producto.getId() );
                                if (dbproducto==null){
                                    viewModelProductos.insertProducto(producto);
                                }else{
                                    viewModelProductos.updateProducto(producto);
                                }

                            }
                            //  mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }

                        Contador+=1;
                        if (Contador==CantidadPenticiones){
                            Mensaje+=" "+responseUser.size()+" Productos";
                            mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + getMensaje());
                        }else{
                            Mensaje+=" "+responseUser.size()+" Productos , ";
                        }
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos : "+e.getMessage());
                    } catch (InterruptedException e) {
                        //   e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos: "+e.getMessage());
                    }



                } else {
                    mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos");
                }
            }

            @Override
            public void onFailure(Call<List<ProductoEntity>> call, Throwable t) {
                mSincronizarview.ShowMessageResult("No es posible conectarse con el web services.");
            }
        });
    }

    public void _DescargarStock(){


        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerStock(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                final List<StockEntity> responseUser = (List<StockEntity>) response.body();
                if (response.code() == 404) {
                    // mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    try {
                        List<StockEntity> listStock = viewModelStock.getAllStock();

                        for (int i = 0; i < responseUser.size(); i++) {
                            StockEntity stock = responseUser.get(i);  //Obtenemos el registro del server
                            //viewModel.insertCliente(cliente);
                            StockEntity dbStock = viewModelStock.getStock(stock.getProductoId() );
                            if (dbStock == null) {
                                viewModelStock.insertStock(stock);
                            } else {
                                for (int j = 0; j < listStock.size(); j++) {
                                    StockEntity dbStock02=listStock.get(j);

                                    if (stock.getProductoId()==dbStock02.getProductoId()&&stock.getCantidad()!=dbStock02.getCantidad()){
                                        viewModelStock.updateStock(stock);
                                    }

                                }
                            }


                        }

                    } catch (ExecutionException e) {
                    } catch (InterruptedException e) {
                    }

                } else {
                    // mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos");
                }
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {

            }
        });
    }

    public void _DescargarCategoria(){
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerCategorias(new Callback<List<CategoriaEntity>>() {
            @Override
            public void onResponse(Call<List<CategoriaEntity>> call, Response<List<CategoriaEntity>> response) {
                final List<CategoriaEntity> responseUser = (List<CategoriaEntity>) response.body();
                if (response.code() == 404) {
                    // mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {

                        viewModelCategoria.deleteAllCategorias();
                        for (int i = 0; i < responseUser.size(); i++) {
                            CategoriaEntity stock = responseUser.get(i);  //Obtenemos el registro del server
                            viewModelCategoria.insertCategorias(stock);



                        }


                } else {
                    // mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos");
                }
            }

            @Override
            public void onFailure(Call<List<CategoriaEntity>> call, Throwable t) {

            }
        });
    }
    public void _DecargarPedidos(){
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerPedidos( new Callback<List<PedidoEntity>>() {
            @Override
            public void onResponse(Call<List<PedidoEntity>> call, Response<List<PedidoEntity>> response) {
                final List<PedidoEntity> responseUser = (List<PedidoEntity>) response.body();
                if (response.code()==404){
                    mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    try {
                        List<PedidoEntity> listCliente = viewModelPedidos.getMAllPedido(1);
                        viewModelPedidos.deleteAllPedido();
                        viewModelDetalles.deleteAllDetalles();
                        if (listCliente.size() <= 0) {
                            for (int i = 0; i < responseUser.size(); i++) {
                                PedidoEntity pedido = responseUser.get(i);
                                if (pedido.getEstadoPedido() !=3){

                                    if (pedido.getEstadoPedido() == 1) {
                                        pedido.setEstadoPedido(2) ;
                                        pedido.setEstado(2);
                                        viewModelPedidos.insertPedido(pedido);

                                    }else{
                                        viewModelPedidos.insertPedido(pedido);
                                    }


                                }

                            }

                            // mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }else{
                          /*  viewModelProductos.deleteAllProductos();
                            for (int i = 0; i < responseUser.size(); i++) {
                                ProductoEntity producto = responseUser.get(i);
                                viewModelProductos.insertProducto(producto);
                            }*/
                            for (int i = 0; i < responseUser.size(); i++) {
                                PedidoEntity pedido = responseUser.get(i);
                                //viewModel.insertCliente(cliente);
                                PedidoEntity dbproducto=viewModelPedidos.getPedido(pedido.getOanumi());
                                if (dbproducto==null){
                                    viewModelPedidos.insertPedido(pedido);
                                }else{
                                    viewModelPedidos.updatePedido(pedido);
                                }

                            }

                        }
                        cantidadPedidos=responseUser.size();
                        _DecargarDetalles();
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos : "+e.getMessage());
                    } catch (InterruptedException e) {
                        //   e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos: "+e.getMessage());
                    }



                } else {
                    mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos");
                }
            }

            @Override
            public void onFailure(Call<List<PedidoEntity>> call, Throwable t) {
                mSincronizarview.ShowMessageResult("No es posible conectarse con el web services.");
            }
        });
    }

    public void _DecargarDetalles(){
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerDetalles( new Callback<List<DetalleEntity>>() {
            @Override
            public void onResponse(Call<List<DetalleEntity>> call, Response<List<DetalleEntity>> response) {
                final List<DetalleEntity> responseUser = (List<DetalleEntity>) response.body();
                if (response.code()==404){
                    mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {
                    try {
                        List<DetalleEntity> listDetalle = viewModelDetalles.getMAllDetalle(1);
                        if (listDetalle.size() <= 0) {
                            for (int i = 0; i < responseUser.size(); i++) {
                                DetalleEntity pedido = responseUser.get(i);
                                viewModelDetalles.insertDetalle(pedido);
                            }
                            // mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + responseUser.size() + " Precios");
                        }else{
                          /*  viewModelProductos.deleteAllProductos();
                            for (int i = 0; i < responseUser.size(); i++) {
                                ProductoEntity producto = responseUser.get(i);
                                viewModelProductos.insertProducto(producto);
                            }*/
                            for (int i = 0; i < responseUser.size(); i++) {
                                DetalleEntity detalle = responseUser.get(i);

                                if (!existeDetalle(listDetalle,detalle)){
                                    viewModelDetalles.insertDetalle(detalle);
                                }else{
                                    viewModelDetalles.updateDetalle(detalle);
                                }

                            }

                        }

                        Contador+=1;
                        if (Contador==CantidadPenticiones){
                            Mensaje+=" "+cantidadPedidos+" Pedidos";
                            mSincronizarview.ShowSyncroMgs("Se ha Registrado/Actualizado " + getMensaje());
                        }else{
                            Mensaje+=" "+cantidadPedidos+" Pedidos , ";
                        }
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Detalles : "+e.getMessage());
                    } catch (InterruptedException e) {
                        //   e.printStackTrace();
                        mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Detalles: "+e.getMessage());
                    }



                } else {
                    mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Detalles");
                }
            }

            @Override
            public void onFailure(Call<List<DetalleEntity>> call, Throwable t) {
                mSincronizarview.ShowMessageResult("No es posible conectarse con el web services.");
            }
        });
    }
    public void _DescargarImagenes(){
        ApiManager apiManager=ApiManager.getInstance(mContext);
        apiManager.ObtenerImagenes(new Callback<List<ProductosImagenesEntity>>() {
            @Override
            public void onResponse(Call<List<ProductosImagenesEntity>> call, Response<List<ProductosImagenesEntity>> response) {
                final List<ProductosImagenesEntity> responseUser = (List<ProductosImagenesEntity>) response.body();
                if (response.code() == 404) {
                    // mSincronizarview.ShowMessageResult("No es posible conectarse con el servicio. "+ response.message());
                    return;
                }
                if (response.isSuccessful() && responseUser != null) {

                    viewModelImagenes.deleteAllImagenes();
                    for (int i = 0; i < responseUser.size(); i++) {
                        ProductosImagenesEntity stock = responseUser.get(i);  //Obtenemos el registro del server
                        viewModelImagenes.insertImagenes(stock);



                    }


                } else {
                    // mSincronizarview.ShowMessageResult("No se pudo Obtener Datos del Servidor para Productos");
                }
            }

            @Override
            public void onFailure(Call<List<ProductosImagenesEntity>> call, Throwable t) {

            }
        });
    }
    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public int getCantidadPenticiones() {
        return CantidadPenticiones;
    }

    public void setCantidadPenticiones(int cantidadPenticiones) {
        CantidadPenticiones = cantidadPenticiones;
    }

    public boolean existeDetalle(List<DetalleEntity> listDetalle, DetalleEntity detail){
        for (int i = 0; i < listDetalle.size(); i++) {
            DetalleEntity detalle=listDetalle.get(i);
            if (detalle.getPedidoId().toString().trim().equals(detail.getPedidoId().toString().trim())&& detalle.getProductoId()==detail.getProductoId()){
                return true;
            }
        }
        return false;
    }

}
