package com.olshevchenko.ghjobwatcher.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubApiJob(
        val id: String,
//        @Json(name = "type") private val employmentType: String,
//        @Json(name = "url") private val jobUrl: String,
        @Json(name = "created_at") val _createdAt: String,
        val company: String = "N/A",
//        @Json(name = "company_url") private val companyUrl: String,
//        private val location: String,
        val title: String = "N/A",
        val description: String = "N/A",
        @Json(name = "how_to_apply") val howToApply: String = "N/A",
//        private val companyLogo: String
) : Parcelable
