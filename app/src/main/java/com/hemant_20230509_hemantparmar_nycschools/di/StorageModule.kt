package com.hemant_20230509_hemantparmar_nycschools.di


import com.hemant_20230509_hemantparmar_nycschools.data.storage.SharedPreferencesStorage
import com.hemant_20230509_hemantparmar_nycschools.data.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(
        storage: SharedPreferencesStorage
    ): Storage

}
