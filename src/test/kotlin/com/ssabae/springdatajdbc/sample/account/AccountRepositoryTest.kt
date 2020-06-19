package com.ssabae.springdatajdbc.sample.account

import com.ssabae.springdatajdbc.sample.support.EncryptString
import com.ssabae.springdatajdbc.sample.test.DataInitializeExecutionListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import java.util.*

@SpringBootTest
@TestExecutionListeners(
    listeners = [ DataInitializeExecutionListener::class ],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
internal class AccountRepositoryTest {
    @Autowired
    lateinit var sut: AccountService

    private val accounts = listOf(
        Account(
            id = UUID.randomUUID(),
            loginId = "test.com",
            name = "test",
            state = AccountState.ACTIVE,
            email = EncryptString("test@test.com")
        ),
        Account(
            id = UUID.randomUUID(),
            loginId = "ssabae.lee",
            name = "ssabae",
            state = AccountState.ACTIVE,
            email = EncryptString("ssabae@test.com")
        )
    )

    @Test
    fun insert() {
        // given
        val account = this.accounts[0]

        // when
        val actual = this.sut.insert(account)

        // then
        assertThat(account).isSameAs(account)
    }

    @Test
    fun encryptDecrypt() {
        // given
        val account = this.accounts[0]
        this.sut.insert(account)

        // when
        val actual = this.sut.findById(account.id!!)

        // then
        assertThat(actual.get().email.value).isEqualTo("test@test.com")
    }

    @Test
    fun softDelete() {
        // given
        val account = this.sut.insert(this.accounts[0])

        // when
        this.sut.delete(account)

        // then
        val load = this.sut.findById(account.id!!)
        assertThat(load).isPresent()
        assertThat(load.get().state).isEqualTo(AccountState.DELETED)

        val exclude = this.sut.findByIdExcludeDeleted(account.id!!)
        assertThat(exclude).isEmpty()

    }

}