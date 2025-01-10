package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityWriteBinding
import java.text.SimpleDateFormat
import java.util.Date

class WriteActivity : AppCompatActivity() {
    private val date = Date()
    lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        date.time = intent.extras!!.getLong("datetime")
        val date = SimpleDateFormat("yyyy.MM.dd (EEE)").format(date)
        val toolbar = binding.toolbar
        toolbar.setTitle(date)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

