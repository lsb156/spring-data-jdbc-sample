package com.ssabae.springdatajdbc.sample.account

import com.ssabae.springdatajdbc.sample.support.EncryptString
import org.springframework.data.annotation.Id
import java.time.Instant
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Size

class Account (
    @Id
    var id: UUID? = null,

    @NotBlank
    @Size(max = 50)
    var loginId: String,

    @NotBlank
    @Size(max = 100)
    var name: String,

    @NotNull
    var state: AccountState,

    @Valid
    val email: EncryptString,

    @NotNull
    @PastOrPresent
    val createAt: Instant = Instant.now()

) {
    fun lock() {
        this.state = AccountState.LOCK
    }

    fun delete() {
        this.state = AccountState.DELETED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Account(id=$id, loginId='$loginId', name='$name', state=$state, email=$email, createAt=$createAt)"
    }

}