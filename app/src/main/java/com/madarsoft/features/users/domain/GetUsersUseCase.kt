package com.madarsoft.features.users.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    operator fun invoke() = repository.getUsers()
}
