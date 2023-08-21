package uz.star.mytaxi.utils.helpers.network

import okhttp3.Response
import uz.star.mytaxi.utils.helpers.ServerException

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:26
 **/

object ServerExceptionFactory {
    fun createFromResponse(response: Response): ServerException {
        return when (response.code) {
            405 -> ServerException.UserNotValidException()
            500 -> ServerException.ServerFaultException()
            else -> {
                ServerException.UnknownErrorException(response.body?.charStream()?.toString() ?: "")
            }
        }
    }
}
