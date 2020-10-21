import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.diffplug.spotless") version "5.6.1"
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}

buildscript {

    repositories {
        mavenCentral()
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.1")
        classpath("gradle.plugin.me.tadej:versioning:0.2.0")
    }
}



subprojects {
    apply{ plugin("com.diffplug.spotless")}
    configure<com.diffplug.gradle.spotless.SpotlessExtension>  {
        kotlin {
            target("**/*.kt")
            ktlint("0.39.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.allWarningsAsErrors = true
    }
}