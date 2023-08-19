package uz.star.mytaxi.presentation.screens.splash.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:34
 **/

abstract class SplashViewModel : BaseViewModel() {

    abstract val navigateMainScreen: LiveData<Unit>

    abstract fun startApp()

}