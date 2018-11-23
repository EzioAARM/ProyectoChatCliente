package com.ed2.aleja.proyectochatcliente;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ed2.aleja.utilidades.Utilidades;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;

public class ConversationsFragment extends Fragment {

    View rootView;
    String UsuarioActual;
    String tokenActual;

    FloatingActionButton nuevoChat;
    FloatingActionButton nuevoGrupo;
    boolean isFABOpen = false;
    int i;
    JsonArray arrayConversaciones;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.conversations_fragment, container, false);

        FloatingActionButton nuevaConversacion = (FloatingActionButton) rootView.findViewById(R.id.nuevo_conversacion);
        nuevoChat = (FloatingActionButton) rootView.findViewById(R.id.crear_chat);
        nuevoGrupo = (FloatingActionButton) rootView.findViewById(R.id.crear_grupo);
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

        return rootView;
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
