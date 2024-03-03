package com.andreikslpv.common

/**
 * Any in-app managed exceptions.
 */
open class AppException(
    message: String = "",
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Unknown exception
 */
class UnknownException : AppException()

/**
 * Throw when an unauthorized user tries to perform an action
 */
class NotAuthorizedException : AppException()
