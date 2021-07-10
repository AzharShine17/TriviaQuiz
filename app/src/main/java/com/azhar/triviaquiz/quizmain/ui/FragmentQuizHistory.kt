package com.azhar.triviaquiz.quizmain.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseFragment
import com.azhar.triviaquiz.database.QuizModel
import com.azhar.triviaquiz.databinding.FragmentQuizHistoryBinding
import com.azhar.triviaquiz.quizmain.adapter.HistoryAdapter
import com.azhar.triviaquiz.quizmain.viewmodel.QuizViewModel

class FragmentQuizHistory : AppBaseFragment<FragmentQuizHistoryBinding, QuizViewModel>() {
  override fun getLayout(): Int {
    return R.layout.fragment_quiz_history
  }

  override fun getViewModelClass(): Class<QuizViewModel> {
    return QuizViewModel::class.java
  }
companion object{
  fun newInstance(): FragmentQuizHistory {
    return FragmentQuizHistory()
  }
}
  override fun onCreateView() {
    super.onCreateView()
    viewModel?.historyMutableLiveData()?.observe(viewLifecycleOwner, {
      setupRecyclerView(it)
    })
  }

  private fun setupRecyclerView(list: List<QuizModel>) {
    val adapter = HistoryAdapter(list)
    binding?.rvQuizHistory?.layoutManager =
      LinearLayoutManager(baseActivity, RecyclerView.VERTICAL, false)
    binding?.rvQuizHistory?.setHasFixedSize(true)
    binding?.rvQuizHistory?.adapter = adapter

  }
}