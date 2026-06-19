plugins {
    alias(libs.plugins.jooq)
    alias(libs.plugins.flyway)
}

dependencies {
    api(libs.bundles.logging)

    implementation(libs.spring.data.jdbc)
    implementation(libs.spring.data.redis)
    implementation(libs.uuid)
    implementation(libs.bundles.jwt)
    implementation(libs.bundles.jooq)

    compileOnly(libs.spring.web)
    runtimeOnly(libs.mysql.driver)
    runtimeOnly(libs.bundles.flyway)
    jooqCodegen(libs.jooq.meta)

    testImplementation(libs.spring.test)
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.spring.web)
    testFixturesImplementation(libs.spring.data.jdbc)
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.bundles.testcontainers)
}

tasks {
    bootJar {
        enabled = false
    }

    compileKotlin {
        dependsOn(jooqCodegen)
    }
}

sourceSets {
    main {
        java {
            srcDirs("build/generated")
        }
    }
}

jooq {
    configuration {
        generator {
            name = "org.jooq.codegen.KotlinGenerator"

            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"

                properties {
                    property {
                        key = "scripts"
                        value = "src/main/resources/db/migration/*.sql"
                    }

                    property {
                        key = "sort"
                        value = "flyway"
                    }

                    property {
                        key = "defaultNameCase"
                        value = "lower"
                    }
                }

                forcedTypes {
                    forcedType {
                        userType = "java.util.UUID"
                        converter = "com.quizit.core.global.converter.UuidConverter"
                        includeTypes = "BINARY\\(16\\)"
                        includeExpression = "(?i).*\\.(id|.*_id)$"
                    }

                    forcedType {
                        userType = "java.time.Instant"
                        converter = "com.quizit.core.global.converter.InstantConverter"
                        includeTypes = "TIMESTAMP\\(6\\)"
                        includeExpression = ".*\\..*_at"
                    }
                }
            }

            generate {
                isKotlinNotNullRecordAttributes = true
            }

            target {
                packageName = "com.quizit.core.global.jooq"
                directory = "build/generated"
            }
        }
    }
}
