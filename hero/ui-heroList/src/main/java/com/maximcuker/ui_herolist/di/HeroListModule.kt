package com.maximcuker.ui_herolist.di

import com.maximcuker.core.Logger
import com.maximcuker.hero_interactors.GetHeroes
import com.maximcuker.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger():Logger {
        return Logger(
            tag = "HeroList",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeroes(
        heroInteractors: HeroInteractors
    ): GetHeroes {
        return heroInteractors.getHeros
    }

}