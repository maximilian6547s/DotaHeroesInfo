package com.maximcuker.hero_interactors

import com.maximcuker.core.domain.FilterOrder
import com.maximcuker.hero_datasource_test.network.data.HeroDataValid
import com.maximcuker.hero_datasource_test.network.serializeHeroData
import com.maximcuker.hero_domain.HeroAttribute
import com.maximcuker.hero_domain.HeroFilter
import org.junit.Test
import kotlin.math.round

/**
 * 1. Success (Search for specific hero by 'localizedName' param)
 * 2. Success (Order by 'localizedName' param DESC)
 * 3. Success (Order by 'localizedName' param ASC)
 * 4. Success (Order by 'proWins' % ASC)
 * 5. Success (Order by 'proWins' % DESC)
 * 6. Success (Filter by 'HeroAttribute' "Strength")
 * 7. Success (Filter by 'HeroAttribute' "Agility")
 * 8. Success (Filter by 'HeroAttribute' "Intelligence")
 */
class FilterHeroesTest {

    // System in test
    private lateinit var filterHeroes: FilterHeroes

    // Data
    private val heroList = serializeHeroData(HeroDataValid.data)

    @Test
    fun searchHeroByName(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "Slark",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm it returns a single hero
        assert(emissions[0].localizedName == "Slark")
    }

    @Test
    fun orderByNameDesc(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(order = FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered Z-A
        for(index in 1..emissions.size - 1){
            assert(emissions[index-1].localizedName.toCharArray()[0] >= emissions[index].localizedName.toCharArray()[0])
        }
    }

    @Test
    fun orderByNameAsc(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(order = FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered A-Z
        for(index in 1..emissions.size - 1){
            assert(emissions[index-1].localizedName.toCharArray()[0] <= emissions[index].localizedName.toCharArray()[0])
        }
    }

    @Test
    fun orderByProWinsDesc(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.ProWins(order = FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered highest to lowest
        for(index in 1..emissions.size - 1){
            val prevWinPercentage = round(emissions[index-1].proWins.toDouble() / emissions[index-1].proPick.toDouble() * 100).toInt()
            val currWinPercentage = round(emissions[index].proWins.toDouble() / emissions[index].proPick.toDouble() * 100).toInt()

            assert(prevWinPercentage >= currWinPercentage)
        }
    }

    @Test
    fun orderByProWinsAsc(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.ProWins(order = FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered lowest to highest
        for(index in 1..emissions.size - 1){
            val prevWinPercentage = round(emissions[index-1].proWins.toDouble() / emissions[index-1].proPick.toDouble() * 100).toInt()
            val currWinPercentage = round(emissions[index].proWins.toDouble() / emissions[index].proPick.toDouble() * 100).toInt()

            assert(prevWinPercentage <= currWinPercentage)
        }
    }

    @Test
    fun filterByStrength(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Strength,
        )

        for(hero in emissions){
            assert(hero.primaryAttribute is HeroAttribute.Strength)
        }
    }

    @Test
    fun filterByAgility(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Agility,
        )

        for(hero in emissions){
            assert(hero.primaryAttribute is HeroAttribute.Agility)
        }
    }

    @Test
    fun filterByIntelligence(){
        filterHeroes = FilterHeroes()

        // Execute use-case
        val emissions = filterHeroes.execute(
            current = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Intelligence,
        )

        for(hero in emissions){
            assert(hero.primaryAttribute is HeroAttribute.Intelligence)
        }
    }
}
