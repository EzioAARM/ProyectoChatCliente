package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.ed2.aleja.objetos.Mensaje;
import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MensajesActivity extends AppCompatActivity {

    Toolbar toolbar;
    String usernameReceptorChat;
    String usernameEmisorChat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajes_activity);

        try {
            usernameReceptorChat = Utilidades.retornarUsername(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent Argumentos = getIntent();
        usernameEmisorChat = Argumentos.getStringExtra("emisor");

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
        ListView listado = findViewById(R.id.mensajes_chat);
        List<Mensaje> messageList = new ArrayList<>();
        messageList.add(new Mensaje("Hola", "julia", "EzioA", false, "", false, false, "", ""));
        messageList.add(new Mensaje("Como estás?", "EzioA", "julia", false, "", false, false, "", ""));
        
    }

    @Override
    public void onBackPressed() {
        Intent Principal = new Intent(MensajesActivity.this, PrincipalActivity.class);
        startActivity(Principal);
        finish();
        super.onBackPressed();
    }
}
