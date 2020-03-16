package com.clickerhunt.cookieclicker.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.model.ShopModel
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private lateinit var adapter: ShopAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShopAdapter(
            listOf(
                ShopModel(1, R.string.shop_1, 50),
                ShopModel(5, R.string.shop_5, 500),
                ShopModel(10, R.string.shop_10, 2500),
                ShopModel(boostText = R.string.shop_additional, byFor = 10000)
            ), listenerAdapter
        )

        recycler_shop.layoutManager = LinearLayoutManager(requireContext())
        recycler_shop.adapter = adapter
    }

    private val listenerAdapter = object : ShopAdapter.Listener {
        override fun onBuyClicked(shopModel: ShopModel) {
            Log.d("On Buy Clicked", "${shopModel.boostValue}")
        }
    }
}