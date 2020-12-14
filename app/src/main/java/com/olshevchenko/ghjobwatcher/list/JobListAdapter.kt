package com.olshevchenko.ghjobwatcher.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olshevchenko.ghjobwatcher.GitHubJob
import com.olshevchenko.ghjobwatcher.databinding.ListViewItemBinding
import kotlinx.android.synthetic.main.list_view_item.view.*
import java.util.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class JobListAdapter(val clickListener: JobClickListener) :
    ListAdapter<GitHubJob, JobListAdapter.GitHubJobViewHolder>(DiffCallback) {


    /**
     * The GitHubJobViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [GitHubJob] information.
     */
    class GitHubJobViewHolder(private var binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: JobClickListener, job: GitHubJob) {
            binding.job = job
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when
     * the list of [GitHubJob] has been updated
     */
    companion object DiffCallback : DiffUtil.ItemCallback<GitHubJob>() {
        override fun areItemsTheSame(oldItem: GitHubJob, newItem: GitHubJob): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GitHubJob, newItem: GitHubJob): Boolean {
            return oldItem.apiJob.id == newItem.apiJob.id
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder]
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GitHubJobViewHolder {
        return GitHubJobViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item
     */
    override fun onBindViewHolder(holder: GitHubJobViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

/**
 * Handle clicks on items
 * Pass the [GitHubJob] of the current item to the [onClick] function
 */
class JobClickListener(val clickListener: (job: GitHubJob) -> Unit) {
    fun onClick(job: GitHubJob) = clickListener(job)
}
