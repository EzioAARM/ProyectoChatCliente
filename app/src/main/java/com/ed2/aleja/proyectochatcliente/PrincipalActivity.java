package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ed2.aleja.utilidades.JwtUtility;

public class PrincipalActivity extends AppCompatActivity {

    Toolbar toolbar;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion_principal_toolbar:
                JwtUtility jwtUtility = new JwtUtility();
                jwtUtility.escribirToken("", getApplicationContext());
                jwtUtility.escribirUsername("", getApplicationContext());
                Intent Login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Login);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
