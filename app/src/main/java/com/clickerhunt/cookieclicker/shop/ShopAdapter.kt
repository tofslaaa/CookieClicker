package com.clickerhunt.cookieclicker.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.model.ShopModel
import kotlinx.android.synthetic.main.item_shop.view.*

class ShopAdapter(private val values: List<ShopModel>, private val listener: Listener) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopModel = values[position]

        if (shopModel.boostValue == null) {
            holder.boostValue.visibility = View.INVISIBLE
            holder.boostIcon.visibility = View.INVISIBLE
            holder.boostTick.visibility = View.INVISIBLE
        }

        holder.boostText.text = shopModel.boostText
        holder.buyFor.text = "Купить за ${shopModel.byFor}"

        holder.buyButton?.setOnClickListener { listener.onBuyClicked(shopModel) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boostValue: TextView = itemView.shop_boost_value
        val boostIcon: ImageView = itemView.shop_boost_icon
        val boostTick: TextView = itemView.shop_boost_tick

        val boostText: TextView = itemView.shop_item_text

        val buyButton: LinearLayoutCompat? = itemView.buy_button
        val buyFor: TextView = itemView.buy_for
    }

    interface Listener {
        fun onBuyClicked(shopModel: ShopModel)
    }
}