package com.maximcuker.ui_herodetail.ui

import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.core.domain.Queue
import com.maximcuker.core.domain.UIComponent
import com.maximcuker.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)