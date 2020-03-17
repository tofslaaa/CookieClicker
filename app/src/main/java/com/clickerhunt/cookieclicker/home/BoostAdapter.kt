package com.clickerhunt.cookieclicker.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.model.BoostModel
import kotlinx.android.synthetic.main.item_boost.view.*

class BoostAdapter(private val listener: Listener) :
    RecyclerView.Adapter<BoostAdapter.ViewHolder>() {

    val values = (1..3).map { BoostModel() }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_boost, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val boostModel = values[position]

        if (boostModel.boostValue == null) {
            holder.boostValue.visibility = View.INVISIBLE
            holder.boostIcon.visibility = View.INVISIBLE
            holder.boostTick.visibility = View.INVISIBLE
            holder.deleteButton.visibility = View.INVISIBLE
        } else {
            holder.boostValue.text = "+${boostModel.boostValue}"
            holder.deleteButton.setOnClickListener { listener.onDeleteBoostClicked(position) }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boostValue: TextView = itemView.boost_value
        val boostIcon: ImageView = itemView.boost_image
        val boostTick: TextView = itemView.boost_tick

        val deleteButton: ImageView = itemView.boost_delete_button
    }

    interface Listener {
        fun onDeleteBoostClicked(position: Int)
    }
}