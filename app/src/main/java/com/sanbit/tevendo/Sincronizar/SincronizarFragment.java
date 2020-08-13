package com.sanbit.tevendo.Sincronizar;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.sanbit.tevendo.Clientes.DbLocal.ClientesListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PreciosListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockListViewModel;
import com.sanbit.tevendo.Productos.DbLocal.ProductosListViewModel;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.LocationGeo;

import org.jetbrains.annotations.NotNull;


public class SincronizarFragment extends Fragment implements SincronizarMvp.View  {

    CheckBox checkTodo;
    CheckBox checkCliente;
    CheckBox checkProducto,checkPedidos;
    CheckBox checkPersonal;
    Button btnSincronizar;
    private SincronizarMvp.Presenter mSincronizarPresenter;
    private ClientesListViewModel viewModel;
    private PreciosListViewModel viewModelPrecio;
    private ProductosListViewModel viewModelProducto;
    private PedidoListViewModel viewModelPedidos;
    private DetalleListViewModel viewModelDetalle;
    private StockListViewModel viewModelStock;
    LottieAlertDialog alertDialog;
    public SincronizarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocationGeo.getInstance(getContext(),getActivity());
        LocationGeo.PedirPermisoApp();
        DataCache.tvTitleMenu.setText("Sincronizar");
    }
    @Override
    public void onResume() {
        super.onResume();
        DataCache.tvTitleMenu.setText("Sincronizar");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataCache.tvTitleMenu.setText("Sincronizar");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sincronizar, container, false);
        checkTodo=rootView.findViewById(R.id.view_sinc_all);
        checkCliente=rootView.findViewById(R.id.view_sinc_cliente);
        checkProducto=rootView.findViewById(R.id.view_sinc_producto);
        checkPersonal=rootView.findViewById(R.id.view_sinc_personal);
        btnSincronizar=rootView.findViewById(R.id.id_sync_btn_sync);
        checkPedidos=rootView.findViewById(R.id.view_sinc_pedidos);
        viewModel = ViewModelProviders.of(getActivity()).get(ClientesListViewModel.class);
        viewModelPrecio = ViewModelProviders.of(getActivity()).get(PreciosListViewModel.class);
        viewModelProducto = ViewModelProviders.of(getActivity()).get(ProductosListViewModel.class);
        viewModelPedidos = ViewModelProviders.of(getActivity()).get(PedidoListViewModel.class);
        viewModelDetalle = ViewModelProviders.of(getActivity()).get(DetalleListViewModel.class);
        viewModelStock=ViewModelProviders.of(getActivity()).get(StockListViewModel.class);
      /*  NoteEntity note = new NoteEntity(inputNote.getText().toString());
        viewModel.insertNote(note);*/
        new SincronizarPresenter(this,getContext(),viewModel,getActivity(),viewModelPrecio,viewModelProducto,viewModelPedidos,viewModelDetalle,viewModelStock);
        checkTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MarcarDesmarcarTodos(b);
            }
        });
        OnclickButton();
        ShowDialogSincronizando();


       /* if (ServiceSincronizacion.getInstance()==null){
            UtilShare.mActivity=getActivity();
            Intent intent = new Intent(getContext(),new ServiceSincronizacion(viewModel,getActivity()).getClass());
            getContext().startService(intent);
        }*/
        return rootView;
    }

    public void showDialogs() {
        ShowDialogSincronizando();
        alertDialog.show();
    }

    public void OnclickButton(){
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidarcheckSeleccionado()){
                    if (! isOnline()){
                        ShowMessageResult("Sin Conexión. Por favor conectarse a una red");
                        return;
                    }else{
                        showDialogs();
                        new ChecarNotificaciones().execute();

                    }

                }else{
                    ShowMessageResult("Error: No Existen Item seleccionado");
                }
            }
        });
    }
    public boolean ValidarcheckSeleccionado(){
        return checkProducto.isChecked() ||checkPersonal.isChecked()||checkCliente.isChecked()||checkPedidos.isChecked();
    }

    @Override
    public void MarcarDesmarcarTodos(boolean bandera) {
        checkCliente.setChecked(bandera);
        checkPersonal.setChecked(bandera);
        checkProducto.setChecked(bandera);
        checkPedidos.setChecked(bandera);
    }

    @Override
    public void setPresenter(SincronizarMvp.Presenter presenter) {
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

    @Override
    public void ShowSyncroMgs(String message) {
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        Snackbar snackbar= Snackbar.make(checkPersonal, message, Snackbar.LENGTH_LONG);
        View snackbar_view=snackbar.getView();
        TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_checked,0);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbar.show();

    }
    private void ShowDialogSincronizando(){


        try
        {

            alertDialog = new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_LOADING).setTitle("Sincronizacion")
                    .setDescription("Obteniendo Datos .....")
                    .build();

            alertDialog.setCancelable(false);
        }catch (Error e){

            String d=e.getMessage();

        }

    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
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
                    mSincronizarPresenter.GuadarDatos(checkProducto.isChecked() ,checkPersonal.isChecked(),checkCliente.isChecked(),checkPedidos.isChecked());
                }
            }, 1 * 2000);
            super.onPostExecute(result);
        }
    }
}
