package com.ssabae.springdatajdbc.sample.issue

import com.ssabae.springdatajdbc.sample.label.Label
import com.ssabae.springdatajdbc.sample.repo.Repo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.util.*

interface IssueRepositoryCustom {
    fun findByRepoIdAndAttachedLabelsLabelId(
        repoId: AggregateReference<Repo, String>,
        labelId: AggregateReference<Label, UUID>,
        pageable: Pageable
    ): Page<Issue>
}
