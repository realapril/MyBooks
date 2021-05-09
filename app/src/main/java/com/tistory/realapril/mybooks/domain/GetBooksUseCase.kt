package com.tistory.realapril.mybooks.domain

import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.data.Result
import com.tistory.realapril.mybooks.entity.BookInfo

class GetBooksUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke() : Result<BookInfo> {
        return bookRepository.getBooksFromNetwork()
    }

}