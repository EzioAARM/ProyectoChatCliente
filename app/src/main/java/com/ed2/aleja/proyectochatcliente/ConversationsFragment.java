package com.ed2.aleja.proyectochatcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.utilidades.IHttpRequests;
import com.ed2.aleja.utilidades.Utilidades;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationsFragment extends Fragment {

    View rootView;
    String UsuarioActual;
    String tokenActual;

    FloatingActionButton nuevoChat;
    FloatingActionButton nuevoGrupo;
    boolean isFABOpen = false;

    IHttpRequests Peticiones;

    ArrayList<ConversationListViewItem> ListViewItems;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.conversations_fragment, container, false);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        FloatingActionButton nuevaConversacion = (FloatingActionButton) rootView.findViewById(R.id.nuevo_conversacion);
        nuevoChat = (FloatingActionButton) rootView.findViewById(R.id.crear_chat);
        nuevoGrupo = (FloatingActionButton) rootView.findViewById(R.id.crear_grupo);
        nuevoGrupo.setVisibility(View.INVISIBLE);
        nuevaConversacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
        try {
            UsuarioActual = Utilidades.retornarUsername(rootView.getContext());
            tokenActual = Utilidades.retornarToken(rootView.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Peticiones = Utilidades.RetrofitClient.create(IHttpRequests.class);
        Call<ArrayList<Conversaciones>> Llamada = Peticiones.ObtenerConversacioens(tokenActual, UsuarioActual);
        Llamada.enqueue(new Callback<ArrayList<Conversaciones>>() {
            @Override
            public void onResponse(Call<ArrayList<Conversaciones>> call, Response<ArrayList<Conversaciones>> response) {
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                switch (response.code()) {
                    case 201:

                        break;
                    case 502:
                        onFailure(call, new Exception(getString(R.string.error_502)));
                        break;
                    default:
                        onFailure(call, new Exception("Error desconocido"));
                        break;
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Conversaciones>> call, Throwable t) {
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(rootView.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        nuevoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CrearChat = new Intent(getActivity(), ContactsListActivity.class);
                getActivity().startActivity(CrearChat);
            }
        });

        return rootView;
    }

    private void MostrarConversaciones(ArrayList<Conversaciones> conversaciones) {
        ListViewItems = new ArrayList<>();
        for (int i = 0; i < conversaciones.size()) {
            
        }
    }

    private void showFABMenu(){
        isFABOpen=true;
        nuevoChat.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        nuevoGrupo.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        nuevoChat.animate().translationY(0);
        nuevoGrupo.animate().translationY(0);
    }

}
