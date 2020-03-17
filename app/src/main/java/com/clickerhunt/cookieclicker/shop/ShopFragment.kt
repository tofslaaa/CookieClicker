package com.clickerhunt.cookieclicker.shop

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.database.StorageBoost
import com.clickerhunt.cookieclicker.model.BoostModel
import com.clickerhunt.cookieclicker.model.ShopModel
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment(private val listener: Listener) : Fragment(R.layout.fragment_shop) {

    private lateinit var adapterShop: ShopAdapter
    private lateinit var adapterBoosts: StorageBoostsAdapter

    private val storageBoostsDao by lazy { AppDatabase.invoke(requireContext()).storageBoostsDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cookiesCount =
            AppDatabase.invoke(requireContext()).configurationDao().getConfiguration()?.cookiesCount
        adapterShop = ShopAdapter(listenerShopAdapter)
        if (cookiesCount != null) adapterShop.cookiesCount = cookiesCount

        recycler_shop.layoutManager = LinearLayoutManager(requireContext())
        recycler_shop.adapter = adapterShop

        adapterBoosts = StorageBoostsAdapter(listenerBoostsAdapter)
        val storageBoosts = storageBoostsDao.getStorageBoosts().map {
            BoostModel(it.score, it.id)
        }
        recycler_your_boost.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recycler_your_boost.adapter = adapterBoosts
        if (storageBoosts != null) {
            adapterBoosts.values.addAll(storageBoosts)
            adapterBoosts.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        updateViews()
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
            listener.onBuyAdditionalSlot()
        } else {
            storageBoostsDao.insert(StorageBoost(score = value))
            adapterBoosts.values.add(
                BoostModel(
                    value,
                    storageBoostsDao.getStorageBoosts().last().id
                )
            )
            adapterBoosts.notifyDataSetChanged()
            updateViews()
        }
    }

    private val listenerShopAdapter = object : ShopAdapter.Listener {
        override fun onBuyClicked(shopModel: ShopModel) {
            listener.onBuyBoost(shopModel.byFor)
            updateStorageBoosts(shopModel.boostValue)
            adapterShop.cookiesCount -= shopModel.byFor
            adapterShop.notifyDataSetChanged()
        }
    }

    private val listenerBoostsAdapter = object : StorageBoostsAdapter.Listener {
        override fun onBoostClicked(boost: BoostModel) {
            Log.d("BOOST", "${boost.id} and ${boost.boostValue}")

            listener.onBoostClicked(boost) { done ->
                if (done) {
                    storageBoostsDao.delete(storageBoostsDao.getBoostById(boost.id!!))

                    adapterBoosts.values.remove(boost)
                    adapterBoosts.notifyDataSetChanged()
                    updateViews()
                }
            }
        }
    }

    interface Listener {
        fun onBuyBoost(cost: Int)
        fun onBuyAdditionalSlot()
        fun onBoostClicked(boost: BoostModel, callback: (Boolean) -> Unit)
    }
}