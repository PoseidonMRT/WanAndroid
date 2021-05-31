package com.poseidon.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.poseidon.wanandroid.ui.theme.WanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun startFlutterActivity(){
        var intent = FlutterActivity.createDefaultIntent(this@MainActivity)
        startActivity(intent)
    }

    @Composable
    fun Greeting(name: String) {
        val modifier = Modifier.clickable (enabled=true, onClick = {
            viewModel.login()
            startFlutterActivity()
        } )
        Text(text = "Hello Flutter Activity",modifier = modifier)
    }
}