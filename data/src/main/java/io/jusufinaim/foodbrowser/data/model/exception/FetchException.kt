package io.jusufinaim.foodbrowser.data.model.exception

import io.jusufinaim.foodbrowser.common.util.isCausedBy

/**
 * [FetchException] is a sealed class representing exceptions that can occur during the fetching of data.
 *
 * @param message A descriptive message for the exception.
 */
sealed class FetchException(message: String) : Exception(message) {
    /**
     * [NetworkException] is a singleton object representing an exception when the device is not connected to the internet.
     */
    data object NetworkException : FetchException("Device is not connected to the internet")

    /**
     * [FetchItemsException] is a data class representing an exception when there is an issue fetching items with a custom message.
     *
     * @param message A descriptive message for the exception.
     */
    data class FetchItemsException(override val message: String) : FetchException(message)

    /**
     * [UnknownException] is a data class representing an unknown exception with a default or custom message.
     *
     * @param message A descriptive message for the exception (default is "Something went wrong").
     */
    data class UnknownException(override val message: String = "Something went wrong") :
        FetchException(message)

    /**
     * [NoItemsFoundException] is a data class representing an exception when no items are found with a custom message.
     *
     * @param message A descriptive message for the exception.
     */
    data class NoItemsFoundException(override val message: String) : FetchException(message)

    fun Throwable.isFetchException() = isCausedBy<FetchException>()

}