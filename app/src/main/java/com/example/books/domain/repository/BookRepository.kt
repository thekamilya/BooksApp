package com.example.books.domain.repository

import com.example.books.data.remote.BookRequest
import com.example.books.data.remote.CommentRequest
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BookDetail
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.data.remote.dto.Comment
import com.example.books.data.remote.dto.LoginResponseDto
import com.example.books.data.remote.dto.SignupResponseDto

interface BookRepository {

    suspend fun login(email:String, password:String): LoginResponseDto

    suspend fun sigup(name:String, email:String, password: String):SignupResponseDto

    suspend fun get_books(title: String, num_books: Int, start_index: Int): BooksResponseDto

    suspend fun get_book(id:String): BookDetail

    suspend fun post_comment(commentRequest: CommentRequest)

    suspend fun delete_comment(id:Int):Boolean

    suspend fun get_trending_books(subject: String, num_books: Int, is_magazine:Boolean): List<Book>

    suspend fun save_book(bookRequest: BookRequest):Boolean

    suspend fun is_book_saved(id: String):Boolean

    suspend fun delete_book(id:String):Boolean

    suspend fun get_saved_books():List<Book>

    suspend fun save_comment(comment: CommentRequest):Boolean

    suspend fun delete_saved_comment(comment_id:Int):Boolean

    suspend fun get_saved_comments(): List<Comment>

    suspend fun is_comment_saved(comment_id:Int): Boolean
}