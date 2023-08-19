package uz.star.mytaxi.utils.extensions

import android.util.Log
import uz.star.mytaxi.BuildConfig

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:31
 **/

fun showLog(message: String) {
    if (BuildConfig.DEBUG)
        Log.e("T12T", message)
}