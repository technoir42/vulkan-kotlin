plugins {
    id("io.github.technoir42.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    buildFeatures {
        cinterop = true
    }
}
