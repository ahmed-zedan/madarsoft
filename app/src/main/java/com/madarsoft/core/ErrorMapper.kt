package com.madarsoft.core

import com.madarsoft.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMapper @Inject constructor() {
    fun map(t: Throwable): TextResource {
        val text = when (t) {
            is ValidationException -> TextResource.composite(
                separator = "\n",
                texts = t.code.map { ValidationException.map(it) }
            )

            else -> null
        }

        return text ?: TextResource.fromResource(R.string.unknown_error)
    }
}
