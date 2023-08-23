package uz.star.mytaxi.base.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.star.mytaxi.data.local.room.AppDatabase
import uz.star.mytaxi.data.local.room.dao.AppDatabaseDao
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 23-August-2023, 10:27
 **/

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabaseDao = AppDatabase.getDatabase(context).appDao()
}