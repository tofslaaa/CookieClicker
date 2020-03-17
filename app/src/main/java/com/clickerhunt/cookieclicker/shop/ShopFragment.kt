package com.clickerhunt.cookieclicker.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.model.ShopModel
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment(private val listener: Listener) : Fragment(R.layout.fragment_shop) {

    private lateinit var adapter: ShopAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cookiesCount =
            AppDatabase.invoke(requireContext()).configurationDao().getConfiguration()?.cookiesCount
        adapter = ShopAdapter(listenerAdapter)
        if (cookiesCount != null) adapter.cookiesCount = cookiesCount

        recycler_shop.layoutManager = LinearLayoutManager(requireContext())
        recycler_shop.adapter = adapter
    }

    private val listenerAdapter = object : ShopAdapter.Listener {
        override fun onBuyClicked(shopModel: ShopModel) {
            listener.onBuyBoost(shopModel.byFor)
            adapter.cookiesCount -= shopModel.byFor
            adapter.notifyDataSetChanged()
        }
    }

    interface Listener {
        fun onBuyBoost(cost: Int)
    }
}