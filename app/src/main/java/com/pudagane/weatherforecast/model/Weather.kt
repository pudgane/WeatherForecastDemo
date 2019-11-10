package com.pudagane.weatherforecast.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Weather(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
):Parcelable