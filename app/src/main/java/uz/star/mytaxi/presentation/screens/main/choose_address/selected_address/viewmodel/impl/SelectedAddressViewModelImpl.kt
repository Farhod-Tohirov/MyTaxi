package uz.star.mytaxi.presentation.screens.main.choose_address.selected_address.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.choose_address.selected_address.viewmodel.SelectedAddressViewModel
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:07
 **/

@HiltViewModel
class SelectedAddressViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase
) : SelectedAddressViewModel() {

    override val selectedAddressList = MutableLiveData<List<SelectedAddressData>>()

    init {
        getSelectedAddresses()
    }

    override fun getSelectedAddresses() {
        tryLoadData {
            onIOContext {
                val selectedAddresses = addressesUseCase.getSelectedAddresses()
                onUIContext {
                    selectedAddressList.value = selectedAddresses
                }
            }
        }
    }
}