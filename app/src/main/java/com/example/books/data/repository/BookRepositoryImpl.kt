package com.example.books.data.repository

import android.content.SharedPreferences
import com.example.books.data.remote.BookRequest
import com.example.books.data.remote.BooksApi
import com.example.books.data.remote.CommentRequest
import com.example.books.data.remote.SignupRequest
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BookDetail
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.data.remote.dto.Comment
import com.example.books.data.remote.dto.LoginResponseDto
import com.example.books.data.remote.dto.SignupResponseDto
import com.example.books.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val sharedPrefs:SharedPreferences,
    private val api: BooksApi
):BookRepository {
    override suspend fun login(email: String, password: String): LoginResponseDto {
        return api.login(username = email, password = password)
    }

    override suspend fun sigup(name: String, email: String, password: String): SignupResponseDto {
        return api.sigup(SignupRequest( name = name, email = email, password = password))
    }

    override suspend fun get_books(title: String, num_books: Int, start_index: Int): BooksResponseDto{
        return api.get_books(
            "Bearer ${sharedPrefs.getString("token", "")}",
            title, num_books, start_index)
    }

    override suspend fun get_book(id: String): BookDetail {
        return api.get_book(id)
    }

    override suspend fun post_comment(commentRequest: CommentRequest) {
        return api.post_comment(
            "Bearer ${sharedPrefs.getString("token", "")}",
            commentRequest)
    }

    override suspend fun delete_comment(id: Int):Boolean {
        return api.delete_comment("Bearer ${sharedPrefs.getString("token", "")}",id)
    }

    override suspend fun get_trending_books(subject: String,num_books:Int , is_magazine: Boolean): List<Book> {
        return api.find_trending_books(subject, num_books, is_magazine)
    }

    override suspend fun save_book(bookRequest: BookRequest): Boolean {
        return api.save_book("Bearer ${sharedPrefs.getString("token", "")}", bookRequest)
    }

    override suspend fun is_book_saved(id: String): Boolean {
        return api.is_saved_book("Bearer ${sharedPrefs.getString("token", "")}", id)
    }

    override suspend fun delete_book(id: String): Boolean {
        return api.delete_book("Bearer ${sharedPrefs.getString("token", "")}", id)
    }

    override suspend fun get_saved_books(): List<Book> {
        return api.get_saved_books("Bearer ${sharedPrefs.getString("token", "")}")
    }

    override suspend fun save_comment(comment: CommentRequest): Boolean {
        return api.save_comment("Bearer ${sharedPrefs.getString("token", "")}", comment)
    }

    override suspend fun delete_saved_comment(comment_id: Int): Boolean {
        return api.delete_saved_comment("Bearer ${sharedPrefs.getString("token", "")}",comment_id)
    }

    override suspend fun get_saved_comments(): List<Comment> {
        return api.get_saved_comments("Bearer ${sharedPrefs.getString("token", "")}")
    }

    override suspend fun is_comment_saved(comment_id: Int): Boolean {
        return api.is_saved_comment("Bearer ${sharedPrefs.getString("token", "")}", comment_id)
    }
}