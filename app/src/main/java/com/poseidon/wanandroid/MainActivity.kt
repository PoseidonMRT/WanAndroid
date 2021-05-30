package com.poseidon.wanandroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.poseidon.lib.common.BaseActivity
import com.poseidon.wanandroid.ui.theme.WanAndroidTheme
import io.flutter.embedding.android.FlutterActivity


class MainActivity : BaseActivity() {
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
            startFlutterActivity()
        } )
        Text(text = "Hello Flutter Activity",modifier = modifier)
    }
}