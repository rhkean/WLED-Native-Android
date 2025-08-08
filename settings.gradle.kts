pluginManagement {
    repositories {
        exclusiveContent { // First type of filter
            forRepository { google() } // Specify the repository this applies to
            filter { // Start specifying what dependencies are *only* found in this repo
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroup("com.google.testing.platform")
            }
        }
        exclusiveContent {
            forRepository { mavenCentral() }
            filter {
                includeGroup("com.google.dagger.hilt.android")
            }
        }
        google {
            content {
                includeGroup("com.google.devtools.ksp")
            }
        }
        gradlePluginPortal {
            content {
                includeGroupAndSubgroups("org.jetbrains.kotlin")
                includeGroup("com.google.protobuf")
            }
        }
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
       exclusiveContent {
            forRepository { google() }
            filter {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
            }
        }
        google {
            content {
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
rootProject.name = "WLED-Native-Android"
include(":app")