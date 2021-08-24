package com.maximcuker.hero_interactors

import com.maximcuker.core.domain.DataState
import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.hero_datasource_test.cache.HeroCacheFake
import com.maximcuker.hero_datasource_test.cache.HeroDatabaseFake
import com.maximcuker.hero_datasource_test.network.HeroServiceFake
import com.maximcuker.hero_datasource_test.network.HeroServiceResponseType
import com.maximcuker.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.maximcuker.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHeroesTest {

    // system in test
    private lateinit var getHeroes: GetHeroes

    @Test
    fun getHeros_success() =  runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.GoodData // good data
        )

        getHeroes = GetHeroes(
            cache = heroCache,
            service = heroService
        )

        // Confirm the cache is empty before any use-cases have been executed
        var cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.isEmpty())

        // Execute the use-case
        val emissions = getHeroes.execute().toList() //get all emissions from the flow and put it on the list

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // Confirm second emission is data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size?: 0 == NUM_HEROES)

        // Confirm the cache is no longer empty
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.size == NUM_HEROES)

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }


}