package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import model.Item
import java.text.SimpleDateFormat

class HistoryAdapter(val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val array = ArrayList<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view :View
        if(viewType==0){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_header, parent,false)
            return HeaderView(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
            return HolderView(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == 0){
            val day = array.get(position) as Long
            val date = SimpleDateFormat("yyyy.MM.dd (EEE)").format(day)
            val view = holder as HeaderView
            view.textDay.setText(date)
        } else{
            val item = array.get(position) as Item
            val view = holder as HolderView
            view.textMemo.setText(item.content)
            view.textPay.setText(item.pay.toString())
            view.textType.setText(item.type2)
            when(item.type){
                0 ->{
                    view.textPay.setTextColor(context.resources.getColor(android.R.color.holo_blue_dark, null))

                }
                1->{
                    view.textPay.setTextColor(context.resources.getColor(android.R.color.holo_red_dark, null))

                }
            }
        }
    }
    override fun getItemCount(): Int {
        return array.size
    }

    override fun getItemViewType(position: Int): Int {
        if (array.get(position) is Long) {
            return 0
        }
        if (array.get(position) is Item){
            return 1
        }
        return super.getItemViewType(position)
    }

    fun setList(array: ArrayList<Any>){
        this.array.clear()
        this.array.addAll(array)
        notifyDataSetChanged()

    }

    private class HeaderView(view: View) : RecyclerView.ViewHolder(view){
        val textDay: TextView = view.findViewById(R.id.text_day)
    }
    private class HolderView(view: View) : RecyclerView.ViewHolder(view){
        val textType : TextView = view.findViewById(R.id.text_type)
        val textMemo : TextView = view.findViewById(R.id.text_memo)
        val textPay : TextView = view.findViewById(R.id.text_pay)
    }

}