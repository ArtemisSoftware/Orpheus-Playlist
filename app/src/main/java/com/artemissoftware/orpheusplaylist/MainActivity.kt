package com.artemissoftware.orpheusplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.orpheusplaylist.playaudio.presentation.AudioPlaylistScreen
import com.artemissoftware.orpheusplaylist.player.PlayerScreen
import com.artemissoftware.orpheusplaylist.ui.theme.OrpheusPlaylistTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrpheusPlaylistTheme {
                // --RequestPermission(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
//                val permissionState = rememberPermissionState(
//                    permission = Manifest.permission.READ_EXTERNAL_STORAGE,
//                )
//
//                val lifecycleOwner = LocalLifecycleOwner.current
//
//                DisposableEffect(key1 = lifecycleOwner) {
//                    val observer = LifecycleEventObserver { _, event ->
//                        if (event == Lifecycle.Event.ON_RESUME) {
//                            permissionState.launchPermissionRequest()
//                        }
//                    }
//                    lifecycleOwner.lifecycle.addObserver(observer)
//
//                    onDispose {
//                        lifecycleOwner.lifecycle.removeObserver(observer)
//                    }
//                }
//
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                     AudioPlaylistScreen()
                    //PlayerScreen()
                    //Greeting("Android")
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
