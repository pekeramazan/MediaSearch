package com.ramazan.mediasearch.ui.search.list

sealed class SearchListViewEvent {

    data class ShowInputError(val input: SearchListInputEnums) : SearchListViewEvent()

    object Error : SearchListViewEvent()


}