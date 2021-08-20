package com.maximcuker.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.ui_herolist.ui.HeroListState

@Composable
fun HeroList(
    imageLoader: ImageLoader,
    state: HeroListState,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ){
            items(state.heroes){ hero ->
                HeroListItem(
                    hero = hero,
                    onSelectHero = {},
                    imageLoader = imageLoader
                )
            }
        }
        if(state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}