package com.kailas.kb_hsbc_coding_challenge.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kailas.kb_hsbc_coding_challenge.adapters.UserAdapter
import com.kailas.kb_hsbc_coding_challenge.api.RetrofitHelper
import com.kailas.kb_hsbc_coding_challenge.api.UsersApi
import com.kailas.kb_hsbc_coding_challenge.databinding.FragmentAllUserBinding
import com.kailas.kb_hsbc_coding_challenge.models.Users
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import com.kailas.mvvmretrofit.viewModels.MainViewModel
import com.kailas.mvvmretrofit.viewModels.MainViewModelFactory

class AllUserFragment : Fragment() {
    private lateinit var maiViewModel: MainViewModel
    private lateinit var binding: FragmentAllUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val quoteApi = RetrofitHelper.getInstance().create(UsersApi::class.java)
        val userRepository = activity?.applicationContext?.let { UserRepository(quoteApi, it) }

        userRepository?.let {
            maiViewModel = ViewModelProvider(
                this,
                MainViewModelFactory(userRepository)
            )[MainViewModel::class.java]
        }

        maiViewModel.users.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {
                    // TODO: we can use loading dialog here
                }
                is Response.Success -> {
                    it.data?.let { users ->
                        showAllUsers(users)
                    }
                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showAllUsers(users: Users) {
        binding.allUserRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(context, users)
        }
    }
}