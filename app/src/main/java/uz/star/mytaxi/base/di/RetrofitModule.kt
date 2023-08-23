package uz.star.mytaxi.base.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.star.mytaxi.data.local.LocalStorage
import uz.star.mytaxi.utils.extensions.showLog
import uz.star.mytaxi.utils.helpers.Const
import uz.star.mytaxi.utils.helpers.network.ServerExceptionFactory
import javax.inject.Singleton

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:21
 **/

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun getChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(2500000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()

    @Provides
    @Singleton
    fun okHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        localStorage: LocalStorage
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor) //for seeing responses and requests from emulator
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + localStorage.token)
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor {
            try {
                val response = it.proceed(it.request())
                if (response.code >= 400) // check server exceptions here.
                    throw ServerExceptionFactory.createFromResponse(response = response)
                response
            } catch (e: Exception) {
                showLog("EXCEPTION = $e")
                throw e
            }
        }
        .build()

    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Const.BaseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}