package uz.star.mytaxi.data.local

import android.content.Context
import android.content.SharedPreferences
import uz.star.mytaxi.utils.helpers.StringPreference

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:28
 **/

class LocalStorage private constructor(context: Context) {
    companion object {
        @Volatile
        lateinit var instance: LocalStorage
            private set

        fun init(context: Context) {
            synchronized(this) {
                instance = LocalStorage(context)
            }
        }
    }

    private val pref: SharedPreferences = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var token: String by StringPreference(pref, "UKFT7JWIN6AWQOSKYW1HXG")

}