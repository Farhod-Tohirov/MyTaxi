package uz.star.mytaxi.presentation.screens.main.favourite_address.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:07
 **/

abstract class FavouriteAddressViewModel : BaseViewModel() {

    abstract val favouriteAddressList: LiveData<List<FavouriteAddressData>>

    abstract fun getSelectedAddresses()
}