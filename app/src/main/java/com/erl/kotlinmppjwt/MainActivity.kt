package com.erl.kotlinmppjwt

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.erl.kotlinmppjwt.util.Token
import com.erl.mpp.mobile.createApplicationScreenMessage
import com.erl.mpp.mobile.echo.EchoPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), EchoPresenter.EchoView {

    val TAG = "MainActivity"
    lateinit var echoPresent: EchoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_text).text = createApplicationScreenMessage()

        echoPresent = EchoPresenter(lifecycleScope.coroutineContext, this)

        var token = Token().getToken("KotlinMppJwt@hotmail.com")
        echoPresent.getEchoMessage(token, "Ktor Rocks!")

        //token = "Invalid Token"
        //echoPresent.getEchoMessage(token, "Will not work")
    }

    private fun showMessage(message: String) {
        toast(message)
        Log.d(TAG, message)
    }

    //<editor-fold desc="EchoPresenter.EchoView">
    override fun onEcho(message: String) {
        showMessage(message)
        echoMessage.text = message
    }

    override fun showError(error: Throwable) {
        error.message?.let {
            showMessage(it)
        }
    }

    override fun showError(message: String) {
        showMessage(message)
    }
    //</editor-fold>

}