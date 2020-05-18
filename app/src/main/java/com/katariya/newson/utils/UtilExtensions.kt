package com.katariya.newson.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun getDate(time_stamp_server: Long): String? {
    val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH)
    //     formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = Date(time_stamp_server * 1000L)
    return formatter.format(dateTime)
}

@Suppress("DEPRECATION")
fun fromHtml(html: String?): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
         Html.fromHtml(html)
        }
}