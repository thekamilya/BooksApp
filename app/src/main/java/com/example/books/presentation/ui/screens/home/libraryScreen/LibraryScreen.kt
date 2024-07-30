package com.example.books.presentation.ui.screens.home.libraryScreen

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.books.ui.components.Tabbar
import com.example.books.R
import com.example.books.presentation.ui.screens.home.common.BookSelectionViewModel
import com.example.books.presentation.ui.screens.home.discoverScreen.InnerScreens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import kotlinx.coroutines.launch

@Composable
fun LibraryListScreen(navController: NavHostController,bookSelectionViewModel: BookSelectionViewModel, libraryViewModel: LibraryViewModel = hiltViewModel()) {



    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(
            rememberScrollState()
        )) {
        Tabbar(title = "Library")
        TrendingGanres(libraryViewModel)
        TrendingMagazines(libraryViewModel, onClick = {
            id->
            bookSelectionViewModel.setId(id)
            navController.navigate(InnerScreens.DETAIL.name)})
        Spacer(modifier = Modifier.height(110.dp))

    }

}
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun TrendingMagazines(libraryViewModel: LibraryViewModel, onClick: (id:String) -> Unit) {

    LaunchedEffect(Unit) {
        if (libraryViewModel.magazineListState.value.booksResponse.isEmpty()){
            libraryViewModel.getMagazines()
        }
    }

    var magazines = libraryViewModel.magazineListState.value.booksResponse
    var pageCount by remember { mutableStateOf(4) }
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(initialPage = 0,pageCount = { 5 })

    Column(modifier = Modifier
        .background(color = Color.White)
        .padding(top = 16.dp)
        .height(415.dp)) {


        Text(text = "Trending Magazines", fontSize = 25.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding( start = 32.dp, end = 32.dp),
            fontFamily = FontFamily(Font(R.font.my_custom_font)) )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Recently released magazines",
            color = Color.Gray, fontSize = 17.sp,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp),
            fontFamily = FontFamily(Font(R.font.my_custom_font_medium)) )

        Spacer(modifier = Modifier.height(16.dp))
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp
        val screenWidth = configuration.screenWidthDp

        androidx.compose.foundation.pager.HorizontalPager(
            contentPadding = PaddingValues(horizontal = 100.dp),
            state = pagerState,
            pageSpacing = (screenWidth/2).dp,
            pageSize = PageSize.Fill,
        ) {
            if(libraryViewModel.magazineListState.value.isLoading){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    CircularProgressIndicator(color = Color(0xFFA8C10F), strokeWidth = 4.dp)
                }
            }else if (libraryViewModel.magazineListState.value.error != ""){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Network error")
                }
            }
            if (magazines.isNotEmpty()){
                MagazineItem(
                    title =  magazines[pagerState.currentPage].title,
                    author = magazines[pagerState.currentPage].authors ,
                    url = magazines[pagerState.currentPage].image_resource,
                    onClick = {
                        onClick(magazines[pagerState.currentPage].id)
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        DotsIndicator(
            dotCount = pageCount,
            type = WormIndicatorType(
                dotsGraphic = DotGraphic(
                    7.dp,
                    borderWidth = 2.dp,
                    borderColor = Color(0xFFC4C4C4) ,
                    color = Color(0xFFC4C4C4),
                ),
                wormDotGraphic = DotGraphic(
                    7.dp,
                    color = Color(0xFFA8C10F)
                )
            ),
            pagerState = pagerState
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TrendingGanres(libraryViewModel: LibraryViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pages = listOf("Fiction","Romance","Drama","History","Politics","Business", "Mystery","Thriller")
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    Column(modifier = Modifier.background(color = Color(0xFFEBEBEB) )) {
        Spacer(modifier = Modifier.height(16.dp))
        ScrollableTabRow(
            divider = {},
            edgePadding = 22.dp,
            modifier = Modifier.height(40.dp),
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            backgroundColor = Color(0xFFEBEBEB)
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.zIndex(6f).clip(RoundedCornerShape(30.dp)),
                    text = { Text(text = title.uppercase(),color = Color.Black, fontSize = 12.sp,  fontFamily = FontFamily(Font(R.font.my_custom_font_medium))) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            count = pages.size,
            state = pagerState,
        ) { page ->
            Box(Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .background(color = Color(0xFFEBEBEB))
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(400.dp)) {
                    Text(text = "New & Trending ${pages[page]}", fontSize = 25.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding( start = 32.dp, end = 32.dp),
                        fontFamily = FontFamily(Font(R.font.my_custom_font)) )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Recently released famous books",
                        color = Color.Gray, fontSize = 17.sp,
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                        fontFamily = FontFamily(Font(R.font.my_custom_font_medium)) )

                    Spacer(modifier = Modifier.height(16.dp))

                    if(libraryViewModel.ganreListState.value.isLoading){
                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally){
                            CircularProgressIndicator(color = Color(0xFFA8C10F), strokeWidth = 4.dp)
                        }
                    }else if (libraryViewModel.ganreListState.value.error != ""){
                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Network error")
                        }
                    }

                    LaunchedEffect(Unit) {
                        libraryViewModel.getBooks(pages[page])
                    }


                    var books = libraryViewModel.ganreListState.value.booksResponse

                    LazyRow(modifier = Modifier.pointerInput(Unit) {
                        detectHorizontalDragGestures { change, _ ->
                            change.consume() // Consume all horizontal drag gestures within LazyRow
                        }
                    }){
                        item { Spacer(modifier = Modifier.width(22.dp)) }
                        items(books){book->
                            BookListItem(title = book.title, author = book.authors, url = book.image_resource) {
                            }
                        }
                        item { Spacer(modifier = Modifier.width(32.dp)) }
                    }

                }

            }
        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState <= targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState <= targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(2.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFA8C10F),
                RoundedCornerShape(50)
            )
            .zIndex(1f)
    )
}


@Composable
fun BookListItem(title:String = "Title", author: String = "Auth", url:String = " ", onClick:()-> Unit){

    Surface(modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .clickable(onClick = { onClick() })){
        val painter = rememberAsyncImagePainter(model = url)
        Column(modifier = Modifier
            .background(color = Color(0xFFEBEBEB))
            .padding(horizontal = 10.dp, vertical = 16.dp)

        ){

            Column {

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp) // Take 50% of the available width
                        .aspectRatio(0.7f)    // Maintain aspect ratio (square)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.weight(2f)){
                    Text(modifier = Modifier.width(150.dp), textAlign = TextAlign.Center,text = title, fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.my_custom_font_regular),
                        ))
                    Spacer(modifier = Modifier.height(5.dp))
//                    Text(text = author.uppercase(), fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.my_custom_font_regular)), color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


}

@Composable
fun MagazineItem(title:String = "Title", author: String = "Auth", url:String = " ", onClick:()-> Unit){
    Surface(modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .clickable(onClick = { onClick() })) {
        val painter = rememberAsyncImagePainter(model = url)
        Column(
            modifier = Modifier

                .background(color = Color.White)
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .background(color = Color.White)

        ) {


            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .background(color = Color.White)
                    .width(450.dp) // Take 50% of the available width
                    .aspectRatio(0.7f)    // Maintain aspect ratio (square)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Color.White),
                contentScale = ContentScale.Crop
            )
        }
    }
}
