# Halia

[![](https://jitpack.io/v/cn.quickits/Halia.svg)](https://jitpack.io/#cn.quickits/Halia)

Halia is a LoadingDialog binder with RxJava

## Download

- Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- Add the dependency

```
dependencies {
    implementation 'cn.quickits:Halia:x.y.z'
}
```

## Usage

### Init

```Kotlin
class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Halia.init(this)
    }
}
```

### Bind loading dialog with RxJava

```kotlin
API.loadingByNetwork()
    .loading() // bind loading dialog
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({
        Toast.makeText(this, it, Toast.LENGTH_SHORT.show()
    }, {
        it.printStackTrace()
    })
```

## Licence

Apache License Version 2.0

Copyright (c) 2019-present, Quickits.CN
