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

class ShopAdapter(
    private val listener: Listener
) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    var cookiesCount: Int = 0
    private val values = listOf(
        ShopModel(1, R.string.shop_1, 5),
        ShopModel(5, R.string.shop_5, 10),
        ShopModel(10, R.string.shop_10, 15),
        ShopModel(boostText = R.string.shop_additional, byFor = 20)
    )

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
        } else {
            holder.boostValue.text = "+${shopModel.boostValue}"
        }

        val available = shopModel.byFor <= cookiesCount
        holder.buyButton?.apply {
            isEnabled = available
            alpha = if (available) 1f else 0.5f
        }

        holder.boostText.setText(shopModel.boostText)
        holder.buyFor.text = "Купить за ${shopModel.byFor.format()}"

        holder.buyButton?.setOnClickListener {
            listener.onBuyClicked(shopModel)
        }

    }

    private fun Int.format(): String {
        return if (this >= 1000) {
            val second = this.toString().takeLast(3)
            val first = this.toString().removeSuffix(second)
            "$first $second"
        } else {
            this.toString()
        }
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