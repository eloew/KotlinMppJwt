# KotlinMppJwtReact 



### To run React app


Open KotlinMppJwt project up in Android studio<br/>
Change ip address in [endpoint constant](../../SharedCode/src/commonMain/kotlin/ConstantsShared.kt)<br/>
To publish [SharedCode](../../SharedCode) to local maven repository, Run Gradle Tasks
* SharedCode:build
* SharedCode:publishToMavenLocal

OpenOpen [KotlinMppJwtKtor](../../backend/KotlinMppJwtKtor) project in Intellij Idea.<br/>
Run ktor project.<br/><br/><br/>


Open KotlinMppJwtReact project in Intellij Idea.<br/>
Run ktor project.<br/>
In the terminal run command: <br/>
* ./gradlew run -t
* or ./gradlew run --continuous


Screen should look like: <br/>
![React Picture](https://github.com/eloew/KotlinMppJwt/blob/master/app/src/main/res/drawable/react.png?raw=true)


