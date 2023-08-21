package uz.star.mytaxi.base.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.star.mytaxi.data.repository.search_address.SearchAddressRepository
import uz.star.mytaxi.data.repository.search_address.impl.SearchAddressRepositoryImpl
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:35
 **/

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getSearchAddressRepository(repo: SearchAddressRepositoryImpl): SearchAddressRepository
}