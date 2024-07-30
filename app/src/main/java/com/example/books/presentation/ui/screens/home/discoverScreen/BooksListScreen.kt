package com.example.books.presentation.ui.screens.home.discoverScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.books.ui.components.Tabbar
import com.example.books.R
import com.example.books.presentation.ui.screens.home.common.BookSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(navController: NavHostController, bookSelectionViewModel: BookSelectionViewModel = BookSelectionViewModel(), discoverViewModel: DiscoverViewModel = hiltViewModel()) {



    // Call getBooks only once when the composable is first displayed
    LaunchedEffect(Unit) {
        discoverViewModel.getBooks(discoverViewModel.searchRequest.value, 10, discoverViewModel.start_index.value)
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 40.dp)){
        Tabbar(title = "Search")

        val state = discoverViewModel.listState.value



        val search = remember { mutableStateOf(TextFieldValue()) }


        LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 64.dp)){
            item{
                OutlinedTextField(
                    shape = RoundedCornerShape(25),
                    maxLines = 1,
                    value = search.value,
                    onValueChange = { search.value = it },
                    label = { Text("Search for a book..",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.my_custom_font_medium))) },
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Color.DarkGray,
                        focusedContainerColor = Color(0xFFCDCDD2),
                        unfocusedContainerColor = Color(0xFFCDCDD2),
                        disabledContainerColor = Color(0xFFCDCDD2),
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color(0xFFCDCDD2),
                        unfocusedIndicatorColor = Color(0xFFCDCDD2),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        ,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                discoverViewModel.setSearchRequest(search.value.text)
                                discoverViewModel.getBooks(search.value.text, 10, discoverViewModel.start_index.value)
                                search.value = TextFieldValue("")
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                    }
                )

            }

            item{
                if ((!state.isLoading) and (state.error == "")){
                    Row( horizontalArrangement = Arrangement.SpaceBetween, modifier =  Modifier.fillMaxWidth().padding(horizontal = 38.dp,)){
                        Text(text =  state.booksResponse.total.toString() + " books",fontFamily = FontFamily(Font(R.font.my_custom_font_medium)))
                        Text(text = discoverViewModel.currentPage.value.toString() + "/" + discoverViewModel.totalPages.value.toString(), fontFamily = FontFamily(Font(R.font.my_custom_font_medium)))
                    }
                }
            }


            items(state.booksResponse.list) { book ->
                BookListItem(title = book.title,
                    onClick = { bookSelectionViewModel.setId(book.id)
                              navController.navigate(InnerScreens.DETAIL.name)},
                    author = book.authors,
                    url = book.image_resource)

            }
            item {
                if (state.booksResponse.total != 0){
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = {
                            if (discoverViewModel.currentPage.value == 1){

                            }else{
                                discoverViewModel.currentPage.value -= 1
                                discoverViewModel.start_index.value -= 10
                                discoverViewModel.getBooks(discoverViewModel.searchRequest.value, 10, discoverViewModel.start_index.value)

                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Previous",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            if (discoverViewModel.currentPage.value == discoverViewModel.totalPages.value){

                            }else{
                                discoverViewModel.currentPage.value += 1
                                discoverViewModel.start_index.value += 10
                                discoverViewModel.getBooks(discoverViewModel.searchRequest.value, 10, discoverViewModel.start_index.value)

                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Next",
                                tint = Color.Black
                            )
                        }
                    }
                }

            }
        }


        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,){
            if(state.isLoading){
                CircularProgressIndicator(color = Color(0xFFA8C10F), strokeWidth = 4.dp)

            }else if(state.error != ""){
                Text(text = "Network error")

            }
        }

    }
}

data class Book(val title: String, val author: String, val url:String)

@Composable
fun BookListItem(title:String, author: String?, url:String?, onClick:()-> Unit){

    Surface(modifier = Modifier
        .clip(RoundedCornerShape(5.dp))
        .clickable(onClick = {onClick()})){
        val painter = rememberAsyncImagePainter(model = url)

        Column(modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.5.dp.toPx()
                )
            }){

            Row {

                if (url!= null){
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f) // Take 50% of the available width
                            .aspectRatio(0.7f)    // Maintain aspect ratio (square)
                            .clip(RoundedCornerShape(3.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))
                Column(modifier = Modifier.weight(2f)){
                    Text(text = title, fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.my_custom_font_regular)))
                    Spacer(modifier = Modifier.height(5.dp))
                    if (author != null) {
                        Text(text = author.uppercase(), fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.my_custom_font_regular)), color = Color.Gray)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


}
