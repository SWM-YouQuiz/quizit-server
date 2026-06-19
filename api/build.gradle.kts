import com.epages.restdocs.apispec.gradle.OpenApi3Task

plugins {
    alias(libs.plugins.restdocs.api.spec)
}

dependencies {
    implementation(project(":core"))
    implementation(libs.spring.web)
    implementation(libs.spring.security)
    implementation(libs.spring.validation)
    implementation(libs.spring.actuator)
    implementation(libs.kotlin.logging)
    implementation(libs.bundles.jackson)

    testImplementation(testFixtures(project(":core")))

    testFixturesImplementation(testFixtures(project(":core")))
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.spring.security)
    testFixturesImplementation(libs.bundles.spring.restdocs)
}

tasks {
    bootJar {
        enabled = true
    }

    jar {
        enabled = false
    }

    bootRun {
        systemProperty("user.timezone", "UTC")
    }

    test {
        finalizedBy(withType<OpenApi3Task>())
    }

    withType<OpenApi3Task> {
        doFirst {
            file(openapi3.outputDirectory).mkdirs()
        }
    }
}

openapi3 {
    title = "Quizit API"
    description = "Quizit API Documentation"
    version = project.version.toString()
    format = "yaml"
    outputFileNamePrefix = "api"
    outputDirectory = "src/main/resources/static/docs"
    setServer("/api/v1")
}
