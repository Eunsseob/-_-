package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context) : SQLiteOpenHelper(context, "money.db", null, 1, null){
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table " + Constant.TABLE_NAME + " (" + Constant.COL_ID+ " integer primary key autoincrement, " + Constant.COL_TYPE+ " integer, "+Constant.COL_TYPE2+" text, "+Constant.COL_CONTENT+" text, "+Constant.COL_PAY+" integer, "+Constant.COL_DATE+" integer)"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}