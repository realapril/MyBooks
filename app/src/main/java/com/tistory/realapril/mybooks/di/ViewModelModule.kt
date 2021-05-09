package com.tistory.realapril.mybooks.di

import com.tistory.realapril.mybooks.ui.BookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BookViewModel() }
}