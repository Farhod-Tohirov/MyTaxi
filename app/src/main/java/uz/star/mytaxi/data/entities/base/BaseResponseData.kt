package uz.star.mytaxi.data.entities.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:40
 **/

data class BaseResponseData<T>(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("data")
    val data: T
)