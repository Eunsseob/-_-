package db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import model.Memo
import java.util.Calendar

class DBLoader_1(context: Context) {

    private val context = context
    private val db: DBHelper = DBHelper(context)

    fun save(title: String, memo: String, getCalendar: Calendar?) {
        val calendar :Calendar
        if (getCalendar == null){
            calendar = Calendar.getInstance()
        }else {
            calendar = getCalendar
        }
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)
        contentValues.put("daytime", calendar.timeInMillis)
        db.writableDatabase.insert("note", null, contentValues)
        db.close()
        Toast.makeText(context,"저장됨",Toast.LENGTH_SHORT).show()
    }

    fun delete(id: Int) {
        db.writableDatabase.delete("note", "id=?", arrayOf(id.toString()))
        db.writableDatabase.close()
        Toast.makeText(context, "삭제됨", Toast.LENGTH_SHORT).show()
    }
    fun update(id: Int, title: String, memo: String) {
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)
        db.writableDatabase.update("note", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun memoList(datetime: Long?): ArrayList<Memo> {
        val array = ArrayList<Memo>()
        val sql: String
        val selectionArgs: Array<String>?

        if (datetime == null) {
            sql = "SELECT * FROM note ORDER BY daytime DESC"
            selectionArgs = null
        } else {
            sql = "SELECT * FROM note WHERE daytime = ? ORDER BY daytime DESC"
            selectionArgs = arrayOf(datetime.toString())
        }

        val cursor = db.readableDatabase.rawQuery(sql, selectionArgs)
        cursor.use {
            if (cursor.count == 0) {
                Log.e("DBLoader", "Cursor returned no results.")
            } else {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val memo = cursor.getString(cursor.getColumnIndex("memo"))
                    val getDatetime = cursor.getLong(cursor.getColumnIndex("daytime"))

                    val memoItem = Memo(id, title, memo, getDatetime)
                    array.add(memoItem)
                }
            }
        }

        db.readableDatabase.close()
        return array
    }

}
