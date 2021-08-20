package com.maximcuker.hero_interactors

import com.maximcuker.hero_datasource.cache.HeroCache
import com.maximcuker.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeroes: GetHeroes,
    val getHeroFromCache: GetHeroFromCache,
    val filterHeroes: FilterHeroes,
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors{
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service,
                    cache = cache,
                ),
                getHeroFromCache = GetHeroFromCache(
                    cache = cache
                ),
                filterHeroes = FilterHeroes(),
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema

        val dbName: String = HeroCache.dbName
    }
}
