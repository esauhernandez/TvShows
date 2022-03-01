package com.example.tvshows.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.data.model.PersonItem
import com.example.tvshows.databinding.ItemTalentBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class TalentAdapter (private val talents: ArrayList<PersonItem>) : RecyclerView.Adapter<TalentAdapter.TalentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentViewHolder {
        var binding = ItemTalentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TalentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return talents.size
    }

    override fun onBindViewHolder(holder: TalentViewHolder, position: Int) {
        holder.bind(talents[position])
    }

    inner class TalentViewHolder(view: ItemTalentBinding) : RecyclerView.ViewHolder(view.root) {
        var mBinding = view
        fun bind(talent: PersonItem) {
            mBinding.talent = talent
            if(talent.person.image != null){
                Picasso.get().load(talent.person.image.medium).into(mBinding.imageView)
            }
        }
    }

}
