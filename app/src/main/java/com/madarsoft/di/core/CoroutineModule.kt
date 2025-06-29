package com.madarsoft.di.core

import com.madarsoft.core.CoroutinePorts
import com.madarsoft.core.Ports
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModuleProvider {

    @Provides
    @Singleton
    fun providePorts(): Ports {
        return CoroutinePorts()
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(ports: Ports) = CoroutineScope(SupervisorJob() + ports.main())
}
