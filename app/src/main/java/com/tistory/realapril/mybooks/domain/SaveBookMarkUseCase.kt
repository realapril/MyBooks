package com.tistory.realapril.mybooks.domain

import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.entity.Item

class SaveBookMarkUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(item: Item){
        bookRepository.saveBookMark(item = item)
    }

}
