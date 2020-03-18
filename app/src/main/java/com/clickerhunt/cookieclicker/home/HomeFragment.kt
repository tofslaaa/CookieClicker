package com.clickerhunt.cookieclicker.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.cookie.CookieFragment
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.database.Configuration
import com.clickerhunt.cookieclicker.database.StorageBoost
import com.clickerhunt.cookieclicker.database.UsedBoost
import com.clickerhunt.cookieclicker.model.BoostModel
import com.clickerhunt.cookieclicker.settings.SettingsManager
import com.clickerhunt.cookieclicker.shop.ShopFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: BoostAdapter

    private val cookiesDao by lazy { AppDatabase.instance.configurationDao() }
    private val usedBoostsDao by lazy { AppDatabase.instance.usedBoostsDao() }
    private val storageBoostsDao by lazy { AppDatabase.instance.storageBoostsDao() }

    private val configurationLive by lazy { cookiesDao.getConfiguration() }
    private val configuration get() = configurationLive.value!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BoostAdapter(listenerAdapter)

        recycler_boost.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler_boost.adapter = adapter

        shop_open_button.setOnClickListener { displayShop() }
        shop_close_button.setOnClickListener { displayCookie() }
        settings_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        usedBoostsDao.getUsedBoosts().observe(viewLifecycleOwner) { boosts ->
            val storageBoosts = boosts.map { BoostModel(it.score, it.id, it.empty) }
            adapter.values.clear()
            adapter.values.addAll(storageBoosts)
            adapter.notifyDataSetChanged()
        }

        configurationLive.observe(viewLifecycleOwner) {
            cookies_count.text = it.cookiesCount.toString()
        }

        displayCookie()
    }

    private fun displayCookie() {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.placeholder,
            CookieFragment(listenerCookie)
        )
        fragmentTransaction.commit()
        shop_open_button.visibility = View.VISIBLE
        shop_close_button.visibility = View.INVISIBLE
    }

    private fun displayShop() {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.placeholder,
            ShopFragment()
        )
        fragmentTransaction.commit()
        shop_open_button.visibility = View.INVISIBLE
        shop_close_button.visibility = View.VISIBLE
    }

    private val listenerAdapter = object : BoostAdapter.Listener {
        override fun onDeleteBoostClicked(boostModel: BoostModel) {
            SettingsManager.vibrate()
            storageBoostsDao.insert(StorageBoost(boostModel.id, boostModel.boostValue))
            usedBoostsDao.update(UsedBoost(boostModel.id, 0, true))
        }
    }


    private val listenerCookie = object : CookieFragment.Listener {
        override fun onCookieClicked() {
            cookiesDao.upsert(configuration.copy(cookiesCount = configuration.cookiesCount + 1))
        }
    }


}