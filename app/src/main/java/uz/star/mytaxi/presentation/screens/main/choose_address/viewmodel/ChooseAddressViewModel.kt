package uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 19-August-2023, 13:38
 **/

abstract class ChooseAddressViewModel : BaseViewModel() {

    abstract val currentLocationName: LiveData<String>

    abstract fun currentLocationChanged(location: LatLng)

}