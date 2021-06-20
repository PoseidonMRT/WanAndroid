package com.poseidon.wanandroid.main

import android.content.ComponentName
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poseidon.lib.common.base.BaseActivity
import com.poseidon.wanandroid.FlutterActivity
import com.poseidon.wanandroid.R
import com.poseidon.wanandroid.theme.WanAndroidTheme
import com.zj.banner.BannerPager
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity.withNewEngine

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
                    BuildPage(state)
                }
            }
        }
    }

    override fun initObservers() {
    }

    override fun initData() {
        viewModel.loadData()
    }

    private fun startFlutterActivity() {
        val intent =
            FlutterActivity.withNewEngine().initialRoute("/answer").build(this@MainActivity)
        intent.component = ComponentName(this@MainActivity, FlutterActivity::class.java)
        startActivity(intent)
    }

    @Composable
    fun BuildPage(state: State<MainViewState?>) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            ShowBanners(state = state)
            BuildCard()
            Text(
                text = stringResource(R.string.recommend_article),
                modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                fontSize = 16.sp
            )
            ShowRecommendArticle(state)
        }
    }

    @Composable
    fun BuildCard() {
        Row(
            modifier = Modifier
                .height(100.dp)
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF9999FF), RoundedCornerShape(25.dp))
                    .clickable { }, contentAlignment = Alignment.Center
            ) {
                Text("公众号", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF3399CC), RoundedCornerShape(25.dp))
                    .clickable { }, contentAlignment = Alignment.Center
            ) {
                Text("体系", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF3366FF), RoundedCornerShape(25.dp))
                    .clickable { startFlutterActivity() }, contentAlignment = Alignment.Center
            ) {
                Text("问答", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF3399FF), RoundedCornerShape(25.dp))
                    .clickable { }, contentAlignment = Alignment.Center
            ) {
                Text("项目", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

    }

    @Composable
    fun ShowBanners(state: State<MainViewState?>) {
        val banners = state.value!!.banners
        if (!banners.isNullOrEmpty()) {
            BannerPager(items = banners) {

            }
        }
    }

    @Composable
    fun ShowRecommendArticle(state: State<MainViewState?>) {
        val recommendArticleBean = state.value!!.recommendArticleBean
        recommendArticleBean?.forEach { item ->
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xCC000000)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(
                        text = "作者：${item.author}",
                        textAlign = TextAlign.Left,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "分类：${item.superChapterName}/${item.chapterName}",
                        textAlign = TextAlign.Right,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
