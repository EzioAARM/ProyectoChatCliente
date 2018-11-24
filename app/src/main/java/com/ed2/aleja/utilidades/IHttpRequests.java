package com.ed2.aleja.utilidades;

import com.ed2.aleja.objetos.Conversaciones;
import com.ed2.aleja.objetos.Mensaje;
import com.ed2.aleja.objetos.Token;
import com.ed2.aleja.objetos.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("users/all/{username}")
    Call<ArrayList<Usuario>> ObtenerTodosUsuarios(@Header("Authorization") String token,
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

    @PUT("conversaciones/reiniciar/{conversacion}/{usernameEmisor}/{username}")
    Call<Token> BorrarFlags(@Header("Authorization") String token,
                            @Path("conversacion") String id,
                            @Path("usernameEmisor") String emisor,
                            @Path("username") String username);

    /* Terminan las peticiones de las conversaciones */

    /* Empiezan las peticiones de los mensajes */

    @GET("mensajes/{username}/{id}")
    Call<ArrayList<Mensaje>> ObtenerMensajes(@Header("Authorization") String token,
                                             @Path("username") String username,
                                             @Path("id") String id);

    @PUT("mensajes/leer/{conversacion}/{receptor}/{username}")
    Call<Token> ActualizarLeidos(@Header("Authorization") String token,
                                        @Path("conversacion") String id,
                                        @Path("receptor") String receptor,
                                        @Path("username") String username);

    /* Terminan las peticiones de los mensajes */

    /* Peticiones multipart */
    @Multipart
    @GET("download/{username}/{nombre}")
    Call<ResponseBody> SubirArchivo(@Header("Authorization") String token,
                                    @Body MultipartBody.Part file);

}
