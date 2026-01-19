package com.ashutosh.musicsync.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.ashutosh.musicsync.data.local.AppDatabase
import com.ashutosh.musicsync.data.remote.ApiService
import com.ashutosh.musicsync.data.repository.GetSongDetailRepositoryImpl
import com.ashutosh.musicsync.domain.repository.GetSongDetailRepository
import com.ashutosh.musicsync.domain.repository.SearchRepository
import com.example.saavn.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}
@Module
@InstallIn(SingletonComponent::class)
abstract class GetSongDetialRepositoryModule {
    @Binds
    abstract fun bindgetSongDetailRepostiory(impl: GetSongDetailRepositoryImpl): GetSongDetailRepository
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.jiosaavn.com/")
            .addConverterFactory(ScalarsConverterFactory.create()) // For String responses
            .addConverterFactory(GsonConverterFactory.create(gson)) // For JSON object responses
            .build()

    @Provides @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }
}
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideSongDao(db: AppDatabase) = db.songDao()
}
