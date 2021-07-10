package com.azhar.triviaquiz.base

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.customViews.CustomToolbar
import com.azhar.triviaquiz.databinding.ActivityFragmentContainerBinding
import kotlinx.android.synthetic.main.activity_fragment_container.*


const val FRAGMENT_TYPE = "FRAGMENT_TYPE"

abstract class FragmentContainerActivity :
  BaseActivity<ActivityFragmentContainerBinding, BaseViewModel>() {

  protected var type: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    type = intent?.extras?.getInt(FRAGMENT_TYPE)
    super.onCreate(savedInstanceState)
  }

  override fun getLayout(): Int {
    return R.layout.activity_fragment_container
  }

  override fun onCreateView() {
    setFragment()
  }

  override fun getToolbar(): CustomToolbar? {
    return binding?.appBarLayout?.toolbar
  }

  override fun getViewModelClass(): Class<BaseViewModel> {
    return BaseViewModel::class.java
  }


  private fun setFragment() {
    val fragment = getFragmentInstance(type)
    fragment.arguments = intent.extras
    binding?.container?.id?.let { addFragmentReplace(it, fragment, shouldAddToBackStack()) }
  }

  abstract fun shouldAddToBackStack(): Boolean
  abstract fun getFragmentInstance(type: Int?): BaseFragment<*, *>
}

fun Intent.setFragmentType(type: Int) {
  this.putExtra(FRAGMENT_TYPE, type)
}