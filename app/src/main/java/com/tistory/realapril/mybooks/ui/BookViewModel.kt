package com.tistory.realapril.mybooks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tistory.realapril.mybooks.entity.ImageLinks
import com.tistory.realapril.mybooks.entity.Item
import com.tistory.realapril.mybooks.entity.VolumeInfo
import kotlinx.coroutines.launch

class BookViewModel() : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _bookList = MutableLiveData<List<Item>>()
    val bookList: LiveData<List<Item>> = _bookList

    init {

        viewModelScope.launch {
            val items: ArrayList<Item> = ArrayList()
            val vol = VolumeInfo("Head First Android Development", arrayListOf("a","u"),"최신 인지과학 어쩌", ImageLinks("http://books.google.com/books/content?id=mtRmDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"))
            items.add(Item("mtRmDwAAQBAJ", "f", vol))

            val vol2 = VolumeInfo("빠른시작 안내", arrayListOf("Google"),"ANDROID 빠른 시작 안내서, Android 모바일 기술 플랫폼", ImageLinks("http://books.google.com/books/content?id=ayv3AgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"))
            items.add(Item("ayv3AgAAQBAJ", "f", vol2))

            _bookList.value = items
            //_bookList.value =
//            val result = getConcertsUseCase.invoke(realmCode.value!!, sido.value!!)
//
//            if(result is Result.Success) {
//                _concertResponse.value = result.data
//                _concertList.value = result.data.apiBody.perforList
//            }
//            else {
//                Log.e("LOG>>","Result error from viewModel : ${result}")
//            }

            _dataLoading.value = false
        }
    }
}