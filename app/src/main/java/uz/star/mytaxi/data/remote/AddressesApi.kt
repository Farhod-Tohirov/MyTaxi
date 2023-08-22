package uz.star.mytaxi.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.star.mytaxi.data.entities.address.AddressListResponseData
import uz.star.mytaxi.data.entities.address.PolylineResponseData
import uz.star.mytaxi.data.entities.address.SingleAddressObjectResponseData
import uz.star.mytaxi.data.entities.base.BaseResponseData

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:37
 **/

interface AddressesApi {

    @GET("v1/address/lat/{lat}/lon/{long}")
    suspend fun searchSingleLocation(@Path("lat") lat: Double, @Path("long") long: Double): BaseResponseData<SingleAddressObjectResponseData>

    @GET("v2/search")
    suspend fun searchAddressByName(
        @Query("limit") limit: Int,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("query") name: String
    ): BaseResponseData<AddressListResponseData>

    @GET("v1/route")
    suspend fun getRouteData(@Query("points") points: String): BaseResponseData<PolylineResponseData>
}