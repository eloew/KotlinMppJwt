import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("kotlinx-serialization")
}

//group = "com.erl.KotlinMppJwt"
//version = "1.0.0"

kotlin {

    jvm("android")

    js {
        //browser()
        nodejs()
    }

    /* Device
    iosArm64("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }
     */
    /*  Emulator    */
    iosX64("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    val serialization_version = "0.20.0"
    val ktor_version = "1.3.2"
    val sqldelight_version = "1.3.0"
    val coroutines_version = "1.3.5-native-mt"

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines_version")


        implementation("io.ktor:ktor-client-core:$ktor_version")
        implementation("io.ktor:ktor-client-json:$ktor_version")
        implementation("io.ktor:ktor-client-serialization:$ktor_version")

    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

        implementation("io.ktor:ktor-client-android:$ktor_version")
        implementation("io.ktor:ktor-client-okhttp:$ktor_version")
        implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutines_version")

        implementation("io.ktor:ktor-client-core-js:$ktor_version")
        implementation("io.ktor:ktor-client-json-js:$ktor_version")
        implementation("io.ktor:ktor-client-serialization-js:$ktor_version")
        implementation("io.ktor:ktor-client-js:$ktor_version")

    }
    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines_version")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization_version")

        implementation("io.ktor:ktor-client-ios:$ktor_version")
        implementation("io.ktor:ktor-client-core-native:$ktor_version")
        implementation("io.ktor:ktor-client-json-native:$ktor_version")

        implementation("io.ktor:ktor-client-serialization-native:$ktor_version")
    }


}

val packForXcode by tasks.creating(Sync::class) {
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)

    dependsOn(framework.linkTask)

    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)

    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.erl.kotlinmppjwt"
            artifactId = "KotlinMppJwt"
            version = "1.0"

        }
    }
}
tasks.getByName("assemble").dependsOn(packForXcode)