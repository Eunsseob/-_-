package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MemoActivity
import com.example.myapplication.R
import model.Memo
import java.text.SimpleDateFormat
import java.util.Date

class MemoAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = context
    private val arrayList = ArrayList<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)

        return HolderView(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = arrayList.get(position)
        val view = holder as HolderView
        view.text_title.setText(item.title)
        view.text_memo.setText(item.memo)
        val date = Date()
        date.time = item.datetime.toLong()
        view.text_datetime.setText(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date))

        view.itemView.setOnClickListener {

            val intent = Intent(context, MemoActivity::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
        }


    }

    fun setList(array: ArrayList<Memo>) {
        arrayList.clear()
        arrayList.addAll(array)
        notifyDataSetChanged()
    }

    private class HolderView(view: View) : RecyclerView.ViewHolder(view){
        val text_title = view.findViewById<TextView>(R.id.text_title)
        val text_memo = view.findViewById<TextView>(R.id.text_memo)
        val text_datetime = view.findViewById<TextView>(R.id.text_datetime)

    }
}