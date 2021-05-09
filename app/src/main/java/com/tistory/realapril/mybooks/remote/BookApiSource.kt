package com.tistory.realapril.mybooks.remote

import com.tistory.realapril.mybooks.entity.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface BookApiSource {

    /**
     * Google Book Search API
     * @return ApiResponse
     * */
    @GET("books/v1/volumes")
    suspend fun getBooks( @Query("q") keyWord: String,
    @Query("maxResults") maxResults: Int,
    @Query("fields") fields: String
    ):Response<ApiResponse>


}