package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // Find the preferences and set click listeners
        val profileSettingPreference: Preference? = findPreference("propile11")
        val profileChangePreference: Preference? = findPreference("name11")

        profileSettingPreference?.setOnPreferenceClickListener {
            // Navigate to CameraActivity
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
            true
        }

        profileChangePreference?.setOnPreferenceClickListener {
            // Navigate to NameActivity
            val intent = Intent(activity, NameActivity::class.java)
            startActivity(intent)
            true
        }
    }
}