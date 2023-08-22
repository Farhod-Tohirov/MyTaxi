package uz.star.mytaxi.presentation.screens.main.choose_address_manual

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.databinding.ScreenChooseAddressManualBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.MainScreen
import uz.star.mytaxi.presentation.screens.main.choose_address_manual.viewmodel.ChooseAddressManualViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address_manual.viewmodel.impl.ChooseAddressManualViewModelImpl
import uz.star.mytaxi.utils.helpers.Const

/**
 * Created by Farhod Tohirov on 22-August-2023, 10:23
 **/

@AndroidEntryPoint
class ChooseAddressManualScreen : BaseScreen<ScreenChooseAddressManualBinding>(R.layout.screen_choose_address_manual, ScreenChooseAddressManualBinding::bind) {

    override val viewModel: ChooseAddressManualViewModel by viewModels<ChooseAddressManualViewModelImpl>()

    private val parentScreen: MainScreen by lazy { requireParentFragment().requireParentFragment() as MainScreen }

    override fun loadViews() {
        viewModel.selectedLocationChanged(parentScreen.getMapCenterPosition())

        parentScreen.setOnCameraIdleListener(viewModel::selectedLocationChanged)
        binding.navigateCurrentLocationButton.setOnClickListener { parentScreen.navigateUserLocation() }
        binding.confirmButton.setOnClickListener { viewModel.confirmButtonClick() }
    }

    override fun loadObservers() {
        viewModel.selectedLocationName.observe(viewLifecycleOwner, selectedLocationNameObserver)
        viewModel.selectAddressConfirmed.observe(this, selectedAddressConfirmedObserver)
    }

    private val selectedAddressConfirmedObserver = Observer<AddressData> { addressData ->
        setFragmentResult(Const.manualSelectedAddress, result = bundleOf(Const.manualSelectedAddress to addressData))
        closeScreen()
    }

    private val selectedLocationNameObserver = Observer<String> {
        binding.addressName.text = it
    }
}