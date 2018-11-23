package com.ed2.aleja.objetos;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
