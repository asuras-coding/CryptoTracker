package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import com.plcoding.cryptotracker.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    return try {
        responseToResult(execute())
    } catch (e: UnresolvedAddressException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Result.Error(NetworkError.UNKNOWN)
    }
}