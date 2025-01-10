package adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import db.DBLOader
import model.Item

class ContentAdapter(val context: Context, val onChangeData: OnChangeData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val array = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false)
        return HolderView(view)

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        val item = array.get(position)
        val view = holder as HolderView
        view.textMemo.setText(item.content)
        view.textPay.setText(item.pay.toString())
        view.textType.setText(item.type2)
        when(item.type){
            0 ->{
                view.textPay.setTextColor(context.resources.getColor(android.R.color.holo_blue_dark))

            }
            1->{
                view.textPay.setTextColor(context.resources.getColor(android.R.color.holo_red_dark))

            }
        }

        holder.itemView.setOnClickListener{
            dialog(item, position)
        }

    }
    override fun getItemCount(): Int {
        return array.size

    }
    fun setList(array: ArrayList<Item>){
        this.array.clear()
        this.array.addAll(array)
        notifyDataSetChanged()
        var revenueMax = 0
        var expenditureMax = 0
        if (array.size > 0){
            val item = array.get(0)
            revenueMax = DBLOader(context).getMaxPay(item.datetiem, 0)
            expenditureMax = DBLOader(context).getMaxPay(item.datetiem, 1)
        }
        onChangeData.onChange(revenueMax, expenditureMax)

    }

    private class HolderView(view: View) : RecyclerView.ViewHolder(view){
        val textType : TextView = view.findViewById(R.id.text_type)
        val textMemo : TextView = view.findViewById(R.id.text_memo)
        val textPay : TextView = view.findViewById(R.id.text_pay)

    }

    private fun editDialog(item:Item, position: Int){
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("수정하기")
        builder.setView(view)
        val editContent = view.findViewById<EditText>(R.id.edit_content)
        val editType2 = view.findViewById<EditText>(R.id.edit_type2)
        val editpay = view.findViewById<EditText>(R.id.edit_pay)
        val rg = view.findViewById<RadioGroup>(R.id.rg)
        editContent.setText(item.content)
        editType2.setText(item.type2)
        var type = item.type
        var pay = item.pay
        if(pay<0){
            pay=pay*-1
        }
        editpay.setText(pay.toString())
        when(item.type){
            0->{
                rg.check(R.id.radio_revenue)
            }
            1->{
                rg.check(R.id.radio_expenditure)
            }
        }
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
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val content = editContent.text.toString()
                    val type2 = editType2.text.toString()
                    if(editpay.text.toString().trim().equals("")){
                        pay = 0
                        editpay.setText(pay.toString())
                    }else{
                        pay = editpay.text.toString().toInt()
                    }
                    DBLOader(context).update(item.id, type, type2, content, pay)
                    val newItem = DBLOader(context).getItem(item.id)
                    if(newItem != null) {
                        array.set(position, newItem)
                        var revenueMax = 0
                        var expenditureMax = 0
                        if (array.size > 0){
                            revenueMax = DBLOader(context).getMaxPay(item.datetiem, 0)
                            expenditureMax = DBLOader(context).getMaxPay(item.datetiem, 1)
                        }
                        onChangeData.onChange(revenueMax, expenditureMax)
                        notifyDataSetChanged()
                    }
                    dialog!!.dismiss()
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
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }
    private fun dialog(item: Item, position: Int) {
        val builder = AlertDialog.Builder(context)
        val list : Array<CharSequence> = arrayOf("수정", "삭제")
        builder.setTitle(item.content)
        builder.setItems(list, object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1:Int) {
                when(p1){
                    0->{
                        editDialog(item, position)
                    }
                    1->{
                        DBLOader(context).delete(item.id)
                        array.remove(item)
                        notifyDataSetChanged()
                        var revenueMax = 0
                        var expenditureMax = 0
                        if (array.size > 0){
                            revenueMax = DBLOader(context).getMaxPay(item.datetiem, 0)
                            expenditureMax = DBLOader(context).getMaxPay(item.datetiem, 1)
                        }
                        onChangeData.onChange(revenueMax, expenditureMax)
                    }
                }
                p0!!.dismiss()
            }
        })
        val dialog = builder.create()
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "취소", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
            }
        })
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
    }
    interface OnChangeData{
        fun onChange(revenue:Int, expenditure:Int)
    }
}
