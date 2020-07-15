package com.ssabae.springdatajdbc.sample.label

import com.ssabae.springdatajdbc.sample.repo.Repo
import org.springframework.context.ApplicationListener
import org.springframework.core.Ordered
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Transient
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class Label {

    @Id
    var id: UUID? = UUID.randomUUID()

    var repoId: AggregateReference<Repo, @NotBlank @Size(max = 200) String>

    @NotBlank
    @Size(max = 100)
    var name: String

    @NotBlank
    @Size(max = 20)
    var color: String

    @PersistenceConstructor
    constructor(
        id: UUID?,
        repoId: AggregateReference<Repo, @NotBlank @Size(max = 200) String>,
        name: String,
        color: String,
        isNew: Boolean
    ) {
        this.id = id
        this.repoId = repoId
        this.name = name
        this.color = color
        this.isNew = false
    }

    constructor(
        repoId: AggregateReference<Repo, @NotBlank @Size(max = 200) String>,
        name: String,
        color: String
    ) {
        this.id = UUID.randomUUID()
        this.repoId = repoId
        this.name = name
        this.color = color
    }

    companion object {
        class LabelAfterSaveEventListener() : ApplicationListener<AfterSaveEvent<*>>, Ordered {
            override fun onApplicationEvent(event: AfterSaveEvent<*>) {
                val entity = event.entity
                if (entity is Label) {
                    entity.isNew = false
                }
            }
            override fun getOrder(): Int {
                return Ordered.HIGHEST_PRECEDENCE
            }
        }
    }

    @Transient
    var isNew: Boolean = true
}