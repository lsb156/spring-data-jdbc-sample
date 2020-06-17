package com.ssabae.springdatajdbc.sample.support

import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class SimpleEncryptor : Encryptor {

    private val transformation = "AES/ECB/PKCS5Padding"
    private val algorithm = "AES"
    private val keyBytes: ByteArray = "thisisa128bitkey".toByteArray(StandardCharsets.UTF_8)


    override fun encrypt(value: String?): ByteArray? {
        if (value == null) return null
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            val secretKey: SecretKey = SecretKeySpec(keyBytes, algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        } catch (ex: Exception) {
            throw RuntimeException("Encrypt value is failed.", ex)
        }
    }

    override fun decrypt(encrypted: ByteArray?): String? {
        if (encrypted == null) return null
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            val secretKey: SecretKey = SecretKeySpec(keyBytes, algorithm)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decrypted = cipher.doFinal(encrypted)
            return String(decrypted, StandardCharsets.UTF_8)
        } catch (ex: Exception) {
            throw RuntimeException("Decrypt email is failed.", ex)
        }
    }
}