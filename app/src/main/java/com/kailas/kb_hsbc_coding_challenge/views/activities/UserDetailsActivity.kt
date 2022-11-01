package com.kailas.kb_hsbc_coding_challenge.views.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kailas.kb_hsbc_coding_challenge.adapters.ReposAdapter
import com.kailas.kb_hsbc_coding_challenge.api.RetrofitHelper
import com.kailas.kb_hsbc_coding_challenge.api.UsersApi
import com.kailas.kb_hsbc_coding_challenge.databinding.ActivityUserDetailsBinding
import com.kailas.kb_hsbc_coding_challenge.models.Repos
import com.kailas.kb_hsbc_coding_challenge.models.UserItem
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import com.kailas.kb_hsbc_coding_challenge.viewModels.UserDetailsViewModel
import com.kailas.kb_hsbc_coding_challenge.viewModels.UserDetailsViewModelFactory

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapUi()
    }

    private fun mapUi() {
        val gson = Gson()
        val user: UserItem = gson.fromJson(intent.getStringExtra("USER"), UserItem::class.java)

        Glide.with(applicationContext).load(user.avatar_url).into(binding.ivAvatar)
        binding.tvLogin.text = user.login
        binding.tvNode.text = user.node_id

        val quoteApi = RetrofitHelper.getInstance().create(UsersApi::class.java)
        val userRepository = applicationContext?.let { UserRepository(quoteApi, it) }

        userRepository?.let {
            userDetailsViewModel = ViewModelProvider(
                this,
                UserDetailsViewModelFactory(userRepository, user.login)
            )[UserDetailsViewModel::class.java]
        }

        userDetailsViewModel.repos.observe(this) {
            when (it) {
                is Response.Loading -> {
                    // TODO: we can use loading dialog here
                }
                is Response.Success -> {
                    it.data?.let { repos ->
                        showAllRepository(repos)
                    }
                }
                is Response.Error -> {
                    Toast.makeText(
                        this,
                        it.errorMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showAllRepository(repos: Repos) {
        binding.rvAllRepository.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = ReposAdapter(context, repos)
        }
    }
}