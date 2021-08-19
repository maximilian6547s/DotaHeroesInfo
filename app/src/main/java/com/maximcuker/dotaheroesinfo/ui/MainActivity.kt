package com.maximcuker.dotaheroesinfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.maximcuker.core.DataState
import com.maximcuker.core.Logger
import com.maximcuker.core.UIComponent
import com.maximcuker.dotaheroesinfo.R
import com.maximcuker.hero_interactors.HeroInteractors
import com.maximcuker.ui_herolist.HeroList
import com.maximcuker.ui_herolist.ui.HeroListState
import com.maximcuker.ui_herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .build()

        setContent {
            DotaInfoTheme {
                val viewModel: HeroListViewModel = hiltViewModel()
                HeroList(
                    imageLoader = imageLoader,
                    state = viewModel.state.value,
                )
            }
        }
    }
}