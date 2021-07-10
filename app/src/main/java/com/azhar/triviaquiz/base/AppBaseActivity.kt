package com.azhar.triviaquiz.base
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.azhar.triviaquiz.R

abstract class AppBaseActivity<Binding : ViewDataBinding, ViewModel : BaseViewModel> :
    BaseActivity<Binding, ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView() {
    }

    override fun getNavIconScale(): Float {
        return 0.75f
    }

    override fun getToolbarBackgroundColor(): Int? {
        return Color.parseColor("#747474")
    }

    override fun getToolbarTitleColor(): Int? {
        return Color.parseColor("#FFFFFF")
    }

    override fun getToolbarTitleGravity(): Int {
        return Gravity.CENTER
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideToolBar(){

    }

    fun changeTheme(color: Int, taskBarColor: Int) {
        getToolbar()?.setBackgroundColor(ContextCompat.getColor(this, color))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = ContextCompat.getColor(this, taskBarColor)
        }
    }
}
