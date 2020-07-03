package com.erl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.erl.mpp.mobile.echo.EchoRequest
import com.erl.mpp.mobile.echo.EchoResponse
import com.erl.util.Constants
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import kotlinx.coroutines.*
import io.ktor.client.features.logging.*
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import org.kotlin.mpp.mobile.ConstantsShared

//import org.kotlin.mpp.mobile.ConstantsShared

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DefaultHeaders) {
        header("X-Developer", "Eric Loew")
        header("X-DeveloperEmail", "ericloew@hotmail.com")
        //header("Access-Control-Allow-Origin","*")
    }

    install(CORS)  {
        //method(HttpMethod.Options)
        methods.addAll(listOf(HttpMethod.Get, HttpMethod.Post, HttpMethod.Options))
        //header(HttpHeaders.XForwardedProto)
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
        //host("10.0.0.21:8082")
        allowCredentials = true
        allowNonSimpleContentTypes = true
        header("Authorization")
    }

    val verifier = JWT
            //.require(Algorithm.none())
            .require(Algorithm.HMAC256(ConstantsShared.secret))
            .withIssuer(ConstantsShared.jwtIssuer )
            .withAudience(ConstantsShared.jwtAudience )
            .withSubject(ConstantsShared.jwtSubject)
            .build()

    install(Authentication) {
        jwt("jwt") {
            realm = ConstantsShared.jwtRealm
            verifier(verifier)
            validate { credential ->
                if (credential.payload.audience.contains(ConstantsShared.jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
    }
    runBlocking {
        // Sample for making a HTTP Client request
        /*
        val message = client.post<JsonSampleClass> {
            url("http://127.0.0.1:8080/path/to/endpoint")
            contentType(ContentType.Application.Json)
            body = JsonSampleClass(hello = "world")
        }
        */
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        authenticate("jwt") {
            post("/echo") {
                val request = call.receive<EchoRequest>()
                val response = EchoResponse("From Ktor: ${request.message}")
                call.respond(response)
            }
        }

    }
}


data class JsonSampleClass(val hello: String)

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}


