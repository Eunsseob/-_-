package com.example.myapplication

import adapter.ContentAdapter
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentEditBinding
import db.DBLOader
import java.text.SimpleDateFormat
import java.util.Calendar


class EditFragment : Fragment() {
    private var _binding:FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter :ContentAdapter
    private lateinit var calender:Calendar
    private val onClickListener = object : View.OnClickListener{
        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.btn_left->{
                    calender.set(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)-1)
                    changeDate()

                }
                R.id.btn_right->{
                    calender.set(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)+1)
                    changeDate()

                }
                R.id.text_day->{
                    CalendarDialog()
                }
                R.id.btn_add->{
                    addDialog()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        calender = Calendar.getInstance()
        calender.set(Calendar.HOUR, 0)
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 0)
        calender.set(Calendar.MILLISECOND, 0)
        Log.d("hh", "1" + calender.timeInMillis.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _binding!!.btnLeft.setOnClickListener(onClickListener)
        _binding!!.btnRight.setOnClickListener(onClickListener)
        _binding!!.textDay.setOnClickListener(onClickListener)
        _binding!!.btnAdd.setOnClickListener(onClickListener)
        // Inflate the layout for this fragment
        adapter = ContentAdapter(requireContext(), object : ContentAdapter.OnChangeData{
            override fun onChange(revenue: Int, expenditure: Int) {
                _binding!!.textRevenue.setText(revenue.toString())
                _binding!!.textExpenditure.setText(expenditure.toString())
                _binding!!.textPay.setText((revenue+expenditure).toString())
            }
        })
        val recyclerView = _binding!!.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        changeDate()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_input, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_write->{
                val intent = Intent(requireContext(), WriteActivity::class.java)
                intent.putExtra("datetime", calender.time.time)
                startActivityForResult(intent, 1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeDate(){
        val date = SimpleDateFormat("yyyy.MM.dd (EEE)").format(calender.time)
        _binding!!.textDay.setText(date)
        adapter.setList(DBLOader(requireContext()).getList(calender.timeInMillis))
        if (adapter.itemCount == 0){
            _binding!!.textNodata.visibility = View.VISIBLE
        }
        else{
            _binding!!.textNodata.visibility = View.GONE
        }
    }

    private fun CalendarDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_datepicker, null)
        val builder = AlertDialog.Builder(requireContext())
        val datepicker = view.findViewById<DatePicker>(R.id.datepicker)
        builder.setView(view)
        datepicker.updateDate(
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH))
        val dialog = builder.create()
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            "선택",
            object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val year = datepicker.year
                    val month = datepicker.month
                    val day = datepicker.dayOfMonth
                    calender.set(year, month, day, 0,0,0)
                    calender.set(Calendar.MILLISECOND, 0)
                    changeDate()
                    dialog!!.dismiss()
                }
            })
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            "취소",
            object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

//        val datePicker = DatePickerDialog(requireContext(), object :DatePickerDialog.OnDateSetListener{
//            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                Toast.makeText(requireContext(),year.toString(), Toast.LENGTH_SHORT).show()
//                calender.set(year, month, dayOfMonth)
//            }
//        },calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH))
//
//        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE,"ok", object :DialogInterface.OnClickListener{
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//                changeDate()
//                dialog!!.dismiss()
//            }
//        })
//        datePicker.show()
//        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
    }
    private fun addDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val editContent = view.findViewById<EditText>(R.id.edit_content)
        val editType2 = view.findViewById<EditText>(R.id.edit_type2)
        val editpay = view.findViewById<EditText>(R.id.edit_pay)
        val rg = view.findViewById<RadioGroup>(R.id.rg)
        var type = 1
        var pay = 0
        editpay.setText(pay.toString())
        rg!!.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.radio_revenue -> {
                        type = 0
                    }
                    R.id.radio_expenditure -> {
                        type = 1
                    }
                }
            }
        })
        val dialog = builder.create()
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            "save",
            object : DialogInterface.OnClickListener{
                override fun onClick(d: DialogInterface?, p1: Int) {
                    val content = editContent.text.toString()
                    val type2 = editType2.text.toString()
                    if(editpay.text.toString().trim().equals("")){
                        pay = 0
                        editpay.setText(pay.toString())
                    }else{
                        pay = editpay.text.toString().toInt()
                    }
                    Log.d("hh", "2" + calender.timeInMillis.toString())
                    DBLOader(requireContext()).add(type, type2, content, pay, calender.timeInMillis)
                    changeDate()
                    d!!.dismiss()
                }
            })
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            "cancel",
            object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
        dialog.show()
    }
}
