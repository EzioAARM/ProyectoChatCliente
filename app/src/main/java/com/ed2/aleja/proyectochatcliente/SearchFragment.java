package com.ed2.aleja.proyectochatcliente;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFragment extends Fragment {

    View rootView;
    EditText campoBusqueda;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        Bundle Argumentos = getArguments();
        if (Argumentos != null) {
            String entranteBusqueda = Argumentos.getString("busqueda");
            // Hacer la busqueda
        }

        TabLayout tabs = (TabLayout) rootView.findViewById(R.id.tab_container_search);
        tabs.addTab(tabs.newTab().setText("Mensajes"));
        tabs.addTab(tabs.newTab().setText("Contactos"));

        return rootView;
    }

}
