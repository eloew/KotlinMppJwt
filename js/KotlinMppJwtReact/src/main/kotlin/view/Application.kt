package view

import com.erl.mpp.mobile.createApplicationScreenMessage
import com.erl.mpp.mobile.echo.EchoPresenter
import kotlinext.js.js
import kotlinx.coroutines.CoroutineScope
import kotlinx.css.*
import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.visible
import org.kotlin.mpp.mobile.ConstantsShared
import org.w3c.dom.css.CSSMarginRule
import react.*
import react.dom.*
import styled.*
import util.getToken
import util.jwsSign


interface ApplicationProps : RProps {
    var coroutineScope: CoroutineScope
}

class ApplicationState : RState {
    var messageSharedCode = ""
    var messageKtor: String = ""
    var errorMessage = ""
}

class ApplicationComponent : RComponent<ApplicationProps, ApplicationState>(), EchoPresenter.EchoView {

    lateinit var echoPresenter: EchoPresenter

    init {
        state = ApplicationState()
    }

    object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val header by css {
            display = Display.inlineBlock
            backgroundColor = Color.green
        }
        val error by css {
            color = Color.red

        }
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun componentDidMount() {
        require("bootstrap/dist/css/bootstrap.min.css")
        require("bootstrap/dist/js/bootstrap.bundle.min.js")

        require("jsonwebtoken/sign.js")
        require("jws/lib/sign-stream.js")

        setState {
            messageSharedCode = createApplicationScreenMessage()
        }

        echoPresenter = EchoPresenter(props.coroutineScope.coroutineContext, this)
        val token = getToken("KotlinMppJwt@hotmail.com")
        echoPresenter.getEchoMessage(token, "Ktor Rocks with React!")
    }

    override fun RBuilder.render() {
        //Header
        styledDiv {
            css { +ComponentStyles.header }
            label {
                h2 { +"Kotlin Mpp Jwt" }
            }
        }
        br {  }
        styledDiv {
            css { +ComponentStyles.error }
            label { +state.errorMessage }
        }
        br {  }
        label { +state.messageSharedCode }
        br {  }
        label { +state.messageKtor}
    }

    //<editor-fold desc="EchoPresenter.EchoView">
    override fun onEcho(message: String) {
        setState{
            this.messageKtor = message
        }
    }

    override fun showError(message: String) {
        setState {
            errorMessage = message
        }
    }

    override fun showError(error: Throwable) {
        error.message?.let {
            setState {
                errorMessage = it
            }
        }
    }
    //</editor-fold>

}

external fun require(name: String): dynamic


