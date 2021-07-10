package com.azhar.triviaquiz.base

import android.text.TextUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val FORMAT = "EEEE','MMMM dd',' hh:mm a"
fun parseDate(timestamp: String, format: String?, locale: Locale = Locale.getDefault()): String? {
  if (!TextUtils.isEmpty(timestamp) && !TextUtils.isEmpty(format)) {
    val timeStamp = timestamp.toLong()
    val dateFormat: DateFormat = SimpleDateFormat(format, locale)
    return dateFormat.format(Date(timeStamp))
  }
  return ""
}