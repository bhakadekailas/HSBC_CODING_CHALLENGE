package com.kailas.kb_hsbc_coding_challenge.api

import com.kailas.kb_hsbc_coding_challenge.models.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): Response<Users>
}