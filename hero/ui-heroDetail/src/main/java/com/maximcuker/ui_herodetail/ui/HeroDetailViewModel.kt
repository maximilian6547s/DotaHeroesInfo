package com.maximcuker.ui_herodetail.ui

import androidx.lifecycle.ViewModel
import com.maximcuker.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject
constructor(
    private val getHeroFromCache: GetHeroFromCache
): ViewModel(){


}