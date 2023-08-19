package uz.star.mytaxi.presentation.screens.splash

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.databinding.ScreenSplashBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.splash.viewmodel.SplashViewModel
import uz.star.mytaxi.presentation.screens.splash.viewmodel.impl.SplashViewModelImpl

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:33
 **/

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseScreen<ScreenSplashBinding>(R.layout.screen_splash, ScreenSplashBinding::bind) {

    override val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun loadObservers() {
        viewModel.navigateMainScreen.observe(this, navigateMainScreenObserver)
    }

    private val navigateMainScreenObserver = Observer<Unit> {
        safeNavigate(SplashScreenDirections.actionSplashScreenToMainScreen())
    }
}