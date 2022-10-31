package com.kailas.kb_hsbc_coding_challenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.api.RetrofitHelper
import com.kailas.kb_hsbc_coding_challenge.api.UsersApi
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import com.kailas.mvvmretrofit.viewModels.MainViewModel
import com.kailas.mvvmretrofit.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var maiViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quoteApi = RetrofitHelper.getInstance().create(UsersApi::class.java)
        val userRepository = UserRepository(quoteApi, applicationContext)

        maiViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(userRepository)
        ).get(MainViewModel::class.java)

        maiViewModel.users.observe(this, Observer {
            when (it) {
                is Response.Loading -> {
//Loading dialog
                }
                is Response.Success -> {
                    Log.e("KAILASA", "onCreate: " + (it.data?.size))
                    Toast.makeText(
                        this@MainActivity,
                        it.data?.size.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Response.Error -> {
                    Toast.makeText(
                        this@MainActivity,
                        it.errorMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }
}