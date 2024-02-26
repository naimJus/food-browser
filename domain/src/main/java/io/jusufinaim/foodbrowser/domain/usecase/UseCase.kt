package io.jusufinaim.foodbrowser.domain.usecase

/**
 * [UseCase] is an interface defining a use case in the application, where an operation is performed with input [A]
 * to produce a result of type [R].
 *
 * @param A The type of the input argument.
 * @param R The type of the result.
 */
interface UseCase<A, R> {

    /**
     * Executes the use case with the provided argument [arg].
     *
     * @param arg The input argument for the use case.
     * @return The result of the use case operation.
     */
    suspend operator fun invoke(arg: A): R
}