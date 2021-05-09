package com.tistory.realapril.mybooks.entity

import androidx.room.*
import com.tistory.realapril.mybooks.utils.Converters

//data class ApiResponse(
//    var apiBody: BookInfo = BookInfo()
//)

data class BookInfo(
    var items: List<Item> = mutableListOf(),
    var totalItems: Int =0,
)

@TypeConverters(Converters::class)
@Entity(tableName = "fav_bookmark")
data class Item(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean?,
    @Embedded var volumeInfo: VolumeInfo
)

data class ImageLinks(
    @ColumnInfo(name = "thumbnail", defaultValue = "") var thumbnail: String
)

data class VolumeInfo(
    @ColumnInfo(name = "title", defaultValue = "") var title: String,
    @ColumnInfo(name = "authors", defaultValue = "") var authors: List<String>,
    @ColumnInfo(name = "description", defaultValue = "") var description: String,
    @Embedded var imageLinks: ImageLinks,
)