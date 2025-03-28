package app.compose.secretnotes.hashing

import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters
import java.security.SecureRandom
import java.util.Base64

fun generateSalt(length: Int = 16): ByteArray {
    val salt = ByteArray(length)
    SecureRandom().nextBytes(salt)
    return salt
}

fun hashPin(pin: String, salt: ByteArray): String? {
    return try {
        val params = Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
            .withSalt(salt)
            .withIterations(3) // Чем больше итераций, тем сложнее взлом
            .withMemoryAsKB(65536) // Использует 64MB RAM для повышения стойкости
            .withParallelism(2) // Использует 2 потока
            .build()

        val generator = Argon2BytesGenerator()
        generator.init(params)

        val hash = ByteArray(32) // 256 бит хэша
        generator.generateBytes(pin.toByteArray(), hash, 0, hash.size)

        Base64.getEncoder().encodeToString(hash) // Преобразуем в удобочитаемый формат
    } catch (e: Exception) {
        e.printStackTrace()
        null // В случае ошибки возвращаем null
    }
}

fun argon2(pin :String, salt :String): String?{
    val hashedPin = hashPin(pin, salt.toByteArray())

    if (hashedPin != null) {
        println("Хэш PIN-кода: $hashedPin")
        println("Соль (Base64): ${Base64.getEncoder().encodeToString(salt.toByteArray())}")
    } else {
        println("Ошибка при хэшировании PIN-кода")
    }
    return hashedPin
}