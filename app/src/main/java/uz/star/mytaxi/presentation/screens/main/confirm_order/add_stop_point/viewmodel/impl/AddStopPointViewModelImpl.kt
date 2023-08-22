package uz.star.mytaxi.presentation.screens.main.confirm_order.add_stop_point.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.confirm_order.add_stop_point.AddStopPointScreenArgs
import uz.star.mytaxi.presentation.screens.main.confirm_order.add_stop_point.viewmodel.AddStopPointViewModel
import uz.star.mytaxi.utils.helpers.AddressNotFoundException
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 22-August-2023, 11:35
 **/
@HiltViewModel
class AddStopPointViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase,
    savedStateHandle: SavedStateHandle
) : AddStopPointViewModel() {

    private val args = AddStopPointScreenArgs.fromSavedStateHandle(savedStateHandle)

    private val shimmerListData = "12345678910".toList()

    override val foundAddressesList = MutableLiveData<List<AddressData>>()
    override val shimmerList = MutableLiveData(shimmerListData)


    private var searchJob: Job? = null

    override fun searchLocationByName(name: String) {
        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = tryLoadData {
            onIOContext {
                onUIContext {
                    foundAddressesList.value = emptyList()
                    shimmerList.value = shimmerListData
                }
                val foundedAddressesList = addressesUseCase.getAddressesByName(locationName = name, locationData = args.currentLocation ?: throw AddressNotFoundException())
                onUIContext {
                    shimmerList.value = emptyList()
                    foundAddressesList.value = foundedAddressesList
                }
            }
        }
    }
}