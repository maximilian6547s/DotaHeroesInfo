package com.maximcuker.hero_interactors

import com.maximcuker.core.domain.DataState
import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.core.domain.UIComponent
import com.maximcuker.hero_datasource_test.cache.HeroCacheFake
import com.maximcuker.hero_datasource_test.cache.HeroDatabaseFake
import com.maximcuker.hero_datasource_test.network.HeroServiceFake
import com.maximcuker.hero_datasource_test.network.HeroServiceResponseType
import com.maximcuker.hero_datasource_test.network.data.HeroDataValid
import com.maximcuker.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.maximcuker.hero_datasource_test.network.serializeHeroData
import com.maximcuker.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHeroesTest {

    // system in test
    private lateinit var getHeroes: GetHeroes

    @Test
    fun getHeroes_success() =  runBlocking {
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

    /**
     * 1. Insert some data into the cache by executing a successful use-case.
     * 2. Configure the network operation to return malformed data.
     * 3. Execute use-case for a second time and confirm it still emits the cached data.
     */
    @Test
    fun getHeroes_malformedData_successFromCache() =  runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.MalformedData // Malformed Data
        )

        getHeroes = GetHeroes(
            cache = heroCache,
            service = heroService
        )

        // Confirm the cache is empty before any use-cases have been executed
        var cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.isEmpty())

        // Add some data to the cache by executing a successful request
        val heroData = serializeHeroData(HeroDataValid.data)
        heroCache.insert(heroData)

        // Confirm the cache is not empty anymore
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.size == 121)

        // Execute the use-case
        val emissions = getHeroes.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // Confirm second emission is error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))

        // Confirm third emission is data from the cache
        assert(emissions[2] is DataState.Data)
        assert((emissions[2] as DataState.Data).data?.size == 121)

        // Confirm the cache is still not empty
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.size == 121)

        // Confirm loading state is IDLE
        assert(emissions[3] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }

    @Test
    fun getHeroes_emptyList() =  runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.EmptyList // Empty List
        )

        getHeroes = GetHeroes(
            cache = heroCache,
            service = heroService
        )

        // Confirm the cache is empty before any use-cases have been executed
        var cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.isEmpty())

        // Execute the use-case
        val emissions = getHeroes.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // Confirm second emission is data (empty list)
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size?: 0 == 0)

        // Confirm the cache is STILL EMPTY
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.isEmpty())

        // Confirm loading state is IDLE
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }

}