package com.ssabae.springdatajdbc.sample.repo

import org.springframework.data.relational.core.conversion.MutableAggregateChange
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Repo (
    var id: String?,
    var name: String,
    var createdAt: Instant
) {
    companion object {
        class RepoBeforeSaveCallback : BeforeSaveCallback<Repo> {
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
        }
    }


}