package com.madarsoft.core

import com.madarsoft.R

class ValidationException(val code: List<Int>): Throwable("Code: $code") {
    companion object {
        const val INVALID_NAME = 1001
        const val INVALID_AGE = 1002
        const val INVALID_JOB = 1003

        fun map(code: Int): TextResource {
            return when (code) {
                INVALID_NAME -> TextResource.fromResource(R.string.invalid_name)
                INVALID_JOB -> TextResource.fromResource(R.string.invalid_job)
                INVALID_AGE -> TextResource.fromResource(R.string.invalid_age)
                else -> TextResource.fromResource(R.string.unknown_error)
            }
        }
    }
}
