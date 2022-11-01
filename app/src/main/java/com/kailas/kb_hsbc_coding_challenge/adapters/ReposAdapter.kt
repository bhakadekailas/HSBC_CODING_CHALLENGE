package com.kailas.kb_hsbc_coding_challenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kailas.kb_hsbc_coding_challenge.R
import com.kailas.kb_hsbc_coding_challenge.models.ReposItem

class ReposAdapter(private val context: Context, private val repos: List<ReposItem>) :
    RecyclerView.Adapter<ReposAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repos_item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvRepoName.text = repos[position].name
        holder.tvFullName.text = repos[position].full_name

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "We can redirect to Repository", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvRepoName: TextView = itemView.findViewById(R.id.tv_repo_name)
        var tvFullName: TextView = itemView.findViewById(R.id.tv_full_name)
    }
}