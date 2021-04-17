package com.ramazan.mediasearch.network.requests

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("term") val term: String?

)