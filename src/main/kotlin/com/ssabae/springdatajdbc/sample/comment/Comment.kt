package com.ssabae.springdatajdbc.sample.comment

import com.ssabae.springdatajdbc.sample.account.Account
import com.ssabae.springdatajdbc.sample.issue.Issue
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.PositiveOrZero

class Comment (

    @Id
    var id: Long? = null,

    @PositiveOrZero
    @Version
    var version: Long? = null,

    var issueId: AggregateReference<Issue, UUID>,

    @Valid
    @Column("ID") // PK Mapping, default: "COMMENT"
    var content: CommentContent,

    @PastOrPresent
    var createdAt: Instant = Instant.now(),

    var createdBy: AggregateReference<Account, UUID>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Comment(id=$id, version=$version, issueId=$issueId, content=$content, createdAt=$createdAt, createdBy=$createdBy)"
    }
}