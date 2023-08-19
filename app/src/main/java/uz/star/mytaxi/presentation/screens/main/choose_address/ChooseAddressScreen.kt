package uz.star.mytaxi.presentation.screens.main.choose_address

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.databinding.ScreenChooseAddressBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.MainScreen
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.ChooseAddressViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.impl.ChooseAddressViewModelImpl

/**
 * Created by Farhod Tohirov on 19-August-2023, 13:17
 **/

@AndroidEntryPoint
class ChooseAddressScreen : BaseScreen<ScreenChooseAddressBinding>(R.layout.screen_choose_address, ScreenChooseAddressBinding::bind) {

    override val viewModel: ChooseAddressViewModel by viewModels<ChooseAddressViewModelImpl>()

    private val parentScreen: MainScreen by lazy { requireParentFragment().requireParentFragment() as MainScreen }

    override fun loadViews() {
        parentScreen.onMapReady {
            binding.navigateCurrentLocationButton.setOnClickListener {
                parentScreen.navigateUserLocation()
            }

            parentScreen.setOnCameraIdleListener(viewModel::currentLocationChanged)
        }

        binding.chooseAddressPanel.selectedAddressButton.setOnClickListener {
            safeNavigate(ChooseAddressScreenDirections.actionTestScreenToSearchAddressScreen())
        }

        binding.chooseAddressPanel.navigateMapButton.setOnClickListener {}
    }

    override fun loadObservers() {
        viewModel.currentLocationName.observe(viewLifecycleOwner, currentLocationNameObserver)
    }

    private val currentLocationNameObserver = Observer<String> {
        binding.chooseAddressPanel.currentAddress.text = it
    }

    override fun onDestroyScreenUI() {
        parentScreen.removeMapCameraMoveListeners()
    }
}