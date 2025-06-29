package com.madarsoft.features.users.domain

import com.madarsoft.core.ValidationException
import com.madarsoft.features.users.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserValidator @Inject constructor() {
    fun validate(user: UserEntity): Boolean {
        val codes = mutableListOf<Int>()

        if (!isNameValid(user.name)) codes.add(ValidationException.INVALID_NAME)

        if (!isAgeValid(user.age)) codes.add(ValidationException.INVALID_AGE)

        if (!isJobValid(user.job)) codes.add(ValidationException.INVALID_JOB)

        if (codes.isNotEmpty()) {
            throw ValidationException(codes)
        }

        return true
    }

    private fun isNameValid(name: String): Boolean {
        return name.length in 2..50
    }

    private fun isAgeValid(age: String): Boolean {
        return (age.toFloatOrNull() ?: return false) in 2f..100f
    }

    private fun isJobValid(job: String): Boolean {
        return job.length in 2..50 && !job.all { it.isDigit() }
    }
}
