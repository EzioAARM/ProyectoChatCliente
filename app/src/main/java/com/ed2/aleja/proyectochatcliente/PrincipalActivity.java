package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;

public class PrincipalActivity extends AppCompatActivity {

    Toolbar toolbar;
    boolean searchActive = false;
    Fragment fragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        toolbar = findViewById(R.id.toolbar_navigation_principal_activity);
        toolbar.setTitle("Conversaciones");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_principal);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedor_principal_principal_activity, new ConversationsFragment());
        transaction.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bar_principal_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSeleccionado = null;
                switch (item.getItemId()) {
                    case R.id.conversaciones_navigation_bar:
                        toolbar.setTitle("Conversaciones");
                        fragmentSeleccionado = new ConversationsFragment();
                        searchActive = false;
                        break;
                    case R.id.buscar_navigation_bar:
                        toolbar.setTitle("Buscar");
                        searchActive = true;
                        fragmentSeleccionado = new SearchFragment();
                        break;
                    case R.id.perfil_navigation_bar:
                        toolbar.setTitle("Mi perfil");
                        fragmentSeleccionado = new ProfileFragment();
                        searchActive = false;
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ActionMenuItemView itemSearch = findViewById(R.id.action_search);
                transaction.replace(R.id.contenedor_principal_principal_activity, fragmentSeleccionado);
                transaction.commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_principal, menu);
        MenuItem busqueda = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) busqueda.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new SearchFragment();
                Bundle argumentos = new Bundle();
                argumentos.putSerializable("busqueda", query);
                fragment.setArguments(argumentos);
                transaction.replace(R.id.contenedor_principal_principal_activity, fragment);
                transaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion_principal_toolbar:
                try {
                    Utilidades.escribirToken("", getApplicationContext());
                    Utilidades.escribirUsername("", getApplicationContext());
                    Intent Login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(Login);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
