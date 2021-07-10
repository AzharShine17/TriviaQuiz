package com.azhar.triviaquiz.base

import android.app.Application
import android.content.Context
import com.azhar.triviaquiz.database.QuizDataBase

class QuizApplication: Application() {

  companion object{
    private  var instance: QuizApplication? = null
    fun getInstance(): Context? {
     return this.instance?.applicationContext
    }

  }
  override fun onCreate() {
    super.onCreate()
    instance = this
    QuizDataBase.getInstance(this)
  }
}