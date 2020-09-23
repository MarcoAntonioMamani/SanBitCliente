package com.sanbit.tevendo.ListadoVentas;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ClientesListViewModel;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoListViewModel;
import com.sanbit.tevendo.ListadoVentas.Adapter.AdapterPedidos;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.DataPreferences;
import com.sanbit.tevendo.ShareUtil.ShareMethods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoVentasFragment extends Fragment  implements PedidosMvp.View {
    private List<ClienteEntity> lisClientes=new ArrayList<>();
    private List<PedidoEntity> listPedidos=new ArrayList<>();
    TextView tvDesde,tvHasta,tvCantidad,tvTotalContado,tvTotalCredito,tvTotalVenta,TvUtilidad;
    ImageButton btnDesde,btnHasta;
    Button btnCargar;
    View view;
    public final Calendar c = Calendar.getInstance();
    private static final String CERO = "0";
    private static final String BARRA = "/";
    Context context;
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    RecyclerView recList;
    public AdapterPedidos adapterPerfil;
    private ClientesListViewModel viewModelClientes;
    private PedidoListViewModel viewModelPedidos;
    private PedidosMvp.Presenter mPedidosPresenter;
    private FloatingActionButton btnAddPedido;
    int Tipo=1;  //1 = Pendientes   2 =Entregados
    Boolean FisrtData=false;
    public ListadoVentasFragment() {
        // Required empty public constructor
        // Required empty public constructor
        FisrtData=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Tipo==1){
            DataCache.tvTitleMenu.setText("Ventas Realizadas");
            getActivity().setTitle("");
        }else{
            getActivity().setTitle("Pedidos Entregados");
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_listado_ventas, container, false);
        recList = (RecyclerView) view.findViewById(R.id.Pedidos_CardList);

        tvDesde=(TextView)view.findViewById(R.id.id_tvfecha_desde);
        tvHasta=(TextView)view.findViewById(R.id.id_tvfecha_hasta);
        tvCantidad=(TextView)view.findViewById(R.id.list_tvcantidad);
        tvTotalContado =(TextView)view.findViewById(R.id.list_venta_contado);
        tvTotalCredito =(TextView)view.findViewById(R.id.list_venta_credito );
        tvTotalVenta =(TextView)view.findViewById(R.id.list_venta_total);
        TvUtilidad =(TextView)view.findViewById(R.id.list_venta_utilidad);

        //tvTotalContado,tvTotalCredito,tvTotalVenta,TvUtilidad

        tvDesde.setEnabled(false);
        tvHasta.setEnabled(false);
        btnDesde=(ImageButton) view.findViewById(R.id.ib_btn_desde);
        btnHasta=(ImageButton)view.findViewById(R.id.id_btn_hasta);
        btnCargar=(Button)view.findViewById(R.id.id_btn_buscar) ;
        tvDesde.setText(FormatearFecha(dia,mes+1,anio));
        tvHasta.setText(FormatearFecha(dia,mes+1,anio));
        recList.setHasFixedSize(true);
        viewModelPedidos = ViewModelProviders.of(getActivity()).get(PedidoListViewModel.class);
        viewModelClientes = ViewModelProviders.of(getActivity()).get(ClientesListViewModel.class);
        FisrtData=false;
        new PedidosPresenter(this,getContext(),viewModelPedidos,getActivity(),Tipo);
        cargarClientes();
        _OnClickBtnAddPedidos();
        onclickObtenerFechaDesde();
        onclickObtenerFechaHasta();
        OnClickCargar();


        return view;
    }

    public void OnClickCargar(){
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPedidosPresenter.CargarPedidos();
            }
        });
    }
    public static Long getFechaHastaFromStringtoLong(String date, String pattern) {
        Long lngFecha =0L;
        Date df =parseDate(date,pattern);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        cal.set(Calendar.YEAR, df.getYear()+1900);
        cal.set(Calendar.MONTH, df.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, df.getDate());
        lngFecha = cal.getTimeInMillis();
        return lngFecha;
    }
    public static Date parseDate(String dateString, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Long getFechaFromStringtoLong(String date, String pattern) {
        Long lngFecha =0L;
        Date df =parseDate(date,pattern);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.YEAR, df.getYear()+1900);
        cal.set(Calendar.MONTH, df.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, df.getDate());
        lngFecha = cal.getTimeInMillis();
        return lngFecha;
    }
    private void obtenerFecha(final int tipo){  ///1 = desde    2=hasta
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                if (tipo==1){
                    tvDesde.setText(FormatearFecha(dayOfMonth ,mesActual ,year));
                }else{
                    tvHasta.setText(FormatearFecha(dayOfMonth ,mesActual ,year));
                }

            }

        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    public void onclickObtenerFechaDesde(){
        btnDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerFecha(1);

            }
        });
    }
    public void onclickObtenerFechaHasta(){
        btnHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerFecha(2);

            }
        });
    }
    public String FormatearFecha (int dayOfMonth,int mesActual,int year){
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
        return diaFormateado + BARRA + mesFormateado + BARRA + year;
    }
    @SuppressLint("RestrictedApi")
    public void _OnClickBtnAddPedidos(){
      /*  if (Tipo==3){
            btnAddPedido.setVisibility(View.GONE);
        }
        btnAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new CreatePedidoFragment();
                MainActivity fca = (MainActivity) getActivity();
                fca.switchFragment(frag,"CREATE_PEDIDOS");
            }
        });*/
    }
    public void cargarClientes(){
        try {
            lisClientes = viewModelClientes.getMAllCliente(1);
            if (lisClientes.size()>0){
                mPedidosPresenter.CargarPedidos();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void recyclerViewListClicked(View v, PedidoEntity pedido) {
      /*  if (pedido!=null){
            if (Tipo==2 || Tipo==1){
                try {
                    int EditPedido= DataPreferences.getPrefInt("EditarPedidos",getContext());
                    if (EditPedido==1){
                        ClienteEntity cliente=obtenerCliente(pedido);

                        if (cliente!=null){
                            Fragment frag = new ModifyPedidoFragment(pedido,obtenerCliente(pedido),Tipo);
                            MainActivity fca = (MainActivity) getActivity();
                            fca.switchFragment(frag,"VIEW_PEDIDOS");
                        }else{
                            ShowMessageResult("No Existe el Cliente en la zona del usuario");
                        }

                    }else{
                        ClienteEntity cliente=obtenerCliente(pedido);

                        if (cliente!=null){
                            Fragment frag = new ViewPedidoFragment(pedido,obtenerCliente(pedido),Tipo);
                            MainActivity fca = (MainActivity) getActivity();
                            fca.switchFragment(frag,"VIEW_PEDIDOS");
                        }else{
                            ShowMessageResult("No Existe el Cliente en la zona del usuario");
                        }

                    }
                }catch (Exception e){
                    ClienteEntity cliente=obtenerCliente(pedido);

                    if (cliente!=null){
                        Fragment frag = new ModifyPedidoFragment(pedido,obtenerCliente(pedido),Tipo);
                        MainActivity fca = (MainActivity) getActivity();
                        fca.switchFragment(frag,"VIEW_PEDIDOS");
                    }else{
                        ShowMessageResult("No Existe el Cliente en la zona del usuario");
                    }

                }


            }else{
                ClienteEntity cliente=obtenerCliente(pedido);

                if (cliente!=null){
                    Fragment frag = new ViewPedidoFragment(pedido,obtenerCliente(pedido),Tipo);
                    MainActivity fca = (MainActivity) getActivity();
                    fca.switchFragment(frag,"VIEW_PEDIDOS");
                }else{
                    ShowMessageResult("No Existe el Cliente en la zona del usuario");
                }

            }

        }*/

    }

    public void ShowMessageResult(String message) {

        Snackbar snackbar= Snackbar.make(tvDesde, message, Snackbar.LENGTH_LONG);
        View snackbar_view=snackbar.getView();
        TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_iinfo,0);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbar.show();
    }
    public ClienteEntity obtenerCliente(PedidoEntity pedido){
        for (int i = 0; i < lisClientes.size(); i++) {
            ClienteEntity client=lisClientes.get(i);
            if (pedido.getClienteId().trim().equals((""+client.getNumi()).trim())){
                return client;
            }
        }
        return null;
    }
    @Override
    public void MostrarPedidos(List<PedidoEntity> clientes) {
        listPedidos=clientes;
        tvCantidad.setText("Cantidad: "+clientes.size());
        CargarRecycler(listPedidos);

    }
    public void SetearTotales(List<PedidoEntity> list){
       double TotalContado=0;
        double TotalCredito=0;
        double TotalGeneral=0;
        double TotalUtilidad=0;


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTipoVenta()==1)//Es Contado
            {
                TotalContado+=list.get(i).getTotal();

            }
            if (list.get(i).getTipoVenta()==0)//Es Credito
            {
                TotalCredito+=list.get(i).getTotal();

            }
            TotalGeneral+=list.get(i).getTotal();
            TotalUtilidad+=list.get(i).getUtilidad();
        }
        tvTotalContado.setText((CharSequence) ShareMethods.ObtenerDecimalToString(TotalContado,2)+" Bs");
        tvTotalCredito .setText((CharSequence) ShareMethods.ObtenerDecimalToString(TotalCredito,2)+" Bs");
        tvTotalVenta .setText((CharSequence) ShareMethods.ObtenerDecimalToString(TotalGeneral,2)+" Bs");
        TvUtilidad.setText((CharSequence) ShareMethods.ObtenerDecimalToString(TotalGeneral-TotalUtilidad,2)+" Bs");

    }

    @Override
    public void setPresenter(PedidosMvp.Presenter presenter) {
        mPedidosPresenter = Preconditions.checkNotNull(presenter);
    }



    public void CargarRecycler(List<PedidoEntity> listPedidos){


        listPedidos=ObtenerPedidosFiltrados(listPedidos);
        tvCantidad.setText("Cantidad: "+listPedidos.size());
        SetearTotales(listPedidos);
        if (listPedidos!=null){
            Collections.sort(listPedidos);
            adapterPerfil = new AdapterPedidos(getContext(),listPedidos,this,lisClientes);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), R.anim.layout_animation_fall_right);
            recList.setLayoutAnimation(controller);
            recList.setLayoutManager(llm);
            recList.setAdapter(adapterPerfil);


        }

    }

    public List<PedidoEntity> ObtenerPedidosFiltrados(List<PedidoEntity> listp){
        if (FisrtData==false){
            tvDesde.setText(ObtenerFechaMinima(listp));
            FisrtData=true;
            return listp;

        }else{
            List<PedidoEntity> ListPedi=new ArrayList<>();

            Long fechaDesde = getFechaFromStringtoLong(tvDesde.getText().toString(), "dd/MM/yyyy");
            Long fechaHasta = getFechaHastaFromStringtoLong(tvHasta.getText().toString(), "dd/MM/yyyy");

            for (int i = 0; i < listp.size(); i++) {
                PedidoEntity ped=listp.get(i);

                if (ped.getFechaVenta().getTime()>=fechaDesde && ped.getFechaVenta().getTime()<=fechaHasta){
                    ListPedi.add(ped);
                }
            }
            return ListPedi;
        }

    }

    public String ObtenerFechaMinima(List<PedidoEntity> list){
        if (list.size()>0){

            PedidoEntity menor=list.get(0);

            for (int i = 1; i < list.size(); i++) {
                PedidoEntity data=list.get(i);

                if (data.getFechaVenta().getTime()<menor.getFechaVenta().getTime()){
                    menor=data;
                }

            }
            return ShareMethods.ObtenerFecha02(menor.getFechaVenta());

        }
        return FormatearFecha(dia,mes+1,anio);
    }
}
