package com.artemissoftware.orpheusplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.orpheusplaylist.navigation.RootNavigationGraph
import com.artemissoftware.orpheusplaylist.ui.theme.OrpheusPlaylistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            OrpheusPlaylistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
//                    PlayListScreen(
//                        state = mainViewModel.state.collectAsState().value,
//                        event = mainViewModel::onEvent,
//                        visualizerData = mainViewModel.visualizerData,
//                        requestPermissionLauncher = requestPermissionLauncher,
//                    )

                    // AlbumScreen()
                    // PlaylistScreen()
                    // PlayerPage()

                    val navController = rememberNavController()
                    RootNavigationGraph(navController = navController)
                    //NavigationGraph(navController = navController)
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
