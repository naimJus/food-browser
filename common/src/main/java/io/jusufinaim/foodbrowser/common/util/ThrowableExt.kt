package io.jusufinaim.foodbrowser.common.util

import kotlin.coroutines.cancellation.CancellationException

val Throwable.root: Throwable
    get() {
        var cause = this.cause
        var current = this

        while (cause != current && cause != null) {
            current = cause
            cause = cause.cause
        }

        return current
    }

inline fun <reified T : Throwable> Throwable.findCause(): T? {
    if (this is T) return this

    var cause = this.cause
    var current = this

    while (cause != current && cause != null) {
        current = cause
        cause = cause.cause

        if (current is T) return current
    }

    return null
}

inline fun <reified T : Throwable> Throwable.isCausedBy(): Boolean = findCause<T>() != null

/**
 * Throwable is considered unrecoverable if error handler shouldn't swallow the error.
 * For example, if flow is canceled, InterruptedException will be thrown.
 * As flow is canceled, swallowing the error and continuing the flow in that case isn't desired behavior.
 */
fun Throwable.isUnrecoverable() = isCausedBy<InterruptedException>() || isCausedBy<CancellationException>()

/**
 * Throws this throwable in case it shouldn't be caught.
 * Throwable is considered unrecoverable if error handler shouldn't swallow the error.
 * For example, if flow is canceled, InterruptedException will be thrown.
 * As flow is canceled, swallowing the error and continuing the flow in that case isn't desired behavior.
 */
fun Throwable.throwIfUnrecoverable() = if (isUnrecoverable()) throw this else DoNothing
