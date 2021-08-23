package com.maximcuker.hero_interactors

import com.maximcuker.core.domain.DataState
import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.core.domain.UIComponent
import com.maximcuker.hero_datasource.cache.HeroCache
import com.maximcuker.hero_datasource.network.HeroService
import com.maximcuker.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val service:HeroService,
    private val cache:HeroCache,
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))

            val heroes: List<Hero> = try {
                service.getHeroStats()
            } catch (e:Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message?: "Unknown Error"
                        )
                    )
                )
                listOf()
            }

            //cache the network data
            cache.insert(heroes)

            //emit data from cache
            val cachedHeroes = cache.selectAll()

            emit(DataState.Data(cachedHeroes))
            throw Exception("something went wrong")

        } catch (e:Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message?: "Unknown Error"
                    )
                )
            )
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}