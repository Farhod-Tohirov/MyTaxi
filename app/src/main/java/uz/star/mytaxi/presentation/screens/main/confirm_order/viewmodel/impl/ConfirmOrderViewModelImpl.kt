package uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.ConfirmOrderViewModel
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

@HiltViewModel
class ConfirmOrderViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase
) : ConfirmOrderViewModel() {

    override val orderTypesList = MutableLiveData<List<CarOrderTypeData>>()

    init {
        getOrderTypes()
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
}