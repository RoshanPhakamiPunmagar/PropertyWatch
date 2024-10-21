plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-kapt")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
}

android {
    namespace = "au.cqu.edu.propertywatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "au.cqu.edu.propertywatch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    kapt ("androidx.room:room-compiler:2.4.2")
    implementation ("androidx.room:room-ktx:2.4.2")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation ("com.squareup.retrofit2:retrofit:2.5.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation ("com.google.android.gms:play-services-location:17.0.0")
    implementation ("com.google.android.gms:play-services-maps:18.0.0")



}