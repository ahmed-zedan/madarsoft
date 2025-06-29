package com.madarsoft.features.users.domain

import com.madarsoft.features.users.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddUserUseCase @Inject constructor(
    private val repository: UsersRepository,
    private val validator: UserValidator
) {
    suspend operator fun invoke(user: UserEntity) {
        validator.validate(user)
        repository.addUser(user)
    }
}
