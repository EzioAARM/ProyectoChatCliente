package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConversacionesToken {
    @SerializedName("conversaciones")
    ArrayList<Conversaciones> Conversaciones;

    @SerializedName("token")
    String token;

    public ConversacionesToken(ArrayList<com.ed2.aleja.objetos.Conversaciones> conversaciones, String token) {
        Conversaciones = conversaciones;
        this.token = token;
    }

    public ArrayList<com.ed2.aleja.objetos.Conversaciones> getConversaciones() {
        return Conversaciones;
    }

    public String getToken() {
        return token;
    }

    public void setConversaciones(ArrayList<com.ed2.aleja.objetos.Conversaciones> conversaciones) {
        Conversaciones = conversaciones;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
