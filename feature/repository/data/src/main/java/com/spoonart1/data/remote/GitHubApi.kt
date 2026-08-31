package com.spoonart1.data.remote

import com.spoonart1.data.remote.dto.OwnerDto
import com.spoonart1.data.remote.dto.ReadmeDto
import com.spoonart1.data.remote.dto.RepositoryDto
import com.spoonart1.data.remote.dto.TopicsDto
import com.spoonart1.data.remote.response.RepositorySearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): RepositorySearchResponse

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositoryDto

    @GET("repos/{owner}/{repo}/topics")
    suspend fun getRepositoryTopics(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): TopicsDto

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): OwnerDto

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : ReadmeDto

}