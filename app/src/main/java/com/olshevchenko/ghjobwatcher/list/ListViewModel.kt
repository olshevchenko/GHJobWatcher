package com.olshevchenko.ghjobwatcher.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olshevchenko.ghjobwatcher.GitHubDateUtils
import com.olshevchenko.ghjobwatcher.GitHubJob
import com.olshevchenko.ghjobwatcher.network.GitHubApi
import com.olshevchenko.ghjobwatcher.network.GitHubApiJob
import kotlinx.coroutines.launch


enum class GitHubApiStatus { LOADING, ERROR, DONE }

/**
 * ViewModel attached to the ListFragment
 */
class ListViewModel : ViewModel() {

    /** Internal mut. LiveData status that stores the state of most recent request */
    private val _status = MutableLiveData<GitHubApiStatus>()

    /** EXTERNAL IMMUT. LiveData for the request status */
    val status: LiveData<GitHubApiStatus>
        get() = _status

    /** Internal mut. LiveData List that stores the most recent response */
    private val _jobs = MutableLiveData<List<GitHubJob>>()

    /** EXTERNAL IMMUT. LiveData for the response */
    val jobs: LiveData<List<GitHubJob>>
        get() = _jobs

    /** LiveData to handle navigation to the selected job */
    private val _navigateToSelectedJob = MutableLiveData<GitHubJob>()
    val navigateToSelectedJob: LiveData<GitHubJob>
        get() = _navigateToSelectedJob

    init {
        reloadJobs() // call immediately on start to get status & fresh list content
    }

    /**
     * When the GitHub job is clicked, set the _navigateToSelectedJob
     */
    fun displayJobDetails(job: GitHubJob) {
        _navigateToSelectedJob.value = job
    }

    fun displayJobDetailsComplete() {
        _navigateToSelectedJob.value = null
    }

    /**
     * Convert GitHub API jobs list:
     * - fill corresponding GitHubJob item
     * - calculate dates fields & visibility flag
     */
    private fun convertApiJobs(jobsApiList: List<GitHubApiJob>): MutableList<GitHubJob> {
        var jobsList: MutableList<GitHubJob> = ArrayList()
        var prevJob: GitHubJob? = null

        for (jobApi in jobsApiList) {
            var job = GitHubJob(jobApi)
            if (null == prevJob)
                // always show Date of the 1'st item
                job.isDateVisible = true
            else {
                // show Date in UI if it differs from previous ones
                job.isDateVisible = (job.createdMNDate != prevJob.createdMNDate)
            }
            jobsList.add(job)
            prevJob = job
        }
        return jobsList
    }

    /**
     * Get API- jobs from GitHub API Retrofit service.
     * Update the GitHubJob list and GitHubApiStatus.
     * The Retrofit service returns a coroutine to get the result of the transaction.
     */
    private fun getGitHubJobs() {
        viewModelScope.launch {
            _status.value = GitHubApiStatus.LOADING
            try {
                val jobsApiList = GitHubApi.retrofitService.getApiJobs(0) // ToDo add pagination later!
                _jobs.value = convertApiJobs(jobsApiList)
                _status.value = GitHubApiStatus.DONE
            } catch (e: Exception) {
                _status.value = GitHubApiStatus.ERROR
                _jobs.value = ArrayList()
            }
        }
    }

    /**
     * Refresh current date
     * Get new job list from server and rebuild string form of job's creation dates according date refreshed
     */
    fun reloadJobs() {
        GitHubDateUtils.refreshDates()
        getGitHubJobs()
    }

}
