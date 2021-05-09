package com.tistory.realapril.mybooks.domain

import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.entity.ApiResponse
import com.tistory.realapril.mybooks.data.Result

class GetBooksUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke() : Result<ApiResponse> {
        return bookRepository.getBooksFromNetwork()
    }

}