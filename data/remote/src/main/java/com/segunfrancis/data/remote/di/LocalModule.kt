package com.segunfrancis.data.remote.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.segunfrancis.data.remote.db.LawRightsDatabase
import com.segunfrancis.data.remote.util.RemoteConstants
import com.segunfrancis.data.remote.util.RemoteConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(RemoteConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LawRightsDatabase {
        return Room.databaseBuilder(
            context,
            LawRightsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
