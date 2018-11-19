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

import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;

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

    String baseURL = "http://ec2-18-220-77-115.us-east-2.compute.amazonaws.com:3000/";

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
                if (verificarCampos()) {
                    try {
                        Usuario nuevoUusario = new Usuario();
                        nuevoUusario.setNombre(nombreEditText.getText().toString());
                        nuevoUusario.setApellido(apellido.getText().toString());
                        nuevoUusario.setCorreo(correoRecibido);
                        nuevoUusario.setPassword(passwordRecibida);
                        nuevoUusario.setFechaNacimiento(fechaNacimientoEditText.getText().toString());
                        nuevoUusario.setStatus(true);
                        nuevoUusario.setImagen("");
                        nuevoUusario.setTelefono(telefono.getText().toString());
                        nuevoUusario.setUsername(usernameRecibido);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseURL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IHttpRequests registrarRecurso = retrofit.create(IHttpRequests.class);
                        Call<ResponseBody> llamada = registrarRecurso.registrarUser(nuevoUusario);
                        llamada.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                                    /*if (response.body().equals("")) {
                                        Toast.makeText(getApplicationContext(), "Se registró con éxito", Toast.LENGTH_LONG).show();
                                        Intent Login = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(Login);
                                        finish();
                                    }*/
                                } else {
                                    Toast.makeText(getApplicationContext(), "Hubo un error realizando su registro", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Hubo un error realizando su registro", Toast.LENGTH_LONG).show();
                                t.printStackTrace();
                            }
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Hubo un error al registrarse", Toast.LENGTH_LONG).show();
                    }
                } else {
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
        return (verificarNombre() && verificarApellido() && verificarTelefono());
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
