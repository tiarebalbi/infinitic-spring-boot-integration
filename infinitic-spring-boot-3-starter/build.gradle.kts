import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("com.github.ben-manes.versions") version "0.47.0"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    `java-library`
    `maven-publish`
    `signing`
}

group = "com.tiarebalbi.infinitic"
version = "1.0.0"
extra["infiniticVersion"] = "0.11.6"

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false

val jar: Jar by tasks
jar.enabled = true

java {
    sourceCompatibility = JavaVersion.VERSION_17
    withSourcesJar()

    manifest {
        attributes["Automatic-Module-Name"] = "com.tiarebalbi.infinitic.spring-boot-3-starter"
        attributes["Build-Timestamp"] = project.version
        attributes["Build-Version"] = project.version
        attributes["Implementation-Version"] = project.extra["infiniticVersion"]
        attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
        attributes["Build-Jdk"] =
            "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")})"
        attributes["Build-OS"] =
            "${System.getProperty("os.name")} (${System.getProperty("or.arch")} ${System.getProperty("or.version")})"
    }
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

publishing {
    publications {
        repositories {
            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.findProperty("ossrhUsername") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("ossrhPassword") as String? ?: System.getenv("TOKEN")
                }
            }
        }

        register<MavenPublication>("mavenJava") {
            setVersion(project.version)
            from(components["java"])
            pom {
                name = "Infinitic Spring Boot 3 Starter"
                description =
                    "This repository provides an auto-configuration integration with Spring Boot for the Infinitic library, which is designed to orchestrate services distributed on multiple servers, built on top of Apache Pulsar. With Infinitic, you can easily manage complex scenarios, ensuring that failures don't disrupt your workflows."
                url = "https://github.com/tiarebalbi/infinitic-spring-boot-integration"
                packaging = "jar"

                signing {
                    sign(publishing.publications["mavenJava"])
                }

                licenses {
                    license {
                        name = "Apache License 2.0"
                        url = "https://github.com/tiarebalbi/infinitic-spring-boot-integration/blob/main/LICENSE"
                    }
                }

                developers {
                    developer {
                        id = "tiarebalbi"
                        name = "Tiare Balbi Bonamini"
                        email = "me@tiarebalbi.com"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/tiarebalbi/infinitic-spring-boot-integration.git"
                    developerConnection = "scm:git@github.com:tiarebalbi/infinitic-spring-boot-integration.git"
                    url = "https://github.com/tiarebalbi/infinitic-spring-boot-integration.git"
                }

                versionMapping {
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }
    }
}
