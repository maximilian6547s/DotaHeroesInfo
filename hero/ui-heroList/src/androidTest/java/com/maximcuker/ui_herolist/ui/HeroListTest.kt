package com.maximcuker.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.codingwithmitch.ui_herolist.HeroList
import com.maximcuker.hero_datasource_test.network.data.HeroDataValid
import com.maximcuker.hero_datasource_test.network.serializeHeroData
import com.maximcuker.ui_herolist.coil.FakeImageLoader
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
class HeroListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader:ImageLoader = FakeImageLoader.build(context)
    private val heroData = serializeHeroData(HeroDataValid.data)

    @ExperimentalAnimationApi
    @Test
    fun areHeroesShown() {
        composeTestRule.setContent {
            val state = remember {
                HeroListState(
                    heroes = heroData,
                    filteredHeroes = heroData
                )
            }
            HeroList(
                state = state,
                events = {},
                imageLoader = imageLoader,
                navigateToDetailScreen ={}
            )
        }

        composeTestRule.onNodeWithText("Anti-Mage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Axe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bane").assertIsDisplayed()
    }
}