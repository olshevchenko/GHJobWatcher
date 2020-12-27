package com.olshevchenko.ghjobwatcher

/**
 * class displaying state of network operation with GitHub
 */
data class GitHubApiState private constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val RUNNING = GitHubApiState(Status.LOADING)
        val OK = GitHubApiState(Status.DONE)
        fun error(errMsg: String?) = GitHubApiState(Status.ERROR, msg = errMsg)
    }

    enum class Status { LOADING, ERROR, DONE }
}