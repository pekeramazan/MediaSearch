package com.ramazan.mediasearch.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.ramazan.mediasearch.data.Event
import com.ramazan.mediasearch.network.responses.ApiError
import retrofit2.HttpException
import java.net.UnknownHostException


abstract class BaseViewModel: ViewModel() {
    var mLastClickTime: Long = 0

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _baseEvent = MutableLiveData<Event<BaseViewEvent>>()
    val baseEvent: LiveData<Event<BaseViewEvent>> = _baseEvent

    fun setLoading(loading: Boolean) = _loading.postValue(loading)

    fun setLoadingSync(loading: Boolean) {
        _loading.value = loading
    }
    private fun showCommonNetworkError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCommonNetworkError))

    private fun showConnectivityError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowConnectivityError))

    private fun showCustomError(message: String) =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCustomError(message)))

    open fun handleException(e: Exception) {
        when (e) {
            is HttpException -> {
                when (e.code()) {
                    401, 462 -> _baseEvent.postValue(Event(BaseViewEvent.ShowCommonNetworkError))

                    403 -> _baseEvent.postValue(Event(BaseViewEvent.ShowCommonNetworkError))

                    else -> {
                        if (e.code() in 499..599) {
                            _baseEvent.postValue(Event(BaseViewEvent.ShowInternalServerError))
                        } else {
                            try {
                                val errorMessage = Gson().fromJson(
                                    e.response()?.errorBody()?.string(),
                                    ApiError::class.java
                                )?.detail
                                if (!errorMessage.isNullOrEmpty()) {
                                    showCustomError(errorMessage)
                                } else {
                                    showCommonNetworkError()
                                }

                            } catch (exception: Exception) {
                                showCommonNetworkError()
                            }
                        }
                    }
                }
            }

            is JsonSyntaxException -> showCommonNetworkError()

            is UnknownHostException -> showCommonNetworkError()
        }
    }

}