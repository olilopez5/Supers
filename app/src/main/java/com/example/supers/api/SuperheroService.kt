package com.example.supers.api

import com.example.supers.Superhero
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroService {

    //Interfaz para llamar a retrofit -> configurar @get ....
    //Ruta dinamica {x} -> @Path("x") query es el nombre que le ponemos a la busqueda
    //suspend -> para ejecutar en segundo plano
    // parse auto con retrofit

    @GET("search/{name}")
    suspend fun findSupersByName(@Path("name")query: String) : Superhero

    @GET("{id}")
    suspend fun findSuperheroById(@Path("id")id: String) : Call<Superhero>

}