package com.erl.mpp.mobile.echo


import com.erl.mpp.mobile.api.BaseApi
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class EchoApi(override var endPoint: String, var client: HttpClient): BaseApi(endPoint) {
    suspend fun getEchoMessage(request: EchoRequest): EchoResponse = client.post {
        apiUrl("/echo")
        method = HttpMethod.Post
        contentType(ContentType.Application.Json)
        body = request
    }
}