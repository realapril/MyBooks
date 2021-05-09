package com.tistory.realapril.mybooks.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.entity.Item
import com.tistory.realapril.mybooks.data.Result


class GetBookMarkUseCase(
    private val bookRepository: BookRepository
) {
    /**
     * To check if a particular item is bookmarked
     * @param item
     * @return Boolean
     * */
    suspend fun isItemBookMarked(item: Item) : Boolean {
        val result = bookRepository.getBookMark(item.id)
        if(result is Result.Success) {
            return result.data != null
        }
        return false
    }

    fun getAllBookMarks() : List<Item> {
        val result = bookRepository.getAllBookMarks()
        return result
    }
}