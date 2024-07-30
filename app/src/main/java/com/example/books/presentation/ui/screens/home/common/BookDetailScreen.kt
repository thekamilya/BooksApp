package com.example.books.presentation.ui.screens.home.common

//import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.books.R
import com.example.books.common.Constants.EMAIL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavHostController, id: String, bookDetailViewModel: BookDetailViewModel = hiltViewModel(), ) {

    LaunchedEffect(Unit) {
        bookDetailViewModel.isBookSaved(id)
        bookDetailViewModel.getBook(id)
        bookDetailViewModel.getSavedComments()
    }
    val book = bookDetailViewModel.detailState.value.booksResponse//
    val savedState = bookDetailViewModel.bookSavedState


    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .verticalScroll(rememberScrollState()))
    {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 0.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.White)
            ,) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = {
                        if (savedState.value){
                            bookDetailViewModel.deleteBook(id)
                        }else{
                            bookDetailViewModel.saveBook(id =id, title = book.title , description = book.description , image_resource = book.image_resource, authors = book.authors)
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = if (savedState.value){ Icons.Filled.Favorite}else{ Icons.Filled.FavoriteBorder},
                        contentDescription = "Send",
                        tint = Color(0xFF29323D)
                    )
                }
            }
            val painter = rememberAsyncImagePainter(model = book.image_resource)
//            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp) // Take 50% of the available width
                    .aspectRatio(0.7f)    // Maintain aspect ratio (square)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(70.dp))

            Text(textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 50.dp), text =  book.title,
                fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.my_custom_font)))
            Spacer(modifier = Modifier.height(20.dp))
            Text(textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 50.dp),text = book.title,
                fontSize = 17.sp, color = Color.Gray, fontFamily = FontFamily(Font(R.font.my_custom_font_medium)))
            Spacer(modifier = Modifier.height(20.dp))


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Color.LightGray))


            Color(0xFFF7F7F7)
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF7F7F7))){
                Spacer(modifier = Modifier.height(20.dp))
                Text(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                    text = "Comments",
                    fontSize = 24.sp, color = Color(0xFF29323D), fontFamily = FontFamily(Font(R.font.my_custom_font)))
                val comment = remember { mutableStateOf(TextFieldValue()) }

                OutlinedTextField(
                    value = comment.value,
                    onValueChange = { comment.value = it },
                    label = { Text(text = "Add your comment...", color = Color(0xFF29323D), fontFamily = FontFamily(Font(R.font.my_custom_font_medium))) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                bookDetailViewModel.postComment(content = comment.value.text, book_id = id)
                                comment.value = TextFieldValue("")
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Send",
                                tint = Color(0xFF29323D)
                            )
                        }
                    }
                )

                for(comment in bookDetailViewModel.comments.value){
                    Comment(
                        email = if(comment.email!= null){comment.email}else{""},
                        content = comment.content,
                        commentId = comment.id,
                        bookDetailViewModel = bookDetailViewModel,
                        bookId = id
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }



        }


    }

}

//Chnage parameter passing of viewModel to callback functions
@Composable
fun Comment(email: String, content: String, commentId:  Int,
            bookDetailViewModel: BookDetailViewModel, bookId: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if (email == EMAIL) {
                            bookDetailViewModel.showOptions.value = commentId
                        } else {
                            bookDetailViewModel.showOptions.value = -1
                        }
                    },
                    onTap = {
                        bookDetailViewModel.showOptions.value = -1
                    }
                )
            },
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
                    if (bookDetailViewModel.showOptions.value == commentId) {
                        IconButton(onClick = {
                            bookDetailViewModel.deleteComment(commentId, bookId)
                            bookDetailViewModel.showOptions.value = -1
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Send",
                                tint = Color(0xFF29323D)
                            )
                        }
                    }

                    IconButton(onClick = {
                        if(commentId in bookDetailViewModel.commentIds.value){
                            bookDetailViewModel.deleteSavedComment(commentId)
                        }else{
                            bookDetailViewModel.saveComment(commentId, content, bookId, email)
                        }

                    }) {
                        Icon(
                            imageVector = if(commentId in bookDetailViewModel.commentIds.value){Icons.Filled.Favorite}else{Icons.Filled.FavoriteBorder},
                            contentDescription = "Send",
                            tint = Color(0xFF29323D)
                        )
                    }
                }
        }

    }
}


//@Preview
//@Composable
//fun CommentPreview(){
//    Comment(email = "nazarhanovak@gmail.com", content = "I like this book!")
//}
