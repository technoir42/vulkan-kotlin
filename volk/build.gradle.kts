plugins {
    id("io.github.technoir42.conventions.native-library")
}

nativeLibrary {
    buildFeatures {
        cinterop = true
    }
}
