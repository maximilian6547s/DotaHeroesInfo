package com.maximcuker.hero_datasource_test.network

import com.maximcuker.hero_datasource.network.model.HeroDto
import com.maximcuker.hero_datasource.network.model.toHero
import com.maximcuker.hero_domain.Hero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeHeroData(jsonData: String): List<Hero> {
    return json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
}