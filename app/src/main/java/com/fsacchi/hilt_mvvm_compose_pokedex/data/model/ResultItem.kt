package com.fsacchi.hilt_mvvm_compose_pokedex.data.model


import com.google.gson.annotations.SerializedName

data class ResultItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun getNameItem(): String {
        val splits = url.split("/").filter { it.isNotEmpty() }
        return splits.last()
    }

    fun getNameImage(): String = "${getNameItem()}.svg"
}