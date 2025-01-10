package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import db.DBLoader_1
import model.Memo
import java.util.Calendar

class MemoActivity : AppCompatActivity() {

    private lateinit var edit_title: EditText
    private lateinit var edit_memo: EditText
    private var item : Memo? = null
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "메모"
        }

        edit_memo = findViewById(R.id.edit_memo)
        edit_title = findViewById(R.id.edit_title)

        date = intent!!.getStringExtra("date")
        item = intent.getSerializableExtra("item") as Memo?
        if (item != null) {
            edit_memo.setText(item?.memo)
            edit_title.setText(item?.title)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memo, menu)
        val deleteItem = menu!!.findItem(R.id.action_delete)
        if (this.item == null){
            deleteItem.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.action_save -> {
                val title = edit_title.text.toString()
                val memo = edit_memo.text.toString()
                if (!memo.equals("")) {
                    var calendar: Calendar? = null
                    if (date != null){
                        calendar = Calendar.getInstance()
                        val date = this.date!!.split("/")
                        calendar.set(date[0].toInt(),date[1].toInt(), date[2].toInt())
                    }
                    if (this.item != null) {
                        DBLoader_1(applicationContext).update(this.item!!.id, title, memo)
                        finish()
                    }else {
                        DBLoader_1(applicationContext).save(title, memo, calendar)
                        finish()
                    }

                }
            }
            R.id.action_delete -> {
                if (this.item != null){
                    DBLoader_1(applicationContext).delete(this.item!!.id)
                    finish()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
