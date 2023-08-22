package uz.star.mytaxi.utils.helpers

import android.content.Context
import uz.star.mytaxi.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:30
 **/

object ResourceProvider {
    fun getString(throwable: Throwable, context: Context): String {
        return when (throwable) {
            is AddressNotFoundException -> throwable.message ?: "Address not found"

            is ServerException.ServerErrorMessageException -> throwable.errorMessage
            is ServerException.ServerFaultException -> context.getString(R.string.failure_500)
            is ServerException.UnknownErrorException -> context.getString(R.string.failure)

            is ConnectException -> context.getString(R.string.no_internet_connection)
            is SocketTimeoutException -> context.getString(R.string.slow_internet)
            is UnknownHostException -> context.getString(R.string.no_internet_connection)
            else -> context.getString(R.string.failure)
        }
    }
}