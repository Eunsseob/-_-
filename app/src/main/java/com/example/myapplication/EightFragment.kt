package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class EightFragment : Fragment() {

    private lateinit var mooniTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_eight, container, false)

        // TextView 찾기
        mooniTextView = view.findViewById(R.id.mooni1)

        // TextView에 클릭 리스너 설정
        mooniTextView.setOnClickListener {
            showInquiryDialog()
        }

        return view
    }

    private fun showInquiryDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_eight_with_preferences, null)
        val editTextInquiry = dialogView.findViewById<EditText>(R.id.editTextInquiry)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("문의하기")
            .setPositiveButton("보내기") { dialogInterface: DialogInterface, i: Int ->
                // 보내기 버튼이 클릭되었을 때의 처리
                val inquiryText = editTextInquiry.text.toString()
                // inquiryText를 처리하는 코드를 여기에 추가하세요.
                Toast.makeText(requireContext(), "문의 보내주셔서 감사합니다!!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소", null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}