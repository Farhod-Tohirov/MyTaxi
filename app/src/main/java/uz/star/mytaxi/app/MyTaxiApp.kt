package uz.star.mytaxi.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.star.mytaxi.data.local.LocalStorage

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:28
 **/

@HiltAndroidApp
class MyTaxiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalStorage.init(this)
    }

}