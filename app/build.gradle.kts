plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val applicationVersionCode = 8
val applicationVersionName = "0.0065"

android {
    namespace = "com.github.mrzahmadi.lightnote"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.mrzahmadi.lightnote"
        minSdk = 30
        targetSdk = 35
        versionCode = applicationVersionCode
        versionName = applicationVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".debug"
            setProperty("archivesBaseName", "LightNote-$applicationVersionName")
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            setProperty("archivesBaseName", "LightNote-$applicationVersionName")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    //AndroidX
    val lifecycleVersion = "2.8.7"
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.browser:browser:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycleVersion")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:$lifecycleVersion")

    //Junit and General Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Compose
    val composeVersion = "1.7.5"
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:$composeVersion")
//    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
//    implementation("androidx.compose.foundation:foundation:$composeVersion")
//    implementation("androidx.compose.animation:animation:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    //Hilt
    val hiltVersion = "2.52"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
//    implementation("androidx.room:room-paging:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    //Navigation Component
    val navigationVersion = "2.8.4"
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")

    //Paging
//    val pagingVersion = "3.2.1"
//    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
//    implementation("androidx.paging:paging-compose:$pagingVersion")
//    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")

    //Retrofit
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //Okhttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Glide
//    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Gson
    implementation("com.google.code.gson:gson:2.11.0")

    //Firebase
     val firebaseAnalyticsVersion = "22.1.2"
    implementation("com.google.firebase:firebase-crashlytics:19.2.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-analytics:$firebaseAnalyticsVersion")
    implementation("com.google.firebase:firebase-analytics-ktx:$firebaseAnalyticsVersion")
}