package uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.confirm_order.ConfirmOrderScreenArgs
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.ConfirmOrderViewModel
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

@HiltViewModel
class ConfirmOrderViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase,
    savedStateHandle: SavedStateHandle
) : ConfirmOrderViewModel() {

    override val orderTypesList = MutableLiveData<List<CarOrderTypeData>>()
    override val currentAddressData = MutableLiveData<AddressData>()
    override val selectedAddressesData = MutableLiveData<List<AddressData>>()

    private val args = ConfirmOrderScreenArgs.fromSavedStateHandle(savedStateHandle)

    init {
        loadChosenAddresses()
        getOrderTypes()
    }

    override fun loadChosenAddresses() {
        tryLoadData(shouldShowLoader = false) {
            currentAddressData.value = args.currentAddress
            selectedAddressesData.value = listOf(args.selectedAddress)
        }
    }

    override fun getOrderTypes() {
        tryLoadData {
            onIOContext {
                val orderTypes = addressesUseCase.getOrderTypes()
                onUIContext {
                    orderTypesList.value = orderTypes
                }
            }
        }
    }

    override fun carOrderTypeSelected(cardOrderTypeData: CarOrderTypeData) {
        tryLoadData(shouldShowLoader = false) {
            orderTypesList.value = orderTypesList.value?.map { it.copy(isSelected = it.id == cardOrderTypeData.id) }
        }
    }

    override fun addStopPoint(addressData: AddressData) {
        tryLoadData(shouldShowLoader = false) {
            selectedAddressesData.value = selectedAddressesData.value?.toMutableList()?.apply {
                add(addressData)
            }
        }
    }
}