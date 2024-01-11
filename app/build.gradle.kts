import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
}

android {
    namespace = "gerber.apress.com"
    compileSdk = 34

    defaultConfig {
        applicationId = "gerber.apress.com"
        minSdk = 16
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    //noinspection GradleCompatible
    compile("com.android.support:appcompat-v7:23.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}