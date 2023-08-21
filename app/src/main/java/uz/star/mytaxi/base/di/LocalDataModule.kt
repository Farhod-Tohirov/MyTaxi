package uz.star.mytaxi.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.star.mytaxi.data.local.LocalStorage
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:30
 **/

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun getLocalStorage(): LocalStorage = LocalStorage.instance
}