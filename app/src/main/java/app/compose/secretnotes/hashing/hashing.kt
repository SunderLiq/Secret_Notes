package app.compose.secretnotes.hashing

import androidx.compose.ui.text.toLowerCase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale
import kotlin.jvm.Throws

object hashing {
    //SHA-256

    @Throws (NoSuchAlgorithmException::class)
    fun getHash(inByte: ByteArray, type: String): String {
        val digestedBytes = MessageDigest.getInstance(type).digest(inByte)
        return with(StringBuilder()) {
            digestedBytes.forEach { b -> append(String.format("%02X", b)) }
            toString().toLowerCase(Locale.ROOT)
        }
    }
}