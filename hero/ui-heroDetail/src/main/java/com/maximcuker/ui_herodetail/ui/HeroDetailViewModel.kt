package com.maximcuker.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximcuker.core.domain.DataState
import com.maximcuker.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject
constructor(
    private val getHeroFromCache: GetHeroFromCache,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){
    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvents.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents) {
        when (event) {
            is HeroDetailEvents.GetHeroFromCache -> {
                getHeroFromCache(event.id)
            }
        }
    }

    private fun getHeroFromCache(id: Int){
        getHeroFromCache.execute(id).onEach { dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {
                    // TODO(Handle errors)
                }
            }
        }.launchIn(viewModelScope)
    }
}