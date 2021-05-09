package com.tistory.realapril.mybooks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tistory.realapril.mybooks.entity.Item
import com.tistory.realapril.mybooks.local.BookLocalDataSource
import com.tistory.realapril.mybooks.data.Result
import kotlinx.coroutines.*

class BookRepository(
    private val bookLocalDataSource: BookLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseDataSource() {

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
