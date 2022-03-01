package com.example.tvshows.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.data.model.QuerySearch
import com.example.tvshows.databinding.ItemSearchBinding
import java.util.*

class QueryAdapter(private val queries: List<QuerySearch>, private val listener: OnQueryClickListener) : RecyclerView.Adapter<QueryAdapter.QueryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        var binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QueryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return queries.size
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        holder.bind(queries[position])
    }

    inner class QueryViewHolder(view: ItemSearchBinding, listener: OnQueryClickListener) : RecyclerView.ViewHolder(view.root) {
        var mBinding = view
        var listener = listener
        fun bind(query: QuerySearch) {
            mBinding.query = query
            mBinding.listener = listener
        }
    }
}

interface OnQueryClickListener{
    fun onQueryClick(view: View, query: QuerySearch)
}