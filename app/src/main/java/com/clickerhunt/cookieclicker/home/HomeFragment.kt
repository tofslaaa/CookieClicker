package com.clickerhunt.cookieclicker.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.cookie.CookieFragment
import com.clickerhunt.cookieclicker.shop.ShopFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shop_open_button.setOnClickListener { displayShop() }
        shop_close_button.setOnClickListener { displayCookie() }
    }

    override fun onStart() {
        super.onStart()
        displayCookie()
    }

    private fun displayCookie() {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.placeholder,
            CookieFragment()
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


}