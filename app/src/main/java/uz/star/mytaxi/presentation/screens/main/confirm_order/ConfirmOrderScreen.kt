package uz.star.mytaxi.presentation.screens.main.confirm_order

import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.AddressDataList
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.databinding.ScreenConfirmOrderBinding
import uz.star.mytaxi.presentation.adapters.confirm_order.CarOrderTypesAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.ConfirmOrderViewModel
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.impl.ConfirmOrderViewModelImpl
import uz.star.mytaxi.utils.extensions.parcelable
import uz.star.mytaxi.utils.helpers.Const

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

@AndroidEntryPoint
class ConfirmOrderScreen : BaseScreen<ScreenConfirmOrderBinding>(R.layout.screen_confirm_order, ScreenConfirmOrderBinding::bind) {

    override val viewModel: ConfirmOrderViewModel by viewModels<ConfirmOrderViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.orderTypeList)

    private val carOrderTypesAdapter = CarOrderTypesAdapter()

    override fun loadViews() {
        binding.backButton.setOnClickListener { closeScreen() }
        binding.payment.setOnClickListener {}
        binding.schedule.setOnClickListener { }
        binding.wishes.setOnClickListener { }

        binding.orderTypeList.adapter = carOrderTypesAdapter

        carOrderTypesAdapter.setOnAddressClickListener(viewModel::carOrderTypeSelected)

        binding.addAddressButton.setOnClickListener {
            safeNavigate(ConfirmOrderScreenDirections.actionConfirmOrderScreenToAddStopPointScreen(viewModel.currentAddressData.value?.location))
        }

        setFragmentResultListener(Const.selectedAddress) { _, bundle ->
            val selectedAddressData = bundle.parcelable<AddressData>(Const.selectedAddress) ?: return@setFragmentResultListener
            viewModel.addStopPoint(selectedAddressData)
        }

        binding.selectedAddress.setOnClickListener {
            safeNavigate(
                ConfirmOrderScreenDirections.actionConfirmOrderScreenToStopPointsScreen(
                    addresses = AddressDataList(list = viewModel.selectedAddressesData.value ?: emptyList()),
                    currentLocation = viewModel.currentAddressData.value?.location
                )
            )
        }
    }

    override fun loadObservers() {
        viewModel.orderTypesList.observe(this, orderTypesListObserver)
        viewModel.currentAddressData.observe(this, currentAddressDataObserver)
        viewModel.selectedAddressesData.observe(this, selectedAddressesDataObserver)
    }

    private val currentAddressDataObserver = Observer<AddressData> {
        binding.currentAddress.text = it.addressName
    }

    private val selectedAddressesDataObserver = Observer<List<AddressData>> {
        if (it.size == 1) {
            binding.selectedAddress.text = it.first().addressName
        } else {
            binding.selectedAddress.text = getString(R.string.address_count, it.size)
        }
    }

    private val orderTypesListObserver = Observer<List<CarOrderTypeData>> {
        carOrderTypesAdapter.submitList(it)
    }

    override fun onDestroyScreenUI() {
        clearFragmentResultListener(Const.selectedAddress)
    }
}