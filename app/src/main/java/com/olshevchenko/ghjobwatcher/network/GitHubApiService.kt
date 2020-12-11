package com.olshevchenko.ghjobwatcher.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://jobs.github.com/"

/**
* Build the Moshi object for Retrofit, adding the Kotlin adapter
*/
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GitHubApiService {
    /**
     * Returns a Coroutine List of GitHubApiJob which can be fetched in a Coroutine scope
     */
    @GET("positions.json")
    suspend fun getApiJobs(@Query("page") pageNum: Int): List<GitHubApiJob>
}

object GitHubApi {
    val retrofitService : GitHubApiService by lazy {
        retrofit.create( GitHubApiService::class.java ) }
}