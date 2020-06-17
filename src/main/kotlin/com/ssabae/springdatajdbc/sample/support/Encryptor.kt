package com.ssabae.springdatajdbc.sample.support

interface Encryptor {
    fun encrypt(value: String?): ByteArray?
    fun decrypt(encrypted: ByteArray?): String?
}
