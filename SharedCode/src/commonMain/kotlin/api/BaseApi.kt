package com.erl.mpp.mobile.api


import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.takeFrom

open class BaseApi(open val endPoint: String) {


    private fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    protected fun HttpRequestBuilder.apiUrl(path: String, userId: String? = null) {

        if (userId != null) {
            header(HttpHeaders.Authorization, "Bearer $userId")
        }

       // header(HttpHeaders.CacheControl, "no-cache")
        header(HttpHeaders.Accept, ContentType.Application.Json)
        contentType(ContentType.Application.Json)
        url {
            takeFrom(endPoint)
            encodedPath = path
        }

    }
}