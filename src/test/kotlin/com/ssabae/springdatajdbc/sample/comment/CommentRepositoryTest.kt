package com.ssabae.springdatajdbc.sample.comment

import com.ssabae.springdatajdbc.sample.account.Account
import com.ssabae.springdatajdbc.sample.issue.Issue
import com.ssabae.springdatajdbc.sample.test.DataInitializeExecutionListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.test.context.TestExecutionListeners
import java.util.*

@SpringBootTest
@TestExecutionListeners(
    listeners = [ DataInitializeExecutionListener::class ],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
internal class CommentRepositoryTest {

    @Autowired
    lateinit var sut: CommentRepository

    private final val issueId = AggregateReference.to<Issue, UUID>(UUID.randomUUID())
    private final val creatorId = AggregateReference.to<Account, UUID>(UUID.randomUUID())
    private final val comments = listOf(
        Comment(
            issueId = this.issueId,
            content = CommentContent(body = "comment 1", mimeType = "text/plain"),
            createdBy = this.creatorId
        ),
        Comment(
            issueId = this.issueId,
            content = CommentContent(body = "comment 2", mimeType = "text/plain"),
            createdBy = this.creatorId
        )
    )

    @Test
    fun insert() {
        // given
        val comment = this.comments[0]

        // when
        val actual = this.sut.save(comment)

        // then
        assertThat(actual.version).isEqualTo(0L)
        assertThat(comment.content).isSameAs(actual.content)

        val load = this.sut.findById(comment.id!!)
        assertThat(load).isPresent()
        assertThat(load.get().createdBy).isEqualTo(this.creatorId)
        assertThat(load.get().content).isNotNull()
        assertThat(load.get().content.body).isEqualTo("comment 1")
        assertThat(load.get().content.mimeType).isEqualTo("text/plain")
    }
}