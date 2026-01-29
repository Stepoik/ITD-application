package com.itd.app.core.utils

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@Immutable
@Serializable
data class SerializableTextFieldValue(
    @Serializable(with = TextFieldValueSerializer::class)
    val textFieldValue: TextFieldValue
) {
    companion object {
        val EMPTY get() = TextFieldValue().serializable()
    }
}

val SerializableTextFieldValue.text get() = textFieldValue.text

object TextFieldValueSerializer : KSerializer<TextFieldValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TextFieldValue") {
        element<String>("text")
        element("selection", descriptor = TextRangeSerializer.descriptor)
        element(
            "composition",
            descriptor = TextRangeSerializer.descriptor,
            isOptional = true
        )
    }

    override fun serialize(encoder: Encoder, value: TextFieldValue) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.text)
            encodeSerializableElement(descriptor, 1, TextRangeSerializer, value.selection)
            if (value.composition != null) {
                encodeSerializableElement(descriptor, 2, TextRangeSerializer, value.composition!!)
            }
        }
    }

    override fun deserialize(decoder: Decoder): TextFieldValue {
        var text = ""
        var selection = TextRange(0)
        var composition: TextRange? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> text = decodeStringElement(descriptor, 0)
                    1 -> selection = decodeSerializableElement(descriptor, 1, TextRangeSerializer)
                    2 -> composition = decodeSerializableElement(descriptor, 2, TextRangeSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index $index")
                }
            }
        }

        return TextFieldValue(text, selection, composition)
    }
}

object TextRangeSerializer : KSerializer<TextRange> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TextRange") {
        element<Int>("start")
        element<Int>("end")
    }

    override fun serialize(encoder: Encoder, value: TextRange) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.start)
            encodeIntElement(descriptor, 1, value.end)
        }
    }

    override fun deserialize(decoder: Decoder): TextRange {
        var start = 0
        var end = 0

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val i = decodeElementIndex(descriptor)) {
                    0 -> start = decodeIntElement(descriptor, 0)
                    1 -> end = decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index $i")
                }
            }
        }

        return TextRange(start, end)
    }
}

fun TextFieldValue.serializable() = SerializableTextFieldValue(this)