package com.kailas.kb_hsbc_coding_challenge.adapters

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.models.UserItem
import com.kailas.kb_hsbc_coding_challenge.views.activities.UserDetailsActivity
import java.lang.reflect.Type

class UserAdapter(private val context: Context, private var users: List<UserItem>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvLogin.text = users[position].login
        holder.tvNode.text = users[position].node_id
        Glide.with(context).load(users[position].avatar_url).into(holder.ivAvtar)

        if (users[position].isFavorite) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_purple)
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_red)
        }
        holder.itemView.setOnClickListener {
            val gson = Gson()
            val intent = Intent(context, UserDetailsActivity::class.java)
            val user = gson.toJson(users[position])
            intent.putExtra("USER", user)
            context.startActivity(intent)
        }

        holder.ivFavorite.setOnClickListener {
            users[position].isFavorite = !users[position].isFavorite
            notifyItemChanged(position)
            updateStoredPreference(users[position])
            Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStoredPreference(newUser: UserItem) {
        val prefFavoriteUserList = getArrayList()
        if (prefFavoriteUserList.isNullOrEmpty()) {
            val newFavoriteUserList = mutableListOf<UserItem>()
            newFavoriteUserList.add(newUser)
            saveUser(newFavoriteUserList)
        } else {
            prefFavoriteUserList.add(newUser)
            saveUser(prefFavoriteUserList)
        }
    }

    private fun saveUser(newFavoriteUserList: MutableList<UserItem>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(newFavoriteUserList)
        editor.putString("USER_LIST", json)
        editor.apply()
    }

    private fun getArrayList(): MutableList<UserItem>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString("USER_LIST", null)
        if (json != null) {
            val type: Type = object : TypeToken<ArrayList<UserItem?>?>() {}.type
            return gson.fromJson(json, type)
        }
        return null
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLogin: TextView = itemView.findViewById(R.id.tv_login)
        var tvNode: TextView = itemView.findViewById(R.id.tv_node)
        var ivAvtar: ImageView = itemView.findViewById(R.id.iv_avtar)
        var ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)
    }

    fun updateList(updatedList: List<UserItem>) {
        this.users = updatedList
        notifyDataSetChanged()
    }
}