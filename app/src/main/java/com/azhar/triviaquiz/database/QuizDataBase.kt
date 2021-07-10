package com.azhar.triviaquiz.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.azhar.triviaquiz.base.QuizApplication
import com.azhar.triviaquiz.base.StringListConverter
import com.azhar.triviaquiz.base.subscribeOnBackground
import com.azhar.triviaquiz.database.QuizModel.Session.Companion.questions

@Database(entities = [QuizModel::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class QuizDataBase : RoomDatabase() {

  abstract fun quizDao(): QuizDAO

  companion object {
    private var instance: QuizDataBase? = null

    @Synchronized
    fun getInstance(ctx: Context): QuizDataBase {
      if (instance == null)
        instance = Room.databaseBuilder(
          ctx.applicationContext, QuizDataBase::class.java,
          "quiz_database"
        )
          .fallbackToDestructiveMigration()
          .addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
              super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
              super.onOpen(db)

            }
          })
          .build()
      return instance!!
    }
  }

  public fun getSummery(playerName: String): MutableLiveData<List<QuizModel>> {
    val quizDao = getInstance(QuizApplication.getInstance()?.applicationContext!!).quizDao()
    val quizData = MutableLiveData<List<QuizModel>>()
    quizDao.getPlayerSummery(playerName).observeForever {
      quizData.postValue(it)
    }
    return quizData
  }

  public fun getHistory(): MutableLiveData<List<QuizModel>> {
    val quizDao = getInstance(QuizApplication.getInstance()?.applicationContext!!).quizDao()
    val quizData = MutableLiveData<List<QuizModel>>()
    quizDao.getPlayHistory().observeForever {
      quizData.postValue(it)
    }
    return quizData
  }

  public fun populateDatabase(db: QuizDataBase) {
    val quizdao = db.quizDao()
    subscribeOnBackground {
      quizdao.insertAll(questions())
    }
  }
}
