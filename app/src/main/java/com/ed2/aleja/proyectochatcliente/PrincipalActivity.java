package com.ed2.aleja.proyectochatcliente;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class PrincipalActivity extends AppCompatActivity {

    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        toolbar = findViewById(R.id.toolbar_navigation_principal_activity);
        toolbar.setTitle("Conversaciones");
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bar_principal_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSeleccionado = null;
                switch (item.getItemId()) {
                    case R.id.conversaciones_navigation_bar:
                        toolbar.setTitle("Conversaciones");
                        fragmentSeleccionado = new ConversationsFragment();
                        break;
                    case R.id.buscar_navigation_bar:
                        toolbar.setTitle("Buscar");
                        fragmentSeleccionado = new SearchFragment();
                        break;
                    case R.id.perfil_navigation_bar:
                        toolbar.setTitle("Mi perfil");
                        fragmentSeleccionado = new ProfileFragment();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_principal_principal_activity, fragmentSeleccionado);
                transaction.commit();
                return true;
            }
        });


    }

}
