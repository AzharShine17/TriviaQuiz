package com.azhar.triviaquiz.quizmain.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azhar.triviaquiz.base.BaseViewModel
import com.azhar.triviaquiz.base.QuizApplication
import com.azhar.triviaquiz.database.QuizDataBase
import com.azhar.triviaquiz.database.QuizModel

class QuizViewModel : BaseViewModel() {
  var historyMutableLiveData = MutableLiveData<List<QuizModel>>()
  var sessionMutableLiveData = MutableLiveData<List<QuizModel>>()


  fun getSessionData() {
     QuizDataBase.getInstance(QuizApplication.getInstance()?.applicationContext!!).getSummery(playerName = QuizModel.Session.playerName!!).observeForever {
       sessionMutableLiveData.postValue(it)
     }

  }

  fun historyMutableLiveData(): MutableLiveData<List<QuizModel>> {
    getHistoryData()
    return historyMutableLiveData
  }

  fun sessionMutableLiveData(): MutableLiveData<List<QuizModel>> {
    getSessionData()
    return sessionMutableLiveData
  }

  fun getHistoryData() {
    QuizDataBase.getInstance(QuizApplication.getInstance()?.applicationContext!!).getHistory().observeForever { historyMutableLiveData.postValue(it) }

  }
}