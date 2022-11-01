package com.kailas.kb_hsbc_coding_challenge.views.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.adapters.UserAdapter
import com.kailas.kb_hsbc_coding_challenge.databinding.FragmentFavoriteUserBinding
import com.kailas.kb_hsbc_coding_challenge.models.UserItem
import java.lang.reflect.Type

class FavoriteUserFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var userAdapter: UserAdapter
    private var userList: List<UserItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userList = getArrayList()
        userList?.let { showFavoriteUsers(it) }
    }

    private fun getArrayList(): List<UserItem>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString("USER_LIST", null)
        if (json != null) {
            val type: Type = object : TypeToken<ArrayList<UserItem?>?>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    private fun showFavoriteUsers(favoriteUsers: List<UserItem>) {
        binding.favoriteUserRecyclerView.layoutManager = LinearLayoutManager(context)
        userAdapter = context?.let { UserAdapter(it, favoriteUsers) }!!
        binding.favoriteUserRecyclerView.adapter = userAdapter

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
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun updateList(newText: String) {
        val newList = mutableListOf<UserItem>()
        for (userItem in userList!!) {
            if (userItem.login.lowercase().contains(newText.lowercase())) {
                newList.add(userItem)
            }
        }
        userAdapter.updateList(newList)
    }
}