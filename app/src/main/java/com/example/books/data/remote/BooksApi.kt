package com.example.books.data.remote

import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BookDetail
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.data.remote.dto.Comment
import com.example.books.data.remote.dto.LoginResponseDto
import com.example.books.data.remote.dto.SignupResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

data class CommentRequest(
    val id: Int = -1,
    val content: String,
    val book_id: String,
    val email: String = " "
)
data class LoginData(
    val grantType: String = "",
    val username: String,
    val password: String,
    val scope: String = "",
    val clientId: Long = 0,
    val clientSecret: String = ""
)

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)
data class BookRequest(
    val id: String,
    val title: String,
    val description:String,
    val image_resource: String,
    val authors:String
)

interface BooksApi {


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("grantType") grantType: String = "",
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String = "",
        @Field("clientId") clientId: Long = 0,
        @Field("clientSecret") clientSecret: String = ""
    ): LoginResponseDto

    @POST("/users/")
    suspend fun sigup(
        @Body signupRequest: SignupRequest
    ): SignupResponseDto

    @GET("/books/title")
    suspend fun get_books(@Header("Authorization") current_user: String,
                          @Query("title") title: String,
                          @Query("num_books") num_books: Int,
                          @Query("start_index") start_index: Int): BooksResponseDto

    @GET("/books/title/id")
    suspend fun get_book(@Query("id") title: String):BookDetail

    @POST("/comments/add")
    suspend fun post_comment(@Header("Authorization") current_user: String,
                             @Body commentRequest: CommentRequest)

    @GET("/books/trending")
    suspend fun find_trending_books(@Query("subject") subject: String,
                                    @Query("num_books") num_books: Int,
                                    @Query("is_magazine") is_magazine: Boolean):List<Book>

    @DELETE("/comments/deleteComment")
    suspend fun delete_comment(@Header("Authorization") current_user: String,
                               @Query("comment_id") id: Int): Boolean

    @POST("books/save")
    suspend fun save_book(@Header("Authorization") current_user: String,
                          @Body book: BookRequest):Boolean

    @GET("books/isSaved")
    suspend fun is_saved_book(@Header("Authorization") current_user: String,
                              @Query("id")id:String):Boolean
    @DELETE("/books/delete")
    suspend fun delete_book(@Header("Authorization") current_user: String,
                               @Query("book_id") book_id: String): Boolean

    @GET("/books/getSaved")
    suspend fun get_saved_books(@Header("Authorization") current_user: String): List<Book>

    @POST("comments/save")
    suspend fun save_comment(@Header("Authorization") current_user: String,
                          @Body comment: CommentRequest):Boolean

    @DELETE("/comments/delete")
    suspend fun delete_saved_comment(@Header("Authorization") current_user: String,
                               @Query("comment_id")comment_id:Int):Boolean

    @GET("/comments/getSaved")
    suspend fun get_saved_comments(@Header("Authorization") current_user: String): List<Comment>

    @GET("comments/isSaved")
    suspend fun is_saved_comment(@Header("Authorization") current_user: String,
                              @Query("comment_id")comment_id:Int):Boolean
}