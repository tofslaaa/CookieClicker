package com.clickerhunt.cookieclicker.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.database.StorageBoost
import com.clickerhunt.cookieclicker.database.UsedBoost
import com.clickerhunt.cookieclicker.model.BoostModel
import com.clickerhunt.cookieclicker.model.ShopModel
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private lateinit var adapterShop: ShopAdapter
    private lateinit var adapterBoosts: StorageBoostsAdapter

    private val storageBoostsDao by lazy { AppDatabase.instance.storageBoostsDao() }
    private val usedBoostsDao by lazy { AppDatabase.instance.usedBoostsDao() }
    private val cookiesDao by lazy { AppDatabase.instance.configurationDao() }

    private val configurationLive by lazy { cookiesDao.getConfiguration() }
    private val configuration get() = configurationLive.value!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterShop = ShopAdapter(listenerShopAdapter)

        recycler_shop.layoutManager = LinearLayoutManager(requireContext())
        recycler_shop.adapter = adapterShop

        adapterBoosts = StorageBoostsAdapter(listenerBoostsAdapter)
        recycler_your_boost.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recycler_your_boost.adapter = adapterBoosts

        storageBoostsDao.getStorageBoosts().observe(viewLifecycleOwner) { boosts ->
            val storageBoosts = boosts.map { BoostModel(it.score, it.id, false) }
            adapterBoosts.values.clear()
            adapterBoosts.values.addAll(storageBoosts)
            adapterBoosts.notifyDataSetChanged()
            updateViews()
        }

        configurationLive.observe(viewLifecycleOwner) {
            adapterShop.cookiesCount = it.cookiesCount
            adapterShop.notifyDataSetChanged()
        }
    }

    private fun updateViews() {
        if (adapterBoosts.values.isEmpty()) {
            recycler_your_boost.visibility = View.INVISIBLE
            your_boost_title.visibility = View.INVISIBLE

            hint_text.visibility = View.VISIBLE
            hint_arrow.visibility = View.VISIBLE
        } else {
            recycler_your_boost.visibility = View.VISIBLE
            your_boost_title.visibility = View.VISIBLE

            hint_text.visibility = View.GONE
            hint_arrow.visibility = View.GONE
        }
    }

    private fun updateStorageBoosts(value: Int?) {
        if (value == null) {
            usedBoostsDao.insert(UsedBoost(0, 0, true))
        } else {
            storageBoostsDao.insert(StorageBoost(score = value))
        }
    }

    private val listenerShopAdapter = object : ShopAdapter.Listener {
        override fun onBuyClicked(shopModel: ShopModel) {
            cookiesDao.upsert(configuration.copy(cookiesCount = configuration.cookiesCount - shopModel.byFor))
            updateStorageBoosts(shopModel.boostValue)
        }
    }

    private val listenerBoostsAdapter = object : StorageBoostsAdapter.Listener {
        override fun onBoostClicked(boost: BoostModel) {
            Log.d("BOOST", "${boost.id} and ${boost.boostValue}")
            usedBoostsDao.getEmptyBoost()?.let { usedBoost ->
                storageBoostsDao.delete(boost.id)
                usedBoostsDao.update(UsedBoost(usedBoost.id, boost.boostValue, false))
            }
        }
    }

}