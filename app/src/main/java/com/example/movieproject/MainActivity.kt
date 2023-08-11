package com.example.movieproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieproject.ui.theme.ImageSlider
import com.example.movieproject.ui.theme.MovieProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(39,51,67)
                ) {

                    MovieApp()

                }
            }
        }
    }
}

@Composable
fun MovieApp() {
    Column {

        TopAppBar()

        ImageSlider()

    }
}

@Preview
@Composable
fun AppPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(39,51,67)
    ) {

        MovieApp()

    }
}