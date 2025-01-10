package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapplication.databinding.ActivityDevelopBinding
import com.example.myapplication.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar?.title = "이름 변경하기"

        val editTextName = findViewById<EditText>(R.id.editTextInquiry2)
        val buttonChange = findViewById<Button>(R.id.buttonChange)

        buttonChange.setOnClickListener {
            val newName = editTextName.text.toString()

            // MainActivity로 이름 전달
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("newUserName", newName)
            }
            Toast.makeText(this, "$newName 변경되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }
}