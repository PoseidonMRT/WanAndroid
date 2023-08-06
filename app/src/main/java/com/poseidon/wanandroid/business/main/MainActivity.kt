package com.poseidon.wanandroid.business.main

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poseidon.blc.common.ArticleBean
import com.poseidon.blc.tree.entities.TreeListBean
import com.poseidon.lib.common.base.BaseActivity
import com.poseidon.wanandroid.R
import com.poseidon.wanandroid.WanAndroidApplication
import com.poseidon.wanandroid.leakcanary.TestService
import com.poseidon.wanandroid.theme.Teal200
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
//        startService(Intent(this,TestService::class.java))
        viewModel.loadData()
//        var mainHandler:Handler = Handler(Looper.getMainLooper())
//        mainHandler.postDelayed(Runnable { stopService(Intent(MainActivity@this,TestService::class.java)) },300)
    }

    private fun openAnswerGroup() {
        var intent = FlutterActivity
            .withCachedEngine(Constants.nameOfAnswerEngineCache)
            .build(this)
        startActivity(intent)
        WanAndroidApplication.setFlutterInitRoute(Constants.pathOfAnswer)
    }

    @Composable
    fun buildContent(state: State<MainViewState?>) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            openAnswerGroup()
                        },
                        shape = RoundedCornerShape(50),
                        backgroundColor = Teal200
                    ) {
                        Icon(
                            Icons.Filled.QuestionAnswer,
                            tint = Color.White,
                            contentDescription = "Add"
                        )
                    }
                },
                isFloatingActionButtonDocked = true,
                floatingActionButtonPosition = FabPosition.Center,
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

        BottomAppBar(backgroundColor = Color.White, cutoutShape = RoundedCornerShape(50)) {
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
                        viewModel.updateCurrentBottomItem(it.route)
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
        Column(Modifier.verticalScroll(rememberScrollState())) {
            var squareInfo = state.value!!.squareInfo
            squareInfo?.forEach { item ->
                buildArticleItem(item = item)
            }
        }
//        LazyColumn() {
//           state.value?.squareInfo?.let {
//               items(it.size) { item->
//                   buildArticleItem(state.value?.squareInfo!![item])
//               }
//           }
//        }
    }

    @Composable
    fun buildTreePage(state: State<MainViewState?>) {
        LazyColumn() {
            state.value?.treeGroupList?.let {
                items(it?.size) { treeGroup ->
                    buildTreeGroup(state.value?.treeGroupList!![treeGroup])
                }
            }
        }
    }

    @Composable
    fun buildTreeGroup(it: TreeListBean.TreeGroup) {
        var isExpanded by remember {
            mutableStateOf(false)
        }
        Column {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = it.name,
                    color = Color(0xff333333),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.0f, true)
                )
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(
                        id = if (isExpanded) R.drawable.icon_arrow_up
                        else R.drawable.icon_arrow_down
                    ), contentDescription = "更多"
                )
            }
            if (isExpanded) {
                it.children?.forEach { child ->
                    Text(
                        text = child.name,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = 30.dp,
                            end = 10.dp,
                            bottom = 10.dp
                        )
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .background(Color(0xeeeeee))
                    .height(1.dp)
            )
        }
    }

}

@Composable
fun buildWechatPage(state: State<MainViewState?>) {
    val wechatGroup = state.value?.wechatInfo
    LazyColumn() {
        wechatGroup?.size?.let {
            items(it) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = wechatGroup[index].name,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .weight(weight = 1.0f)
                    )
                    Icon(
                        Icons.Filled.ArrowRight,
                        tint = Color.Black,
                        contentDescription = "更多",
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }
        }
    }
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
        Column(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xCC000000)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
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
            Spacer(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp))
        }
    }
}

@Composable
fun showRecommendArticle(state: State<MainViewState?>) {
    val recommendArticleBean = state.value!!.recommendArticleBean
    recommendArticleBean?.forEach { item ->
        buildArticleItem(item = item)
    }
}

@Composable
fun buildArticleItem(item: ArticleBean) {
    Column(modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp)) {
        Text(
            text = item.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xCC000000)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
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
        Spacer(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp))
    }
}
