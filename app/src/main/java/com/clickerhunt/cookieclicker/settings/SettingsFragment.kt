package com.clickerhunt.cookieclicker.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

        sound_switch.setOnCheckedChangeListener { _, isChecked ->
            settings.upsert(Configuration(soundIsOn = isChecked))
        }
        vibration_switch.setOnCheckedChangeListener { _, isChecked ->
            settings.upsert(Configuration(vibrationIsOn = isChecked))
        }
    }

    override fun onStart() {
        super.onStart()
        updateViews()
    }

    private fun updateViews() {
        if (settings.getSettings() != null) {
            sound_switch.isChecked = settings.getSettings().soundIsOn
            vibration_switch.isChecked = settings.getSettings().vibrationIsOn
        }
    }

}