package com.ramazan.mediasearch.ui.search.list

sealed class SearchListViewEvent {

    data class ShowInputError(val input: SearchListInputEnums) : SearchListViewEvent()
    data class Filter(val input: SearchCategoryEnums.Categories) : SearchListViewEvent()

    object Error : SearchListViewEvent()


}