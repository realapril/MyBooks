package com.tistory.realapril.mybooks.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.tistory.realapril.mybooks.entity.Item

@Dao
interface BookDao {

    @Query("SELECT * FROM fav_bookmark ORDER BY id DESC")
    fun getAllBookMarks() : List<Item>

    @Query("SELECT * FROM fav_bookmark WHERE id=:id")
    suspend fun getBookMark(id:String) : Item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookMark(item: Item)

    @Delete
    suspend fun deleteBookMark(item: Item)
}