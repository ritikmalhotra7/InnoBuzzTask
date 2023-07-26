package com.example.innobuzztask.di

import android.app.Application
import androidx.room.Room
import com.example.innobuzztask.data.local.AppDao
import com.example.innobuzztask.data.local.AppDatabase
import com.example.innobuzztask.data.remote.AppApi
import com.example.innobuzztask.domain.repository.AppRepository
import com.example.innobuzztask.domain.usecases.FetchPosts
import com.example.innobuzztask.utils.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(ctx: Application): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "word_db")
            .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): AppDao = db.getDao

    @Provides
    @Singleton
    fun provideApi(): AppApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchPosts(repository: AppRepository) = FetchPosts(repository)
}