package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context,"memo.db", null, 1, null) {

    override fun onCreate(db: SQLiteDatabase?) {
        // id, title, memo, datetime
        val sql =
            "create table note (id integer primary key autoincrement, title text, memo text not null, daytime text)"

        db!!.execSQL(sql);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}