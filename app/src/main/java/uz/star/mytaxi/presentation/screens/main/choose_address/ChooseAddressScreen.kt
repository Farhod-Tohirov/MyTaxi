package uz.star.mytaxi.presentation.screens.main.choose_address

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.databinding.ScreenChooseAddressBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.MainScreen
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.SearchAddressScreen
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.ChooseAddressViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.impl.ChooseAddressViewModelImpl
import uz.star.mytaxi.utils.extensions.getBackStackLiveData
import uz.star.mytaxi.utils.extensions.parcelable
import uz.star.mytaxi.utils.helpers.Const

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
            safeNavigate(
                ChooseAddressScreenDirections.actionTestScreenToSearchAddressScreen(
                    selectedAddressName = viewModel.currentLocationName.value,
                    currentLocation = viewModel.currentLocation?.location
                )
            )
        }

        getBackStackLiveData(title = SearchAddressScreen::class.java.name) { bundle ->
            val selectedAddressData = bundle.parcelable<AddressData>(Const.selectedAddress)

            binding.chooseAddressPanel.selectedAddress.text = selectedAddressData?.addressName

            viewModel.selectedLocationChanged(addressData = selectedAddressData)
        }

        binding.chooseAddressPanel.navigateMapButton.setOnClickListener {}
    }

    override fun loadObservers() {
        viewModel.currentLocationName.observe(viewLifecycleOwner, currentLocationNameObserver)
        viewModel.navigateConfirmOrderScreen.observe(this, navigateConfirmOrderScreenObserver)
    }

    private val navigateConfirmOrderScreenObserver = Observer<AddressData> { selectedAddress ->
        safeNavigate(
            ChooseAddressScreenDirections.actionTestScreenToConfirmOrderScreen(
                currentAddress = viewModel.currentLocation ?: return@Observer,
                selectedAddress = selectedAddress
            )
        )
    }

    private val currentLocationNameObserver = Observer<String> {
        binding.chooseAddressPanel.currentAddress.text = it
    }

    override fun onDestroyScreenUI() {
        parentScreen.removeMapCameraMoveListeners()
    }
}