package com.clickerhunt.cookieclicker.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.model.BoostModel
import kotlinx.android.synthetic.main.item_boost.view.*

class StorageBoostsAdapter(private val listener: Listener) :
    ListAdapter<BoostModel, StorageBoostsAdapter.ViewHolder>(BoostModelDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_boost, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.boostValue.text = "+${item.boostValue}"
        holder.itemView.setOnClickListener { listener.onBoostClicked(item) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boostValue: TextView = itemView.boost_value
    }

    interface Listener {
        fun onBoostClicked(boost: BoostModel)
    }

}