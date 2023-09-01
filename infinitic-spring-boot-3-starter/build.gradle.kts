import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    `java-library`
    `maven-publish`
}

group = "com.tiarebalbi.infinitic"
version = "0.0.1-SNAPSHOT"
extra["infiniticVersion"] = "0.11.6"

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false

val jar: Jar by tasks
jar.enabled = true

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.infinitic:infinitic-client:${project.extra["infiniticVersion"]}")
    implementation("io.infinitic:infinitic-worker:${project.extra["infiniticVersion"]}")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.infinitic:infinitic-inMemory:${project.extra["infiniticVersion"]}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
