package com.azhar.triviaquiz.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.azhar.triviaquiz.R
import com.azhar.triviaquiz.base.AppBaseActivity
import com.azhar.triviaquiz.base.BaseActivity
import com.azhar.triviaquiz.base.BaseViewModel
import com.azhar.triviaquiz.databinding.SplashActivityBinding
import com.azhar.triviaquiz.onboarding.OnBoardingActivity

class SplashActivity : AppBaseActivity<SplashActivityBinding,BaseViewModel>() {
  var handler: Handler? = null
  var runnable:Runnable? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window?.statusBarColor = Color.WHITE
    handler = Handler(Looper.getMainLooper())
    runnable = Runnable {
      startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
      finish()
    }
  }


  override fun onStart() {
    super.onStart()
    runnable?.let { handler?.postDelayed(it,1500) }
  }

  override fun onPause() {
    super.onPause()
    runnable?.let { handler?.removeCallbacks(it) }
  }

  override fun getLayout(): Int {
   return R.layout.splash_activity
  }

  override fun getViewModelClass(): Class<BaseViewModel> {
    return BaseViewModel::class.java
  }

}