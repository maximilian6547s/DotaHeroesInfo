package com.maximcuker.hero_interactors

import com.maximcuker.core.*
import com.maximcuker.hero_datasource.network.HeroService
import com.maximcuker.hero_domain.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val service:HeroService,
    //TODO (Add caching)
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))

            //for testing progressBar
            delay(1000)
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

            //TODO(Caching)

            emit(DataState.Data(heroes))

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