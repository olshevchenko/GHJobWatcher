package com.olshevchenko.ghjobwatcher

import android.os.Parcelable
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.util.Log
import com.olshevchenko.ghjobwatcher.network.GitHubApiJob
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.FileDescriptor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.text.ParseException
import java.util.*


/**
 * Holder class for GitHubApiJob & Date-, String-, flag- fields for operation with creation date
 */
@Parcelize
class GitHubJob(
    val apiJob: GitHubApiJob,
    var createdMNDateString: String = "",
    var howToApplySpanned: @RawValue Spanned? = null, // ToDo NOT forget to write serilizer/deserializer!!
    var descriptionSpanned: @RawValue Spanned? = null, // ToDo ==""==
) : Parcelable {

    @IgnoredOnParcel
    private var _date: Date

    /** parse JSON creation date if possible. Otherwise, get current date (?) */
    init {
        try {
            _date = GitHubDateUtils.parseFromJSONDate(apiJob._createdAt)
        } catch (e: ParseException) {
            Log.w(
                "GitHubJob",
                "Job id ${apiJob.id} 'created_at' date parsing error => use current date.."
            )
            _date = Date()
        }
    }

    /** cast cr.date to midnight */
    @IgnoredOnParcel
    val createdMNDate = GitHubDateUtils.cast2Midnight(_date)

    /**
     * Get suitable string form for 'created_at' date, 'how_to_apply' & 'description' strings
     */
    init {
        createdMNDateString = GitHubDateUtils.formatToHomemadeDate(createdMNDate)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            howToApplySpanned = Html.fromHtml(apiJob.howToApply, FROM_HTML_MODE_LEGACY)
            descriptionSpanned = Html.fromHtml(apiJob.description, FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            howToApplySpanned = Html.fromHtml(apiJob.howToApply)
            @Suppress("DEPRECATION")
            descriptionSpanned = Html.fromHtml(apiJob.description)
        }
    }

    /** flag whether to show [createdMNDateString] of the job in UI list or not */
    @IgnoredOnParcel
    var isDateVisible = false
}
