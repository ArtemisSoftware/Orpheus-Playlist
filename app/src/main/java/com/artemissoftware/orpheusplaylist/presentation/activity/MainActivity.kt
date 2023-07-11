package com.artemissoftware.orpheusplaylist.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlayListScreen
import com.artemissoftware.orpheusplaylist.ui.theme.OrpheusPlaylistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isLoading = mainViewModel.state.collectAsState().value.isLoading

//            installSplashScreen().apply {
//                setKeepOnScreenCondition { isLoading }
//            }

            OrpheusPlaylistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    PlayListScreen(
                        state = mainViewModel.state.collectAsState().value,
                        event = mainViewModel::onEvent,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OrpheusPlaylistTheme {
        Greeting("Android")
    }
}
