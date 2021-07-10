package com.azhar.triviaquiz.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuizDAO {
  @Insert
  fun insert(quiz: QuizModel)

  @Insert
  fun insertAll(quiz: List<QuizModel>)

  @Update
  fun update(quiz: QuizModel)

  @Delete
  fun delete(quiz: QuizModel)

  @Query("delete from game_summery")
  fun deleteAllData()


  @Query("select * from game_summery where playerName=:name")
  fun getPlayerSummery(name: String): LiveData<List<QuizModel>>

  @Query("select * from game_summery")
  fun getPlayHistory(): LiveData<List<QuizModel>>
}