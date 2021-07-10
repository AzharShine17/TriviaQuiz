package com.azhar.triviaquiz.onboarding

import android.view.View
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseActivity
import com.azhar.triviaquiz.base.BaseViewModel
import com.azhar.triviaquiz.database.QuizModel
import com.azhar.triviaquiz.database.QuizModel.Session.Companion
import com.azhar.triviaquiz.databinding.ActivityOnboardingBinding
import com.azhar.triviaquiz.quizmain.startQuizFragmentActivity
import com.weather.apps10xassignment.ui.controller.FragmentType

class OnBoardingActivity: AppBaseActivity<ActivityOnboardingBinding,BaseViewModel>() {
  override fun getLayout(): Int {
    return R.layout.activity_onboarding
  }

  override fun getViewModelClass(): Class<BaseViewModel> {
    return BaseViewModel::class.java
  }

  override fun onCreateView() {
    super.onCreateView()
    setOnClickListener(binding?.btnNext)

  }

  override fun onClick(v: View?) {
    super.onClick(v)
    when(v){
      binding?.btnNext->{
        when (isValid()) {
          true -> {
            QuizModel.Session.initializeSession(binding?.tiName?.editText?.text?.toString())
            startQuizFragmentActivity(FragmentType.HOME_FRAGMENT,clearTop = true)
          }
          else -> {
          }
        }
      }
    }
  }
  private fun isValid(): Boolean {
    val name = binding?.tiName?.editText?.text.toString()
    return when {
      name.isEmpty() -> {
        binding?.tiName?.error = "Please enter your name"
        return false
      }
      else -> true
    }
  }
}