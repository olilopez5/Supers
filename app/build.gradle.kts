plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.supers"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.supers"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // genera clases por cada layout y hace los findviewbyid automaticamente
    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    // retofit2 2.11.0 -> picasso 2.8
    // implementation 'com.squareup.retrofit2:retrofit:(insert latest version)'
    // implementation 'com.squareup.retrofit2:converter-gson:(latest.version')
    //implementation 'com.squareup.picasso:picasso:(insert latest version)'

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.picasso)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}