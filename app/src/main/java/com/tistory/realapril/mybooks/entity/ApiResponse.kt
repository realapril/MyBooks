package com.tistory.realapril.mybooks.entity

data class ApiResponse(
    var apiBody: BookInfo = BookInfo()
)

data class BookInfo(
    val items: List<Item> = mutableListOf(),
    val totalItems: Int =0,
)

data class Item(
    val id: String,
    val isFav: String?,
    val volumeInfo: VolumeInfo
)

data class ImageLinks(
    val thumbnail: String
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
)