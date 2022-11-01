package com.kailas.kb_hsbc_coding_challenge.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kailas.kb_hsbc_coding_challenge.models.Repos
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val repository: UserRepository, private val username: String) :
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllRepo(username)
        }
    }

    val repos: LiveData<Response<Repos>>
        get() = repository.repos
}