package com.crimera.gptwrapper

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.*
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.crimera.gptwrapper.ui.theme.GptWrapperTheme

class MainActivity : ComponentActivity() {
    private var webview: WebView? = null
    private val userAgent = "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.86 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GptWrapperTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Webview("https://chat.openai.com/")
                }
            }
        }
    }

    override fun onBackPressed() {
        if (webview?.canGoBack()!!) {
            webview?.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    @Composable
    fun Webview(url: String) {
        val visibility = remember { mutableStateOf(true) }

        Box(Modifier.fillMaxSize()) {
            AndroidView(factory = {
                WebView(applicationContext).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,

                        LinearLayout.LayoutParams.MATCH_PARENT
                    )

                    settings.userAgentString = userAgent
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.builtInZoomControls = false
                    settings.displayZoomControls = false
                    addJavascriptInterface(JsInterface(getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager), "Bridge")

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            visibility.value = true
                            loadScript("fix_copy.js")
                            println("Start")
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            visibility.value = false
                            CookieManager.getInstance().flush()
                            println("Done")
                        }

                        private fun WebView.loadScript(filename: String) {
                            evaluateJavascript(
                                assets.open(filename).bufferedReader().use { it.readText() }, null
                            )
                        }
                    }

                    loadUrl(url)
                    webview = this
                }
            }, update = {
                webview = it
            })

            if (visibility.value) {
                Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
