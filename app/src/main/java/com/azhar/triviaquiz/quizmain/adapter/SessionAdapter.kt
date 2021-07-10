package com.azhar.triviaquiz.quizmain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.database.QuizModel
import com.google.android.material.textview.MaterialTextView

class SessionAdapter(private var quizList: List<QuizModel>) :
  RecyclerView.Adapter<SessionAdapter.SessionHolder>() {

  class SessionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val questionMtv = itemView.findViewById<MaterialTextView>(R.id.mtv_question)
    val answerMtv = itemView.findViewById<MaterialTextView>(R.id.mtv_answer)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionHolder {
    val inflate =
      LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_summery, null, false)
    return SessionHolder(inflate.rootView)
  }

  override fun onBindViewHolder(holder: SessionHolder, position: Int) {
   holder.answerMtv.text = "Answer :${quizList[position].userResponse}"
   holder.questionMtv.text = "Question :${quizList[position].question}"
  }

  override fun getItemCount(): Int {
    return quizList.size ?: 0
  }
}