package com.ramazan.mediasearch.core

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.ext.observeEvent
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<B:ViewDataBinding,M:BaseViewModel>(
    @LayoutRes private val layoutId:Int
):Fragment() {
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

    private fun arguments(): Bundle {
        val fragmentArgs = arguments ?: Bundle()
        val activityArgs = activity?.intent?.extras ?: Bundle()
        return activityArgs.apply { putAll(fragmentArgs) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewBinding.lifecycleOwner = viewLifecycleOwner
            viewBinding.root
        } finally {
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()

        observeEvent(viewModel.baseEvent, ::onViewEvent)
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {


            BaseViewEvent.ShowCommonNetworkError -> showError(R.string.something_went_wrong)

            BaseViewEvent.ShowConnectivityError -> showError(R.string.connectivity_error)

            BaseViewEvent.ShowInternalServerError -> {
                showError(R.string.internal_server_not_found)
            }

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
        try {
            (requireActivity() as? AppCompatActivity)?.let {

                Toast.makeText(it,getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()

            }
        } catch (e: IllegalStateException) {
        }
    }

    fun showError(@StringRes error: Int) {
        try {
            (requireActivity() as? AppCompatActivity)?.let {
                Toast.makeText(it,getString(error), Toast.LENGTH_SHORT).show()

            }
        } catch (e: IllegalStateException) {
        }
    }



    fun showError(error: String) {
        try {
            (requireActivity() as? AppCompatActivity)?.let {
                Toast.makeText(it,error, Toast.LENGTH_SHORT).show()

            }
        } catch (e: IllegalStateException) {

        }
    }


}