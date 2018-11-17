package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RecuperarActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_password_activity);
    }

    @Override
    public void onBackPressed() {
        Intent Login = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Login);
    }
}
