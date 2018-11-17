package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
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

import java.util.Timer;
import java.util.TimerTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Button logear = findViewById(R.id.logear);
        logear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameInputLayout.getEditText().getText().toString().equals("")){
                    usernameInputLayout.setErrorEnabled(true);
                    usernameInputLayout.setError("Debe ingresar un nombre de usuario");
                } else {
                    usernameInputLayout.setErrorEnabled(false);
                    usernameInputLayout.setError(null);
                }
                if (passwordInputLayout.getEditText().getText().toString().equals("")) {
                    passwordInputLayout.setErrorEnabled(true);
                    passwordInputLayout.setError("Debe ingresar una contraseña");
                } else {
                    passwordInputLayout.setErrorEnabled(false);
                    passwordInputLayout.setError(null);
                }
            }
        });

        passwordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordInputLayout.setError(null);
                passwordInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passwordLogin.getText().toString().equals("")){
                    passwordInputLayout.setErrorEnabled(true);
                    passwordInputLayout.setError("Debe ingresar un nombre de usuario");
                }
            }
        });

        usernameLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameInputLayout.setError(null);
                usernameInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (usernameLogin.getText().toString().equals("")){
                    usernameInputLayout.setErrorEnabled(true);
                    usernameInputLayout.setError("Debe ingresar un nombre de usuario");
                }
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
            }
        });

        Button registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(Registro);
            }
        });
    }
}
