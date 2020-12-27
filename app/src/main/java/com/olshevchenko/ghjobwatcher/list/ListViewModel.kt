package com.olshevchenko.ghjobwatcher.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olshevchenko.ghjobwatcher.GitHubApiState
import com.olshevchenko.ghjobwatcher.GitHubJob
import com.olshevchenko.ghjobwatcher.network.GitHubApi
import com.olshevchenko.ghjobwatcher.network.GitHubApiJob
import com.olshevchenko.ghjobwatcher.utils.GitHubDateUtils
import kotlinx.coroutines.launch
import java.text.ParseException


/**
 * ViewModel attached to the ListFragment
 */
class ListViewModel : ViewModel() {

    /** Internal mut. LiveData status that stores the state of most recent request */
    private val _state = MutableLiveData<GitHubApiState>()

    /** EXTERNAL IMMUT. LiveData for the request status */
    val state: LiveData<GitHubApiState>
        get() = _state

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
        reloadJobs() // call immediately on start to get state & fresh list content
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
    private fun convertApiJobs(jobApiList: List<GitHubApiJob>): MutableList<GitHubJob> {
        var jobList: MutableList<GitHubJob> = ArrayList()
        var job: GitHubJob

        for (jobApi in jobApiList) {
            try {
                job = GitHubJob(jobApi)
            } catch (e: ParseException) {
                Log.w(
                    "convertApiJobs()",
                    "Got incorrect job from server (${e.message}) => skipping it.."
                )
                continue //ignore job without ID
            }
            jobList.add(job)
        }

        jobList.sortByDescending { it.createdMNDate } // sort jobs from recent downto older ones

        /** loop through the jobs to define date visibility for each job */
        var prevJob: GitHubJob? = null
        for (job in jobList) {
            if (null == prevJob)
            // always show Date of the 1'st item
                job.isDateVisible = true
            else {
                // show Date in UI if it differs from previous ones
                job.isDateVisible = (job.createdMNDate != prevJob.createdMNDate)
            }
            prevJob = job
        }
        return jobList
    }

    /**
     * Get API- jobs from GitHub API Retrofit service.
     * Update the GitHubJob list and GitHubApiStatus.
     * The Retrofit service returns a coroutine to get the result of the transaction.
     */
    private fun getGitHubJobs() {
        viewModelScope.launch {
            _state.value = GitHubApiState.RUNNING
            try {
                val jobsApiList = GitHubApi.retrofitService.getApiJobs(0) // ToDo add pagination later!
                _jobs.value = convertApiJobs(jobsApiList)
                _state.value = GitHubApiState.OK
            } catch (e: Exception) {
                _state.value = GitHubApiState.error(e.message)
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
