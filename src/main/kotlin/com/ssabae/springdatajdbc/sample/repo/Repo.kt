package com.ssabae.springdatajdbc.sample.repo

import com.ssabae.springdatajdbc.sample.account.Account
import org.springframework.core.Ordered
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.conversion.MutableAggregateChange
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Size

class Repo (
    @Id
    var id: String?,

    @NotBlank
    @Size(max = 100)
    var name: String,

    @Size(max = 255)
    var description: String,

    var createdBy: AggregateReference<Account, @NotNull UUID>,

    @NotNull
    @PastOrPresent
    var createdAt: Instant = Instant.now()
) {
    companion object {
        class RepoBeforeSaveCallback : BeforeSaveCallback<Repo>, Ordered{
            companion object {
                private val ID_PREFIX_FORMAT = DateTimeFormatter
                    .ofPattern("yyyyMMddHHmmss")
                    .withZone(ZoneId.of("Asia/Seoul"))

                fun generateId(repo: Repo): String {
                    return StringJoiner("-")
                        .add(ID_PREFIX_FORMAT.format(repo.createdAt))
                        .add(repo.name)
                        .toString()
                }
            }
            override fun onBeforeSave(aggregate: Repo, aggregateChange: MutableAggregateChange<Repo>): Repo {
                return aggregate.apply {
                    id = id ?: generateId(aggregate)
                }
            }

            override fun getOrder(): Int {
                return Ordered.LOWEST_PRECEDENCE
            }
        }
    }
}