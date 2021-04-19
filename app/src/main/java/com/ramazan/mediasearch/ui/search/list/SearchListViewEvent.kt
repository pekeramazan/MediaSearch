package com.ramazan.mediasearch.ui.search.list

sealed class SearchListViewEvent {

    data class Filter(val input: SearchCategoryEnums.Categories) : SearchListViewEvent()
    data class NavigateDetail(
        val artUrl100: String?,
        val artistName: String?,
        val collectionName: String?,
        val longDescription: String?
    ) : SearchListViewEvent()

    object Error : SearchListViewEvent()


}