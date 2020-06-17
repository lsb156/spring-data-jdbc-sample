package com.ssabae.springdatajdbc.sample.support

import org.springframework.data.jdbc.core.JdbcAggregateOperations

open class WithInsertImpl<T>(private val _jdbcAggregateOperations: JdbcAggregateOperations): WithInsert<T> {

    override fun getJdbcAggregateOperations(): JdbcAggregateOperations {
        return this._jdbcAggregateOperations
    }
}