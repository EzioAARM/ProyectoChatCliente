package com.ed2.aleja.proyectochatcliente;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageSwitcher loginSlider;
    private int[] imagenes = { R.drawable.b1, R.drawable.b2, R.drawable.b3 };
    private int actual;
    private static final int duracion = 4000;
    private Timer timer = null;

    TextInputLayout usernameInputLayout;
    TextInputLayout passwordInputLayout;
    EditText usernameLogin;
    EditText passwordLogin;

    String usuarioRecibido;
    String passwordRecibida;

    IHttpRequests Peticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = null;
        try {
            token = Utilidades.retornarToken(getApplicationContext());
            if (!token.equals("") && token.length() > 10){
                Intent principal = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(principal);
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent Argumentos = getIntent();
        boolean recienRegistrado = false;
        if (Argumentos.hasExtra("username_registrado") && Argumentos.hasExtra("password_registrado")) {
            recienRegistrado = true;
            usuarioRecibido = Argumentos.getStringExtra("username_registrado");
            passwordRecibida = Argumentos.getStringExtra("password_registrado");
        }

        /*
        Pedir permisos de acceso a internet
         */

        final int respuesta = 0;
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, respuesta);
        }

        /*
        Maneja el slider de la parte de arriba del login
         */

        loginSlider = (ImageSwitcher) findViewById(R.id.loginSlider);
        loginSlider.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                loginSlider.setInAnimation(fadeIn);
                loginSlider.setOutAnimation(fadeOut);
                return imageView;
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        loginSlider.setImageResource(imagenes[actual]);
                        actual++;
                        if (actual == imagenes.length)
                            actual = 0;
                    }
                });
            }
        }, 0, duracion);

        /*
        Maneja los errores de los TextInputLayout
         */

        usernameInputLayout = (TextInputLayout) findViewById(R.id.inputUsername_Login);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.inputPassword_login);
        usernameLogin = (EditText) findViewById(R.id.username_login);
        passwordLogin = (EditText) findViewById(R.id.password_login);
        if (recienRegistrado) {
            usernameLogin.setText(usuarioRecibido);
            passwordLogin.setText(passwordRecibida);
        }

        Button logear = findViewById(R.id.logear);
        logear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarDatos() && Utilidades.verificarConexion(getApplicationContext())) {
                    try {
                        String username = usernameLogin.getText().toString();
                        String password = passwordLogin.getText().toString();
                        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
                        Call<Token> Llamada = Peticiones.LogearUsuario(username, Utilidades.CifrarSHA256(password));
                        Llamada.enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                switch (response.code()) {
                                    case 202:
                                        try {
                                            Utilidades.escribirToken(response.body().token, getApplicationContext());
                                            Utilidades.escribirUsername(usernameLogin.getText().toString(), getApplicationContext());
                                            Intent Principal = new Intent(MainActivity.this, PrincipalActivity.class);
                                            startActivity(Principal);
                                            finish();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
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
                            public void onFailure(Call<Token> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else if (!Utilidades.verificarConexion(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_SHORT).show();
                }
            }
        });

        passwordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        usernameLogin.addTextChangedListener(new TextWatcher() {
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

        /*
        Recuperación de la contraseña y registro
         */

        TextView recuperarContraseña = (TextView) findViewById(R.id.olvidoContraseña_login);
        recuperarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Recuperar = new Intent(getApplicationContext(), RecuperarActivity.class);
                startActivity(Recuperar);
                finish();
            }
        });

        Button registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(Registro);
                finish();
            }
        });
    }

    private boolean verificarDatos() {
        boolean vP = verificarPassword();
        boolean vU = verificarUsername();
        return (vP && vU);
    }

    private boolean verificarPassword() {
        if (passwordInputLayout.getEditText().getText().toString().equals("")) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("Debe ingresar una contraseña");
            return false;
        } else {
            passwordInputLayout.setErrorEnabled(false);
            passwordInputLayout.setError(null);
            return true;
        }
    }

    private boolean verificarUsername() {
        if (usernameInputLayout.getEditText().getText().toString().equals("")){
            usernameInputLayout.setErrorEnabled(true);
            usernameInputLayout.setError("Debe ingresar un nombre de usuario");
            return false;
        } else {
            usernameInputLayout.setErrorEnabled(false);
            usernameInputLayout.setError(null);
            return true;
        }
    }

}
