package com.maximcuker.ui_herodetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.maximcuker.ui_herodetail.ui.HeroDetailState

@Composable
fun HeroDetail(
    state: HeroDetailState,
){
    Text(state.hero?.localizedName?: "LOADING")
}