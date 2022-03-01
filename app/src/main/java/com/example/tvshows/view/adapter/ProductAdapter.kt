package com.example.tvshows.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.data.model.TvProgramsItem
import com.example.tvshows.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ProductAdapter (private val products: ArrayList<TvProgramsItem>, private val listener: OnShowClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductViewHolder(view: ItemProductBinding, listener: OnShowClickListener) : RecyclerView.ViewHolder(view.root) {
        var mBinding = view
        var listener = listener
        fun bind(program: TvProgramsItem) {
            mBinding.program = program
            mBinding.listener = listener
            if(program.show.image != null){
                Picasso.get().load(program.show.image.medium).into(mBinding.imageView)
            }
        }
    }

}

interface OnShowClickListener{
    fun onShowClick(view: View, idShow: Int)
}