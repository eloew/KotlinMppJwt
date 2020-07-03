package com.erl.kotlinmppjwt.util

import android.content.Context
import android.util.Log
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*
import org.kotlin.mpp.mobile.ConstantsShared


class Token() {
    val TAG: String = Token::class.toString()

    fun getToken(email: String): String {
        val key = Keys.hmacShaKeyFor(ConstantsShared.secret.toByteArray())

        val jws = Jwts.builder()
            .setExpiration(getExpireationDate())
            .setIssuer(ConstantsShared.jwtIssuer)
            .setAudience(ConstantsShared.jwtAudience)
            .signWith(key, SignatureAlgorithm.HS256)
            .setSubject(ConstantsShared.jwtSubject)
            .claim("email", email)
             .compact();
        return jws
    }

    private fun getExpireationDate(): Date {
        val cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 60)
        return cal.time
    }

}