package com.azhar.triviaquiz.quizmain.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseFragment
import com.azhar.triviaquiz.database.QuizModel
import com.azhar.triviaquiz.databinding.FragmentSummeryBinding
import com.azhar.triviaquiz.quizmain.adapter.SessionAdapter
import com.azhar.triviaquiz.quizmain.startQuizFragmentActivity
import com.azhar.triviaquiz.quizmain.viewmodel.QuizViewModel
import com.weather.apps10xassignment.ui.controller.FragmentType

class FragmentSummery : AppBaseFragment<FragmentSummeryBinding, QuizViewModel>() {
  override fun getLayout(): Int {
    return R.layout.fragment_summery
  }

  override fun getViewModelClass(): Class<QuizViewModel> {
    return QuizViewModel::class.java
  }

  companion object {
    fun newInstance(): FragmentSummery {
      return FragmentSummery()
    }
  }

  override fun onCreateView() {
    super.onCreateView()
    setOnClickListener(binding?.btnFinish, binding?.btnHistory)
    setView()
  }

  private fun setView() {
    binding?.tvTitle?.text =
      "Hello ${QuizModel.Session.playerName}\n Here are the answers selected:\n"
    viewModel?.sessionMutableLiveData()?.observe(viewLifecycleOwner, {
      setupRecyclerView(it)
    })
  }


  private fun setupRecyclerView(list: List<QuizModel>) {
    val adapter = SessionAdapter(list)
    binding?.rvQuizSummery?.layoutManager =
      LinearLayoutManager(baseActivity, RecyclerView.VERTICAL, false)
    binding?.rvQuizSummery?.setHasFixedSize(true)
    binding?.rvQuizSummery?.adapter = adapter

  }

  override fun onClick(v: View) {
    super.onClick(v)
    when (v) {
      binding?.btnFinish -> {
        QuizModel.Session.reInitGame()
        baseActivity.startQuizFragmentActivity(FragmentType.HOME_FRAGMENT, clearTop = true)
      }
      binding?.btnHistory -> {
        baseActivity.startQuizFragmentActivity(FragmentType.HISTORY_FRAGMENT, clearTop = false)
      }
    }
  }
}