package com.kailas.mvvmretrofit.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kailas.kb_hsbc_coding_challenge.models.Users
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers()
        }
    }

    val users: LiveData<Response<Users>>
        get() = repository.users
}