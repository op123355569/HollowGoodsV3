package com.hg.hollowgoods.UI.Base;

public class Help {

//    apply plugin: 'kotlin-android'
//    apply plugin: 'kotlin-android-extensions'
//
//    android {
//        buildToolsVersion '28.0.3'
//        defaultConfig {
//        ...
//            /**** 拷贝S 1/4 ****/
//            // Enabling MultiDex Support.
//            multiDexEnabled true
//            vectorDrawables.useSupportLibrary = true
//            /**** 拷贝E 1/4 ****/
//        }
//
//        /**** 拷贝S 2/4 ****/
//        sourceSets {
//            main() {
//                jniLibs.srcDirs = ['jniLibs']
//            }
//        }
//        dataBinding {
//            enabled = true
//        }
//        compileOptions {
//            targetCompatibility JavaVersion.VERSION_1_8
//            sourceCompatibility JavaVersion.VERSION_1_8
//        }
//        // 打包APK重命名
//        applicationVariants.all { variant ->
//                variant.outputs.all { output ->
//                def outputFile = output.outputFile
//            if (outputFile != null && outputFile.name.endsWith('.apk')) {
//                def version = "V${versionName}"
//                def apkName = project.name
//                def fileName = "${apkName}-${version}-${variant.buildType.name}-${releaseTime()}.apk"
//                outputFileName = fileName
//            }
//        }
//        }
//        /**** 拷贝E 2/4 ****/
//    }
//
//    /**** 拷贝S 3/4 ****/
//    static def releaseTime() {
//        return new Date().format("yyyy.MM.dd HH.mm.ss", TimeZone.getDefault())
//    }
///**** 拷贝E 3/4 ****/
//
///**** 拷贝S 4/4 ****/
//// 对应拷入根目录的Gradle文件中
//    buildscript {
//        ext.kotlin_version = '1.3.11'
//
//        repositories {
//        ...
//            mavenCentral()
//        }
//        dependencies {
//        ...
//            classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//        }
//    }
//
//    allprojects {
//        repositories {
//        ...
//            mavenCentral()
//            mavenCentral(url: "https://dl.bintray.com/ycjiang/ycjiang")
//            maven { url "https://jitpack.io" }
//        }
//    }
//
///**** 拷贝E 4/4 ****/

}
