package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ed2.aleja.utilidades.IHttpRequests;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    TextInputLayout correoInputLayot;
    TextInputLayout usernameInputLayot;
    TextInputLayout passwordInputLayot;
    TextInputLayout confirmacionPasswordInputLayot;

    String baseURL = "http://ec2-18-220-77-115.us-east-2.compute.amazonaws.com:3000/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity);

        correoInputLayot = (TextInputLayout) findViewById(R.id.campo_email_registro);
        usernameInputLayot = (TextInputLayout) findViewById(R.id.campo_username_registro);
        passwordInputLayot = (TextInputLayout) findViewById(R.id.campo_password_registro);
        confirmacionPasswordInputLayot = (TextInputLayout) findViewById(R.id.campo_confirmar_password_registro);

        Button continuarRegistro = (Button) findViewById(R.id.continuar_registro);
        continuarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    Intent ContinuarRegistro = new Intent(RegistroActivity.this, ContinuarRegistroActivity.class);
                    ContinuarRegistro.putExtra("correo", correoInputLayot.getEditText().getText().toString());
                    ContinuarRegistro.putExtra("username", usernameInputLayot.getEditText().getText().toString());
                    ContinuarRegistro.putExtra("password", passwordInputLayot.getEditText().getText().toString());
                    startActivity(ContinuarRegistro);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Verifique los datos que ingresó", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancelarRegistro = (Button) findViewById(R.id.cancelar_registro);
        cancelarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Login);
                finish();
            }
        });

        EditText correoEditText = (EditText) correoInputLayot.getEditText();
        correoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarCorreo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText usernameEditText = (EditText) usernameInputLayot.getEditText();
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText passwordEditText = (EditText) passwordInputLayot.getEditText();
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarContraseña();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText verifyEditText = (EditText) confirmacionPasswordInputLayot.getEditText();
        verifyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarContraseñasCoinciden();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent Login = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Login);
        finish();
    }

    /*
    Funciones que validan que los datos esten ingresados correctamente.
     */

    private boolean validarCampos() {
        boolean vC = verificarContraseña();
        boolean vCC = verificarContraseñasCoinciden();
        boolean vCo = verificarCorreo();
        boolean vU = verificarUsername();
        return (vC && vCC && vCo && vU);
    }

    private boolean verificarCorreo() {
        if (correoInputLayot.getEditText().getText().toString().equals("")) {
            correoInputLayot.setErrorEnabled(true);
            correoInputLayot.setError("Debe ingresar una dirección de correo");
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(correoInputLayot.getEditText().getText().toString()).matches()){
                correoInputLayot.setErrorEnabled(false);
                correoInputLayot.setError(null);
                return true;
            } else {
                correoInputLayot.setErrorEnabled(true);
                correoInputLayot.setError("Debe ingresar una dirección de correo valida");
                return true;
            }
        }
    }

    private boolean verificarUsername() {
        if (usernameInputLayot.getEditText().getText().toString().equals("")) {
            usernameInputLayot.setErrorEnabled(true);
            usernameInputLayot.setError("Debe ingresar un nombre de usuario");
            return false;
        } else {
            Pattern usernamePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
            if (usernamePattern.matcher(usernameInputLayot.getEditText().getText().toString()).matches()) {
                usernameInputLayot.setErrorEnabled(false);
                usernameInputLayot.setError(null);
                return true;
            } else {
                usernameInputLayot.setErrorEnabled(true);
                usernameInputLayot.setError("Debe ingresar un nombre de usuario valido");
                return false;
            }
        }
    }

    private boolean verificarContraseña() {
        if (passwordInputLayot.getEditText().getText().toString().equals("")) {
            passwordInputLayot.setErrorEnabled(true);
            passwordInputLayot.setError("Debe ingresar una contraseña");
            return false;
        } else {
            if (passwordInputLayot.getEditText().getText().toString().length() <= 8) {
                passwordInputLayot.setErrorEnabled(true);
                passwordInputLayot.setError("Debe ingresar una contraseña de al menos 9 caracteres ");
                return false;
            } else {
                passwordInputLayot.setErrorEnabled(false);
                passwordInputLayot.setError(null);
                return true;
            }
        }
    }

    private boolean verificarContraseñasCoinciden() {
        if (confirmacionPasswordInputLayot.getEditText().getText().toString().equals("")) {
            confirmacionPasswordInputLayot.setErrorEnabled(true);
            confirmacionPasswordInputLayot.setError("Debe ingresar ambas contraseñas y deben coincidir");
            return false;
        } else {
            if (!confirmacionPasswordInputLayot.getEditText().getText().toString().equals(passwordInputLayot.getEditText().getText().toString())) {
                confirmacionPasswordInputLayot.setErrorEnabled(true);
                confirmacionPasswordInputLayot.setError("Las contraseñas no coinciden");
                return false;
            } else {
                confirmacionPasswordInputLayot.setErrorEnabled(false);
                confirmacionPasswordInputLayot.setError(null);
                return true;
            }
        }
    }
}
