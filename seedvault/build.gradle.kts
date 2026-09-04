/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    alias(libs.plugins.android.library)
    signing
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.solanamobile.seedvault"
    compileSdk = 36

    defaultConfig {
        // Note: 'minSdk 17' ensures that the library can be used with apps targeting much older
        // Android versions. It's only useful on devices which expose the Seed Vault API.
        minSdk = 17

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates(
        "com.michaeltchuang.solanamobile",
        "seedvault-wallet-sdk",
        System.getenv("VERSION_TAG") ?: "0.0.1",
    )

    pom {
        name.set("Seed Vault - Wallet SDK")
        description.set("UI layer for AlgoKit Wallet SDK")
        url.set("https://github.com/michaeltchuangllc/seed-vault-sdk")

        licenses {
            license {
                name.set("GPL3.0 License")
                url.set("https://opensource.org/licenses/GPL-3.0")
            }
        }

        developers {
            developer {
                name.set("Michael T Chuang")
                email.set("contact@michaeltchuang.com")
                organization.set("Michael T Chuang LLC")
                organizationUrl.set("https://michaeltchuang.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/solana-mobile/seed-vault-sdk.git")
            developerConnection.set("scm:git:ssh://github.com/solana-mobile/seed-vault-sdk.git")
            url.set("https://github.com/solana-mobile/seed-vault-sdk/tree/main")
        }
    }
}

// Define JavaDoc build task for all variants
android.libraryVariants.configureEach {
    val variantName = name.replaceFirstChar { it.uppercase() }
    tasks.register<Javadoc>("generate${variantName}Javadoc") {
        description = "Generates Javadoc for $name."
        source = javaCompileProvider.get().source
        classpath = project.files(android.bootClasspath.joinToString(File.pathSeparator))
        classpath += files(javaCompileProvider.get().classpath)
        (options as StandardJavadocDocletOptions).links("https://docs.oracle.com/en/java/javase/11/docs/api/")
        (options as StandardJavadocDocletOptions).links("https://d.android.com/reference/")
        exclude("**/BuildConfig.java")
        exclude("**/R.java")
    }
}

// Build JavaDoc when making a release build
tasks.configureEach {
    if (name == "assembleRelease") {
        dependsOn("generateReleaseJavadoc")
    }
}

dependencies {
    compileOnly(libs.androidx.annotation)
}
