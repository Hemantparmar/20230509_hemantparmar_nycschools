package com.hemant_20230509_hemantparmar_nycschools.di

import android.content.Context
import com.hemant_20230509_hemantparmar_nycschools.data.room.Repository
import com.hemant_20230509_hemantparmar_nycschools.data.room.SchoolDatabase
import com.hemant_20230509_hemantparmar_nycschools.network.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModuleDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SchoolDatabase =
        SchoolDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideRepository(
        apiHelper: ApiHelper,
        schoolDb: SchoolDatabase
    ): Repository = Repository(apiHelper, schoolDb)
}