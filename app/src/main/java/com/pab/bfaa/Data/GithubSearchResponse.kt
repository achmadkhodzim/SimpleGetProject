package com.pab.bfaa.Data

import com.google.gson.annotations.SerializedName

class GithubSearchResponse {
    @SerializedName("total_count")
    var total: Int = 0
    @SerializedName("items")
    var listGithub  = ArrayList<SearchData>()
}
class SearchData {
    @SerializedName("login")
    var username: String? = null
    @SerializedName("avatar_url")
    var avatar: String? = null
}