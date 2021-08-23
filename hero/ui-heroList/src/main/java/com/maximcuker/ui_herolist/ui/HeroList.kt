package com.codingwithmitch.ui_herolist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.core.domain.UIComponentState
import com.maximcuker.ui_herolist.HeroListItem
import com.maximcuker.ui_herolist.components.HeroListFilter
import com.maximcuker.ui_herolist.components.HeroListToolbar
import com.maximcuker.ui_herolist.ui.HeroListEvents
import com.maximcuker.ui_herolist.ui.HeroListState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            val name = remember { mutableStateOf("") }
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeroes)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.filteredHeroes) { hero ->
                    HeroListItem(
                        hero = hero,
                        onSelectHero = { heroId ->
                            navigateToDetailScreen(heroId)
                        },
                        imageLoader = imageLoader,
                    )
                }
            }
        }

        if (state.filterDialogState is UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                onUpdateHeroFilter = {
                    events(HeroListEvents.UpdateHeroFilter(it))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                },
                attributeFilter = state.primaryAttribute,
                onUpdateAttributeFilter = { heroAttr ->
                    events(HeroListEvents.UpdateAttributeFilter(heroAttr))

                }
                )
        }

        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}












