package com.artemissoftware.orpheusplaylist

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.orpheusplaylist.navigation.RootNavigationGraph
import com.artemissoftware.orpheusplaylist.ui.theme.OrpheusPlaylistTheme
import com.artemissoftware.orpheusplaylist.utils.extensions.isPermissionGranted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionsToRequest = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            val permissionGranted = remember {
                mutableStateOf(
                    context.isPermissionGranted(permissionsToRequest),
                )
            }

            val permissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { perms ->

                    var isGranted = true

                    permissionsToRequest.forEach { permission ->
                        isGranted = isGranted && (perms[permission] == true)
                    }

                    permissionGranted.value = isGranted
                },
            )

            OrpheusPlaylistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    if (permissionGranted.value) {
                        val navController = rememberNavController()
                        RootNavigationGraph(navController = navController)
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(id = R.string.grant_permission_first))
                                Button(
                                    onClick = {
                                        permissionResultLauncher.launch(permissionsToRequest)
                                    },
                                    content = {
                                        Text(text = stringResource(id = R.string.request_permissions))
                                    },
                                )
                            }
                        }
                        LaunchedEffect(Unit) {
                            permissionResultLauncher.launch(permissionsToRequest)
                        }
                    }
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
