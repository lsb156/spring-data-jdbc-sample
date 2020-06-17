package com.ssabae.springdatajdbc.sample.account

import com.ssabae.springdatajdbc.sample.support.WithInsert
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, UUID>, AccountRepositoryCustom, WithInsert<Account> {

    override fun deleteById(id: UUID)

    override fun delete(entity: Account)

    override fun deleteAll(entities: MutableIterable<Account>)

    override fun deleteAll()

    fun findByIdAndStateIn(uuid: UUID, states: Set<AccountState>): Optional<Account>
}