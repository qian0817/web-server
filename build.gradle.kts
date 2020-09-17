import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.0"
}
group = "com.qianlei"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-reflect")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.11.+")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.9")

    implementation("io.github.microutils", "kotlin-logging", "1.11.0")
    implementation("org.slf4j", "slf4j-log4j12", "1.7.30")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")
}
tasks.withType<Test> {
    useJUnitPlatform()
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}