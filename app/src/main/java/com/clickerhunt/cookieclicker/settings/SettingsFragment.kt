package com.clickerhunt.cookieclicker.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val settings by lazy { AppDatabase.instance.configurationDao() }

    val configuration = settings.getConfiguration()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_close_button.setOnClickListener { it.findNavController().popBackStack() }

        vibration_switch.setOnCheckedChangeListener { _, isChecked ->
            val a = settings.getConfigurationRx().blockingFirst()
            settings.enableVibration(isChecked)
            val b = settings.getConfigurationRx().blockingFirst()
            val aTime = a.updateTime
            val bTime = b.updateTime
            val result = bTime - aTime
        }

        configuration.observe(viewLifecycleOwner) {
            vibration_switch.isChecked = it.vibrationIsOn
        }
    }

}