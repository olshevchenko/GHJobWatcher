package com.olshevchenko.ghjobwatcher.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olshevchenko.ghjobwatcher.databinding.ListViewItemBinding
import com.olshevchenko.ghjobwatcher.GitHubJob
import kotlinx.android.synthetic.main.list_view_item.view.*
import java.util.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class JobListAdapter(private val onClickListener: OnClickListener ) :
        ListAdapter<GitHubJob, JobListAdapter.GitHubJobViewHolder>(DiffCallback) {

    /**
     * The GitHubJobViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [GitHubJob] information.
     */
    class GitHubJobViewHolder(private var binding: ListViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(gitHubJob: GitHubJob) {
            binding.job = gitHubJob
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
     * Create new RecyclerView item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GitHubJobViewHolder {
        return GitHubJobViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: GitHubJobViewHolder, position: Int) {
        val job = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(job)
        }
        holder.bind(job)
    }

    /**
     * Handle clicks on items.
     * Pass the [GitHubJob] of the current item to the [onClick] function
     */
    class OnClickListener(val clickListener: (gitHubJob:GitHubJob) -> Unit) {
        fun onClick(gitHubJob:GitHubJob) = clickListener(gitHubJob)
    }
}