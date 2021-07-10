package com.azhar.triviaquiz.quizmain.ui
import android.graphics.Color
import android.view.View
import androidx.core.view.children
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseFragment
import com.azhar.triviaquiz.base.BaseViewModel
import com.azhar.triviaquiz.base.subscribeOnBackground
import com.azhar.triviaquiz.database.QuestionType
import com.azhar.triviaquiz.database.QuizDataBase
import com.azhar.triviaquiz.database.QuizModel
import com.azhar.triviaquiz.database.QuizModel.Session.Companion.clearUserResponse
import com.azhar.triviaquiz.database.QuizModel.Session.Companion.getCurrentQuestion
import com.azhar.triviaquiz.database.QuizModel.Session.Companion.getCurrentUserResponse
import com.azhar.triviaquiz.database.QuizModel.Session.Companion.isLastQuestion
import com.azhar.triviaquiz.databinding.FragmentQuizScreenBinding
import com.azhar.triviaquiz.quizmain.startQuizFragmentActivity
import com.google.android.material.button.MaterialButton
import com.weather.apps10xassignment.ui.controller.FragmentType

class FragmentQuizScreen : AppBaseFragment<FragmentQuizScreenBinding, BaseViewModel>() {
  companion object {
    fun newInstance(): FragmentQuizScreen {
      return FragmentQuizScreen()
    }
  }

  override fun getLayout(): Int {
    return R.layout.fragment_quiz_screen
  }

  override fun getViewModelClass(): Class<BaseViewModel> {
    return BaseViewModel::class.java
  }

  override fun onCreateView() {
    super.onCreateView()
    setOnClickListener(
      binding?.optionA,
      binding?.optionB,
      binding?.optionC,
      binding?.optionD,
      binding?.btnNext,
    )
    setGameView()
  }

  private fun setGameView() {
    binding?.linearLayoutCompat?.children?.forEach {
      (it as? MaterialButton)?.apply {
        isEnabled = true;isClickable = true;setBackgroundColor(getColor(R.color.purple_500))
      }
    }
    val currentQuestion = getCurrentQuestion()
    binding?.mtvQuestion?.text = currentQuestion.question
    binding?.optionA?.text = "A) ${currentQuestion.optionA}"
    binding?.optionB?.text = "B) ${currentQuestion.optionB}"
    binding?.optionC?.text = "C) ${currentQuestion.optionC}"
    binding?.optionD?.text = "D) ${currentQuestion.optionD}"
    binding?.btnNext?.isEnabled = false
    binding?.btnNext?.visibility = View.VISIBLE


  }

  override fun onClick(v: View) {
    super.onClick(v)
    when (v) {
      binding?.optionA -> {
        markAns(binding?.optionA)
      }
      binding?.optionB -> {
        markAns(binding?.optionB)
      }
      binding?.optionC -> {
        markAns(binding?.optionC)
      }
      binding?.optionD -> {
        markAns(binding?.optionD)
      }
      binding?.btnNext -> {
        saveMoveNext()
      }

    }
  }
  private fun markAns(usersResponse: MaterialButton?) {
    when (QuizModel.Session.getCurrentQuestionType()) {
      QuestionType.SINGLE_ANS -> {
        binding?.linearLayoutCompat?.children?.forEach {
          (it as? MaterialButton)?.isEnabled = false
        }
        usersResponse?.setBackgroundColor(Color.YELLOW)
        binding?.btnNext?.isEnabled = true
      }
      QuestionType.MULTIPLE_ANS -> {
        usersResponse?.setBackgroundColor(Color.YELLOW)
        usersResponse?.isEnabled = false
        when (binding?.linearLayoutCompat?.children?.filter { !it.isEnabled }
          ?.toList()?.size ?: 0 >= 2) {
          true -> {
            binding?.btnNext?.isEnabled = true
          }
          else -> {
            binding?.btnNext?.isEnabled = false
          }
        }
      }
    }
    QuizModel.Session.addUsersResponse(usersResponse?.text.toString())

  }

  private fun saveMoveNext() {
    val currentQuestion = getCurrentQuestion()
    currentQuestion.userResponse?.clear()
    currentQuestion.userResponse?.addAll(getCurrentUserResponse()!!)
    subscribeOnBackground {
      QuizDataBase.getInstance(baseActivity).quizDao().insert(currentQuestion)
    }
    currentQuestion.playerName = QuizModel.Session.playerName
    if (isLastQuestion().not()) {
      QuizModel.Session.nextQuestion()
      clearUserResponse()
      setGameView()
    } else {
      binding?.btnNext?.text = getString(R.string.summery)
   if (isLastQuestion()){
     baseActivity.startQuizFragmentActivity(FragmentType.SUMMERY_FRAGMENT,clearTop = false)
   }
    }
  }
}