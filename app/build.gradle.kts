plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.fwhyn.app.noos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fwhyn.app.noos"
        minSdk = 24
        targetSdk = 35
        versionCode = 1000000
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndkVersion = "27.2.12479018"

        externalNativeBuild {
            cmake {
                cppFlags += listOf("-std=c++17")
            }
        }
    }

//    externalNativeBuild {
//        cmake {
//            path = file("src/main/cpp/CMakeLists.txt")
//        }
//    }

    flavorDimensions += "default"
    productFlavors {
        create("Real") {
            dimension = "default"
        }

        create("Fake") {
            dimension = "default"
        }
    }

    buildTypes {
        release {
            // TODO isMinifyEnabled = false for temporary until proguard is fixed before public release
            isMinifyEnabled = false

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = false

            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += listOf("META-INF/LICENSE-notice.md", "META-INF/LICENSE.md")
        }
    }
}

dependencies {
    // Main Dependencies
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.fwhyn.lib.baze)

    //// Retrofit
    implementation(libs.bundles.retrofit2)
    implementation(libs.bundles.okhttp)

    //// Compose
    implementation(libs.bundles.androidx.compose)
    implementation(platform(libs.androidx.compose.bom))

    /// Dagger Hilt
    implementation(libs.bundles.dagger.hilt)
    kapt(libs.bundles.dagger.hilt.compiler)
    annotationProcessor(libs.bundles.dagger.hilt.compiler)

    // Testing Dependencies
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)

    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    testImplementation(libs.bundles.org.mockito.test)
    androidTestImplementation(libs.bundles.org.mockito.test)

    //// Compose Testing
    androidTestImplementation(libs.bundles.androidx.compose.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}