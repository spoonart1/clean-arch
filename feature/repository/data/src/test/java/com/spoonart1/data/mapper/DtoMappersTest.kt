package com.spoonart1.data.mapper

import com.spoonart1.data.remote.dto.LicenseDto
import com.spoonart1.data.remote.dto.OwnerDto
import com.spoonart1.data.remote.dto.ReadmeDto
import com.spoonart1.data.remote.dto.ReadmeLinksDto
import com.spoonart1.data.remote.dto.RepositoryDto
import com.spoonart1.data.remote.dto.TopicsDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DtoMappersTest {

    private fun ownerDto(
        login: String = "octocat",
        avatarUrl: String = "https://avatars.example.com/octocat.png"
    ) = OwnerDto(
        login = login,
        id = 1L,
        nodeId = "node-1",
        avatarUrl = avatarUrl,
        gravatarId = "",
        url = "https://api.github.com/users/$login",
        htmlUrl = "https://github.com/$login",
        followersUrl = "https://api.github.com/users/$login/followers",
        followingUrl = "https://api.github.com/users/$login/following{/other_user}",
        gistsUrl = "https://api.github.com/users/$login/gists{/gist_id}",
        starredUrl = "https://api.github.com/users/$login/starred{/owner}{/repo}",
        subscriptionsUrl = "https://api.github.com/users/$login/subscriptions",
        organizationsUrl = "https://api.github.com/users/$login/orgs",
        reposUrl = "https://api.github.com/users/$login/repos",
        eventsUrl = "https://api.github.com/users/$login/events{/privacy}",
        receivedEventsUrl = "https://api.github.com/users/$login/received_events",
        type = "User",
        userViewType = "public",
        siteAdmin = false
    )

    private fun repositoryDto(
        id: Long = 42L,
        name: String = "clean-arch",
        description: String? = "A clean architecture sample",
        owner: OwnerDto = ownerDto()
    ) = RepositoryDto(
        id = id,
        nodeId = "node-repo",
        name = name,
        fullName = "${owner.login}/$name",
        private = false,
        owner = owner,
        htmlUrl = "https://github.com/${owner.login}/$name",
        description = description,
        fork = false,
        url = "https://api.github.com/repos/${owner.login}/$name",
        homepage = null,
        size = 100,
        stargazersCount = 10,
        watchersCount = 10,
        language = "Kotlin",
        hasIssues = true,
        hasProjects = true,
        hasWiki = true,
        hasPages = false,
        hasDiscussions = false,
        forksCount = 2,
        archived = false,
        disabled = false,
        openIssuesCount = 1,
        license = null,
        allowForking = true,
        isTemplate = false,
        topics = listOf("kotlin", "android"),
        visibility = "public",
        forks = 2,
        openIssues = 1,
        watchers = 10,
        defaultBranch = "main",
        createdAt = "2020-01-01T00:00:00Z",
        updatedAt = "2020-01-02T00:00:00Z",
        pushedAt = "2020-01-03T00:00:00Z",
        score = null
    )

    @Test
    fun `RepositoryDto toModel maps id name description and owner`() {
        val dto = repositoryDto()

        val model = dto.toModel()

        assertEquals(dto.id, model.id)
        assertEquals(dto.name, model.name)
        assertEquals(dto.description, model.description)
        assertEquals(dto.owner.login, model.owner.username)
        assertEquals(dto.owner.avatarUrl, model.owner.avatarUrl)
    }

    @Test
    fun `RepositoryDto toModel preserves null description`() {
        val dto = repositoryDto(description = null)

        val model = dto.toModel()

        assertNull(model.description)
    }

    @Test
    fun `OwnerDto toModel maps login to username and avatarUrl`() {
        val dto = ownerDto(login = "spoonart1", avatarUrl = "https://avatars.example.com/spoonart1.png")

        val model = dto.toModel()

        assertEquals("spoonart1", model.username)
        assertEquals("https://avatars.example.com/spoonart1.png", model.avatarUrl)
    }

    @Test
    fun `LicenseDto toModel maps all fields`() {
        val dto = LicenseDto(
            key = "mit",
            name = "MIT License",
            spdxId = "MIT",
            url = "https://api.github.com/licenses/mit",
            nodeId = "node-license"
        )

        val model = dto.toModel()

        assertEquals(dto.key, model.key)
        assertEquals(dto.name, model.name)
        assertEquals(dto.spdxId, model.spdxId)
        assertEquals(dto.url, model.url)
    }

    @Test
    fun `LicenseDto toModel preserves null spdxId and url`() {
        val dto = LicenseDto(
            key = "other",
            name = "Other",
            spdxId = null,
            url = null,
            nodeId = "node-license"
        )

        val model = dto.toModel()

        assertNull(model.spdxId)
        assertNull(model.url)
    }

    @Test
    fun `TopicsDto toModel maps names list`() {
        val dto = TopicsDto(names = listOf("kotlin", "clean-architecture"))

        val model = dto.toModel()

        assertEquals(dto.names, model.names)
    }

    @Test
    fun `ReadmeDto toModel maps display fields`() {
        val dto = ReadmeDto(
            name = "README.md",
            path = "README.md",
            sha = "abc123",
            size = 1234,
            url = "https://api.github.com/repos/owner/repo/contents/README.md",
            htmlUrl = "https://github.com/owner/repo/blob/main/README.md",
            gitUrl = "https://api.github.com/repos/owner/repo/git/blobs/abc123",
            downloadUrl = "https://raw.githubusercontent.com/owner/repo/main/README.md",
            type = "file",
            content = "IyBIZWxsbw==",
            encoding = "base64",
            links = ReadmeLinksDto(
                self = "https://api.github.com/repos/owner/repo/contents/README.md",
                git = "https://api.github.com/repos/owner/repo/git/blobs/abc123",
                html = "https://github.com/owner/repo/blob/main/README.md"
            )
        )

        val model = dto.toModel()

        assertEquals(dto.name, model.name)
        assertEquals(dto.path, model.path)
        assertEquals(dto.htmlUrl, model.htmlUrl)
        assertEquals(dto.downloadUrl, model.downloadUrl)
        assertEquals(dto.content, model.content)
        assertEquals(dto.encoding, model.encoding)
    }

    @Test
    fun `ReadmeDto toModel preserves null downloadUrl`() {
        val dto = ReadmeDto(
            name = "README.md",
            path = "README.md",
            sha = "abc123",
            size = 1234,
            url = "https://api.github.com/repos/owner/repo/contents/README.md",
            htmlUrl = "https://github.com/owner/repo/blob/main/README.md",
            gitUrl = "https://api.github.com/repos/owner/repo/git/blobs/abc123",
            downloadUrl = null,
            type = "file",
            content = "IyBIZWxsbw==",
            encoding = "base64",
            links = ReadmeLinksDto(
                self = "https://api.github.com/repos/owner/repo/contents/README.md",
                git = "https://api.github.com/repos/owner/repo/git/blobs/abc123",
                html = "https://github.com/owner/repo/blob/main/README.md"
            )
        )

        val model = dto.toModel()

        assertNull(model.downloadUrl)
    }
}
