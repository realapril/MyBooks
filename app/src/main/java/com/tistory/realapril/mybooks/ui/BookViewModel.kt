package com.tistory.realapril.mybooks.ui

import android.util.Log
import androidx.lifecycle.*
import com.tistory.realapril.mybooks.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList
import com.tistory.realapril.mybooks.data.Result
import com.tistory.realapril.mybooks.entity.*


class BookViewModel(
    private val saveBookMarkUseCase: SaveBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val getBookMarkUseCase: GetBookMarkUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    // LiveData of whole performance response from public API
    private val _bookResponse = MutableLiveData<BookInfo>()
    val bookResponse: LiveData<BookInfo> = _bookResponse

    private var _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // LiveData of all books saved in Room
//    private var _bookList = MutableLiveData<List<Item>>()
//    val bookList: LiveData<List<Item>> = _bookList

    private var _bookList2 = MutableLiveData<List<Item>>()
    val bookList2: LiveData<List<Item>> = _bookList2


    // LiveData of the item the user selects from the list.
    private var _selectedConcertItem = MutableLiveData<Item>()
    val selectedConcertItem: LiveData<Item> = _selectedConcertItem

    // LiveData of checking whether a selectedConcertItem is a bookmarked
    private var _isBookMarked = MutableLiveData<Boolean>(false)
    val isBookMarked: LiveData<Boolean> = _isBookMarked

    // LiveData of list of the user's bookmark. Using Paging library
    private var _bookMarkList = MutableLiveData<List<Item>>()
    val bookMarkList: LiveData<List<Item>> = _bookMarkList

    init {
        loadRemoteBooks()
        getLocalBookmarks()
    }

    fun loadRemoteBooks(){
        _dataLoading.value = true
        viewModelScope.launch {
            val result = getBooksUseCase.invoke()
            if(result is Result.Success) {
                //_bookList.value = result.data.items
                _bookResponse.value = result.data

                CoroutineScope(Dispatchers.IO).launch {
                    var value = result.data.items!!.map {
                        Item(it.id, getBookMarkUseCase.isItemBookMarked(it), it.volumeInfo)
                    }
                    Log.e("결과", value.toString())
                    _bookList2.postValue(value)
                }
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
        var item_ = item

            viewModelScope.launch {
                // Check already bookmarked or not or null
                // Already bookmarked => remove it.
                when(item.isBookmarked) {
                    true -> {
                        item_.isBookmarked = false
                        deleteBookMarkUseCase.invoke(item)
                    }
                    else ->{
                        item_.isBookmarked = true
                        saveBookMarkUseCase.invoke(item_)
                    }
                }

                var res = getBookMarkUseCase.isItemBookMarked(item)
                Log.e("saveBookmark 저장",res.toString())

                getLocalBookmarks()

            }
    }
}