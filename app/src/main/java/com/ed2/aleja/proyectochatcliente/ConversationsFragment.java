package com.ed2.aleja.proyectochatcliente;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ed2.aleja.utilidades.JwtUtility;
import com.google.gson.JsonArray;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConversationsFragment extends Fragment {

    View rootView;
    String UsuarioActual;
    String tokenActual;
    String baseURL = "http://ec2-18-220-77-115.us-east-2.compute.amazonaws.com:3000/";

    String emisor = "", _id = "", nuevos = "", imagenURL = "";
    ArrayList<ConversationListViewItem> conversaciones = null;

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
        JwtUtility jwtUtility = new JwtUtility();
        UsuarioActual = jwtUtility.retornarUsername(rootView.getContext());
        tokenActual = jwtUtility.retornarToken(rootView.getContext());

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
