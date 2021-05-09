package com.tistory.realapril.mybooks.local


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tistory.realapril.mybooks.entity.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookLocalDataSource(
    private val bookDao: BookDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getAllBookMarks() : List<Item> {
        return bookDao.getAllBookMarks()
    }

    suspend fun getBookMark(id: String) : Item = withContext(ioDispatcher) {
        return@withContext bookDao.getBookMark(id)
    }

    suspend fun saveBookMark(item: Item) = withContext(ioDispatcher){
        bookDao.saveBookMark(item)
    }

    suspend fun deleteBookMark(item: Item) = withContext(ioDispatcher){
        bookDao.deleteBookMark(item)
    }
}