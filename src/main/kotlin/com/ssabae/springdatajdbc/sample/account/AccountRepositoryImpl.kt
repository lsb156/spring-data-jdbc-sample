package com.ssabae.springdatajdbc.sample.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.TransientDataAccessResourceException
import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class AccountRepositoryImpl(
    @Autowired private val jdbcAggregateOperations: JdbcAggregateOperations
) : AccountRepositoryCustom {

    @Transactional
    open fun deleteById(id: UUID) {
        val account: Account = this.jdbcAggregateOperations.findById(id, Account::class.java) ?: throw TransientDataAccessResourceException("account does not exist.id: $id")
        this.delete(account)
    }

    @Transactional
    open fun delete(entity: Account) {
        entity.delete()
        this.jdbcAggregateOperations.update(entity)
    }

    @Transactional
    open fun deleteAll(entities: Iterable<Account>) {
        entities.forEach(::delete)
    }

    @Transactional
    open fun deleteAll() {
        val accounts = this.jdbcAggregateOperations.findAll(Account::class.java)
        this.deleteAll(accounts)
    }

}