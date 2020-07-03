package view

import kotlinx.css.*
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.onChange
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.StyleSheet
import styled.css
import styled.styledButton
import styled.styledInput
import kotlin.Float


external interface LoginProps : RProps {
    var onContinueButtonPressed: (String) -> Unit
}

class LoginState : RState {
    var email: String = ""
}

class Login: RComponent<LoginProps, LoginState>() {
    init {
        state = LoginState()
    }

    object LoginStyles : StyleSheet("LoginStyles", isStatic = true) {
        val email by ApplicationComponent.ComponentStyles.css {
            //placeholder { +"Your organization username..."   }
        }
    }

    override fun RBuilder.render() {
        div("App-header") {
            br {  }
            h2 { +"Please input your username below" }
            label { +"Your username: " }
            styledInput {
                css { +LoginStyles.email }
                attrs {
                    type = InputType.email
                    //value = state.email
                    onChangeFunction = ::onInputChange
                    placeholder = "Your organization username..."
                }
            }
            styledButton {
                css {
                    display = Display.block
                }
                attrs {
                    onClickFunction = {
                        props.onContinueButtonPressed(state.email)
                    }
                }
                +"Continue"
            }
        }
    }

    fun onInputChange(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value
        setState {
            email = value
        }
    }
}

fun RBuilder.login(handler: LoginProps.() -> Unit): ReactElement {
    return child(Login::class) {
        this.attrs(handler)
    }
}
