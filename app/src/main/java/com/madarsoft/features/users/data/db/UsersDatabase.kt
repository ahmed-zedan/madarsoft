package com.madarsoft.features.users.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.madarsoft.features.users.data.db.converters.GenderConverter
import com.madarsoft.features.users.data.db.tables.UsersDao
import com.madarsoft.features.users.data.db.tables.UserModel

@Database(entities = [UserModel::class], version = 1)
@TypeConverters(GenderConverter::class)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}
