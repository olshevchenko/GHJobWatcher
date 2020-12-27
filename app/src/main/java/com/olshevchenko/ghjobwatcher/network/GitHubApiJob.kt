package com.olshevchenko.ghjobwatcher.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubApiJob(
    val id: String?,
    val type: String?,
//    @Json(name = "url")
    val created_at: String?,
    val company: String?,
    val company_url: String?,
//    @Json(name = "location")
    val title: String?,
    val description: String?,
    val how_to_apply: String?,
    val company_logo: String?
) : Parcelable
