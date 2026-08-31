package com.spoonart1.data.remote

import com.squareup.moshi.Moshi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GitHubApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: GitHubApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val moshi = Moshi.Builder().build()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GitHubApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `searchRepositories returns ok response mapped to RepositorySearchResponse`() = runTest {
        val json = javaClass.classLoader!!.getResourceAsStream("search_response.json")!!
            .bufferedReader()
            .use { it.readText() }
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val response = api.searchRepositories(query = "kotlin", page = 1)

        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/search/repositories?q=kotlin&sort=stars&order=desc&page=1&per_page=20", request.path)

        assertEquals(139106, response.totalCount)
        assertFalse(response.incompleteResults)
        assertNotNull(response.items)
        assertEquals(20, response.items.size)

        val first = response.items.first()
        assertEquals(14098069L, first.id)
        assertEquals("free-programming-books-zh_CN", first.name)
        assertEquals("justjavac/free-programming-books-zh_CN", first.fullName)
        assertEquals("justjavac", first.owner.login)
        assertEquals(118570, first.stargazersCount)
        assertEquals("gpl-3.0", first.license?.key)
        assertEquals(14, first.topics.size)
    }

    @Test
    fun `getRepository returns ok response mapped to RepositoryDto`() = runTest {
        val json = javaClass.classLoader!!.getResourceAsStream("repository_detail.json")!!
            .bufferedReader()
            .use { it.readText() }
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val response = api.getRepository(owner = "JetBrains", repo = "compose-multiplatform")

        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/repos/JetBrains/compose-multiplatform", request.path)

        assertEquals(293498508L, response.id)
        assertEquals("compose-multiplatform", response.name)
        assertEquals("JetBrains/compose-multiplatform", response.fullName)
        assertEquals("JetBrains", response.owner.login)
        assertEquals(19328, response.stargazersCount)
        assertEquals("Kotlin", response.language)
        assertEquals("apache-2.0", response.license?.key)
        assertEquals(16, response.topics.size)
        assertEquals("master", response.defaultBranch)
        assertNull(response.score)
    }

    @Test
    fun `getRepositoryTopics returns ok response mapped to TopicsDto`() = runTest {
        val json = javaClass.classLoader!!.getResourceAsStream("topics.json")!!
            .bufferedReader()
            .use { it.readText() }
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val response = api.getRepositoryTopics(owner = "JetBrains", repo = "compose-multiplatform")

        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/repos/JetBrains/compose-multiplatform/topics", request.path)

        assertEquals(16, response.names.size)
        assertEquals("kotlin", response.names.first())
        assertEquals("webassembly", response.names.last())
    }

    @Test
    fun `getUser returns ok response mapped to OwnerDto`() = runTest {
        val json = javaClass.classLoader!!.getResourceAsStream("user.json")!!
            .bufferedReader()
            .use { it.readText() }
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val response = api.getUser(username = "spoonart1")

        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/users/spoonart1", request.path)

        assertEquals("spoonart1", response.login)
        assertEquals(17171325L, response.id)
        assertEquals("User", response.type)
        assertFalse(response.siteAdmin)
    }

    @Test
    fun `getReadme returns ok response mapped to ReadmeDto`() = runTest {
        val json = javaClass.classLoader!!.getResourceAsStream("repository_readme.json")!!
            .bufferedReader()
            .use { it.readText() }
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val response = api.getReadme(owner = "JetBrains", repo = "compose-multiplatform")

        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/repos/JetBrains/compose-multiplatform/readme", request.path)

        assertEquals("README.md", response.name)
        assertEquals("README.md", response.path)
        assertEquals("ce2d34e9e5bd3040b64e6e3000cbad911688d850", response.sha)
        assertEquals(5136, response.size)
        assertEquals("file", response.type)
        assertEquals("base64", response.encoding)
        assertEquals(
            "https://github.com/JetBrains/compose-multiplatform/blob/master/README.md",
            response.links.html
        )
    }
}
