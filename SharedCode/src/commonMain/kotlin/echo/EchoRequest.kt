package com.erl.mpp.mobile.echo


import kotlinx.serialization.Serializable

@Serializable
class EchoRequest  (
    var message: String
)