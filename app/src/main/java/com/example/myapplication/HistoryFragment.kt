package com.example.myapplication

import adapter.HistoryAdapter
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHistoryBinding
import db.DBLOader
import java.text.SimpleDateFormat
import java.util.Calendar

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyadapter : HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root:View = binding.root
        val array = resources.getStringArray(R.array.spinner)
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.
            simple_spinner_dropdown_item,
            array
        ){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView = super.getDropDownView(position, convertView, parent) as TextView
                textView.setTextColor(Color.WHITE)
                return textView
            }
        }
        _binding!!.spinner.adapter = adapter
        _binding!!.spinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        _binding!!.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2<6)
                    load(p2)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        _binding!!.spinner.setOnSelected(object : CustomSpinner.OnItemSelected{

            override fun onitemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Log.d("hhh", _binding!!.spinner.selectedItemPosition.toString() + "/" + p2)
                if (_binding!!.spinner.selectedItemPosition == 6 && p2 == 6)
                    load(p2)
            }

            fun onItemSelectde(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        })

        historyadapter = HistoryAdapter(requireContext())
        _binding!!.recyclerView.setHasFixedSize(true)
        _binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        _binding!!.recyclerView.adapter = historyadapter
        load(_binding!!.spinner.selectedItemPosition)
        return root
    }
    private fun load(selected:Int){
        when(selected){
            0->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                changeData(calendar.timeInMillis, calendar.timeInMillis)

            }
            1->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1)
                changeData(calendar.timeInMillis, calendar.timeInMillis)

            }
            2->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1)
                val sDate = calendar.timeInMillis
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-6)
                val eDate = calendar.timeInMillis
                changeData(sDate, eDate)

            }
            3->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sDate = calendar.time.time
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
                val eDate = calendar.time.time
                changeData(sDate, eDate)

            }
            4->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1)
                val sDate = calendar.timeInMillis
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-29)
                val eDate = calendar.timeInMillis
                changeData(sDate, eDate)

            }
            5->{
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1)
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                val sDate = calendar.timeInMillis
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
                val eDate = calendar.timeInMillis
                changeData(sDate, eDate)

            }
            6->{
                dateDialog()

            }

        }
    }
    private fun changeData(sdata:Long, edata:Long){
        historyadapter.setList(DBLOader(requireContext()).historyList(sdata, edata))
        if(historyadapter.itemCount == 0){
            _binding!!.textNodata.visibility = View.VISIBLE
        }else{
            _binding!!.textNodata.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dateDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_spinner, null)
        val sDatePicker = view.findViewById<DatePicker>(R.id.s_picker)
        val eDatePicker = view.findViewById<DatePicker>(R.id.e_picker)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()
        builder.setNegativeButton("취소", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        }).setPositiveButton("확인", object : DialogInterface.OnClickListener {
            override fun onClick(d: DialogInterface?, p1: Int) {
                val calendar = Calendar.getInstance()
                calendar.set(sDatePicker.year, sDatePicker.month, sDatePicker.dayOfMonth, 0,0,0)
                calendar.set(Calendar.MILLISECOND,0)
                val sDate = calendar.timeInMillis
                calendar.set(eDatePicker.year, eDatePicker.month, eDatePicker.dayOfMonth, 0,0,0)
                calendar.set(Calendar.MILLISECOND,0)
                val eDate = calendar.timeInMillis
                if (sDate > eDate) {
                    Toast.makeText(requireContext(), "기간이 잘못되었습니다", Toast.LENGTH_SHORT).show()
                }else{
                    changeData(eDate, sDate)
                    val seleteDate = SimpleDateFormat("yyyy.MM.dd").format(sDate) + " ~ " + SimpleDateFormat(
                        "yyyy.MM.dd"
                    ).format(eDate)
                    (binding!!.spinner.selectedView as (TextView)).setText(seleteDate)
                    d!!.dismiss()
                }
            }
        })
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            "save",
            object : DialogInterface.OnClickListener{
                override fun onClick(d: DialogInterface?, p1: Int) {

                    d!!.dismiss()
                }
            })
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            "cancel",
            object : DialogInterface.OnClickListener{
                override fun onClick(d: DialogInterface?, which: Int) {
                    d!!.dismiss()
                }
            })
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

}