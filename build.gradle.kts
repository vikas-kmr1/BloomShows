buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
    //TODO move to toml  to declare dependency versions in a central place
    extra.apply {

        set("ktx-core-version","1.10.1")
        set("runtime-ktx-version","2.6.1")
        set("activity-compose-version","1.7.2")

        /*------------sdk and tools-------------*/
        set("compileSdkVersion", 34)
        set("minSdkVersion", 26)
        set("targetSdkVersion", 34)

        /*------------application-version-------------*/
        set("versionCode", 1)
        set("versionName", "1.0.0")

        /*------------kotlin-------------*/
        set("kotlinCompilerExtensionVersion", "1.4.3")

        /*------------viewModel--------*/
        set("viewModel-version", "2.6.1")

        /*--------------------------------compose-navigation-------------------------------------*/
        set("navigation-compose-version", "2.7.1")

        /*--------------------------------lottie-------------------------------------*/
        set("lottie-compose-version", "5.2.0")

        /*--------------------------------Coil-------------------------------------*/
        set("coil-compose-version", "2.4.0")

        /*--------------------------------timber-------------------------------------*/
        set("timber-version", "5.0.1")

        /*--------------------------------skydoves-sandwich-------------------------------------*/
        set("sandwich-version", "1.3.8")

        /*--------------------------------retrofit-------------------------------------*/
        set("retrofit-version", "2.9.0")

        /*--------------------------------okhttp3-------------------------------------*/
        set("okhttp3-version", "4.11.0")

        /*--------------------------------room-------------------------------------*/
        set("room-version", "2.5.2")

        /*--------------------------------datastore-------------------------------------*/
        set("datastore-preferences-version", "1.0.0")


        /*--------------------------------firebase-------------------------------------*/
        set("firebase-bom-version", "32.2.2")
        set("play-services-auth-version","20.6.0")

        /*---------------------------------hilt-------------------------------------*/
        set("hilt-android-version","2.47")
        set("hilt-android-compiler-version","2.44")
        set("hilt-navigation-version","1.0.0")

    }

}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
