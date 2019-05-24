# HollowGoodsV3
## 1.导入
### LastVersion：
[![](https://jitpack.io/v/op123355569/HollowGoodsV3.svg)](https://jitpack.io/#op123355569/HollowGoodsV3)

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
  
```
dependencies {
	implementation 'com.github.op123355569:HollowGoodsV3:LastVersion'
}
```

## 2.拷贝Gradle
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    buildToolsVersion '28.0.3'
    defaultConfig {
        ...
        /**** 拷贝S 1/4 ****/
        // Enabling MultiDex Support.
        multiDexEnabled true
        vectorDrawables.useSupportLibrary = true
        /**** 拷贝E 1/4 ****/
    }

    /**** 拷贝S 2/4 ****/
    sourceSets {
        main() {
            jniLibs.srcDirs = ['jniLibs']
        }
    }
    dataBinding {
        enabled = true
    }
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }
    // 打包APK重命名
    applicationVariants.all { variant ->
        variant.outputs.all {
            def version = "V${versionName}"
            def apkName = project.name
            def fileName = "${apkName}-${version}-${variant.buildType.name}-${releaseTime()}.apk"
            outputFileName = fileName
        }
    }
    /**** 拷贝E 2/4 ****/
}

/**** 拷贝S 3/4 ****/
static def releaseTime() {
    return new Date().format("yyyy.MM.dd HH.mm.ss", TimeZone.getDefault())
}
/**** 拷贝E 3/4 ****/

/**** 拷贝S 4/4 ****/
// 对应拷入根目录的Gradle文件中
buildscript {
    ext.kotlin_version = '1.3.21'

    repositories {
        ...
        mavenCentral()
    }
    dependencies {
        ...
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

allprojects {
    repositories {
        ...
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}

/**** 拷贝E 4/4 ****/
```

## 3.修改Style文件
```
<style name="AppTheme" parent="HGBaseAppTheme">
	<!-- Customize your theme here. -->
	<item name="colorPrimary">@color/colorPrimary</item>
	<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
	<item name="colorAccent">@color/colorAccent</item>
</style>
```

## 4.重写Application
继承BaseApplication，参照ExampleApplication。

## 5.添加权限
```
    <!-- **** 本APP相关权限 **** -->
    <uses-permission
        android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
        tools:ignore="ProtectedPermissions" /><!-- 在SDCard中创建与删除文件权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /><!-- 在SDCard中写入文件权限 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /><!-- 在SDCard中读取文件权限 -->
    <uses-permission android:name="android.permission.INTERNET" /><!-- 上网权限 -->
   
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /><!-- 查看网络状态权限 -->
   
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /><!-- BugView所需权限 -->
    <uses-permission android:name="android.permission.GET_TASKS" /><!-- 获取最近任务权限 -->
    <uses-permission android:name="android.permission.NFC" /><!-- NFC -->
    <uses-permission android:name="android.permission.VIBRATE" /><!-- 震动 -->
    <uses-permission android:name="android.permission.CAMERA" /><!-- 相机 -->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /> <!-- 安装APK许可 -->

    <!-- **** 二维码扫描 **** -->
    <uses-permission android:name="android.permission.VIBRATE" /><!-- 许可振动 -->
    <uses-permission android:name="android.permission.FLASHLIGHT" /><!-- 打开闪光灯 -->
    <uses-permission android:name="android.permission.CAMERA" /><!-- 许可使用照相机 -->
    <uses-feature android:name="android.hardware.camera" /><!-- 许可调用相机硬件 -->
    <uses-feature android:name="android.hardware.camera.autofocus" /><!-- 许可调用相机自动对焦 -->

    <!-- **** 录音模块 **** -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />

    <supports-screens
        android:anyDensity="true"
        android:largeScreens="true"
        android:normalScreens="true"
        android:smallScreens="true" /><!-- 适应所有屏幕 -->
```
