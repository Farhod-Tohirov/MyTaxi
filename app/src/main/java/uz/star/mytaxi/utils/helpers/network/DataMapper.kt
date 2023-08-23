package uz.star.mytaxi.utils.helpers.network

/**
 * Created by Farhod Tohirov on 21-February-2023, 15:47
 **/

interface DataMapper<T, S> {
    fun T.mapToDomain(): S
}

fun <T : DataMapper<T, S>, S> T.mapToDomain() = this.mapToDomain()


interface DataRequester<T, S> {
    fun T.mapToRequest(): S
}

fun <T : DataRequester<T, S>, S> T.mapToRequest() = this.mapToRequest()


interface DataEntityConverter<T, S> {
    fun T.mapToEntity(): S
}

fun <T : DataEntityConverter<T, S>, S> T.mapToEntity() = this.mapToEntity()