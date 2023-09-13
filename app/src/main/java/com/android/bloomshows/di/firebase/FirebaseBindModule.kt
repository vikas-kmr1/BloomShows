package com.android.bloomshows.di.firebase

import com.android.bloomshows.network.services.auth.AccountService
import com.android.bloomshows.network.services.auth.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseBindModule {

    @Binds
    abstract fun bindAccountService(impl: AccountServiceImpl): AccountService


}