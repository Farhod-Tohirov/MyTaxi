package uz.star.mytaxi.data.repository.local

import uz.star.mytaxi.data.entities.address.FavouriteAddressData

/**
 * Created by Farhod Tohirov on 23-August-2023, 10:19
 **/

interface AppLocalRepository {

    suspend fun getFavouriteAddressList(): List<FavouriteAddressData>

    suspend fun insertFavouriteAddress(favouriteAddressData: FavouriteAddressData)

    suspend fun insertFavouriteAddressList(favouriteAddressList: List<FavouriteAddressData>)

}