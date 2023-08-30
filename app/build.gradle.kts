import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.android.bloomshows"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.bloomshows"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //defaults
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    //implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // ViewModel utilities for Compose
    val lifecycle_version = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")

    //compose-navigation
    implementation("androidx.navigation:navigation-compose:2.5.0")

    //animation
    implementation("androidx.compose.animation:animation:1.4.0")

    //lottie-compose
    implementation("com.airbnb.android:lottie-compose:5.2.0")
    //material Icons
    implementation("androidx.compose.material:material-icons-extended")

//    //core-splash-
//    implementation("androidx.core:core-splashscreen:1.0.1")

    //timber for better logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    //sandwich by (skydoves Jaewoong Eum) to standardized interfaces from the Retrofit network response
    implementation ("com.github.skydoves:sandwich:1.3.8")

    //compose Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit with Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //http interceptors
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    //room
    val room_version = "2.5.2"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    /* To use Kotlin annotation processing tool (kapt)
     kapt("androidx.room:room-compiler:$room_version") using ksp instead*/

    //datastore for preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")



}

//// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}
