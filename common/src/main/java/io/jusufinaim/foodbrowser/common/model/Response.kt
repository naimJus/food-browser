package io.jusufinaim.foodbrowser.common.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * [Response] is a sealed class representing the result of an operation that can either succeed or fail.
 * It is designed to handle both success and failure scenarios in a type-safe manner.
 *
 * @param S The type of data in case of success.
 * @param E The type of error in case of failure, extending [Throwable].
 */
sealed class Response<out S, out E : Throwable> {
    /**
     * [Success] is a data class representing the successful result of an operation.
     *
     * @param data The data resulting from a successful operation.
     */
    data class Success<S>(val data: S) : Response<S, Nothing>()

    /**
     * [Failure] is a data class representing the failure result of an operation.
     *
     * @param error The error representing the cause of the failure, extending [Throwable].
     */
    data class Failure<E : Throwable>(val error: E) : Response<Nothing, E>()

    @OptIn(ExperimentalContracts::class)
    fun isSuccess(): Boolean {
        contract {
            returns(true) implies (this@Response is Success<S>)
            returns(false) implies (this@Response is Failure<E>)
        }
        return this@Response is Success<S>
    }

    @OptIn(ExperimentalContracts::class)
    fun isFailure(): Boolean {
        contract {
            returns(false) implies (this@Response is Success<S>)
            returns(true) implies (this@Response is Failure<E>)
        }
        return this@Response is Success<S>
    }

    @OptIn(ExperimentalContracts::class)
    inline fun isSuccess(predicate: (S) -> Boolean): Boolean {
        contract { returns(true) implies (this@Response is Success<S>) }
        return this@Response is Success<S> && predicate(data)
    }

    @OptIn(ExperimentalContracts::class)
    inline fun isError(predicate: (E) -> Boolean): Boolean {
        contract { returns(true) implies (this@Response is Failure<E>) }
        return this@Response is Failure<E> && predicate(error)
    }

    @OptIn(ExperimentalContracts::class)
    fun getOrNull(): S? {
        contract {
            returns(null) implies (this@Response is Failure<E>)
            returnsNotNull() implies (this@Response is Success<S>)
        }
        return fold({ success -> success }, { _ -> null })
    }

    /**
     * Transform an [Response] into a value of [C].
     * Alternative to using `when` to fold an [Response] into a value [C].
     */
    @OptIn(ExperimentalContracts::class)
    inline fun <C> fold(ifSuccess: (success: S) -> C, ifFailure: (failure: E) -> C): C {
        contract {
            callsInPlace(ifSuccess, InvocationKind.AT_MOST_ONCE)
            callsInPlace(ifFailure, InvocationKind.AT_MOST_ONCE)
        }
        return when (this) {
            is Failure -> ifFailure(error)
            is Success -> ifSuccess(data)
        }
    }
}

