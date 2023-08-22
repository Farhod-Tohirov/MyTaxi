package uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.StopPointsScreenArgs
import uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.viewmodel.StopPointsViewModel
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 22-August-2023, 12:02
 **/
@HiltViewModel
class StopPointsViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase,
    savedStateHandle: SavedStateHandle
) : StopPointsViewModel() {

    private val args = StopPointsScreenArgs.fromSavedStateHandle(savedStateHandle)

    override val stopPointsList = MutableLiveData<List<AddressData>>(args.addresses?.list)

    override val notifyItemsChanged = MutableLiveData<Int>()

    override fun pointDeleteClick(addressData: AddressData) {
        tryLoadData(shouldShowLoader = false) {
            if (stopPointsList.value?.size == 1) throw Exception("At least one point should be saved")
            stopPointsList.value = stopPointsList.value?.toMutableList()?.apply {
                remove(addressData)
            }
            notifyItemsChanged.value = addressData.addressId ?: 0
        }
    }
}