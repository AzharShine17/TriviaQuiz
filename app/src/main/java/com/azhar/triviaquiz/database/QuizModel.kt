package com.azhar.triviaquiz.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.azhar.triviaquiz.base.StringListConverter
import java.io.Serializable

@Entity(tableName = "game_summery")
data class QuizModel(
  var question: String? = null,
  var answers: ArrayList<String?>? = arrayListOf(),
  var optionA: String? = null,
  var optionB: String? = null,
  var optionC: String? = null,
  var optionD: String? = null,
  @Ignore
  var Type: QuestionType? = QuestionType.SINGLE_ANS,
  @PrimaryKey(autoGenerate = true)
  var id: Int? = null,
  var playerName: String? = null,
  var currentDateTime: Long? = System.currentTimeMillis(),
  var userResponse: ArrayList<String?>? = arrayListOf()

) : Serializable {


  data class Session(var question: QuizModel? = null) {
    companion object {
      var currentDateTime: Long? = 0
      var usersAns: ArrayList<String?>? = arrayListOf()
      var playerName: String? = ""
      var questionIndex = 0;
      fun initializeSession(playerName: String?) {
        this.playerName = playerName
        this.currentDateTime = System.currentTimeMillis()
      }

      fun reInitGame() {
        usersAns?.clear()
        questionIndex = 0;
        currentDateTime = System.currentTimeMillis()
      }

      fun nextQuestion() {
        if (++questionIndex < questions().size)
          questions()[questionIndex]
      }

      fun isLastQuestion(): Boolean {
        return questionIndex == questions().size - 1
      }

      fun getCurrentQuestion(): QuizModel {
        return questions()[questionIndex]
      }

      fun getCurrentQuestionType(): QuestionType? {
        return questions()[questionIndex].Type
      }

      fun addUsersResponse(userResponse: String? = "") {
        this.usersAns?.add(userResponse)
      }

      fun getCurrentUserResponse(): ArrayList<String?>? {
        return this.usersAns
      }

      fun clearUserResponse() {
        this.usersAns?.clear()
      }

      fun questions(): ArrayList<QuizModel> {
        return arrayListOf(
          QuizModel(
            "Who is the best cricketer in the world?",
            arrayListOf("Sachin Tendulkar"),
            "Sachin Tendulkar",
            "Virat Kolli",
            "Adam Gilchirst",
            "Jacques Kallis"
          ),
          QuizModel(
            "What are the colors in the Indian national flag? Select all:",
            arrayListOf("Orange", "Green", "White"),
            "Orange",
            "Green",
            "White",
            "Yellow",
            QuestionType.MULTIPLE_ANS
          )

        )
      }
    }


  }
}

enum class QuestionType() {
  MULTIPLE_ANS,
  SINGLE_ANS
}
