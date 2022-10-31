package com.kailas.kb_hsbc_coding_challenge.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailas.kb_hsbc_coding_challenge.api.UsersApi
import com.kailas.kb_hsbc_coding_challenge.models.Users
import com.kailas.kb_hsbc_coding_challenge.utils.NetworkUtils


class UserRepository(
    private val usersApi: UsersApi,
    private val applicationContext: Context
) {
    private val usersLiveData = MutableLiveData<Response<Users>>()

    val users: LiveData<Response<Users>>
        get() = usersLiveData

    suspend fun getUsers() {
        if (NetworkUtils.isOnline(applicationContext)) {
            try {
                val result = usersApi.getUsers()
                if (result.body() != null) {
                    usersLiveData.postValue(Response.Success(result.body()))
                } else {
                    usersLiveData.postValue(Response.Error("Api error"))
                }
            } catch (e: Exception) {
                usersLiveData.postValue(Response.Error(e.message.toString()))
            }
        } else {
            usersLiveData.postValue(Response.Error("Network Not Found"))
        }
    }
}