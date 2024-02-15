package com.example.movieproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieproject.presentation.theme.MovieProjectTheme
import com.example.movieproject.presentation.ui.navigation.MovieApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieProjectTheme {

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