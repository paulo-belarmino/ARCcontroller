plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.5.31"
}

android {
    namespace = "com.example.arccontroller"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.arccontroller"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    //implementation("androidx.work:work-runtime:2.7.0-alpha05")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    //implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    //implementation("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1"){

    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5" )// Original Mqtt dependency
    //implementation("androidx.work:work-runtime:2.7.1") // WorkManager
    //implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.0.0") // Replace v4 & do not use 1.1.0
    //implementation("com.github.QingHeYang:MqttFix:1.0.3-fix3")
    implementation("com.github.hannesa2:paho.mqtt.android:3.3.5")
}



