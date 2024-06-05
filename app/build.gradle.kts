plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kapt)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.vosaa.majoritytechassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vosaa.majoritytechassignment"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://restcountries.com/v3.1/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://restcountries.com/v3.1/\"")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Constraint Layout
    implementation(libs.androidx.constraintlayout)
    //Material Design
    implementation(libs.material)

    //Hilt-Dagger
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    //Retrofit2 & Gson
    implementation(libs.retrofit2.retrofit)
    implementation(libs.google.gson)
    implementation(libs.retrofit2.converter.gson)
    //OkHttp
    implementation(libs.okhttp3.logging.interceptor)

    //Material3
    implementation(libs.androidx.material3)

    //RecyclerView
    implementation(libs.androidx.recyclerview)

    // ViewModel Lifecycle
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //NAVIGATION COMPONENT
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Glide
    implementation(libs.glide)

    //Fragment
    implementation(libs.androidx.fragment.ktx)

    //Timber
    implementation(libs.timber)

}