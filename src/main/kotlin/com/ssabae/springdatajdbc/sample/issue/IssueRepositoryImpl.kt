package com.ssabae.springdatajdbc.sample.issue

import com.ssabae.springdatajdbc.sample.issue.sql.IssueSql
import com.ssabae.springdatajdbc.sample.label.Label
import com.ssabae.springdatajdbc.sample.repo.Repo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.core.convert.EntityRowMapper
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity
import org.springframework.data.repository.support.PageableExecutionUtils
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import java.util.*

class IssueRepositoryImpl(
    private val jdbcOperations: NamedParameterJdbcOperations,
    mappingContext: RelationalMappingContext,
    jdbcConverter: JdbcConverter
) : IssueRepositoryCustom {

    private val rowMapper: EntityRowMapper<Issue> = EntityRowMapper(
        mappingContext.getRequiredPersistentEntity((Issue::class.java)) as RelationalPersistentEntity<Issue>,
        jdbcConverter
    )

    override fun findByRepoIdAndAttachedLabelsLabelId(
        repoId: AggregateReference<Repo, String>,
        labelId: AggregateReference<Label, UUID>,
        pageable: Pageable
    ): Page<Issue> {
        val parameterSource = MapSqlParameterSource()
            .addValue("repoId", repoId.id)
            .addValue("labelId", labelId)
            .addValue("offset", pageable.offset)
            .addValue("pageSize", pageable.pageSize)

        val issues = this.jdbcOperations.query(
            IssueSql.selectByRepoIdAndAttachedLabelsLabelId(pageable.sort), parameterSource, this.rowMapper)

        return PageableExecutionUtils.getPage(issues, pageable) {
            this.jdbcOperations.queryForObject(IssueSql.countByRepoIdAndAttachedLabelsLabelId(), parameterSource, Long::class.java) ?: 0
        }
    }
}