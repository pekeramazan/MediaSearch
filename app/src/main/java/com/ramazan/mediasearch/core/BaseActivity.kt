package com.ramazan.mediasearch.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.BoringLayout.make
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.ext.observeEvent
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.reflect.KClass


abstract class BaseActivity<B: ViewDataBinding,M:BaseViewModel>(
    @LayoutRes private val layoutId: Int
):AppCompatActivity() {

    lateinit var viewBinding: B

    var mLastClickTime: Long = 0
    val viewModel: M by lazy {
        getViewModel(
            clazz = viewModelClass(),
            parameters = { parametersOf(arguments()) }
        )
    }

    abstract fun onInitDataBinding()
    abstract fun viewModelClass(): KClass<M>
    fun arguments() = intent.extras ?: Bundle()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this
        onInitDataBinding()
        observeEvent(viewModel.baseEvent, ::onViewEvent)


    }
    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {

            BaseViewEvent.ShowInternalServerError -> {showError(R.string.something_went_wrong)
            }

            BaseViewEvent.ShowCommonNetworkError -> showError(R.string.something_went_wrong)

            BaseViewEvent.ShowConnectivityError -> showError(R.string.connectivity_error)

            is BaseViewEvent.ShowCustomError -> showError(event.message)
        }
    }

    fun doubleClickBlocked() {
        if ((SystemClock.elapsedRealtime() - mLastClickTime) < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    fun showCommonError() {
        Toast.makeText(applicationContext,getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show()

    }

    fun showError(@StringRes error: Int) {
        Toast.makeText(applicationContext,getString(error),Toast.LENGTH_SHORT).show()

    }

    fun showError(error: String) {
        Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT).show()
    }







}