package com.erl.mpp.mobile.api

import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.http.ContentType


fun getClient(token: String): HttpClient {
    val client = HttpClient {
        defaultRequest {
            header("Authorization", "Bearer " + token)
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

    }
    return client
}
