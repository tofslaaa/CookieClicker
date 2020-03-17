package com.clickerhunt.cookieclicker.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.cookie.CookieFragment
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.database.Configuration
import com.clickerhunt.cookieclicker.shop.ShopFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: BoostAdapter
    private var cookiesCount = 0

    private val cookiesDao by lazy { AppDatabase.invoke(requireContext()).configurationDao() }

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
    }

    override fun onStart() {
        super.onStart()
        displayCookie()

        val configuration = cookiesDao.getConfiguration()
        if (configuration != null) {
            cookiesCount = configuration.cookiesCount
        }
        cookies_count.text = cookiesCount.toString()
    }

    override fun onStop() {
        super.onStop()
        cookiesDao.upsert(Configuration(cookiesCount = cookiesCount))
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
        override fun onDeleteBoostClicked(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private val listenerCookie = object : CookieFragment.Listener {
        override fun onCookieClicked() {
            cookiesCount++
            cookies_count.text = cookiesCount.toString()
        }
    }

}