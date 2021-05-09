package com.tistory.realapril.mybooks.ui

import android.util.Log
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
    val bookList: LiveData<List<Item>> = _bookList

    // LiveData of list of the user's bookmark.
    private var _bookMarkList = MutableLiveData<List<Item>>()
    private lateinit var _bookMarkListCache : List<Item>
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

                _bookMarkListCache = result.data.items!!.map {
                    Item(it.id, getBookMarkUseCase.isItemBookMarked(it), it.volumeInfo)
                }
                _bookList.postValue(_bookMarkListCache)
            }
            _dataLoading.value = false
        }
    }

    fun getLocalBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            _bookMarkList.postValue(getBookMarkUseCase.getAllBookMarks())
        }
    }

    fun saveBookMark(item: Item, idx: Int) {
        var item_ = item

            viewModelScope.launch {
                var changedBookmark = false;
                // Check already bookmarked or not or null
                // Already bookmarked => remove it.
                when(item.isBookmarked) {
                    true -> {
                        item_.isBookmarked = false
                        changedBookmark = false
                        deleteBookMarkUseCase.invoke(item)
                    }
                    else ->{
                        item_.isBookmarked = true
                        changedBookmark = true
                        saveBookMarkUseCase.invoke(item_)
                    }
                }

                getLocalBookmarks()
                updateListBooks(idx, changedBookmark)
                Log.e("클릭", idx.toString())
            }
    }

    fun updateListBooks(idx: Int, changedBookmark: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            _bookMarkListCache[idx].isBookmarked =changedBookmark
            _bookList.postValue(_bookMarkListCache)
        }
    }
}