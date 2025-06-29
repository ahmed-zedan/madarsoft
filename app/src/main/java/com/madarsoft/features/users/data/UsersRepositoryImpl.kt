package com.madarsoft.features.users.data

import com.madarsoft.features.users.data.db.tables.UsersDao
import com.madarsoft.features.users.data.db.tables.toEntity
import com.madarsoft.features.users.data.db.tables.toModel
import com.madarsoft.features.users.domain.UsersRepository
import com.madarsoft.features.users.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
) : UsersRepository {

    override suspend fun addUser(user: UserEntity) {
        usersDao.insert(user.toModel())
    }

    override fun getUsers(): Flow<List<UserEntity>> {
        return usersDao.getAll().map { users -> users.map { it.toEntity() } }
    }
}
