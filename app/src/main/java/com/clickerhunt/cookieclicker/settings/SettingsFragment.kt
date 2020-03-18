package com.clickerhunt.cookieclicker.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.database.Configuration
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val settings by lazy { AppDatabase.invoke(requireContext()).configurationDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_close_button.setOnClickListener { it.findNavController().popBackStack() }

        vibration_switch.setOnCheckedChangeListener { _, isChecked ->
            settings.upsert(Configuration(vibrationIsOn = isChecked))
        }
        settings.getConfiguration().observe(viewLifecycleOwner){
            vibration_switch.isChecked = it.vibrationIsOn
        }
    }

}