package com.ssabae.springdatajdbc.sample.issue

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface IssueRepository : PagingAndSortingRepository<Issue, UUID>, IssueRepositoryCustom {

    @Query("SELECT COUNT(*) FROM ISSUE WHERE title LIKE :titleStartAt AND status = :status")
    fun countByTitleLikeAndStatus(@Param("titleStartAt") titleStartAt: String, @Param("status") status: Status)

    @Modifying
    @Query("UPDATE ISSUE SET VERSION = VERSION + 1, STATUS = :status WHERE ID = :id")
    fun changeStatus(@Param("id") id: UUID, @Param("status") status: Status)
}