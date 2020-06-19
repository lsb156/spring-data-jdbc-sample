package com.ssabae.springdatajdbc.sample.config

import com.ssabae.springdatajdbc.sample.label.Label.Companion.LabelAfterSaveEventListener
import com.ssabae.springdatajdbc.sample.repo.Repo.Companion.RepoBeforeSaveCallback
import com.ssabae.springdatajdbc.sample.support.EncryptString
import com.ssabae.springdatajdbc.sample.support.Encryptor
import com.ssabae.springdatajdbc.sample.support.SimpleEncryptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.relational.core.conversion.MutableAggregateChange
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback
import java.lang.IllegalStateException
import java.sql.Clob
import java.sql.SQLException
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Configuration
class JdbcConfig : AbstractJdbcConfiguration() {

    companion object {
        @WritingConverter
        class EncryptStringWritingConverter(private val encryptor: Encryptor) : Converter<EncryptString, ByteArray> {
            override fun convert(source: EncryptString): ByteArray? {
                return this.encryptor.encrypt(source.value)
            }
        }

        @ReadingConverter
        class EncryptStringReadingConverter(private val encryptor: Encryptor) : Converter<ByteArray, EncryptString> {
            override fun convert(source: ByteArray): EncryptString? {
                val value = this.encryptor.decrypt(source) ?: return null
                return EncryptString(value)
            }
        }

        class ClobConverter : Converter<Clob, String> {
            override fun convert(source: Clob): String? {
                try {
                    return if (Math.toIntExact(source.length()) == 0) ""
                        else source.getSubString(1, Math.toIntExact(source.length()))
                } catch (e: SQLException) {
                    throw IllegalStateException("Failed to convert CLOB to String.", e)
                }
            }
        }
    }

    @Bean
    override fun jdbcCustomConversions(): JdbcCustomConversions {
        val encryptor = SimpleEncryptor()
        return JdbcCustomConversions(
            listOf(
                EncryptStringWritingConverter(encryptor),
                EncryptStringReadingConverter(encryptor),
                ClobConverter()
            )
        )
    }

    @Bean
    @Order
    fun validateBeforeSave(validator: Validator): BeforeSaveCallback<*> {
        return BeforeSaveCallback { aggregate: Any, mutableAggregateChange: MutableAggregateChange<*> ->
            val violations = validator.validate(aggregate)
            if (violations.isEmpty().not()) {
                throw ConstraintViolationException(violations)
            }
            aggregate
        }
    }

    @Bean
    fun labelAfterSaveEventListener(): LabelAfterSaveEventListener {
        return LabelAfterSaveEventListener()
    }

    @Bean
    fun repoBeforeSaveCallback(): RepoBeforeSaveCallback {
        return RepoBeforeSaveCallback()
    }
}