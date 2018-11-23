package com.ed2.aleja.utilidades;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.objetos.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IHttpRequests {

    /* Peticiones del usuario */
    @GET("users/login/{user}/{password}")
    Call<Token> LogearUsuario(@Path("user") String user,
                              @Path("password") String password);

    @GET("users/buscar/{username}")
    Call<Usuario> BuscarUsuario(@Path("username") String username);

    @GET("users/{username}")
    Call<Usuario> ObtenerUsuario(@Header("Authorization") String token,
                                            @Path("username") String username);

    @POST("users/registrar")
    Call<ResponseBody> RegistrarUsuario(@Body Usuario user);

    @PUT("users/{username}")
    Call<Token> ActualizarUsuario(@Header("Authorization") String token,
                                         @Path("username") String username,
                                         @Body Usuario userToUpdate);

    @PUT("users/recuperar/{username}")
    Call<ResponseBody> UpdatePassword(@Path("username") String username,
                                      @Body String password);

    @DELETE("users/{username}")
    Call<Token> EliminarUsuario(@Header("Authorization") String token,
                                    @Path("username") String username);

    /* Terminan las peticiones del usuario */

    /* Empiezan las peticiones de las conversaciones */

    @POST("conversaciones/nueva")
    Call<Token> CrearConversacion(@Header("Authorization") String token,
                                         @Body Conversaciones conversacion);

    @GET("conversaciones/todas/{username}")
    Call<ArrayList<Conversaciones>> ObtenerConversacioens(@Header("Authorization") String token,
                                                          @Path("username") String usernameActual);

    /* Terminan las peticiones de las conversaciones */

}
