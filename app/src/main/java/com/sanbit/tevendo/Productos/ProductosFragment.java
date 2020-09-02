package com.sanbit.tevendo.Productos;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesListViewModel;
import com.sanbit.tevendo.Productos.Adapter.CategoriaAdapter;
import com.sanbit.tevendo.Productos.Adapter.ProductosAdapter;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductosListViewModel;
import com.sanbit.tevendo.Productos.Estados.ProductosEstados;
import com.sanbit.tevendo.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.DataPreferences;
import com.sanbit.tevendo.ShareUtil.LocationGeo;
import com.sanbit.tevendo.ShareUtil.ShareMethods;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment  implements ProductorMvp.View, SearchView.OnQueryTextListener{
    private ProductorMvp.Presenter mSincronizarPresenter;
    LottieAlertDialog alertDialog;

    public ProductosAdapter adapterPerfil;
    public CategoriaAdapter adapterCategoria;
    RecyclerView recListCategoria;
    Context context;
    RecyclerView recList;
    SearchView simpleSearchView;
    List<ProductoEntity> mListEmpresas=new ArrayList<>();
    List<CategoriaEntity> ListCategoriaEntity=new ArrayList<>();
    ProductosListViewModel mProducto;
    CategoriaListViewModel mCategoria;
    ProductosImagenesListViewModel  mImagenes;
    LinearLayout linearTotal;
    TextView CantidadPedido;
    TextView TotalPedido;
    TextView tvVenta;
    LinearLayout btnCarrito;

    ProductosEstados estado=null;
    public ProductosFragment() {
        // Required empty public constructor
    }
    public ProductosFragment(ProductosEstados estado) {
        this.estado=estado;
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("");
        DataCache.tvTitleMenu.setText("Ver Productos");
        if (DataCache.ListCategorias!=null){
            ListCategoriaEntity=DataCache.ListCategorias;
            if (adapterCategoria!=null){
                adapterCategoria.setFilter(ListCategoriaEntity);
                adapterCategoria.notifyDataSetChanged();

            }else{
                CargarRecyclerCategoria(ListCategoriaEntity);
            }


        }
        if (DataCache.listProductos!=null){

            mListEmpresas=listProductoFiltrados(DataCache.listProductos);
            if (adapterPerfil!=null){
                adapterPerfil.setFilter(mListEmpresas);
                adapterPerfil.notifyDataSetChanged();
            }else{
                CargarRecycler(mListEmpresas);
            }

        }
        if (DataCache.listProductos!=null ){

            CantidadPedido.setText(ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoStock(),2)+" Items");
            TotalPedido.setText("Compra: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioCompra(),2)+" Bs");
            tvVenta.setText("Venta: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioVenta(),2)+" Bs");
        }
      //  SetearTotalesResumen();
        context=getContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocationGeo.getInstance(getContext(),getActivity());
        LocationGeo.PedirPermisoApp();
        if (DataCache.ListCategorias!=null){
            ListCategoriaEntity=DataCache.ListCategorias;
            if (adapterCategoria!=null){
                adapterCategoria.setFilter(ListCategoriaEntity);
                adapterCategoria.notifyDataSetChanged();

            }else{
                CargarRecyclerCategoria(ListCategoriaEntity);
            }


        }
        if (DataCache.listProductos!=null){
            mListEmpresas=listProductoFiltrados(DataCache.listProductos);
            if (adapterPerfil!=null){
                adapterPerfil.setFilter(mListEmpresas);
                adapterPerfil.notifyDataSetChanged();
            }else{
                CargarRecycler(mListEmpresas);
            }

        }
        if (DataCache.listProductos!=null ){

            CantidadPedido.setText(ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoStock(),2)+" Items");
            TotalPedido.setText("Compra: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioCompra(),2)+" Bs");
            tvVenta.setText("Venta: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioVenta(),2)+" Bs");
        }


        // SetearTotalesResumen();
        context=getContext();
    }
    public void SetearListCategoriaEntity(){
        List<CategoriaEntity> list=new ArrayList<>();
        list.add(new CategoriaEntity(0,"  Todos  ","","",1));
        for (int i = 0; i < DataCache.ListCategorias.size(); i++) {
          ;

                list.add(DataCache.ListCategorias.get(i));


        }
        ListCategoriaEntity=list;


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        recListCategoria=(RecyclerView) view.findViewById(R.id.Productos_CardCategoria);
        linearTotal=(LinearLayout)view.findViewById(R.id.linear_totalorder);
        CantidadPedido=(TextView)view.findViewById(R.id.cantidad_order);
        TotalPedido=(TextView)view.findViewById(R.id.total_order);
        btnCarrito=(LinearLayout)view.findViewById(R.id.linear_list_carrito);
        recListCategoria.setHasFixedSize(true);
        recList = (RecyclerView) view.findViewById(R.id.Productos_CardList);
        recList.setHasFixedSize(true);
        simpleSearchView = (SearchView) view.findViewById (R.id.simpleSearchViewProductos);
        mProducto= ViewModelProviders.of(getActivity()).get(ProductosListViewModel.class);
        mCategoria=ViewModelProviders.of(getActivity()).get(CategoriaListViewModel.class);
        mImagenes=ViewModelProviders.of(getActivity()).get(ProductosImagenesListViewModel.class);
        simpleSearchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        simpleSearchView.setIconifiedByDefault(false);
        tvVenta=(TextView)view.findViewById(R.id.producto_precioventa);
        context=getContext();
        new ProductosPresenter(this,getContext(),mProducto,mCategoria,mImagenes);

        if (DataCache.listProductos==null &&DataCache.ListCategorias==null){
            showDialogs();
            new ChecarNotificaciones().execute();
        }else{
            CantidadPedido.setText(ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoStock(),2)+" Items");
            TotalPedido.setText("Compra: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioCompra(),2)+" Bs");
            tvVenta.setText("Venta: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioVenta(),2)+" Bs");
        }

           

        

        return view;
    }

    public double  ObtenerTotalProductoPrecioCompra(){
        double compra=0;
        if (mListEmpresas==null){
            return 0;
        }
        if (mListEmpresas.size()>0){
            List<ProductoEntity> list=mListEmpresas;
            for (int i = 0; i < list.size(); i++) {
                compra=compra+(list.get(i).getStock()*list.get(i).getPrecioCosto());
            }
            return compra;
        }else{
            return 0;
        }

    }
    public double  ObtenerTotalProductoStock(){
        double compra=0;
        if (mListEmpresas==null){
            return 0;
        }
        if (mListEmpresas.size()>0){
            List<ProductoEntity> list=mListEmpresas;
            for (int i = 0; i < list.size(); i++) {
                compra=compra+(list.get(i).getStock());
            }
            return compra;
        }else{
            return 0;
        }

    }
    public double  ObtenerTotalProductoPrecioVenta(){
        double compra=0;
        if (mListEmpresas==null){
            return 0;
        }
        if (mListEmpresas.size()>0){
            List<ProductoEntity> list=mListEmpresas;
            for (int i = 0; i < list.size(); i++) {
                compra=compra+(list.get(i).getStock()*list.get(i).getPrecioVenta());
            }
            return compra;
        }else{
            return 0;
        }

    }
    public List<ProductoEntity> filter (List<ProductoEntity> bares ,String texto){
        List<ProductoEntity>ListaFiltrada=new ArrayList<>();
        try{
            texto=texto.toLowerCase();
            for (ProductoEntity b:bares){
                String name=b.getNameProducto().toLowerCase();
                if(name.contains(texto)){
                    ListaFiltrada.add(b);
                }
            }
        }catch (Exception e){

        }
        return ListaFiltrada;
    }


    public void showDialogs() {
        ShowDialogSincronizando();
        alertDialog.show();
    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void CargarRecycler(List<ProductoEntity> listCliente){
        if (listCliente!=null){
            adapterPerfil = new ProductosAdapter(context,listCliente,this,getActivity());
            GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
            llm.setOrientation(GridLayoutManager .VERTICAL);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), R.anim.layout_animation_fall_down);
            recList.setLayoutAnimation(controller);
            recList.setLayoutManager(llm);
            recList.setAdapter(adapterPerfil);

            recList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && linearTotal.getVisibility() == View.VISIBLE) {
                        linearTotal.setVisibility(View.GONE);
                    } else if (dy < 0 && linearTotal.getVisibility() != View.VISIBLE) {
                        linearTotal.setVisibility(View.VISIBLE);

                    }
                }
            });
        }

    }
    public void CargarRecyclerCategoria(List<CategoriaEntity> listCliente){
        if (listCliente!=null){
            adapterCategoria = new CategoriaAdapter(context,listCliente,this,getActivity());
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), R.anim.layout_animation_fall_down);
            recListCategoria.setLayoutAnimation(controller);
            recListCategoria.setLayoutManager(llm);
            recListCategoria.setAdapter(adapterCategoria);

          /*  recList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && btnAddCliente.getVisibility() == View.VISIBLE) {
                        btnAddCliente.hide();
                    } else if (dy < 0 && btnAddCliente.getVisibility() != View.VISIBLE) {
                        btnAddCliente.show();
                    }
                }
            });*/
        }

    }
    private void ShowDialogSincronizando(){

        try
        {

            alertDialog = new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_LOADING).setTitle("Tiendas")
                    .setDescription("Obteniendo Datos .....")
                    .build();

            alertDialog.setCancelable(false);
        }catch (Error e){

            String d=e.getMessage();

        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        try{
            List<ProductoEntity> ListaFiltrada=filter(mListEmpresas,newText);
            adapterPerfil.setFilter(ListaFiltrada);
            CambiarEstado(new CategoriaEntity(0,"  Todos  ","","",1));
        }catch (Exception e){

        }
        return false;

    }

    @Override
    public void recyclerViewListClicked(View v, ProductoEntity empresa) {
      /*  Gson gson = new Gson();
        String json = gson.toJson(listDetalle);
        DataPreferences.putPref("Pedido",json,getContext());
        DataCache.ProductoSelected=empresa;
        Fragment frag = new ViewProductFragment();
        MainActivity fca = (MainActivity) getActivity();
        fca.switchFragment(frag,"VIEW_PRODUCTOS");*/
    }

    public void CambiarEstado(CategoriaEntity categoria){
        List<CategoriaEntity> list=new ArrayList<>();
        for (int i = 0; i < ListCategoriaEntity.size(); i++) {
            if (ListCategoriaEntity.get(i).getId()==categoria.getId()){
                CategoriaEntity ca= null;
                try {
                    ca = ListCategoriaEntity.get(i).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                ca.setEstado(1);
                list.add(ca);
            }else{
                CategoriaEntity ca= null;
                try {
                    ca = ListCategoriaEntity.get(i).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                ca.setEstado(0);
                list.add(ca);
            }


        }
        ListCategoriaEntity=list;

        adapterCategoria.setFilter(list);
        adapterCategoria.notifyDataSetChanged();

    }
    @Override
    public void recyclerViewListClickedCategoria(View v, CategoriaEntity empresa, TextView tvCategoria) {


        CambiarEstado(empresa);


        List<ProductoEntity> list=new ArrayList<>();
        if (empresa.getId()==0){
            adapterPerfil.setFilter(mListEmpresas);
        }else{
            for (int i = 0; i < mListEmpresas.size(); i++) {
                if (mListEmpresas.get(i).getCategoriaId()==empresa.getId()){
                    list.add(mListEmpresas.get(i));
                }

            }

            adapterPerfil.setFilter(list);
        }

    }

    @Override
    public void setPresenter(ProductorMvp.Presenter presenter) {
        mSincronizarPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void ShowMessageResult(String message) {
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        alertDialog=new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_WARNING)
                .setTitle("Advertencia")
                .setDescription(message)
                .setPositiveText("Aceptar")
                .setPositiveButtonColor(Color.parseColor("#008ebe"))
                .setPositiveTextColor(Color.parseColor("#ffffff"))
                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                    }
                }).build();
        alertDialog.show();
    }

    public  List<ProductoEntity> listProductoFiltrados(List<ProductoEntity> list){
     List<ProductoEntity> listProducto=new ArrayList<>();

        if (estado!=null ){
         if (estado.getEstado()==0){
             return list;
         }

         for (int i = 0; i < list.size(); i++) {
            if (estado.getEstado()==1){
                if (list.get(i).getStock()>0&&list.get(i).getStock()>list.get(i).getStockMinimo()){
                    listProducto.add(list.get(i));
                }
            }
            if (estado.getEstado()==2){
                if (list.get(i).getStock()>0&&list.get(i).getStock()<=list.get(i).getStockMinimo()){
                    listProducto.add(list.get(i));
                }
            }
             if (estado.getEstado()==3){
                 if (list.get(i).getStock()<=0){
                     listProducto.add(list.get(i));
                 }
             }
         }
         return listProducto;
     }

       return list;

    }
    @Override
    public void MostrarDatos(List<ProductoEntity> listProducto, List<CategoriaEntity>  listCategoria, List<ProductosImagenesEntity> listImagenes) {
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }

        mListEmpresas=listProductoFiltrados(listProducto);


        Collections.sort(mListEmpresas);
        CargarRecycler(mListEmpresas);
        DataCache.ListCategorias=listCategoria;
        DataCache.listProductos=listProducto;
        SetearListCategoriaEntity();
        DataCache.ListCategorias=ListCategoriaEntity;
        CargarRecyclerCategoria(ListCategoriaEntity);
        if(mListEmpresas!=null && mListEmpresas.size()>0){
            CantidadPedido.setText(ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoStock(),2)+" Items");
            TotalPedido.setText("Compra: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioCompra(),2)+" Bs");
            tvVenta.setText("Venta: "+ShareMethods.ObtenerDecimalToString(ObtenerTotalProductoPrecioVenta(),2)+" Bs");
        }


    }

    @Override
    public void AddCantidadProducto(ElegantNumberButton v, int valor, ProductoEntity producto) {


    }



    @Override
    public void ShowSyncroMgs(String message) {
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        Snackbar snackbar= Snackbar.make(simpleSearchView, message, Snackbar.LENGTH_LONG);
        View snackbar_view=snackbar.getView();
        TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_checked,0);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    private class ChecarNotificaciones extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            //NUESTRO CODIGO
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mSincronizarPresenter.GetDatos();
                }
            }, 1 * 1000);
            super.onPostExecute(result);
        }
    }
}
