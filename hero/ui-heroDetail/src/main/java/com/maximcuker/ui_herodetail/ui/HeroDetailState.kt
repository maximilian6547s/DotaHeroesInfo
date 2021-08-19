package com.maximcuker.ui_herodetail.ui

import com.maximcuker.core.ProgressBarState
import com.maximcuker.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
)