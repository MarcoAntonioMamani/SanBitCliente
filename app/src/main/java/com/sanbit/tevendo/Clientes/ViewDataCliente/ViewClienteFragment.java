package com.sanbit.tevendo.Clientes.ViewDataCliente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanbit.tevendo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewClienteFragment extends Fragment {

    public ViewClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_cliente, container, false);
    }
}
