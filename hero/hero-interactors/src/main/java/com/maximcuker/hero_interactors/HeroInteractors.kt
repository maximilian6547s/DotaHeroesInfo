package com.maximcuker.hero_interactors

import com.maximcuker.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeros: GetHeroes,
    // TODO(Add other hero interactors)
) {
    companion object Factory {
        fun build(): HeroInteractors{
            val service = HeroService.build()
            return HeroInteractors(
                getHeros = GetHeroes(
                    service = service,
                ),
            )
        }
    }
}