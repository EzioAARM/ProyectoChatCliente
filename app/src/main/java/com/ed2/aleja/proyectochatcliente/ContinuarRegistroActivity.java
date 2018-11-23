package com.ed2.aleja.proyectochatcliente;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContinuarRegistroActivity extends AppCompatActivity {

    String correoRecibido;
    String usernameRecibido;
    String passwordRecibida;

    TextInputLayout nombreInputLayout;
    TextInputLayout apellidoInputLayout;
    TextInputLayout telefonoInputLayout;

    IHttpRequests Peticiones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_continuacion_activity);

        Intent Argumentos = getIntent();
        correoRecibido = Argumentos.getStringExtra("correo");
        usernameRecibido = Argumentos.getStringExtra("username");
        passwordRecibida = Argumentos.getStringExtra("password");

        TextView header = (TextView) findViewById(R.id.lbl_registro_continuacion);
        header.setText("Hola " + usernameRecibido);

        nombreInputLayout = (TextInputLayout) findViewById(R.id.campo_nombre_registro);
        apellidoInputLayout = (TextInputLayout) findViewById(R.id.campo_apellido_registro);
        telefonoInputLayout = (TextInputLayout) findViewById(R.id.campo_telefono_registro);

        final EditText nombreEditText = (EditText) nombreInputLayout.getEditText();

        final EditText apellido = (EditText) apellidoInputLayout.getEditText();

        final EditText telefono = (EditText) telefonoInputLayout.getEditText();

        Button anterior = (Button) findViewById(R.id.anterior_registro);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registrarse = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(Registrarse);
                finish();
            }
        });

        final EditText fechaNacimientoEditText = (EditText) findViewById(R.id.fecha_registro);
        fechaNacimientoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        fechaNacimientoEditText.setText(selectedDate);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        Button terminarRegistro = (Button) findViewById(R.id.terminar_registro);
        terminarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos() && Utilidades.verificarConexion(getApplicationContext())) {
                    try {
                        Usuario nuevoUsuario = new Usuario(
                                usernameRecibido, Utilidades.CifrarSHA256(passwordRecibida), nombreEditText.getText().toString(), apellido.getText().toString(), telefono.getText().toString(),
                                fechaNacimientoEditText.getText().toString(), correoRecibido, true, ""
                        );
                        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
                        Call<ResponseBody> Llamada = Peticiones.RegistrarUsuario(nuevoUsuario);
                        Llamada.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                switch (response.code()) {
                                    case 201:
                                        Intent Principal = new Intent(ContinuarRegistroActivity.this, PrincipalActivity.class);
                                        Principal.putExtra("username_registrado", usernameRecibido);
                                        Principal.putExtra("password_registrado", passwordRecibida);
                                        startActivity(Principal);
                                        finish();
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
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                } else if (Utilidades.verificarConexion(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Verifique los datos que ingresó", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent Registrarse = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(Registrarse);
        finish();
    }

    /*
    Funciones que validan los campos
     */

    private boolean verificarCampos() {
        boolean vN = verificarNombre();
        boolean vA = verificarApellido();
        boolean vT = verificarTelefono();
        return (vN && vA && vT);
    }

    private boolean verificarNombre() {
        if (nombreInputLayout.getEditText().getText().toString().equals("")) {
            nombreInputLayout.setErrorEnabled(true);
            nombreInputLayout.setError("Debe ingresar su nombre");
            return false;
        } else {
            nombreInputLayout.setErrorEnabled(false);
            nombreInputLayout.setError(null);
            return true;
        }
    }

    private boolean verificarApellido() {
        if (apellidoInputLayout.getEditText().getText().toString().equals("")) {
            apellidoInputLayout.setErrorEnabled(true);
            apellidoInputLayout.setError("Debe ingresar su apellido");
            return false;
        } else {
            apellidoInputLayout.setErrorEnabled(false);
            apellidoInputLayout.setError(null);
            return true;
        }
    }

    private boolean verificarTelefono() {
        if (!telefonoInputLayout.getEditText().getText().toString().equals("")){
            if (Patterns.PHONE.matcher(telefonoInputLayout.getEditText().getText().toString()).matches()){
                apellidoInputLayout.setErrorEnabled(false);
                apellidoInputLayout.setError(null);
                return true;
            }
            apellidoInputLayout.setErrorEnabled(true);
            apellidoInputLayout.setError("El número ingresado no es válido");
            return false;
        }
        apellidoInputLayout.setErrorEnabled(false);
        apellidoInputLayout.setError(null);
        return true;
    }
}
