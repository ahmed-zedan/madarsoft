package com.madarsoft.di.features

import android.content.Context
import androidx.room.Room
import com.madarsoft.features.users.data.UsersRepositoryImpl
import com.madarsoft.features.users.data.db.UsersDatabase
import com.madarsoft.features.users.data.db.tables.UsersDao
import com.madarsoft.features.users.domain.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UsersModuleBinder {
    @Binds
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
}

@Module
@InstallIn(SingletonComponent::class)
object UsersModuleProvider {

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(db: UsersDatabase): UsersDao = db.usersDao()
}
