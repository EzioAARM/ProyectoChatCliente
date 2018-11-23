package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

public class Conversaciones {

    @SerializedName("_id")
    public String _id;

    @SerializedName("token")
    String Token;

    @SerializedName("user1")
    String UsuarioEmisor;

    @SerializedName("user2")
    String UsuarioReceptor;

    @SerializedName("esGrupo")
    boolean esGrupo;

    @SerializedName("fotoUser1")
    String fotoUser1;

    @SerializedName("fotoUser2")
    String fotoUser2;

    @SerializedName("eliminoUser1")
    boolean eliminoUser1;

    @SerializedName("eliminoUser2")
    boolean eliminoUser2;

    @SerializedName("nuevos")
    int mensajesNuevos;

    @SerializedName("ultimoMensaje")
    String ultimoMensaje;

    public Conversaciones(String usuarioEmisor, String usuarioReceptor, boolean esGrupo, String fotoUser1, String fotoUser2,
                          boolean eliminoUser1, boolean eliminoUser2, int mensajesNuevos, String ultimoMensaje) {
        UsuarioEmisor = usuarioEmisor;
        UsuarioReceptor = usuarioReceptor;
        this.esGrupo = esGrupo;
        this.fotoUser1 = fotoUser1;
        this.fotoUser2 = fotoUser2;
        this.eliminoUser1 = eliminoUser1;
        this.eliminoUser2 = eliminoUser2;
        this.mensajesNuevos = mensajesNuevos;
        this.ultimoMensaje = ultimoMensaje;
    }

    public Conversaciones() {

    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isEliminoUser1() {
        return eliminoUser1;
    }

    public void setEliminoUser1(boolean eliminoUser1) {
        this.eliminoUser1 = eliminoUser1;
    }

    public boolean isEliminoUser2() {
        return eliminoUser2;
    }

    public void setFotoUser1(String fotoUser1) {
        this.fotoUser1 = fotoUser1;
    }

    public void setEliminoUser2(boolean eliminoUser2) {
        this.eliminoUser2 = eliminoUser2;
    }

    public String getFotoUser1() {
        return fotoUser1;
    }

    public String getFotoUser2() {
        return fotoUser2;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setFotoUser2(String fotoUser2) {
        this.fotoUser2 = fotoUser2;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

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
