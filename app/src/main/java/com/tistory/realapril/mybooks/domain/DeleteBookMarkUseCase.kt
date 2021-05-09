package com.tistory.realapril.mybooks.domain

import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.entity.Item

class DeleteBookMarkUseCase(
    private val concertRepository: BookRepository
) {
    suspend operator fun invoke(item: Item){
        concertRepository.deleteBookMark(item = item)
    }

}