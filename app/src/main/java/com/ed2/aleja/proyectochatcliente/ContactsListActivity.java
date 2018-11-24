package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsListActivity extends AppCompatActivity {

    String tokenActual;
    String usernameActual;

    ListView listadoContactos;
    int PosicionSeleccionada = -1;
    ArrayList<ContactListViewItem> ListViewItems;

    IHttpRequests Peticiones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_chat_activity);

        listadoContactos = (ListView) findViewById(R.id.contactos_nuevo_chat);

        try {
            usernameActual = Utilidades.retornarUsername(getApplicationContext());
            tokenActual = Utilidades.retornarToken(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
        Call<ArrayList<Usuario>> Llamada = Peticiones.ObtenerTodosUsuarios(tokenActual, usernameActual);
        Llamada.enqueue(new Callback<ArrayList<Usuario>>() {
            @Override
            public void onResponse(Call<ArrayList<Usuario>> call, Response<ArrayList<Usuario>> response) {
                switch (response.code()) {
                    case 200:
                        MostrarListado(response.body());
                        break;
                    case 502:
                        onFailure(call, new Exception(getString(R.string.error_502)));
                        break;
                    default:
                        onFailure(call, new Exception("Error desconocido"));
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Usuario>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listadoContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PosicionSeleccionada = position;
            }
        });

        FloatingActionButton IniciarChat = (FloatingActionButton) findViewById(R.id.iniciar_chat);
        IniciarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PosicionSeleccionada != -1) {
                    try {
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        String UsernameSelected = ListViewItems.get(PosicionSeleccionada).Username;
                        Conversaciones conversacion = new Conversaciones(
                                Utilidades.retornarUsername(getApplicationContext()), UsernameSelected, false, "",
                                "", false, false, 0, ""
                        );
                        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
                        Call<Token> Llamada = Peticiones.CrearConversacion(tokenActual, conversacion);
                        Llamada.enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                switch (response.code()) {
                                    case 201:
                                        Intent MostrarConversaciones = new Intent(ContactsListActivity.this, PrincipalActivity.class);
                                        startActivity(MostrarConversaciones);
                                        finish();
                                        break;
                                    case 302:
                                        Toast.makeText(getApplicationContext(), "La conversación ya existe", Toast.LENGTH_LONG).show();
                                        break;
                                    case 502:
                                        onFailure(call, new Exception(getString(R.string.error_502)));
                                        break;
                                    default:
                                        onFailure(call, new Exception("Error desconocido"));
                                        break;
                                }
                            }
                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent MostrarConversaciones = new Intent(ContactsListActivity.this, PrincipalActivity.class);
        startActivity(MostrarConversaciones);
    }

    private void MostrarListado(ArrayList<Usuario> usuarios) {
        ListViewItems = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            if (!usuarios.get(i).getUsername().equals(usernameActual)) {
                ListViewItems.add(new ContactListViewItem(usuarios.get(i).Nombre, usuarios.get(i).Username, usuarios.get(i).getImagen()));
            }
        }
        ContactListViewadapter Adaptador = new ContactListViewadapter(ListViewItems, getApplicationContext());
        listadoContactos.setAdapter(Adaptador);
    }
}
