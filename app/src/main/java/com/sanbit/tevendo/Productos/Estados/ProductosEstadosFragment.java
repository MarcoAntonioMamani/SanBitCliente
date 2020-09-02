package com.sanbit.tevendo.Productos.Estados;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.ViewDataCliente.ViewClienteFragment;
import com.sanbit.tevendo.MainActivity;
import com.sanbit.tevendo.Productos.Adapter.CategoriaAdapter;
import com.sanbit.tevendo.Productos.Adapter.ProductosAdapter;
import com.sanbit.tevendo.Productos.Adapter.ProductosEstadosAdapter;
import com.sanbit.tevendo.Productos.ProductosFragment;
import com.sanbit.tevendo.R;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.DataPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosEstadosFragment extends Fragment  implements ProductosEstadosMvp.View {

    List<ProductosEstados> listEstados=new ArrayList<>();
    public ProductosEstadosAdapter adapterProductosEstados;
    RecyclerView recListProductosEstados;
    Context context;
    public ProductosEstadosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("");
        DataCache.tvTitleMenu.setText("Productos");

        context=getContext();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos_estados, container, false);
        recListProductosEstados=(RecyclerView) view.findViewById(R.id.Productos_CardList_estados);
        CargarDatosLista();

        context=getContext();
        CargarRecyclerCategoria(listEstados);

        return view;
    }

    public void CargarDatosLista(){

        if (listEstados.size()==0){

            listEstados.add(new ProductosEstados(1,0,"Ver Todos Los Productos"));
            listEstados.add(new ProductosEstados(2,1,"Ver Productos Con Stock Superior Al Minimo"));
            listEstados.add(new ProductosEstados(3,2,"Listar Productos Con Stock Minimo"));
            listEstados.add(new ProductosEstados(4,3,"Listar Productos Sin Stock"));
        }

    }

    public void CargarRecyclerCategoria(List<ProductosEstados> listCliente){
        if (listCliente!=null){
            adapterProductosEstados = new ProductosEstadosAdapter(context,listCliente,this,getActivity());
            GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), R.anim.layout_animation_fall_down);
            recListProductosEstados.setLayoutAnimation(controller);
            recListProductosEstados.setLayoutManager(llm);
            recListProductosEstados.setAdapter(adapterProductosEstados);



        }

    }
    @Override
    public void recyclerViewListClicked(View v, ProductosEstados producto) {
        if (producto!=null){



            Fragment frag = new ProductosFragment(producto);
            MainActivity fca = (MainActivity) getActivity();
            fca.switchFragment(frag,"ListaProductos");
        }
    }
}
