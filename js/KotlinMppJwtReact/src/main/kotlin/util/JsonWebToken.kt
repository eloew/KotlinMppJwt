package util

import org.kotlin.mpp.mobile.ConstantsShared
import react.RClass

@JsModule("jsonwebtoken/sign")
@JsName("sign")
external fun jwsSign(payload: dynamic, secretOrPrivateKey: dynamic, options: dynamic): String
//external fun jwsSign(payload: dynamic, secretOrPrivateKey: dynamic, options: dynamic, callback: dynamic): String


fun getToken(email: String): String {
    val tkn = jwsSign(
        kotlinext.js.js { this.email = email }, ConstantsShared.secret,  //
        kotlinext.js.js {
            algorithm = "HS256"
            issuer = ConstantsShared.jwtIssuer
            audience = ConstantsShared.jwtAudience
            subject = ConstantsShared.jwtSubject
            expiresIn = 90000
        }
    )
    return tkn
}
