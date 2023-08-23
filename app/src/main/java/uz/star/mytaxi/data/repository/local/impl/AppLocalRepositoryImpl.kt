package uz.star.mytaxi.data.repository.local.impl

import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.data.local.room.dao.AppDatabaseDao
import uz.star.mytaxi.data.repository.local.AppLocalRepository
import uz.star.mytaxi.utils.helpers.network.mapToDomain
import uz.star.mytaxi.utils.helpers.network.mapToEntity
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 23-August-2023, 10:20
 **/

class AppLocalRepositoryImpl @Inject constructor(
    private val appDatabaseDao: AppDatabaseDao
) : AppLocalRepository {

    override suspend fun getFavouriteAddressList(): List<FavouriteAddressData> =
        appDatabaseDao.getFavouriteAddressesList().map { it.mapToDomain() }

    override suspend fun insertFavouriteAddress(favouriteAddressData: FavouriteAddressData) =
        appDatabaseDao.insertFavouriteAddress(favouriteAddressData = favouriteAddressData.mapToEntity())

    override suspend fun insertFavouriteAddressList(favouriteAddressList: List<FavouriteAddressData>) =
        appDatabaseDao.insertFavouriteAddressList(list = favouriteAddressList.map { it.mapToEntity() })
}