package uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.SearchAddressScreenArgs
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.SearchAddressViewModel
import uz.star.mytaxi.utils.helpers.AddressNotFoundException
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 19-August-2023, 15:37
 **/
@HiltViewModel
class SearchAddressViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase,
    savedStateHandle: SavedStateHandle
) : SearchAddressViewModel() {

    private val args = SearchAddressScreenArgs.fromSavedStateHandle(savedStateHandle)

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