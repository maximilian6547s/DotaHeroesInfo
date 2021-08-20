package com.maximcuker.hero_domain

import com.maximcuker.core.domain.FilterOrder

sealed class HeroFilter(val uiValue: String,) {

    data class Hero(
        val order: FilterOrder = FilterOrder.Ascending
    ): HeroFilter("Hero")

    data class ProWins(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Pro win-rate")

}