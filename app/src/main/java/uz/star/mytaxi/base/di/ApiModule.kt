package uz.star.mytaxi.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.star.mytaxi.data.remote.AddressesApi
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:37
 **/
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun getAddressesApi(retrofit: Retrofit): AddressesApi = retrofit.create(AddressesApi::class.java)
}