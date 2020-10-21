import java.util.Properties
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("me.tadej.versioning")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId("no.lekrot.wordlist")
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode(versioning.versionCode())
        versionName(versioning.versionName())
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    val hasReleaseSigningConfig = rootProject.file("keys/release/signing.properties").isFile

    signingConfigs {
        if (hasReleaseSigningConfig) {
            val propsFile = rootProject.file("keys/release/signing.properties")
            val props = Properties().apply {
                load(propsFile.reader())
            }
            create("release") {
                storeFile = rootProject.file(props.getProperty("storeFile"))
                storePassword = props.getProperty("storePassword")
                keyPassword = props.getProperty("keyPassword")
                keyAlias = props.getProperty("keyAlias")
            }
        }
    }

    buildTypes {
        if (hasReleaseSigningConfig) {
            getByName("release") {
                minifyEnabled(false)
                proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    lintOptions {
        textReport = true
        lintConfig = rootProject.file("lint.xml")
        isCheckDependencies = true
        isCheckTestSources = true
        isExplainIssues = true
        isCheckReleaseBuilds = true
        isAbortOnError = true
    }

    compileOptions {
        sourceCompatibility(1.8)
        targetCompatibility(1.8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}



dependencies {
    //Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    //androidx core
    implementation("androidx.core:core-ktx:1.3.2")

    //Material Design
    implementation("com.google.android.material:material:1.3.0-alpha03")

    //Constraint layoyt / MotionLayout
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")

    // Datastore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha02")

    //ViewModel and lifecycle
    val lifecycleVersion = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Navigation
    val navigationVersion = "2.3.1"
    implementation("androidx.navigation:navigation-common-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-runtime-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    //room
    val roomVersion = "2.2.5"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Coroutines
    val coroutinesVersion = "1.3.9"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    //Data binding for viewpager and recyclerview
    implementation("me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:4.0.0")
    implementation("me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-viewpager2:4.0.0")

    //Shortcut maintained livedata extensions
    implementation("com.github.c-b-h:lives:1.0.0")

    //Shortcut made data-binding adapters
    implementation("com.github.shortcut:data-binding:1.0.0")

    //koin
    val koinVersion = "2.2.0-rc-2"
    implementation("org.koin:koin-android:$koinVersion")
    implementation("org.koin:koin-androidx-viewmodel:$koinVersion")
    implementation("org.koin:koin-core:$koinVersion")

    //Unit Testing
    testImplementation("junit:junit:4.13")

    //UI testing
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
