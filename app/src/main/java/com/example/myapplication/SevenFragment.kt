package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class SevenFragment : Fragment() {

    private lateinit var textTextView1: TextView
    private lateinit var textTextView2: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_seven, container, false)

        // TextView 찾기
        textTextView1 = view.findViewById(R.id.text11)
        textTextView2 = view.findViewById(R.id.text12)

        // TextView에 클릭 리스너 설정
        textTextView1.setOnClickListener {
            showColorPickerDialog { color ->
                textTextView1.setTextColor(color)
                Toast.makeText(requireContext(), "글자색이 변경되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        textTextView2.setOnClickListener {
            showColorPickerDialog { color ->
                textTextView2.setBackgroundColor(color)
                Toast.makeText(requireContext(), "배경색이 변경되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun showColorPickerDialog(onColorSelected: (Int) -> Unit){
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_seven_with_preferences, null)
        val radioGroupColors = dialogView.findViewById<RadioGroup>(R.id.radioGroupColors)

        builder.setView(dialogView)
            .setTitle("색상 선택")
            .setPositiveButton("확인") { _, _ ->
                val selectedColor = when (radioGroupColors.checkedRadioButtonId) {
                    R.id.radio_red -> Color.RED
                    R.id.radio_blue -> Color.BLUE
                    R.id.radio_green -> Color.GREEN
                    R.id.radio_black -> Color.BLACK
                    else -> Color.BLACK // 기본 값은 빨간색
                }
                onColorSelected(selectedColor)
            }
            .setNegativeButton("취소", null)
            .create()
            .show()
    }
}