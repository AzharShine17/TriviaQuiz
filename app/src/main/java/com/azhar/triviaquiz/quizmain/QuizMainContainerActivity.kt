package com.azhar.triviaquiz.quizmain

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseActivity
import com.azhar.triviaquiz.base.BaseFragment
import com.azhar.triviaquiz.base.BaseViewModel
import com.azhar.triviaquiz.base.FRAGMENT_TYPE
import com.azhar.triviaquiz.base.customViews.CustomToolbar
import com.azhar.triviaquiz.databinding.ActivityFragmentContainerBinding
import com.azhar.triviaquiz.quizmain.ui.FragmentQuizHistory
import com.azhar.triviaquiz.quizmain.ui.FragmentQuizScreen
import com.azhar.triviaquiz.quizmain.ui.FragmentSummery
import com.weather.apps10xassignment.ui.controller.FragmentType

class QuizMainContainerActivity :
  AppBaseActivity<ActivityFragmentContainerBinding, BaseViewModel>() {
  var fragmentQuizScreen: FragmentQuizScreen? = null
  var fragmentQuizHistory: FragmentQuizHistory? = null
  var fragmentSummery: FragmentSummery? = null
  private var type: FragmentType? = FragmentType.HOME_FRAGMENT

  override fun getLayout(): Int {
    return R.layout.activity_fragment_container
  }

  override fun getViewModelClass(): Class<BaseViewModel> {
    return BaseViewModel::class.java
  }

  override fun getToolbar(): CustomToolbar? {
    when (type) {
      FragmentType.HOME_FRAGMENT -> {
        binding?.appBarLayout?.toolbar?.visibility = View.GONE
      }
      else ->  binding?.appBarLayout?.toolbar
    }
    return binding?.appBarLayout?.toolbar
  }

  override fun getToolbarBackgroundColor(): Int? {
    return R.color.purple_500
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    intent?.extras?.getInt(FRAGMENT_TYPE)?.let { type = FragmentType.values()[it] }
    if (intent?.extras==null) type = FragmentType.HOME_FRAGMENT
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView() {
    super.onCreateView()
    setFragment()
  }

  private fun setFragment() {
    val fragment = getFragmentInstance(type)
    fragment?.arguments = intent.extras
    binding?.container?.id?.let { addFragmentReplace(it, fragment, shouldAddToBackStack()) }
  }

  private fun getFragmentInstance(type: FragmentType?): BaseFragment<*, *>? {
    return when (type) {
      FragmentType.HOME_FRAGMENT -> {
        fragmentQuizScreen = FragmentQuizScreen.newInstance()
        fragmentQuizScreen
      }
      FragmentType.SUMMERY_FRAGMENT -> {
        fragmentSummery = FragmentSummery.newInstance()
        fragmentSummery
      }
      FragmentType.HISTORY_FRAGMENT -> {
        fragmentQuizHistory = FragmentQuizHistory.newInstance()
        fragmentQuizHistory
      }
      else -> throw Exception()
    }
  }

  private fun shouldAddToBackStack(): Boolean {
    return when (type) {
      else -> false
    }
  }
}

fun AppCompatActivity.startQuizFragmentActivity(
  type: FragmentType,
  bundle: Bundle = Bundle(),
  clearTop: Boolean = false
) {
  val intent = Intent(this, QuizMainContainerActivity::class.java)
  intent.putExtras(bundle)
  intent.setFragmentType(type)
  if (clearTop) intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
  startActivity(intent)
}

fun Intent.setFragmentType(type: FragmentType): Intent {
  return this.putExtra(FRAGMENT_TYPE, type.ordinal)
}