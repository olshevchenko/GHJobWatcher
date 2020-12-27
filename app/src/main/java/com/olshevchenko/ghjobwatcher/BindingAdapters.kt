package com.olshevchenko.ghjobwatcher

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.olshevchenko.ghjobwatcher.list.JobListAdapter
import com.olshevchenko.ghjobwatcher.utils.GlideWrapper


@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imgUrl: String?) {
    imgUrl?.let {
        GlideWrapper().loadImage(view, it, view.context)
    }
}

@BindingAdapter("cachedImageUrl")
fun bindCachedImage(view: ImageView, imgUrl: String?) {
    imgUrl?.let {
        GlideWrapper().loadCachedImage(view, imgUrl)
    }
}

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
fun bindStatus(refreshLayout: SwipeRefreshLayout, state: GitHubApiState?) {
    when (state?.status) {
        GitHubApiState.Status.LOADING -> refreshLayout.isRefreshing = true
        GitHubApiState.Status.ERROR -> refreshLayout.isRefreshing = false
        GitHubApiState.Status.DONE -> refreshLayout.isRefreshing = false
    }
}

/**
 * Display the GitHubApiStatus of the network request in an image view during processing
 * (broken image if connection failed).
 * Hide the status image when the request is finished.
 */
@BindingAdapter("gitHubApiStatus")
fun bindStatus(statusImageView: ImageView, state: GitHubApiState?) {
    when (state?.status) {
        GitHubApiState.Status.LOADING -> {
            statusImageView.visibility = View.GONE
        }
        GitHubApiState.Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        GitHubApiState.Status.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
