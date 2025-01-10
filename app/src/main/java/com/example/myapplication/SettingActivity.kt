package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)

            // Set click listeners for profile and name change preferences
            val profilePreference: Preference? = findPreference("propile11")
            profilePreference?.setOnPreferenceClickListener {
                startActivity(Intent(activity, CameraActivity::class.java))
                true
            }

            val nameChangePreference: Preference? = findPreference("name11")
            nameChangePreference?.setOnPreferenceClickListener {
                startActivity(Intent(activity, NameActivity::class.java))
                true
            }

            // Set click listener for "문의하기"
            val contactPreference: Preference? = findPreference("mooni01")
            contactPreference?.setOnPreferenceClickListener {
                showContactDialog()
                true
            }

            // Set preference change listener for list preference
            val listPreference: ListPreference? = findPreference("noti_sori")
            listPreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                SoundManager.playSound(requireContext())
                true
            }
            // Set preference change listener for SwitchPreferenceCompat
            val switchPreference: SwitchPreferenceCompat? = findPreference("noti_sori11")
            switchPreference?.setOnPreferenceChangeListener { preference, newValue ->
                val isOn = newValue as Boolean
                if (isOn) {
                    val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(500) // Vibrate for 500 milliseconds
                }
                true
            }
        }

        class SettingsFragment : PreferenceFragmentCompat(),
            SharedPreferences.OnSharedPreferenceChangeListener {

            override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
                setPreferencesFromResource(R.xml.settings, rootKey)

                // Set preference change listener for ListPreference
                val listPreference: ListPreference? = findPreference("noti_sori")
                listPreference?.setOnPreferenceChangeListener { preference, newValue ->
                    val selectedRingtone = newValue as String
                    saveSelectedRingtone(selectedRingtone)
                    true
                }
            }

            private fun saveSelectedRingtone(ringtone: String) {
                val sharedPreferences = context?.let {
                    PreferenceManager.getDefaultSharedPreferences(
                        it
                    )
                }
                val editor = sharedPreferences?.edit()
                if (editor != null) {
                    editor.putString("selectedRingtone", ringtone)
                }
                if (editor != null) {
                    editor.apply()
                }
            }

            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences?,
                key: String?
            ) {
                TODO("Not yet implemented")
            }

            // Other methods for handling shared preference changes
        }

        private fun showContactDialog() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("문의하기")
            builder.setMessage("정말로 보내시겠습니까?")
            builder.setPositiveButton("보내기") { dialog, _ ->
                Toast.makeText(requireContext(), "문의드려서 진심으로 감사드립니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            // Handle shared preference changes if needed
        }
    }
}