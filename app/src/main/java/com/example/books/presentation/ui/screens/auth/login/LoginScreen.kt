package com.example.books.presentation.ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.books.MainScreens
import com.example.books.R
import com.example.books.presentation.ui.screens.AuthScreens

@Composable
fun LoginScreen(navController: NavHostController, rootNavController: NavHostController, loginViewModel: LoginViewModel = hiltViewModel()) {
    var loginValue by remember { mutableStateOf("") }
    var isShowPasswordChecked by remember { mutableStateOf(true) }
    var passwordValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        if(loginViewModel.loginState.value.isLoading){
            Box(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }

        Column(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "BOOKS",
                    fontFamily = FontFamily(Font(R.font.my_custom_font)),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Log in",
                fontFamily = FontFamily(Font(R.font.my_custom_font)),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp)
            )
//
//            Text(
//                modifier = Modifier
//                    .padding(vertical = 8.dp),
//                text = "Используйте зарегистрированный аккаунт",
//                textAlign = TextAlign.Center,
//                style = TextStyle(fontSize = 14.sp)
//            )

            Column(
                modifier = Modifier.padding(top = 40.dp)
            ) {
                OutlinedTextField(
                    isError = loginViewModel.loginState.value.error != "",
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 1,
                    label = { Text("Email", fontFamily = FontFamily(Font(R.font.my_custom_font_medium))) },
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
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .focusRequester(focusRequester)
                    ,
                    value = loginValue,
                    onValueChange = { loginValue = it }
                )

                OutlinedTextField(
                    visualTransformation =
                    if(isShowPasswordChecked){
                        VisualTransformation.None
                    }else{
                        PasswordVisualTransformation()
                    },
                    isError = loginViewModel.loginState.value.error != "",
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 1,
                    label = { Text("Password", fontFamily = FontFamily(Font(R.font.my_custom_font_medium))) },
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
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .focusRequester(focusRequester),
                    value = passwordValue,
                    onValueChange = { passwordValue = it }
                )

                Button(
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonColors(contentColor = Color.White,
                        containerColor = Color(0xFF4C4C4C),
                        disabledContainerColor =  Color.Black,
                        disabledContentColor =  Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    onClick = {
                        loginViewModel.login(loginValue, passwordValue)
                    }
                ) {
                    Text(text = "LOG IN")
                }
                if (loginViewModel.loginState.value.loginResponse.token_type == "bearer"){

                    rootNavController.navigate(route = MainScreens.HOME.name)
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {  },

                    ) {
                    Checkbox( colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4C4C4C), uncheckedColor = Color(0xFF4C4C4C)),
                        checked = isShowPasswordChecked,
                        onCheckedChange = {isShowPasswordChecked = !isShowPasswordChecked},
                        modifier = Modifier.absoluteOffset((-12).dp))
                    Text(
                        text = "Show password",
                        fontFamily =  FontFamily(Font(R.font.my_custom_font_medium)),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.absoluteOffset((-12).dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                        .clickable { navController.navigate(AuthScreens.PASSWORD.name) },
                    text = "Don't have an account yet?",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.my_custom_font_medium)),
                    color = Color(0xFF4C4C4C),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary),
                )
            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    BooksTheme {
//        LoginScreen(rememberNavController())
//    }
//}
