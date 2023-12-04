package com.fsacchi.hilt_mvvm_compose_pokedex.data.model

import com.google.gson.annotations.SerializedName

data class ResultModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<ResultItem>
)
