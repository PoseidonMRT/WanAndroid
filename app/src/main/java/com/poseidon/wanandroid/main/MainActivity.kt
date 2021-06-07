package com.poseidon.wanandroid.main

import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.poseidon.lib.common.base.BaseActivity
import com.poseidon.wanandroid.theme.WanAndroidTheme
import com.zj.banner.BannerPager
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var state: State<MainViewState?>

    override fun initView() {
        setContent {
            WanAndroidTheme {
                // A surface container using the 'background' color from the theme
                state = viewModel.viewState.observeAsState(viewModel.getViewState())
                Surface(color = MaterialTheme.colors.background) {
                    buildPage(state)
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.viewState.observe(this,
            { t ->
                if (t != null) {

                }
            })
    }

    override fun initData() {
        viewModel.loadData()
    }

    private fun startFlutterActivity() {
        var intent = FlutterActivity.createDefaultIntent(this@MainActivity)
        startActivity(intent)
    }

    @Composable
    fun buildPage(state: State<MainViewState?>) {
        Column() {
            showBanners(state = state)
        }
    }

    @Composable
    fun showBanners(state: State<MainViewState?>) {
        var banners = state.value!!.banners
        if (!banners.isNullOrEmpty()) {
            BannerPager(items = banners) {

            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        val modifier = Modifier.clickable(enabled = true, onClick = {
            startFlutterActivity()
        })
        Text(text = "Hello Flutter Activity", modifier = modifier)
    }
}
