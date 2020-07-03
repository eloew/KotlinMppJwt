package com.erl.mpp.mobile.echo


import com.erl.mpp.mobile.api.getClient
import com.erl.mpp.mobile.util.Constants
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kotlin.mpp.mobile.ConstantsShared
import org.kotlin.mpp.mobile.presentation.BaseView
import org.kotlin.mpp.mobile.presentation.CoroutinePresenter

import kotlin.coroutines.CoroutineContext

class EchoPresenter(uiContext: CoroutineContext, private val view: EchoView) : CoroutinePresenter(uiContext, view) {

    fun getEchoMessage(token: String, message: String){
        val request = EchoRequest(message)
        val api = EchoApi(ConstantsShared.endpoint, getClient(token))
        var response: EchoResponse? = null
        launch {
            try {
                response = api.getEchoMessage(request)
            } catch (e: Exception) {
                val message: String = if (e.message != null) e.message!! else "Unknown Error"
                view.showError(message)
            }

        }.invokeOnCompletion {
            response?.let {
                view.onEcho(it.message)
            }
        }
    }

    interface EchoView: BaseView {
        fun onEcho(message: String)

    }
}
