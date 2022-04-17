package com.pab.bfaa.Rest

import com.pab.bfaa.Data.Github
import com.pab.bfaa.Data.GithubSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUser(@Query("q") q : String): Call<GithubSearchResponse>
    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUser(@Path("username")  userName:String): Call<Github>
    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username")  userName:String): Call<List<Github>>
    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username")  userName:String): Call<List<Github>>
}