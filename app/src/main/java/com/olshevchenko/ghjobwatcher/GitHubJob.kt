package com.olshevchenko.ghjobwatcher

import android.os.Parcelable
import android.util.Log
import com.olshevchenko.ghjobwatcher.network.GitHubApiJob
import com.olshevchenko.ghjobwatcher.utils.GitHubDateUtils
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.ParseException
import java.util.*


/**
 * Holder class for GitHubApiJob & Date-, String-, flag- fields for operation with creation date
 */
@Parcelize
class GitHubJob(
    private val apiJob: GitHubApiJob,
    var createdMNDateString: String = ""
) : Parcelable {

    companion object {
        const val EMPLOYMENT_TYPE_EMPTY = "N/A"
        const val CREATED_AT_EMPTY = ""
        const val COMPANY_NAME_EMPTY = "N/A"
        const val COMPANY_URL_EMPTY = ""
        const val JOB_TITLE_EMPTY = "N/A"
        const val JOB_DESCRIPTION_EMPTY = "N/A"
        const val HOW_TO_APPLY_EMPTY = "N/A"
        const val COMPANY_LOGO_EMPTY = ""
    }

    /**
     * Mapping from nullable API fields to clear model ones
     */
    val id = apiJob.id ?: throw ParseException("Job with id='null'", 0)
    val employmentType = apiJob.type ?: EMPLOYMENT_TYPE_EMPTY
    val createdAt = apiJob.created_at ?: CREATED_AT_EMPTY
    val companyName = apiJob.company ?: COMPANY_NAME_EMPTY
    val companyUrl = apiJob.company_url ?: COMPANY_URL_EMPTY
    val jobTitle = apiJob.title ?: JOB_TITLE_EMPTY
    val jobDescription = apiJob.description ?: JOB_DESCRIPTION_EMPTY
    val howToApply = apiJob.how_to_apply ?: HOW_TO_APPLY_EMPTY
    val companyLogoUrl = apiJob.company_logo ?: COMPANY_LOGO_EMPTY

    @IgnoredOnParcel
    private var _date: Date

    /** parse JSON creation date if possible. Otherwise, get current date (?) */
    init {
        try {
            _date = GitHubDateUtils.parseFromJSONDate(createdAt)
        } catch (e: ParseException) {
            Log.w(
                "GitHubJob",
                "Job id ${apiJob.id} 'created_at' date parsing error (${e}) => use current date.."
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
    }

    /** flag whether to show [createdMNDateString] of the job in UI list or not */
    @IgnoredOnParcel
    var isDateVisible = false
//
//    override fun compareTo(other: GitHubJob): Int {
////        if (this.createdMNDate != other.createdMNDate) {
////            return (this.createdMNDate - other.createdMNDate).toInt()
////        } else return 0
//        return 0;
//    }
}
