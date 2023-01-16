package com.crimera.gptwrapper

import android.content.ClipData
import android.content.ClipboardManager
import android.webkit.JavascriptInterface

class JsInterface(private val clipboardManager: ClipboardManager) {
    @JavascriptInterface
    fun copyToClipboard(text: String) {
        val clipData = ClipData.newPlainText("Gpt", text)
        clipboardManager.setPrimaryClip(clipData)
    }
}