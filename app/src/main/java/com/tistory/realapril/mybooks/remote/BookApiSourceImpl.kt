package com.tistory.realapril.mybooks.remote

import com.tistory.realapril.mybooks.entity.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BookApiSourceImpl(
    private val bookApiSource: BookApiSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getBooks() : Response<ApiResponse> = withContext(ioDispatcher) {
        bookApiSource.getBooks(
            "android"
            ,30
            , "totalItems,items(id,volumeInfo/title,volumeInfo/description,volumeInfo/authors,volumeInfo/imageLinks/thumbnail)"
        )
    }

}
