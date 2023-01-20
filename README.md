# Whisper

[![Build](https://github.com/yasanglass/whisper/workflows/Build/badge.svg)](https://github.com/yasanglass/whisper/actions/workflows/build.yml)
[![](https://jitpack.io/v/yasanglass/whisper.svg)](https://jitpack.io/#yasanglass/whisper)
[![](https://jitpack.io/v/yasanglass/whisper/month.svg)](https://jitpack.io/#yasanglass/whisper)

Simple library to handle one-time events on Android. It contains only [one Kotlin](https://github.com/yasanglass/whisper/blob/main/whisper/src/main/kotlin/glass/yasan/whisper/WhisperViewModel.kt) file.

## Download

1. Add it in your root build.gradle at the end of repositories

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2. Add the dependency

```groovy
dependencies {
	implementation 'com.github.yasanglass:whisper:Tag'
}
```