package com.maximcuker.ui_herolist.ui

import com.maximcuker.core.domain.UIComponentState
import com.maximcuker.hero_domain.HeroAttribute
import com.maximcuker.hero_domain.HeroFilter

sealed class HeroListEvents {

    object GetHeros : HeroListEvents()

    object FilterHeroes: HeroListEvents()

    data class UpdateHeroName(
        val heroName: String,
    ): HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ): HeroListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ): HeroListEvents()

    data class UpdateAttributeFilter(
        val attribute: HeroAttribute
    ): HeroListEvents()
}