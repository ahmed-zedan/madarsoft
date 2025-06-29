plugins {
    alias(libs.plugins.madarsoft.android.application)
    alias(libs.plugins.madarsoft.hilt)
    alias(libs.plugins.madarsoft.android.room)
}

android {
    namespace = "com.madarsoft"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.madarsoft"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
