package com.ed2.aleja.proyectochatcliente;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Usuario;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.JwtUtility;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConversationsFragment extends Fragment {

    View rootView;
    String UsuarioActual;
    String tokenActual;
    String baseURL = "http://ec2-18-220-77-115.us-east-2.compute.amazonaws.com:3000/";

    String emisor = "", _id = "", nuevos = "", imagenURL = "";
    ArrayList<ConversationListViewItem> conversaciones = null;
    int i;
    JsonArray arrayConversaciones;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.conversations_fragment, container, false);

        JwtUtility jwtUtility = new JwtUtility();
        UsuarioActual = jwtUtility.retornarUsername(rootView.getContext());
        tokenActual = jwtUtility.retornarToken(rootView.getContext());

        return rootView;
    }

}
