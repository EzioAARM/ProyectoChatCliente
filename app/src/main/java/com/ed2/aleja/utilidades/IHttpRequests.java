package com.ed2.aleja.utilidades;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Usuario;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("users/buscarImagenPerfil/{user}")
    Call<ResponseBody> buscarImagenPerfil(@Header("Authorization") String token,
                                          @Path("user") String user);

    @GET("conversaciones/todas/{username}")
    Call<ResponseBody> obtenerConversaciones(@Header("Authorization") String token,
            @Path("username") String username);

    @GET("conversaciones/info/{username}/{emisor}")
    Call<ResponseBody> obtenerMensajesNuevos(@Header("Authorization") String token,
                                             @Path("username") String username,
                                             @Path("emisor") String emisor);

}
