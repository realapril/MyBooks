package com.tistory.realapril.mybooks.ui

import android.util.Log
import androidx.lifecycle.*
import com.tistory.realapril.mybooks.domain.*
import com.tistory.realapril.mybooks.entity.ImageLinks
import com.tistory.realapril.mybooks.entity.Item
import com.tistory.realapril.mybooks.entity.VolumeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList
import com.tistory.realapril.mybooks.data.Result
import com.tistory.realapril.mybooks.entity.ApiResponse


class BookViewModel(
    private val saveBookMarkUseCase: SaveBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val getBookMarkUseCase: GetBookMarkUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    // LiveData of whole performance response from public API
    private val _bookResponse = MutableLiveData<ApiResponse>()
    val bookResponse: LiveData<ApiResponse> = _bookResponse

    private var _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private var _bookList = MutableLiveData<List<Item>>()
    val bookList: LiveData<List<Item>> = _bookList

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
                _bookList.value = result.data.apiBody.items
                _bookResponse.value = result.data
            }
            _dataLoading.value = false
        }
    }

    fun getLocalBookmarks(){
        CoroutineScope(Dispatchers.IO).launch {
            _bookMarkList.postValue(getBookMarkUseCase.getAllBookMarks())
        }
    }

    private fun setTestData(): ArrayList<Item>{
        val items: ArrayList<Item> = ArrayList()
        val vol = VolumeInfo("Head First Android Development", arrayListOf("a","u"),"최신 인지과학 어쩌", ImageLinks("http://books.google.com/books/content?id=mtRmDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"))
        items.add(Item("mtRmDwAAQBAJ", null, vol))

        val vol2 = VolumeInfo("안드 빠른시작 안내", arrayListOf("Google"),"ANDROID 빠른 시작 안내서, Android 모바일 기술 플랫폼", ImageLinks("http://books.google.com/books/content?id=ayv3AgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"))
        items.add(Item("ayv3AgAAQBAJ", null, vol2))

        return items
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
//                var _bookList= Transformations.map(bookList) {
//                    it.forEach { item-> getBookMarkUseCase.isItemBookMarked(item) }
//                }
                getLocalBookmarks()

            }
    }
}