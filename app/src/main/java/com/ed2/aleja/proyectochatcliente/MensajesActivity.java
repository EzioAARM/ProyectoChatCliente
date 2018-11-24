package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Mensaje;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajesActivity extends AppCompatActivity {

    Toolbar toolbar;
    String usernameReceptorChat;
    String usernameEmisorChat;
    String _id;
    String tokenActual;
    ListView Mensajes;
    ArrayList<MensajeListViewItem> MensajesList;
    MensajeListViewAdapter Adaptador;
    Button Enviar;
    EditText MensajeEnviar;
    Gson gson = new Gson();

    private Socket socket;
    IHttpRequests Peticiones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajes_activity);
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        MensajesList = new ArrayList<>();
        Mensajes = findViewById(R.id.list_mensajes_chat);

        try {
            usernameReceptorChat = Utilidades.retornarUsername(getApplicationContext());
            tokenActual = Utilidades.retornarToken(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent Argumentos = getIntent();
        usernameEmisorChat = Argumentos.getStringExtra("emisor");
        _id = Argumentos.getStringExtra("id");


        toolbar = findViewById(R.id.toolbar_chat);
        toolbar.setTitle(usernameEmisorChat);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Principal = new Intent(MensajesActivity.this, PrincipalActivity.class);
                startActivity(Principal);
                finish();
            }
        });

        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
        Call<ArrayList<Mensaje>> Llamada = Peticiones.ObtenerMensajes(tokenActual, usernameReceptorChat, _id);
        Llamada.enqueue(new Callback<ArrayList<Mensaje>>() {
            @Override
            public void onResponse(Call<ArrayList<Mensaje>> call, Response<ArrayList<Mensaje>> response) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                switch (response.code()) {
                    case 200:
                        MostrarMensajes(response.body());
                        break;
                    case 404:
                        break;
                    case 502:
                        onFailure(call, new Exception(getString(R.string.error_502)));
                        break;
                    default:
                        onFailure(call, new Exception(getString(R.string.error_xxx)));
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Mensaje>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        Enviar = findViewById(R.id.button_chatbox_send);
        MensajeEnviar = findViewById(R.id.edittext_chatbox);
        try {
            socket = IO.socket(Utilidades.UrlServer + ":" + Utilidades.PuertoSocket);
            socket.connect();
            Enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!MensajeEnviar.getText().toString().equals("")) {
                        socket.emit("EnviarMensaje", usernameReceptorChat, usernameEmisorChat, MensajeEnviar.getText().toString(),
                                false, "", false, false, _id);
                        MensajesList.add(new MensajeListViewItem(usernameReceptorChat, MensajeEnviar.getText().toString()));
                        Adaptador = new MensajeListViewAdapter(MensajesList, getApplicationContext());
                        Adaptador.notifyDataSetChanged();
                        Mensajes.setAdapter(Adaptador);
                        MensajeEnviar.setText("");
                    }
                }
            });
            socket.on("RecibirMensaje", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];
                                String emisor = data.get("emisor").toString();
                                String mensaje = data.get("mensaje").toString();
                                MensajesList.add(new MensajeListViewItem(emisor, mensaje));
                                Adaptador = new MensajeListViewAdapter(MensajesList, getApplicationContext());
                                Adaptador.notifyDataSetChanged();
                                Mensajes.setAdapter(Adaptador);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Hubo un error al enviar el mensaje", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void MostrarMensajes(ArrayList<Mensaje> mensajes) {
        if (mensajes != null) {
            MensajesList = new ArrayList<>();
            for (int i = 0; i < mensajes.size(); i++) {
                MensajesList.add(new MensajeListViewItem(mensajes.get(i).getEmisor(), mensajes.get(i).getMensaje()));
            }
            Adaptador = new MensajeListViewAdapter(MensajesList, getApplicationContext());
            Mensajes.setAdapter(Adaptador);
        }
    }

    @Override
    public void onBackPressed() {
        Intent Principal = new Intent(MensajesActivity.this, PrincipalActivity.class);
        startActivity(Principal);
        finish();
        super.onBackPressed();
    }
}
