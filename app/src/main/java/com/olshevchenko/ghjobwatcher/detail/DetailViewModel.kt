package com.olshevchenko.ghjobwatcher.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.olshevchenko.ghjobwatcher.GitHubJob

/// The ViewModel associated with the DetailFragment
class DetailViewModel(gitHubJob: GitHubJob,
                      app: Application) : AndroidViewModel(app) {

    /// Internal mut. LiveData for selected job
    private val _selectedJob = MutableLiveData<GitHubJob>()

    /// EXTERNAL IMMUT. LiveData for the response
    val selectedJob: LiveData<GitHubJob>
        get() = _selectedJob

    init {
        _selectedJob.value = gitHubJob
    }

    val displayJobTitle = Transformations.map(selectedJob) { it.apiJob.title }
    val displayCompany = Transformations.map(selectedJob) { it.apiJob.company }
    val displayDescription = Transformations.map(selectedJob) { it.apiJob.description }
    val displayHowToApply = Transformations.map(selectedJob) { it.apiJob.howToApply }
}
