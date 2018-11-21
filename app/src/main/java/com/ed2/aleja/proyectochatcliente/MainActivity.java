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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.JwtUtility;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
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

    String baseURL = "http://ec2-18-220-77-115.us-east-2.compute.amazonaws.com:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final JwtUtility jwtUtility = new JwtUtility();
        final String token = jwtUtility.retornarToken(getApplicationContext());
        if (!token.equals("") && token.length() > 10){
            Intent principal = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(principal);
            finish();
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

        int respuesta = 0;
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
                if (verificarDatos()) {
                    try {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseURL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IHttpRequests registrarRecurso = retrofit.create(IHttpRequests.class);
                        final Usuario userLogin = new Usuario();
                        userLogin.setUsername(usernameLogin.getText().toString());
                        userLogin.setPassword(passwordLogin.getText().toString());
                        Call<ResponseBody> llamada = registrarRecurso.logearUsuario(userLogin.getUsername(), userLogin.getPassword());
                        llamada.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        String res = response.body().string();
                                        JsonParser parser = new JsonParser();
                                        JsonObject objeto = (JsonObject) parser.parse(res);
                                        JsonElement message = null;
                                        String status = objeto.get("status").toString();
                                        switch (status) {
                                            case "404":
                                                message = objeto.get("message");
                                                passwordInputLayout.setErrorEnabled(true);
                                                passwordInputLayout.setError("Verifique la contraseña");
                                                usernameInputLayout.setErrorEnabled(true);
                                                usernameInputLayout.setError("Verifique el nombre de usuario");
                                                usernameLogin.setText("");
                                                passwordLogin.setText("");
                                                Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
                                                break;
                                            case "502":
                                                message = objeto.get("message");
                                                Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
                                                break;
                                            case "200":
                                                String tokenObtenido = objeto.get("token").toString();
                                                tokenObtenido = tokenObtenido.substring(1, tokenObtenido.length() - 1);
                                                jwtUtility.escribirToken(tokenObtenido, getApplicationContext());
                                                jwtUtility.escribirUsername(usernameLogin.getText().toString(), getApplicationContext());
                                                Intent PaginaPrincipal = new Intent(getApplicationContext(), PrincipalActivity.class);
                                                startActivity(PaginaPrincipal);
                                                finish();
                                                break;
                                            default:
                                                Toast.makeText(getApplicationContext(), "Se produjo un error desconocido", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Hubo un error realizando su registro", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Hubo un error cifrando su contraseña", Toast.LENGTH_LONG).show();
                    }
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
