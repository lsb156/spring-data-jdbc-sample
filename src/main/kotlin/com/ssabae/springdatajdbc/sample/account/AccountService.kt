package com.ssabae.springdatajdbc.sample.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    @Autowired private val accountRepository: AccountRepository
) {
    fun insert(account: Account): Account {
        return accountRepository.insert(account)
    }

    fun delete(entity: Account) {
        accountRepository.delete(entity)
    }

    fun findById(id: UUID): Optional<Account> {
        return accountRepository.findById(id)
    }

    fun findByIdAndStateIn(uuid: UUID, states: Set<AccountState>): Optional<Account> {
        return accountRepository.findByIdAndStateIn(uuid, states)
    }

    fun findByIdExcludeDeleted(uuid: UUID): Optional<Account> {
        return this.findByIdAndStateIn(uuid, setOf(AccountState.ACTIVE, AccountState.LOCK))
    }

}