package uz.star.mytaxi.base.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.domain.address.impl.AddressesUseCaseImpl
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:18
 **/

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun getAddressesUseCase(impl: AddressesUseCaseImpl): AddressesUseCase

}