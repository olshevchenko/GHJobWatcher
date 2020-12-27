package com.olshevchenko.ghjobwatcher.detail

import android.app.Application
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.olshevchenko.ghjobwatcher.GitHubJob

/**
 * The ViewModel associated with the DetailFragment
 */
class DetailViewModel(
    gitHubJob: GitHubJob,
    app: Application
) : AndroidViewModel(app) {

    /** Internal mut. LiveData for selected job */
    private val _selectedJob = MutableLiveData<GitHubJob>()

    /** EXTERNAL IMMUT. LiveData for the response */
    val selectedJob: LiveData<GitHubJob>
        get() = _selectedJob

    init {
        _selectedJob.value = gitHubJob
    }

    /** Spanned form of corresponding 'howToApply' and 'description' fields */
    val displayHowToApply = Transformations.map(selectedJob) { Html2Spanned(it.howToApply) }
    val displayDescription = Transformations.map(selectedJob) { Html2Spanned(it.jobDescription) }

    /**
     * Convert HTML-formatted fields value into corresponding spanned form
     */
    private fun Html2Spanned(sourceHtmlStr: String): Spanned =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(sourceHtmlStr, Html.FROM_HTML_MODE_LEGACY)
        else
            @Suppress("DEPRECATION")
            Html.fromHtml(sourceHtmlStr)
}
