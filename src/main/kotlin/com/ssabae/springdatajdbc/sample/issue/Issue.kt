package com.ssabae.springdatajdbc.sample.issue

import com.ssabae.springdatajdbc.sample.account.Account
import com.ssabae.springdatajdbc.sample.repo.Repo
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.*

class Issue (

    @Id
    var id: UUID? = null,

    @PositiveOrZero
    @Version
    var version: Long? = null,

    var repoId: AggregateReference<Repo, @NotBlank @Size(max = 200) String>,

    @NotNull
    @PositiveOrZero
    var issueNo: Long,

    @NotNull
    var status: Status,

    @NotBlank
    @Size(max = 200)
    var title: String,

    @Valid
    @Column("ISSUE_ID") // default: "ISSUE"
    var content: IssueContent,

    var createBy: AggregateReference<Account, @NotNull UUID>,

    @NotNull
    @PastOrPresent
    var createdAt: Instant = Instant.now()

) {
    fun changeContent(content: IssueContent) {
        this.content = content
    }
}