package com.madarsoft.features.users.data.db.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.madarsoft.features.users.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users_table")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: String,
    val job: String,
    val gender: UserEntity.Gender
)

@Dao
interface UsersDao {
    @Insert(entity = UserModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM users_table ORDER BY name ASC")
    fun getAll(): Flow<List<UserModel>>
}

fun UserModel.toEntity() = UserEntity(
    id = id,
    name = name,
    age = age,
    job = job,
    gender = gender
)

fun UserEntity.toModel() = UserModel(
    name = name,
    age = age,
    job = job,
    gender = gender
)
