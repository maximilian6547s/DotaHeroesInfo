package com.maximcuker.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximcuker.core.domain.DataState
import com.maximcuker.core.domain.UIComponent
import com.maximcuker.core.util.Logger
import com.maximcuker.hero_interactors.FilterHeroes
import com.maximcuker.hero_interactors.GetHeroes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel
@Inject
constructor(
    private val getHeroes: GetHeroes,
    private val filterHeroes: FilterHeroes,
    private @Named("heroListLogger")val logger: Logger
    ) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeros)
    }

    fun onTriggerEvent(event: HeroListEvents) {
        when(event) {
            is HeroListEvents.GetHeros -> {
                getHeroes()
            }
            is HeroListEvents.FilterHeroes -> {
                filterHeroes()
            }
            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }
        }
    }

    private fun updateHeroName(heroName:String) {
        state.value = state.value.copy(heroName = heroName)
    }

    private fun filterHeroes() {
        val filteredList = filterHeroes.execute(
            current = state.value.heroes,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            attributeFilter = state.value.primaryAttribute
        )

        state.value = state.value.copy(filteredHeroes = filteredList)
    }

    private fun getHeroes() {
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data ?: listOf())
                    filterHeroes()
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }
}
