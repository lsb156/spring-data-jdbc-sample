package com.ssabae.springdatajdbc.sample.support

import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.transaction.annotation.Transactional

interface WithInsert<T> {
    fun getJdbcAggregateOperations(): JdbcAggregateOperations

    @Transactional
    fun insert(t: T): T {
        return this.getJdbcAggregateOperations().insert(t)
    }
}