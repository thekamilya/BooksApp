package com.example.books.presentation.ui.screens.home.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.books.R
import com.example.books.common.Constants
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.Comment
import com.example.books.presentation.ui.screens.AuthScreens
import com.example.books.presentation.ui.screens.home.common.BookSelectionViewModel
import com.example.books.presentation.ui.screens.home.discoverScreen.BookListItem
import com.example.books.presentation.ui.screens.home.discoverScreen.InnerScreens
import java.lang.annotation.RetentionPolicy


enum class ProfileScreens {
    SAVEDBOOKS,
    SAVEDCOMMENTS,

}

@SuppressLint("ResourceAsColor")
@Composable
fun ProfileScreen(navController: NavHostController, bookSelectionViewModel: BookSelectionViewModel, profileViewModel:ProfileViewModel = hiltViewModel(),  onLogOut:()-> Unit) {

    LaunchedEffect(Unit) {
        profileViewModel.getSavedBooks()
        profileViewModel.getSavedComments()
    }

    val showMenu = remember { mutableStateOf(false) }

    val showScreen = remember{ mutableStateOf(ProfileScreens.SAVEDBOOKS) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFA8C10F)),){
        if (!showMenu.value){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                , horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {showMenu.value = true}, ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        } else{
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                , horizontalArrangement = Arrangement.End) {
                Row(modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .height(60.dp)
                    .background(color = Color.White),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                ) {

                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){
                            Icon(
                                modifier = Modifier
                                    .padding( start = 16.dp),
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding( end = 16.dp)
                                    .clickable { onLogOut()},
                                text = "Log out",
                                fontFamily = FontFamily(Font(R.font.my_custom_font_medium)),
                                color = Color(0xFF4C4C4C),
                                style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary),
                            )

                        }


                    IconButton(onClick = {showMenu.value = false}, ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally){

            Spacer(modifier = Modifier.height(100.dp))
            Text(textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth()
                ,
                text =  Constants.NAME,
                fontSize = 25.sp,color = Color.White,  fontFamily = FontFamily(Font(R.font.my_custom_font))
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth(),
                text = Constants.EMAIL,
                fontSize = 17.sp, color = Color.White,
                fontFamily = FontFamily(Font(R.font.my_custom_font_medium))
            )
            Spacer(modifier = Modifier.height(60.dp))

            Column(modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(color = Color.White)) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Saved Books",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.my_custom_font_medium)),
                        modifier = Modifier
                            .padding(30.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                showScreen.value = ProfileScreens.SAVEDBOOKS
                            },
                        color = if (showScreen.value == ProfileScreens.SAVEDBOOKS){Color.Black}else{Color(0xFFD0D1E3)}
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "Saved Comments",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.my_custom_font_medium)),
                        modifier = Modifier
                            .padding(30.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                showScreen.value = ProfileScreens.SAVEDCOMMENTS
                            },
                        color = if (showScreen.value == ProfileScreens.SAVEDCOMMENTS){Color.Black}else{Color(0xFFD0D1E3)}
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (showScreen.value == ProfileScreens.SAVEDBOOKS){
                    val bookListState = profileViewModel.bookListState.value


                    if(bookListState.isLoading){
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,){
                            CircularProgressIndicator(color = Color(0xFFA8C10F), strokeWidth = 4.dp)
                        }

                    }else if(bookListState.error != ""){

                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,) {
                            Text(text = "Network error")
                        }
                    }else if(bookListState.booksResponse.isEmpty() ){
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,) {
                            Text(text = "Saved books will appear here")
                        }
                    }else{
                        showSavedBooks(bookListState.booksResponse.reversed(), onClick = { id->
                            bookSelectionViewModel.setId(id)
                            navController.navigate(InnerScreens.DETAIL.name)
                        })
                    }

                }else if(showScreen.value == ProfileScreens.SAVEDCOMMENTS){
                    val commentsListState = profileViewModel.commentListState.value
                    if(commentsListState.isLoading){
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,){
                            CircularProgressIndicator(color = Color(0xFFA8C10F), strokeWidth = 4.dp)
                        }

                    }else if(commentsListState.error != ""){

                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,) {
                            Text(text = "Network error")
                        }
                    }else if(commentsListState.commentResponse.isEmpty() ){
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,) {
                            Text(text = "Saved books will appear here")
                        }
                    }else{
                        val commentList = profileViewModel.savedComments.value
                        showSavedComments(commentList, profileViewModel)
                    }

                }

            }

        }

    }

}

@Composable
fun showSavedBooks(bookList: List<Book>, onClick:(id:String)-> Unit){

    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 64.dp)){
        items(bookList) { book ->
            BookListItem(title = book.title,
                onClick = {onClick(book.id)},
                author = book.authors,
                url = book.image_resource)

        }
    }

}

@Composable
fun showSavedComments(commentList: List<Comment>, profileViewModel: ProfileViewModel){
    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 64.dp)){
        items(commentList) { comment ->
            Comment(email = comment.email, content = comment.content , commentId =comment.id, profileViewModel = profileViewModel  , bookId = comment.book_id)
        }
    }
}


@Composable
fun Comment(email: String, content: String, commentId:  Int,
            profileViewModel: ProfileViewModel, bookId: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.5.dp, Color.White),
        colors = CardDefaults.cardColors(
            Color(0xFFCDCDD2) // Set the background color to light gray
        )


    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "@$email",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.my_custom_font)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF29323D)
                    )
                )
                Text(
                    text = content,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.my_custom_font_medium)),
                        fontSize = 14.sp,
                        color = Color(0xFF29323D)
                    )
                )
            }


            Row {

                IconButton(onClick = {
                    if(commentId in profileViewModel.commentIds.value){
                        profileViewModel.deleteSavedComment(commentId)
                    }else{
                        profileViewModel.saveComment(commentId, content, bookId, email)
                    }

                }) {
                    Icon(
                        imageVector = if(commentId in profileViewModel.commentIds.value){
                            Icons.Filled.Favorite}else{
                            Icons.Filled.FavoriteBorder},
                        contentDescription = "Send",
                        tint = Color(0xFF29323D)
                    )
                }
            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun MapScreenPreview() {
//    BooksTheme {
//        ProfileScreen()
//    }
//}
