package model

import java.io.Serializable

class Memo(id:Int, title:String, memo: String, datetime: Long): Serializable {
    val id = id;
    val title = title
    val memo = memo
    val datetime = datetime
}