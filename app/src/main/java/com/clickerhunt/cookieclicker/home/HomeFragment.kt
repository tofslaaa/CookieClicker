package com.clickerhunt.cookieclicker.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.cookie.CookieFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }


}