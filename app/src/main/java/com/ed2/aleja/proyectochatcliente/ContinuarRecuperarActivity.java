package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;

import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContinuarRecuperarActivity extends AppCompatActivity {

    String usernameToUpdate;
    TextInputLayout passwordInputLayout;

    IHttpRequests Peticiones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent Argumentos = getIntent();
        usernameToUpdate = Argumentos.getStringExtra("username");

        passwordInputLayout = (TextInputLayout) findViewById(R.id.campo_password_recuperar);

        Button Anterior = (Button) findViewById(R.id.anterior_recuperacion);
        Anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button RecuperarPassword = (Button) findViewById(R.id.reestablecer_recuperacion);
        RecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarContraseña() && Utilidades.verificarConexion(getApplicationContext())) {
                    try {
                        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
                        Call<ResponseBody> Llamada = Peticiones.UpdatePassword(usernameToUpdate, Utilidades.CifrarSHA256(passwordInputLayout.getEditText().getText().toString()));
                        Llamada.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                switch (response.code()) {
                                    case 200:
                                        Intent Login = new Intent(ContinuarRecuperarActivity.this, MainActivity.class);
                                        Login.putExtra("username_registrado", usernameToUpdate);
                                        Login.putExtra("password_registrado", passwordInputLayout.getEditText().getText().toString());
                                        startActivity(Login);
                                        finish();
                                        break;
                                    case 502:
                                        Toast.makeText(getApplicationContext(), getString(R.string.error_502), Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        onFailure(call, new Exception(getString(R.string.error_xxx)));
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else if (Utilidades.verificarConexion(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_LONG).show();
                }
            }
        });

        setContentView(R.layout.recuperar_password_continuacion_activity);
    }

    private boolean verificarContraseña() {
        if (passwordInputLayout.getEditText().getText().toString().equals("")) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("Debe ingresar una contraseña");
            return false;
        } else {
            if (passwordInputLayout.getEditText().getText().toString().length() <= 8) {
                passwordInputLayout.setErrorEnabled(true);
                passwordInputLayout.setError("Debe ingresar una contraseña de al menos 9 caracteres ");
                return false;
            } else {
                passwordInputLayout.setErrorEnabled(false);
                passwordInputLayout.setError(null);
                return true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent Recuperar = new Intent(getApplicationContext(), RecuperarActivity.class);
        startActivity(Recuperar);
        finish();
    }
}
