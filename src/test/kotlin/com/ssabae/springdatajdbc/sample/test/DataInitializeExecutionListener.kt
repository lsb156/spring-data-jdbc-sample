package com.ssabae.springdatajdbc.sample.test

import com.ssabae.springdatajdbc.sample.account.Account
import com.ssabae.springdatajdbc.sample.comment.Comment
import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class DataInitializeExecutionListener : AbstractTestExecutionListener() {
    override fun afterTestMethod(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val jdbcAggregateOperations = applicationContext.getBean(JdbcAggregateOperations::class.java)
        jdbcAggregateOperations.deleteAll(Account::class.java)
//        jdbcAggregateOperations.deleteAll(Issue::class.java)
//        jdbcAggregateOperations.deleteAll(Label::class.java)
//        jdbcAggregateOperations.deleteAll(Repo::class.java)
        jdbcAggregateOperations.deleteAll(Comment::class.java)

    }
}