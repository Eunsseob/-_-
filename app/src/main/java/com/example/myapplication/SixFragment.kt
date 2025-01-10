package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.preference.PreferenceFragmentCompat

class SixFragment : Fragment() {

    private lateinit var switchSound: Switch
    private lateinit var switchVibration: Switch
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_six, container, false)

        switchSound = view.findViewById(R.id.switch_sound)
        switchVibration = view.findViewById(R.id.switch_vibration)

        mediaPlayer = MediaPlayer.create(context, R.raw.bubble)
        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        switchSound.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mediaPlayer.start()
            } else {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                }
            }
        }

        switchVibration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vibrator.vibrate(500) // 500ms 진동
            } else {
                vibrator.cancel()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}