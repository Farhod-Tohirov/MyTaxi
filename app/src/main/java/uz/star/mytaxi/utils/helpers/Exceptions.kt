package uz.star.mytaxi.utils.helpers

import java.io.IOException

/**
 * Created by Farhod Tohirov on 21-August-2023, 15:26
 **/


sealed class ServerException(message: String) : IOException(message) {
    class UserNotValidException : ServerException("User not valid")
    class ServerFaultException : ServerException("Server broken down")
    class UnknownErrorException(message: String) : ServerException(message)
    data class ServerErrorMessageException(val errorMessage: String) : ServerException(errorMessage)
}

class AddressNotFoundException : IOException("Address not found")