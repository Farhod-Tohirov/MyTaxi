package uz.star.mytaxi.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.star.mytaxi.data.entities.address.FavouriteAddressEntityData
import uz.star.mytaxi.data.local.room.dao.AppDatabaseDao

/**
 * Created by Farhod Tohirov on 23-August-2023, 10:24
 **/

@Database(entities = [FavouriteAddressEntityData::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDatabaseDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}