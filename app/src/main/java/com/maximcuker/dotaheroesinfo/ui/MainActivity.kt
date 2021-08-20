package com.maximcuker.dotaheroesinfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.codingwithmitch.ui_herolist.HeroList

import com.maximcuker.dotaheroesinfo.ui.navigation.Screen
import com.maximcuker.ui_herodetail.HeroDetail
import com.maximcuker.ui_herodetail.ui.HeroDetailViewModel
import com.maximcuker.ui_herolist.ui.HeroListViewModel

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroList(
                            navController, imageLoader
                        )
                        addHeroDetail(imageLoader)
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroList.route
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(
            imageLoader = imageLoader,
            state = viewModel.state.value,
            navigateToDetailScreen = { heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetail(
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments,
    ) {
        val viewModel: HeroDetailViewModel = hiltViewModel()
        HeroDetail(
            state = viewModel.state.value,
            imageLoader = imageLoader
        )
    }
}