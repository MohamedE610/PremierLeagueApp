package com.example.premierleagueapp.core.data.source.remote.rxerrorhandling

import com.squareup.moshi.Moshi
import retrofit2.Response
import java.io.IOException

object NetworkException {
    fun httpError(response: Response<Any>?): PremierLeagueException {
        var message: String? = null
        var responseBody: String? = null
        var statusCode = 0
        var errorCode = 0
        response?.let { statusCode = it.code() }
        response?.let {
            responseBody = it.errorBody()?.string()
            try {
                val apiError = Moshi.Builder().build().adapter(ApiError::class.java)
                    .fromJson(responseBody ?: "")

                apiError?.let { error ->
                    message = error.message
                    responseBody = error.data
                    error.code?.let { code -> errorCode = code }
                }
            } catch (exception: Exception) {
            }
        }

        var kind = PremierLeagueException.Kind.HTTP
        when (statusCode) {
            500, 502 -> kind =
                PremierLeagueException.Kind.SERVER_DOWN
            408 -> kind =
                PremierLeagueException.Kind.TIME_OUT
            401 -> kind =
                PremierLeagueException.Kind.UNAUTHORIZED
            304 -> kind =
                PremierLeagueException.Kind.NOT_MODIFIED
        }

        return PremierLeagueException(
            kind,
            message?.let { message }
                ?: kotlin.run { "" })
            .setErrorCode(errorCode)
            .setStatusCode(statusCode)
            .setData(responseBody)
    }

    fun networkError(exception: IOException): PremierLeagueException {
        return PremierLeagueException(
            PremierLeagueException.Kind.NETWORK,
            exception
        )
    }

    fun unexpectedError(exception: Throwable): PremierLeagueException {
        return PremierLeagueException(
            PremierLeagueException.Kind.UNEXPECTED,
            exception
        )
    }

    fun timeoutError(throwable: Throwable): PremierLeagueException {
        return PremierLeagueException(
            PremierLeagueException.Kind.TIME_OUT,
            throwable.message,
            throwable
        )
    }

    fun serverDownError(throwable: Throwable): PremierLeagueException {
        return PremierLeagueException(
            PremierLeagueException.Kind.SERVER_DOWN,
            throwable.message,
            throwable
        )
    }


}