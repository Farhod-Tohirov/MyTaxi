package uz.star.mytaxi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import uz.star.mytaxi.data.entities.address.FavouriteAddressEntityData

/**
 * Created by Farhod Tohirov on 23-August-2023, 10:24
 **/

@Dao
interface AppDatabaseDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavouriteAddress(favouriteAddressData: FavouriteAddressEntityData)

    @Insert(onConflict = REPLACE)
    suspend fun insertFavouriteAddressList(list: List<FavouriteAddressEntityData>)

    @Query("SELECT * from FavouriteAddressEntityData")
    suspend fun getFavouriteAddressesList(): List<FavouriteAddressEntityData>
}
