import java.io.FileInputStream
import java.util.Properties


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
    compileSdk = rootProject.extra.get("compileSdkVersion") as Int

    defaultConfig {
        applicationId = "com.android.bloomshows"
        minSdk = rootProject.extra.get("minSdkVersion") as Int
        targetSdk = rootProject.extra.get("targetSdkVersion") as Int
        versionCode = rootProject.extra.get("versionCode") as Int
        versionName = rootProject.extra.get("versionName") as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "TMDB_API_KEY", "${getProperty("local.properties", "tmdb_api_key")}")

    }

    buildTypes {

        debug {
            isDebuggable = true
        }

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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra.get("kotlinCompilerExtensionVersion") as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //defaults
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    val coreKtxVersion = rootProject.extra.get("ktx-core-version")
    val runtimeKtxVersion = rootProject.extra.get("runtime-ktx-version")
    val activityComposeVersion = rootProject.extra.get("activity-compose-version")

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$runtimeKtxVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
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
//    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.recyclerview:recyclerview:1.3.1")

    /*--------------------------------viewModel-lifecycle-------------------------------------*/
    // ViewModel utilities for Compose
    val viewModelVersion  =   rootProject.extra.get("viewModel-version")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$viewModelVersion")

    /*--------------------------------compose-navigation-------------------------------------*/
    //compose-navigation
    val composeNavigationVersion = rootProject.extra.get("navigation-compose-version")
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")

    /*--------------------------------compose-------------------------------------*/
    //animation
    implementation("androidx.compose.animation:animation")
    //material Icons
    implementation("androidx.compose.material:material-icons-extended")

    /*--------------------------------lottie-------------------------------------*/
    //lottie-compose
    val lottieComposeVersion = rootProject.extra.get("lottie-compose-version")
    implementation("com.airbnb.android:lottie-compose:$lottieComposeVersion")

    /*--------------------------------Coil-------------------------------------*/
    val composeCoilVersion = rootProject.extra.get("coil-compose-version")
    implementation("io.coil-kt:coil-compose:$composeCoilVersion")

    /*--------------------------------core-splash-------------------------------------*/
    //core-splash-
    //implementation("androidx.core:core-splashscreen:1.0.1")


    /*--------------------------------timber-------------------------------------*/
    val timberVersion = rootProject.extra.get("timber-version")
    implementation("com.jakewharton.timber:timber:$timberVersion")

    /*--------------------------------skydoves-sandwich-------------------------------------*/
    //sandwich by (skydoves Jaewoong Eum) to standardized interfaces from the Retrofit network response
    val sandwichVersion = rootProject.extra.get("sandwich-version")
    implementation("com.github.skydoves:sandwich:$sandwichVersion")

    /*---------------------------------hilt-------------------------------------*/
    val hiltVersion = rootProject.extra.get("hilt-android-version")
    val hiltCompilerVersion = rootProject.extra.get("hilt-android-compiler-version")
    val hiltNavigationVersion = rootProject.extra.get("hilt-navigation-version",)

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltCompilerVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion")


    /*--------------------------------retrofit-------------------------------------*/
    val retrofitVersion = rootProject.extra.get("retrofit-version")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    // Retrofit with Converter
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    val moshiVersion = "1.15.0"
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    /*--------------------------------okhttp3-------------------------------------*/
    val okHttpVersion = rootProject.extra.get("okhttp3-version")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")

    /*--------------------------------room-------------------------------------*/
    //room
    val roomVersion = rootProject.extra.get("room-version")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-paging:$roomVersion")

    /* To use Kotlin annotation processing tool (kapt)
     kapt("androidx.room:room-compiler:$room_version") using ksp instead*/

    /*--------------------------------datastore-------------------------------------*/
    val dataStoreVersion = rootProject.extra.get("datastore-preferences-version")
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    /*--------------------------------firebase-------------------------------------*/
    // Import the BoM for the Firebase platform
    val firebaseBomVersion = rootProject.extra.get("firebase-bom-version")
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))

    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")

    val playServiceVersion = rootProject.extra.get("play-services-auth-version")
    implementation("com.google.android.gms:play-services-auth:$playServiceVersion")
    //    implementation("com.google.firebase:firebase-perf-ktx")
    //implementation("com.google.firebase:firebase-config-ktx")

    /*--------------------------------Pager3------------------------------------*/
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")


}

//// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}


fun getProperty(filename: String, propName: String): Any? {
    val propsFile = rootProject.file(filename)
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        if (props[propName] != null) {
            return props[propName]
        } else {
            print("No such property ${propName} in file $filename")
        }
    } else {
        print("$filename  does not exist!")
    }
    return null
}