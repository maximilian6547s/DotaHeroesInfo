package com.maximcuker.ui_herodetail.di

import com.maximcuker.hero_interactors.GetHeroFromCache
import com.maximcuker.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    /**
     * @param interactors is provided in app module.
     */
    @Provides
    @Singleton
    fun provideGetHeroFromCache(
        interactors: HeroInteractors
    ): GetHeroFromCache {
        return interactors.getHeroFromCache
    }
}