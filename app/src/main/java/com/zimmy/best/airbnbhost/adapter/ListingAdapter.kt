package com.zimmy.best.airbnbhost.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zimmy.best.airbnbhost.databinding.ListedItemBinding
import com.zimmy.best.airbnbhost.model.BasicDetails

class ListingAdapter(
    private val listedList: ArrayList<BasicDetails>,
    private val context: Context
) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding = ListedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        with(holder) {
            with(listedList[position]) {
                Picasso.get().load(this.basicPhoto).into(binding.basicImage)
                binding.address.text = this.address
                binding.title.text = this.title
                binding.rating.text= this.rating.toString()
                binding.price.text = buildString {
                    append("&#163;")
                    append(this@with.price)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listedList.size
    }

    inner class ListingViewHolder(val binding: ListedItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}