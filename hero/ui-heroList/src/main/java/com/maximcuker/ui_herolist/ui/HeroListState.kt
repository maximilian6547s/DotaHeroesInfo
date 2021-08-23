package com.maximcuker.ui_herolist.ui

import com.maximcuker.core.domain.ProgressBarState
import com.maximcuker.core.domain.Queue
import com.maximcuker.core.domain.UIComponent
import com.maximcuker.core.domain.UIComponentState
import com.maximcuker.hero_domain.Hero
import com.maximcuker.hero_domain.HeroAttribute
import com.maximcuker.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf(),
    val filteredHeroes: List<Hero> = listOf(),
    val heroName: String = "",
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown,
    val filterDialogState: UIComponentState = UIComponentState.Hide,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)