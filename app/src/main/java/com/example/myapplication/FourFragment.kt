package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView


class FourFragment : Fragment() {

    private lateinit var navHeaderView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_four, container, false)

        // TextView 찾기
        val changeProfileButton: TextView = view.findViewById(R.id.name2)
        val changeNameButton: TextView = view.findViewById(R.id.name1)


        // TextView에 클릭 리스너 설정
        changeProfileButton.setOnClickListener {
            // Start CameraActivity
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

        changeNameButton.setOnClickListener {
            showNameChangeDialog()
        }

        return view
    }

    @SuppressLint("InflateParams")
    private fun showNameChangeDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_four_with_preferences, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextInquiry1)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("이름 변경하기")
            .setPositiveButton("변경하기") { _, _ ->
                val newName = editTextName.text.toString()
                Toast.makeText(requireContext(), "$newName 로 변경했습니다!!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소", null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}