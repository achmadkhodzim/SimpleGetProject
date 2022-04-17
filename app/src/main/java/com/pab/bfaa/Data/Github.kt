package com.pab.bfaa.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Github(
    @SerializedName("name")
    var name: String?,
    @SerializedName("login")
    var username: String?,
    @SerializedName("followers")
    var followers: String,
    @SerializedName("following")
    var following: String,
    @SerializedName("company")
    var company: String?,
    @SerializedName("public_repos")
    var repository: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("avatar_url")
    var avatar: String?
): Parcelable
