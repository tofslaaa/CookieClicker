package com.clickerhunt.cookieclicker.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.model.BoostModel
import kotlinx.android.synthetic.main.item_boost.view.*

class StorageBoostsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<StorageBoostsAdapter.ViewHolder>() {

    val values = mutableListOf<BoostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_boost, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.boostValue.text = "+${values[position].boostValue}"
        holder.itemView.setOnClickListener { listener.onBoostClicked(values[position]) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boostValue: TextView = itemView.boost_value
    }

    interface Listener {
        fun onBoostClicked(boost: BoostModel)
    }
}