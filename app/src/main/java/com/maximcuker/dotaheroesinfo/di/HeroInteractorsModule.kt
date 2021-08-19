package com.maximcuker.dotaheroesinfo.di

import android.app.Application
import com.maximcuker.hero_interactors.HeroInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroInteractorsModule {

    @Provides
    @Singleton
    @Named("heroAndroidSqlDriver") //in case you had another SQL Delight bd
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = HeroInteractors.schema,
            context = app,
            name = HeroInteractors.dbName,
        )
    }

    @Provides
    @Singleton
    fun provideHeroInteractors(
        @Named("heroAndroidSqlDriver") sqlDriver: SqlDriver
    ): HeroInteractors {
        return HeroInteractors.build(sqlDriver = sqlDriver)
    }
}