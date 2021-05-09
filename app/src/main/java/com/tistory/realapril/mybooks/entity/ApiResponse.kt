package com.tistory.realapril.mybooks.entity

import androidx.room.*
import com.tistory.realapril.mybooks.utils.Converters

data class ApiResponse(
    var apiBody: BookInfo = BookInfo()
)

data class BookInfo(
    val items: List<Item> = mutableListOf(),
    val totalItems: Int =0,
)

@TypeConverters(Converters::class)
@Entity(tableName = "fav_bookmark")
data class Item(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean?,
    @Embedded val volumeInfo: VolumeInfo
)

data class ImageLinks(
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)

data class VolumeInfo(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "authors") val authors: List<String>,
    @ColumnInfo(name = "description") val description: String,
    @Embedded val imageLinks: ImageLinks,
)