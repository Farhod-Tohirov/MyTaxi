package uz.star.mytaxi.presentation.screens.splash.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import uz.star.mytaxi.presentation.screens.splash.viewmodel.SplashViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:35
 **/

@HiltViewModel
class SplashViewModelImpl @Inject constructor(

) : SplashViewModel() {

    override val navigateMainScreen = MutableLiveData<Unit>()

    init {
        startApp()
    }

    override fun startApp() {
        tryLoadData(shouldShowLoader = false) {
            delay(1.seconds)
            navigateMainScreen.value = Unit
        }
    }
}