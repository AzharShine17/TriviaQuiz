package com.azhar.triviaquiz.quizmain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.*
import com.azhar.triviaquiz.database.QuizModel
import com.google.android.material.textview.MaterialTextView

class HistoryAdapter(private var quizList: List<QuizModel>) :
  RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

  class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val questionMtv = itemView.findViewById<MaterialTextView>(R.id.mtv_question)
    val answerMtv = itemView.findViewById<MaterialTextView>(R.id.mtv_answer)
    val dateTimeMtv = itemView.findViewById<MaterialTextView>(R.id.mtv_date_time)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
    val inflate =
      LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_history, null, false)
    return HistoryHolder(inflate.rootView)
  }

  override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
    holder.answerMtv.text = "Answer :${quizList[position].answers}"
    holder.questionMtv.text = "Question: ${quizList[position].question}"
    holder.dateTimeMtv.text = "Time :${parseDate(quizList[position].currentDateTime.toString(),FORMAT)}"
  }

  override fun getItemCount(): Int {
    return quizList.size ?: 0
  }
}