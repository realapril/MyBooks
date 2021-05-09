package com.tistory.realapril.mybooks.remote

import com.tistory.realapril.mybooks.entity.BookInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class BookApiSourceImpl(
    private val bookApiSource: BookApiSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getBooks() : Response<BookInfo> = withContext(ioDispatcher) {
        bookApiSource.getBooks(
            "android"
            ,30
            , "totalItems,items(id,volumeInfo/title,volumeInfo/description,volumeInfo/authors,volumeInfo/imageLinks/thumbnail)"
        )
    }

}
