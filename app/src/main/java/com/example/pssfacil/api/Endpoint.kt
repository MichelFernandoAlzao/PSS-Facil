package com.example.pssfacil.api


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("/ws/{cep}/json/")
    fun getEndereco(@Path(value = "cep", encoded = true) cep:String) : Call<JsonObject>
}