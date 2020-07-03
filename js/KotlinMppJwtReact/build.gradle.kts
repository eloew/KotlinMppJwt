plugins {
    id("org.jetbrains.kotlin.js") version "1.3.72"
}

group = "com.erl"
version = "1.0"

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    mavenCentral()
    jcenter()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-js"))

    //React, React DOM + Wrappers
    implementation("org.jetbrains:kotlin-react:16.13.0-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-react-dom:16.13.0-pre.94-kotlin-1.3.70")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))


    //Kotlin Styled
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.94-kotlin-1.3.70")
    implementation(npm("styled-components"))
    implementation(npm("inline-style-prefixer"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")

    implementation("KotlinMppJwt:SharedCode-js:unspecified")

    api(npm("text-encoding"))
    api(npm("abort-controller"))

    implementation(npm("css-loader"))
    implementation(npm("style-loader"))

    implementation(npm("react-bootstrap"))

    implementation(npm("jquery"))
    implementation(npm("bootstrap"))
    implementation(npm("font-awesome"))

    implementation(npm("jsonwebtoken"))
    implementation(npm("bufferutil")) //needed for jsonwebtoken

}

kotlin.target.browser { }
kotlin.target.nodejs { }
kotlin.target.useCommonJs()
