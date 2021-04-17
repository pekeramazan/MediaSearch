package com.ramazan.mediasearch.core

sealed class BaseViewEvent {
    object ShowCommonNetworkError : BaseViewEvent()

    object ShowConnectivityError : BaseViewEvent()
    object ShowInternalServerError : BaseViewEvent()

    data class ShowCustomError(val message: String) : BaseViewEvent()
}