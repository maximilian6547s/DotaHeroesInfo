package com.maximcuker.ui_herolist.ui

sealed class HeroListEvents {

    object GetHeros : HeroListEvents()

    object FilterHeroes: HeroListEvents()

    data class UpdateHeroName(
        val heroName: String,
    ): HeroListEvents()
}