package com.tistory.realapril.mybooks.data

import com.tistory.realapril.mybooks.entity.Item
import com.tistory.realapril.mybooks.local.BookLocalDataSource
import com.tistory.realapril.mybooks.data.Result
import com.tistory.realapril.mybooks.entity.ApiResponse
import com.tistory.realapril.mybooks.remote.BookApiSourceImpl
import kotlinx.coroutines.*

class BookRepository(
    private val bookLocalDataSource: BookLocalDataSource,
    private val bookApiSourceImpl: BookApiSourceImpl,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseDataSource() {

    /**
     * Get book information from Google API
     * @return Result<ApiResponse>
     * */
    suspend fun getBooksFromNetwork(): Result<ApiResponse> {
        return withContext(ioDispatcher){
            getResult { bookApiSourceImpl.getBooks() }
        }
    }

    /**
     * Get the user's bookmark list from local storage(Room).
     * Using Paging library from Jetpack.
     * @return DataSource.Factory<Int, ConcertItem>
     * */
    fun getAllBookMarks() : List<Item> {
        return bookLocalDataSource.getAllBookMarks()
    }

    /**
     * Get a bookmark contents contains id
     * @param id
     * */
    suspend fun getBookMark(id : String) : Result<Item> {
        return withContext(ioDispatcher){
            try {
                Result.Success(bookLocalDataSource.getBookMark(id))
            }catch (e:java.lang.Exception) {
                Result.Error(Exception("Error on getting bookmark data from local."))
            }
        }
    }

    /**
     * Save a performance item as user's bookmark
     * @param item
     * */
    suspend fun saveBookMark(item: Item) {
        coroutineScope {
            launch { bookLocalDataSource.saveBookMark(item) }
        }
    }

    /**
     * Delete a performance item from user's bookmark
     * @param item
     * */
    suspend fun deleteBookMark(item: Item) {
        coroutineScope {
            launch { bookLocalDataSource.deleteBookMark(item) }
        }
    }
}
