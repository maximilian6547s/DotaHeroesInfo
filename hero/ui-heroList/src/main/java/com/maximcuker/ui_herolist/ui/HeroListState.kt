package com.maximcuker.ui_herolist.ui

import com.maximcuker.core.ProgressBarState
import com.maximcuker.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf(),
    val filteredHeros: List<Hero> = listOf(),
    val heroName: String = "",
)