package com.itd.app.core.serializers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Instant

object InstantSpaceOffsetSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("InstantSpaceOffset", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant {
        val raw = decoder.decodeString().trim()
        return if (" " in raw) {
            // 2026-01-26 23:34:18.011525+03
            // 1) пробел -> T
            // 2) +03 -> +03:00 (и -03 тоже)
            val normalized = normalizeToIsoOffsetDateTime(raw)
            // normalized будет типа: 2026-01-26T23:34:18.011525+03:00

            val (ldtPart, offsetPart) = splitDateTimeAndOffset(normalized)

            val ldt =
                LocalDateTime.parse(ldtPart)       // ISO LocalDateTime: 2026-01-26T23:34:18.011525
            val offset = UtcOffset.parse(offsetPart)     // +03:00
            ldt.toInstant(offset)
        } else {
            Instant.parse(raw)
        }
    }

    override fun serialize(encoder: Encoder, value: Instant) {
        // Если хочешь всегда писать именно с +03
        val offset = UtcOffset.parse("+03:00")
        val ldt = value.toLocalDateTime(TimeZone.currentSystemDefault())

        val s = buildString {
            append(ldt.date)                 // 2026-01-26
            append(' ')
            append(ldt.time)                 // 23:34:18.011525 (kotlinx-datetime печатает дробь если есть)
            append(offset.toString().removeSuffix(":00")) // "+03:00" -> "+03" (как у тебя)
        }

        encoder.encodeString(s)
    }

    private fun normalizeToIsoOffsetDateTime(s: String): String {
        // заменяем пробел на T
        var x = s.replace(' ', 'T')

        // Если смещение в конце вида +03 или -05, дописываем :00
        // Также можно поддержать +0300 -> +03:00, если вдруг встретится
        x = x.replace(Regex("([+-])(\\d{2})$"), "$1$2:00")
        x = x.replace(Regex("([+-])(\\d{2})(\\d{2})$"), "$1$2:$3")

        return x
    }

    private fun splitDateTimeAndOffset(iso: String): Pair<String, String> {
        // Ищем последний + или - после времени (не тот минус, что в дате)
        val plus = iso.lastIndexOf('+')
        val minus = iso.lastIndexOf('-')
        val idx = maxOf(plus, minus)

        if (idx <= "yyyy-MM-ddT".length) {
            // На всякий: если вдруг пришло без смещения
            return iso to "+00:00"
        }

        val ldt = iso.substring(0, idx)
        val off = iso.substring(idx)
        return ldt to off
    }
}