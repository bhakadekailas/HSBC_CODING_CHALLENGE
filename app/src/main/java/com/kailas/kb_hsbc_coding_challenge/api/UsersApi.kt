package com.kailas.kb_hsbc_coding_challenge.api

import com.kailas.kb_hsbc_coding_challenge.models.Repos
import com.kailas.kb_hsbc_coding_challenge.models.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): Response<Users>

    @GET("/users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<Repos>
}