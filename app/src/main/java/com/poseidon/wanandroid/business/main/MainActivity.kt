package com.poseidon.wanandroid.business.main

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poseidon.lib.common.base.BaseActivity
import com.poseidon.wanandroid.R
import com.poseidon.wanandroid.WanAndroidApplication
import com.poseidon.wanandroid.theme.WanAndroidTheme
import com.poseidon.wanandroid.utils.Constants
import com.zj.banner.BannerPager
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var state: State<MainViewState?>

    override fun initView() {
//        setContentView(R.layout.activity_main)
        setContent {
            WanAndroidTheme {
                // A surface container using the 'background' color from the theme
                state = viewModel.viewState.observeAsState(viewModel.getViewState())
                buildContent(state = state)
            }
        }
    }

    override fun initObservers() {
    }

    override fun initData() {
        var currentItem = viewModel.viewState.value?.currentSelectedItem;
        when (currentItem) {
            BottomItemScreen.HOME_ITEM -> {
                viewModel.loadHomeData()
            }
            BottomItemScreen.SQUARE_ITEM -> {
                viewModel.loadSquareData()
            }
            BottomItemScreen.TREE_ITEM -> {
                viewModel.loadTreeData()
            }
            BottomItemScreen.WECHAT_ITEM -> {
                viewModel.loadWechatData()
            }
        }

    }

    private fun openAnswerGroup() {
        var intent = FlutterActivity
            .withCachedEngine(Constants.nameOfAnswerEngineCache)
            .build(this)
        startActivity(intent)
        WanAndroidApplication.setFlutterInitRoute(Constants.pathOfAnswer)
    }

    private fun openHierarchyGroup() {

    }

    private fun openProjectGroup() {

    }

    private fun openWechatGroup() {

    }

    @Composable
    fun buildContent(state: State<MainViewState?>) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                bottomBar = {
                    buildBottomAppBar(state)
                }) {
                when (state.value?.currentSelectedItem) {
                    BottomItemScreen.HOME_ITEM -> {
                        buildHomePage(state)
                    }
                    BottomItemScreen.SQUARE_ITEM -> {
                        buildSquarePage(state)
                    }
                    BottomItemScreen.TREE_ITEM -> {
                        buildTreePage(state)
                    }
                    BottomItemScreen.WECHAT_ITEM -> {
                        buildWechatPage(state)
                    }
                }
            }
        }
    }

    @Composable
    fun buildBottomAppBar(state: State<MainViewState?>) {
        val navItem = listOf(
            BottomItemScreen.HOME,
            BottomItemScreen.SQUARE,
            BottomItemScreen.TREE,
            BottomItemScreen.WECHAT
        )

        BottomAppBar(backgroundColor = Color.White) {
            navItem.forEach {
                BottomNavigationItem(
                    label = { Text(text = stringResource(id = it.title)) },//设置item标签
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(id = it.title)
                        )
                    },//设置item图标
                    selectedContentColor = Color.Blue,//选中时颜色
                    unselectedContentColor = colorResource(id = android.R.color.darker_gray),
                    onClick = {
                        viewModel.setCurrentBottomItem(it.route)
                    },
                    selected = state.value?.currentSelectedItem == it.route
                )
            }
        }
    }


    @Composable
    fun buildHomePage(state: State<MainViewState?>) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            showBanners(state = state)
            Text(
                text = stringResource(R.string.top_hot_article),
                modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                fontSize = 16.sp
            )
            showTopHotArticle(state = state)
            Text(
                text = stringResource(R.string.recommend_article),
                modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                fontSize = 16.sp
            )
            showRecommendArticle(state)
        }
    }

    @Composable
    fun buildSquarePage(state: State<MainViewState?>) {
        Text(text = "this is Square page")
    }

    @Composable
    fun buildTreePage(state: State<MainViewState?>) {
        Text(text = "this is Tree page")
    }

    @Composable
    fun buildWechatPage(state: State<MainViewState?>) {
        Text(text = "this is Wechat page")
    }

    @Composable
    fun showBanners(state: State<MainViewState?>) {
        val banners = state.value!!.banners
        if (!banners.isNullOrEmpty()) {
            BannerPager(items = banners) {

            }
        }
    }

    @Composable
    fun showTopHotArticle(state: State<MainViewState?>) {
        val hotArticleList = state.value!!.hotArticleList
        hotArticleList?.forEach { item ->
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
                        text = stringResource(id = R.string.top_hot_article),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.Red,
                        modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = Color.Red,
                                shape = RectangleShape
                            )
                            .padding(start = 0.dp, top = 1.dp, end = 0.dp, bottom = 1.dp)
                            .weight(0.4f)
                    )
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

    @Composable
    fun showRecommendArticle(state: State<MainViewState?>) {
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
