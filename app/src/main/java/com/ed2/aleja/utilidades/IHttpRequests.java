package com.ed2.aleja.utilidades;

import com.ed2.aleja.objetos.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IHttpRequests {

    @POST("users/registrar")
    Call<ResponseBody> registrarUser(@Body Usuario user);

    @GET("users/login/{user}/{password}")
    Call<ResponseBody> logearUsuario(@Path("user") String user,
    @Path("password") String password);

    @GET("users/buscarExacto/{user}")
    Call<ResponseBody> buscarExacto(@Path("user") String user);

}
