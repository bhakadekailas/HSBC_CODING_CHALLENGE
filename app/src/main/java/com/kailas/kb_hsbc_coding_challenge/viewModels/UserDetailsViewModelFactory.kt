package com.kailas.kb_hsbc_coding_challenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository

class UserDetailsViewModelFactory(
    private val repository: UserRepository,
    private val username: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(repository, username) as T
    }
}