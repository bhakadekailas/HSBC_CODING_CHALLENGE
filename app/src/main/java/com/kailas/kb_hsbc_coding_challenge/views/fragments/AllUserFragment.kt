package com.kailas.kb_hsbc_coding_challenge.views.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.adapters.UserAdapter
import com.kailas.kb_hsbc_coding_challenge.api.RetrofitHelper
import com.kailas.kb_hsbc_coding_challenge.api.UsersApi
import com.kailas.kb_hsbc_coding_challenge.databinding.FragmentAllUserBinding
import com.kailas.kb_hsbc_coding_challenge.models.UserItem
import com.kailas.kb_hsbc_coding_challenge.models.Users
import com.kailas.kb_hsbc_coding_challenge.repository.Response
import com.kailas.kb_hsbc_coding_challenge.repository.UserRepository
import com.kailas.mvvmretrofit.viewModels.MainViewModel
import com.kailas.mvvmretrofit.viewModels.MainViewModelFactory

class AllUserFragment : Fragment() {
    private lateinit var maiViewModel: MainViewModel
    private lateinit var binding: FragmentAllUserBinding
    private lateinit var userList: List<UserItem>
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userApi = RetrofitHelper.getInstance().create(UsersApi::class.java)
        val userRepository = activity?.applicationContext?.let { UserRepository(userApi, it) }

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
                        userList = users
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView? = searchItem.actionView as SearchView?
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                updateList(queryText)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                updateList(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun updateList(newText: String) {
        val newList = mutableListOf<UserItem>()
        for (userItem in userList) {
            if (userItem.login.lowercase().contains(newText.lowercase())) {
                newList.add(userItem)
            }
        }
        userAdapter.updateList(newList)
    }

    private fun showAllUsers(users: Users) {
        binding.allUserRecyclerView.layoutManager = LinearLayoutManager(context)
        userAdapter = context?.let { UserAdapter(it, users) }!!
        binding.allUserRecyclerView.adapter = userAdapter
    }
}