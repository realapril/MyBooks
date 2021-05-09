package com.tistory.realapril.mybooks.ui

import androidx.lifecycle.*
import com.tistory.realapril.mybooks.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.tistory.realapril.mybooks.data.Result
import com.tistory.realapril.mybooks.entity.*


class BookViewModel(
    private val saveBookMarkUseCase: SaveBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val getBookMarkUseCase: GetBookMarkUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    // LiveData of whole performance response from public API
    // In case of using total item count
    private val _bookResponse = MutableLiveData<BookInfo>()
    val bookResponse: LiveData<BookInfo> = _bookResponse

    // LiveData of all books got from server
    private var _bookList = MutableLiveData<List<Item>>()
    private lateinit var _bookListCache : List<Item>
    val bookList: LiveData<List<Item>> = _bookList

    // LiveData of list of the user's bookmark.
    private var _bookMarkList = MutableLiveData<List<Item>>()
    val bookMarkList: LiveData<List<Item>> = _bookMarkList

    private var _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    init {
        loadRemoteBooks() // for list Fragment
        getLocalBookmarks() //for fav Fragment
    }

    fun loadRemoteBooks(){
        _dataLoading.value = true
        viewModelScope.launch {
            val result = getBooksUseCase.invoke()
            if(result is Result.Success) {
                _bookResponse.value = result.data

                _bookListCache = result.data.items!!.map {
                    Item(it.id, getBookMarkUseCase.isItemBookMarked(it), it.volumeInfo)
                }
                _bookList.postValue(_bookListCache)
            }
            _dataLoading.value = false
        }
    }

    fun getLocalBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            _bookMarkList.postValue(getBookMarkUseCase.getAllBookMarks())
        }
    }

    fun saveBookMark(item: Item) {
        var _item = item.copy()
        viewModelScope.launch {
                // Check already bookmarked or not or null
                // Already bookmarked => remove it.
                when(item.isBookmarked) {
                    true -> {
                        _item.isBookmarked = false
                        deleteBookMarkUseCase.invoke(item)
                    }
                    else ->{
                        _item.isBookmarked = true
                        saveBookMarkUseCase.invoke(_item)
                    }
                }
                updateListBooks()
                getLocalBookmarks()
            }
    }

    fun updateListBooks(){
        CoroutineScope(Dispatchers.IO).launch {
            _bookListCache = _bookListCache.map {
                Item(it.id, getBookMarkUseCase.isItemBookMarked(it), it.volumeInfo)
            }
            _bookList.postValue(_bookListCache)
        }

    }
}