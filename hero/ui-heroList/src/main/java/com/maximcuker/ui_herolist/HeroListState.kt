package com.maximcuker.ui_herolist

import com.maximcuker.core.ProgressBarState
import com.maximcuker.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
)