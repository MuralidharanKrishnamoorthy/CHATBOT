// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

    dependencies {


        // Add the Maven coordinates and latest version of the plugin
        classpath ("com.google.gms:google-services:4.4.0")
    }
}


plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id ("com.google.gms.google-services") version "4.4.0" apply false
}

