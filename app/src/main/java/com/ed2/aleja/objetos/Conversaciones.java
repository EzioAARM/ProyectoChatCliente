package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

public class Conversaciones {

    @SerializedName("_id")
    public String _id;

    @SerializedName("user1")
    String UsuarioEmisor;

    @SerializedName("user2")
    String UsuarioReceptor;

    @SerializedName("esGrupo")
    boolean esGrupo;

    @SerializedName("nuevos")
    int mensajesNuevos;

    public int getMensajesNuevos() {
        return mensajesNuevos;
    }

    public void setMensajesNuevos(int mensajesNuevos) {
        this.mensajesNuevos = mensajesNuevos;
    }

    public String getUsuarioEmisor() {
        return UsuarioEmisor;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    public String getUsuarioReceptor() {
        return UsuarioReceptor;
    }

    public void setUsuarioEmisor(String usuarioEmisor) {
        UsuarioEmisor = usuarioEmisor;
    }

    public void setUsuarioReceptor(String usuarioReceptor) {
        UsuarioReceptor = usuarioReceptor;
    }

    public boolean isEsGrupo() {
        return esGrupo;
    }
}
