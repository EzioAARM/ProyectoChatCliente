package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarActivity extends AppCompatActivity {

    TextInputLayout usernameInputLayout;
    EditText usernameEditText;

    IHttpRequests Peticiones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_password_activity);

        usernameInputLayout = findViewById(R.id.campo_username_recuperar);
        usernameEditText = usernameInputLayout.getEditText();
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarNombre();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button continuarRecuperacion = (Button) findViewById(R.id.continuar_recuperacion);
        continuarRecuperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarNombre() && Utilidades.verificarConexion(getApplicationContext())) {
                    Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
                    Call<Usuario> Llamada = Peticiones.BuscarUsuario(usernameEditText.getText().toString());
                    Llamada.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            switch (response.code()) {
                                case 200:
                                    Intent ContinuarRecuperacion = new Intent(RecuperarActivity.this, ContinuarRecuperarActivity.class);
                                    ContinuarRecuperacion.putExtra("username", response.body().getUsername());
                                    startActivity(ContinuarRecuperacion);
                                    finish();
                                    break;
                                case 502:
                                    Toast.makeText(getApplicationContext(), getString(R.string.error_502), Toast.LENGTH_LONG).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), getString(R.string.error_404_users), Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    onFailure(call, new Exception(getString(R.string.error_xxx)));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (Utilidades.verificarConexion(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verificarNombre() {
        if (usernameInputLayout.getEditText().getText().toString().equals("")) {
            usernameInputLayout.setErrorEnabled(true);
            usernameInputLayout.setError("Debe ingresar su nombre de usuario");
            return false;
        } else {
            usernameInputLayout.setErrorEnabled(false);
            usernameInputLayout.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent Login = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Login);
        finish();
    }
}
