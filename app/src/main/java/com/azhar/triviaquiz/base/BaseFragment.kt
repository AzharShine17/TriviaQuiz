package com.azhar.triviaquiz.base

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<Binding : ViewDataBinding, ViewModel : BaseViewModel?> : Fragment(), View.OnClickListener {

  protected lateinit var baseActivity: BaseActivity<*, *>
  protected lateinit var root: View
  protected var viewModel: ViewModel? = null
  protected var binding: Binding? = null
  protected var navigator: Navigator? = null
  protected abstract fun getLayout(): Int
  protected abstract fun getViewModelClass(): Class<ViewModel>
  protected abstract fun onCreateView()


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    setHasOptionsMenu(true)
    baseActivity = activity as BaseActivity<*, *>
    binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
    binding?.lifecycleOwner = this
    navigator = Navigator(baseActivity)
    viewModel = ViewModelProvider(this).get(getViewModelClass())
    return binding?.root
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    super.onPrepareOptionsMenu(menu)
  }

  fun setToolbarTitle(title: String?) {
    baseActivity.setToolbarTitle(title)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onCreateView()
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  override fun onClick(v: View) {}


  fun setOnClickListener(vararg views: View?) {
    for (view in views) view?.setOnClickListener(this)
  }

  fun isVisible(vararg views: View?) {
    for (view in views) view?.visibility = View.VISIBLE
  }

  fun isGone(vararg views: View?) {
    for (view in views) view?.visibility = View.GONE
  }

  fun removeOnClickListener(vararg views: View) {
    for (view in views) view.setOnClickListener(null)
  }


  // Transactions
  fun addFragmentReplace(containerID: Int, fragment: Fragment, addToBackStack: Boolean) {
    val fragmentTransaction = fragmentManager?.beginTransaction()
    if (addToBackStack) fragmentTransaction?.addToBackStack(null)
    fragmentTransaction?.replace(containerID, fragment)?.commit()
  }

  fun getTopFragment(): Fragment? {
    parentFragmentManager.run {
      return when (backStackEntryCount) {
        0 -> null
        else -> findFragmentByTag(getBackStackEntryAt(backStackEntryCount - 1).name)
      }
    }
  }

  fun getLifeCycleState(): Lifecycle.State {
    return viewLifecycleOwner.lifecycle.currentState
  }

  fun showLongToast(string: String?) {
    Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
  }

  fun showShortToast(string: String?) {
    Toast.makeText(activity, string, Toast.LENGTH_SHORT).show()
  }

  protected fun getColor(@ColorRes color: Int): Int {
    return ResourcesCompat.getColor(resources, color, context?.theme)
  }

  protected fun getFont(@FontRes font: Int): Typeface? {
    return ResourcesCompat.getFont(baseActivity, font)
  }

}