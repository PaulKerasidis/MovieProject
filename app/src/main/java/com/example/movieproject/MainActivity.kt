package com.example.movieproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.movieproject.ui.screens.composables.BottomBar
import com.example.movieproject.ui.screens.composables.Genres
import com.example.movieproject.ui.screens.composables.ImageSlider
import com.example.movieproject.ui.theme.MovieProjectTheme
import com.example.movieproject.ui.screens.composables.NewReleases
import com.example.movieproject.ui.screens.composables.TopBar
import com.example.movieproject.ui.screens.navigation.MovieApp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieProjectTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color(39, 51, 67)
                    )
                }
                MovieApp()


            }
        }
    }
}





@Preview
@Composable
fun AppPreview() {

        MovieApp()

}