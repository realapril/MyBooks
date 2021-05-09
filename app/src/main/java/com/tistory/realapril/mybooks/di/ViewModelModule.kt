package com.tistory.realapril.mybooks.di

import com.tistory.realapril.mybooks.data.BookRepository
import com.tistory.realapril.mybooks.domain.DeleteBookMarkUseCase
import com.tistory.realapril.mybooks.domain.GetBookMarkUseCase
import com.tistory.realapril.mybooks.domain.GetBooksUseCase
import com.tistory.realapril.mybooks.domain.SaveBookMarkUseCase
import com.tistory.realapril.mybooks.local.BookLocalDataSource
import com.tistory.realapril.mybooks.local.Database
import com.tistory.realapril.mybooks.remote.BookApiSourceImpl
import com.tistory.realapril.mybooks.ui.BookViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BookViewModel(get(), get(), get(),get()) }

    //TODO 나중에 옮길것
    single { BookLocalDataSource(Database.getDatabase(androidContext()).bookDao()) }
    //Room
    single { SaveBookMarkUseCase(get()) }
    single { DeleteBookMarkUseCase(get()) }
    single { GetBookMarkUseCase(get()) }
    single { BookRepository(get(),get()) }

    //Network
    single {BookApiSourceImpl(get())}
    single {GetBooksUseCase(get())}

}