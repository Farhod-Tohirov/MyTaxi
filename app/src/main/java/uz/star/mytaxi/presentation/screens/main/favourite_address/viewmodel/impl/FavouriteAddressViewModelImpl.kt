package uz.star.mytaxi.presentation.screens.main.favourite_address.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.favourite_address.viewmodel.FavouriteAddressViewModel
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:07
 **/

@HiltViewModel
class FavouriteAddressViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase
) : FavouriteAddressViewModel() {

    override val favouriteAddressList = MutableLiveData<List<FavouriteAddressData>>()

    init {
        getSelectedAddresses()
    }

    override fun getSelectedAddresses() {
        tryLoadData {
            onIOContext {
                val selectedAddresses = addressesUseCase.getFavouriteAddresses()
                onUIContext {
                    favouriteAddressList.value = selectedAddresses
                }
            }
        }
    }
}