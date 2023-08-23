package com.artemissoftware.orpheusplaylist.data.media.service

sealed class MediaResource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T? = null) : MediaResource<T>(data, null)
    class Error<T>(message: String, data: T? = null) : MediaResource<T>(data, message)

    class Idle<T>() : MediaResource<T>()
}
