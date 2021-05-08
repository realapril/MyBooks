package com.tistory.realapril.mybooks.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

data class ApiResponse(
        var apiBody: BookInfo = BookInfo()
)

data class BookInfo(
    val items: List<Item> = mutableListOf(),
    val totalItems: Int =0,
)

@Entity(tableName = "concert_bookmark")
data class Item(
    @PrimaryKey
    val id: String,
    val isFav: String?,
    val volumeInfo: VolumeInfo
)

data class ImageLinks(
    @PrimaryKey
    val thumbnail: String
)

data class VolumeInfo(
    @PrimaryKey
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val title: String
)
