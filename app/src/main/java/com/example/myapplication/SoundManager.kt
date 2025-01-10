package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.preference.PreferenceManager

object SoundManager {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context) {
        stopSound() // Stop any currently playing sound

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val soundPreference = sharedPreferences.getString("noti_sori", "bubble")

        val soundResource = when (soundPreference) {
            "bubble" -> R.raw.bubble
            "water" -> R.raw.water
            "mouse" -> R.raw.mouse
            else -> R.raw.bubble
        }

        mediaPlayer = MediaPlayer.create(context, soundResource)
        mediaPlayer?.start()
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
