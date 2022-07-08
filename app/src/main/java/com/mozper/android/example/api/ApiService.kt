package com.mozper.android.example.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET
    suspend fun getEmployes(
        @HeaderMap headerMap: HashMap<String, String>,
        @Url path: String
    ): Response<JsonObject>
}