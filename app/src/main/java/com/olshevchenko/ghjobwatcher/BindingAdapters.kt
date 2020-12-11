package com.olshevchenko.ghjobwatcher

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.olshevchenko.ghjobwatcher.list.GitHubApiStatus
import com.olshevchenko.ghjobwatcher.list.JobListAdapter
import com.olshevchenko.ghjobwatcher.GitHubJob
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Hide the RecyclerView when there is no GitHub jobs data
 */
@BindingAdapter("listData")
fun bindRecyclerView(listRecyclerView: RecyclerView, data: List<GitHubJob>?) {
    val adapter = listRecyclerView.adapter as JobListAdapter
    adapter.submitList(data)
}

/**
 * Control SwipeRefreshLayout's refresh progress engine depending on the GitHubApiStatus value
 */
@BindingAdapter("gitHubApiStatus")
fun bindStatus(refreshLayout: SwipeRefreshLayout, status: GitHubApiStatus?) {
    when (status) {
        GitHubApiStatus.LOADING -> refreshLayout.isRefreshing = true
        GitHubApiStatus.ERROR -> refreshLayout.isRefreshing = false
        GitHubApiStatus.DONE -> refreshLayout.isRefreshing = false
    }
}

/**
 * Display the GitHubApiStatus of the network request in an image view during processing
 * (loading_animation or broken image if connection failed).
 * Hide the status image when the request is finished.
 */
@BindingAdapter("gitHubApiStatus")
fun bindStatus(statusImageView: ImageView, status: GitHubApiStatus?) {
    when (status) {
        GitHubApiStatus.LOADING -> {
//            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        GitHubApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        GitHubApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
