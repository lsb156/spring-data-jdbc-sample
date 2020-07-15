package com.ssabae.springdatajdbc.sample.issue.sql

import org.springframework.data.domain.Sort
import java.util.stream.Collectors.joining

class IssueSql {
    companion object {
        fun selectByRepoIdAndAttachedLabelsLabelId(sort: Sort): String {
            return """
            SELECT ISSUE.ID AS ID, ISSUE.VERSION AS VERSION, ISSUE.REPO_ID AS REPO_ID")
            , ISSUE.ISSUE_NO AS ISSUE_NO, ISSUE.STATUS AS STATUS, ISSUE.TITLE AS TITLE")
            , ISSUE.CREATED_BY AS CREATED_BY, ISSUE.CREATED_AT AS CREATED_AT")
            , CONTENT.BODY AS CONTENT_BODY, CONTENT.MIME_TYPE AS CONTENT_MIME_TYPE")
             FROM ISSUE")
             LEFT OUTER JOIN ISSUE_CONTENT CONTENT")
             ON ISSUE.ID = CONTENT.ISSUE_ID")
             LEFT OUTER JOIN ISSUE_ATTACHED_LABEL ATTACHED_LABELS")
             ON ISSUE.ID = ATTACHED_LABELS.ISSUE_ID")
             WHERE")
             REPO_ID = :repoId")
             AND ATTACHED_LABELS.LABEL_ID = :labelId")
             ORDER BY ${orderBy(sort)}
             LIMIT :pageSize OFFSET :offset")
            """.trimIndent()
        }

        fun countByRepoIdAndAttachedLabelsLabelId(): String {
            return """
            SELECT COUNT(*)"
             FROM ISSUE"
             LEFT OUTER JOIN ISSUE_CONTENT CONTENT"
             ON ISSUE.ID = CONTENT.ISSUE_ID"
             LEFT OUTER JOIN ISSUE_ATTACHED_LABEL ATTACHED_LABELS"
             ON ISSUE.ID = ATTACHED_LABELS.ISSUE_ID"
             WHERE"
             REPO_ID = :repoId"
             AND ATTACHED_LABELS.LABEL_ID = :labelId"
            """.trimIndent()
        }

        private fun orderBy(sort: Sort): String {
            return sort.stream()
                .map { "${it.property} ${it.direction.name}" }
                .collect(joining(", "))
        }
    }
}