package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    View rootView;

    String UsuarioActual;
    String tokenActual;

    EditText UsernameEditText;
    EditText NombreEditText;
    EditText ApellidoEditText;
    EditText FechaNacimientoEditText;
    EditText CorreoEditText;
    EditText TelefonoEditText;
    ImageView FotoPerfil;

    IHttpRequests Peticiones;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        try {
            UsuarioActual = Utilidades.retornarUsername(rootView.getContext());
            tokenActual = Utilidades.retornarToken(rootView.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernameEditText = (EditText) rootView.findViewById(R.id.username_profile);
        NombreEditText = (EditText) rootView.findViewById(R.id.nombre_profile);
        ApellidoEditText = (EditText) rootView.findViewById(R.id.apellido_profile);
        FechaNacimientoEditText = (EditText) rootView.findViewById(R.id.fechaNacimiento_profile);
        CorreoEditText = (EditText) rootView.findViewById(R.id.correo_profile);
        TelefonoEditText = (EditText) rootView.findViewById(R.id.telefono_profile);
        FotoPerfil = (ImageView) rootView.findViewById(R.id.profile_picture_profile);

        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
        Call<Usuario> Llamada = Peticiones.ObtenerUsuario(tokenActual, UsuarioActual);
        Llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                switch (response.code()) {
                    case 200:
                        UsernameEditText.setText(response.body().getUsername());
                        NombreEditText.setText(response.body().getNombre());
                        ApellidoEditText.setText(response.body().getApellido());
                        FechaNacimientoEditText.setText(response.body().getFechaNacimiento());
                        CorreoEditText.setText(response.body().getCorreo());
                        TelefonoEditText.setText(response.body().getTelefono());
                        getProfilePicture(response.body().getImagen());
                        break;
                    case 401:
                        Toast.makeText(rootView.getContext(), "Su sesión expiró", Toast.LENGTH_LONG).show();
                        try {
                            Utilidades.escribirToken("", rootView.getContext());
                            Utilidades.escribirUsername("", rootView.getContext());
                            Intent Login = new Intent(getActivity(), MainActivity.class);
                            startActivity(Login);
                            getActivity().finish();
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
            public void onFailure(Call<Usuario> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(rootView.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void getProfilePicture(String id) {

    }

    private String formatear(String texto) {
        return texto.substring(1, texto.length() - 1);
    }
}
