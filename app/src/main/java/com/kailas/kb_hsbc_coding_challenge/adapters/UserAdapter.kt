package com.kailas.kb_hsbc_coding_challenge.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.models.UserItem


class UserAdapter(private val context: Context, private val users: List<UserItem>) :
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
            Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, DetailsView::class.java)
//            intent.putExtra("URL", articles[position].url)
//            context.startActivity(intent)
        }

        holder.ivFavorite.setOnClickListener {
            users[position].isFavorite = !users[position].isFavorite
            notifyItemChanged(position)
            Toast.makeText(context, "Item added into Favorite", Toast.LENGTH_SHORT).show()
        }
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
}