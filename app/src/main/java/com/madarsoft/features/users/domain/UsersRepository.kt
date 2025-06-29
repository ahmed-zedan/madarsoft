package com.madarsoft.features.users.domain

import com.madarsoft.features.users.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun addUser(user: UserEntity)
    fun getUsers(): Flow<List<UserEntity>>
}