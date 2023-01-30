import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"

}

group = "com.github.theapache64"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Retrofit : A type-safe HTTP client for Android and Java.
    implementation("com.squareup.retrofit2:retrofit:2.7.2")

    // Kotlinx Coroutines Core : Coroutines support libraries for Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // JSON In Java : JSON is a light-weight, language independent, data interchange format.See http://www.JSON.org/The
	// files in this package implement JSON encoders/decoders in Java.It also includes
	// the capability to convert between JSON and XML, HTTPheaders, Cookies, and CDL.This
	// is a reference implementation. There is a large number of JSON packagesin Java.
	// Perhaps someday the Java community will standardize on one. Untilthen, choose carefully.
    implementation("org.json:json:20220924")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}