package com.olshevchenko.ghjobwatcher.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olshevchenko.ghjobwatcher.GitHubJob

/**
 * Simple factory providing the GitHubJob and context to the ViewModel
 */
class DetailViewModelFactory(
    private val gitHubJob: GitHubJob,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(gitHubJob, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
