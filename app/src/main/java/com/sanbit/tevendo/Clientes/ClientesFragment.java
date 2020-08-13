package com.sanbit.tevendo.Clientes;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.sanbit.tevendo.Clientes.DbLocal.ClienteEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ClientesListViewModel;
import com.sanbit.tevendo.MainActivity;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.DataPreferences;
import com.sanbit.tevendo.ShareUtil.LocationGeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientesFragment extends Fragment  implements SearchView.OnQueryTextListener,ClienteMvp.View {

    private List<ClienteEntity> lisClientes=new ArrayList<>();
    View view;
    Context context;
    RecyclerView recList;
    public AdapterClientes adapterPerfil;
    private FloatingActionButton btnAddCliente;
    SearchView simpleSearchView;
    private ClientesListViewModel viewModel;
    private Unbinder unbinder;
    public static final String TAG = ClientesFragment.class.getSimpleName();
    public ClientesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        DataCache.tvTitleMenu.setText("Clientes");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        LocationGeo.getInstance(getContext(),getActivity());
        LocationGeo.PedirPermisoApp();
        DataCache.tvTitleMenu.setText("Clientes");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_clientes, container, false);
        recList = (RecyclerView) view.findViewById(R.id.Client_CardList);
        recList.setHasFixedSize(true);
        simpleSearchView = (SearchView) view.findViewById (R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        simpleSearchView.setIconifiedByDefault(false);

        _CargarCliente();
        // recList.requestFocus();
     //   _OnClickBtnAddCliente();

        return view;
    }
    public void _CargarCliente(){

        viewModel = ViewModelProviders.of(getActivity()).get(ClientesListViewModel.class);
        viewModel.getClientes().observe((LifecycleOwner) getActivity(), new Observer<List<ClienteEntity>>() {
            @Override
            public void onChanged(@Nullable List<ClienteEntity> notes) {
                try{
                    // lisClientes=FiltarByZona(notes)
                    lisClientes=notes;
                    Collections.sort(lisClientes);
                    CargarRecycler(lisClientes);
                }catch(Exception e){

                }



            }  });
        recList.requestFocus();
    }
    public void CargarRecycler(List<ClienteEntity> listCliente){
        if (listCliente!=null){
            adapterPerfil = new AdapterClientes(context,lisClientes,this);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), R.anim.layout_animation_fall_down);
            recList.setLayoutAnimation(controller);
            recList.setLayoutManager(llm);
            recList.setAdapter(adapterPerfil);

            recList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && btnAddCliente.getVisibility() == View.VISIBLE) {
                        btnAddCliente.hide();
                    } else if (dy < 0 && btnAddCliente.getVisibility() != View.VISIBLE) {
                        btnAddCliente.show();
                    }
                }
            });
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try{
            List<ClienteEntity> ListaFiltrada=filter(lisClientes,newText);
            adapterPerfil.setFilter(ListaFiltrada);

        }catch (Exception e){

        }
        return false;
    }
    public  void Refresh(){
        if(recList!=null){
            recList.scrollToPosition(0);
        }
    }


    public List<ClienteEntity> filter (List<ClienteEntity> bares ,String texto){
        List<ClienteEntity>ListaFiltrada=new ArrayList<>();
        try{
            texto=texto.toLowerCase();
            for (ClienteEntity b:bares){
                String name=b.getNamecliente().toLowerCase();
                if(name.contains(texto)){
                    ListaFiltrada.add(b);
                }
            }
        }catch (Exception e){

        }
        return ListaFiltrada;
    }


    @Override
    public void recyclerViewListClicked(View v,  ClienteEntity cliente) {
        if (cliente!=null){
            //UtilShare.cliente=cliente;

           /* int isUpdate= DataPreferences.getPrefInt("UpdateCliente",getContext());

            Fragment frag = new CreateClienteFragment(1,cliente,isUpdate);
            MainActivity fca = (MainActivity) getActivity();
            fca.switchFragment(frag,"UpdateClientes");*/
        }
    }
    public void ShowMessageResult(String message) {
        Snackbar snackbar= Snackbar.make(recList, message, Snackbar.LENGTH_LONG);
        View snackbar_view=snackbar.getView();
        TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_iinfo,0);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbar.show();
    }
}
