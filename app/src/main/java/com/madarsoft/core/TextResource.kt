package com.madarsoft.core

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed class TextResource {
    abstract fun asString(context: Context): String
    @Composable
    abstract fun asString(): String

    companion object {
        fun fromText(text: String): TextResource = StringResource(text)

        fun fromResource(@StringRes key: Int): TextResource = ResourceStringResource(key)

        fun composite(separator: String, texts: List<TextResource>): TextResource =
            CompositeResource(separator, texts)
    }
}

private data class StringResource(val text: String) : TextResource() {
    override fun asString(context: Context): String {
        return text
    }

    @Composable
    override fun asString(): String {
        return text
    }
}

private data class ResourceStringResource(val key: Int) : TextResource() {

    override fun asString(context: Context): String {
        return context.getString(key)
    }

    @Composable
    override fun asString(): String {
        return stringResource(key)
    }
}

private data class CompositeResource(
    val separator: String,
    val texts: List<TextResource>
) : TextResource() {

    override fun asString(context: Context): String {
        return texts.joinToString(separator = separator) { it.asString(context) }
    }

    @Composable
    override fun asString(): String {
        val stringBuilder = StringBuilder()
        val hasMoreThanOneText = texts.size > 1
        for (index in texts.indices) {
            stringBuilder.append(texts[index].asString())
            if (index < texts.size - 1 && hasMoreThanOneText) {
                stringBuilder.append(separator)
            }
        }

        return stringBuilder.toString()
    }
}
